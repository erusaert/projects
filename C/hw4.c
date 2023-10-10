/*
To compile: gcc -Wall -lpthread -o hw4 hw4.c helpers.c
To run: ./hw4 P_value
*/

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <signal.h>
#include <unistd.h>
#include <sys/wait.h>
#include <pthread.h>
#include "helpers.h"
#include "hw4.h"

#define LINELEN 1000
#define JOBSLEN 1000
#define JOBQLEN 100

int P_value;        // P_value
int CURRENT;      // Currrent working jobs
job JOBS[JOBSLEN]; // array for showjobs
queue *JOBQ;       // pointer for job queue

int main(int argc, char **argv){
    char *fnerr;   // .err
    pthread_t tid;

    if (argc != 2){
        printf("Usage: %s P_value\n", argv[0]);
        exit(EXIT_SUCCESS);
    }

    P_value = atoi(argv[1]);

    printf("P_value: %d\n", P_value);

    // .err to file
    fnerr = malloc(sizeof(char) * (strlen(argv[0]) + 5));
    sprintf(fnerr, "%s.err", argv[0]);
    dup2(open_log(fnerr), STDERR_FILENO);

    JOBQ = queue_init(JOBQLEN);

    //threads to create and handle input
    pthread_create(&tid, NULL, complete_jobs, NULL);
    handle_input();

    exit(EXIT_SUCCESS);
}

void handle_input(){
    int i;              // job counter
    char line[LINELEN]; // buffer
    char *command;      // command

    printf("Enter command or Ctrl+D/Ctrl+C to exit: ");

    i = 0;
    while (printf("> ") && get_line(line, LINELEN) != -1){
        if ((command = strtok(get_copy(line), " \t\n")) != NULL){
            if (strcmp(command, "submit") == 0){

                // if (i > JOBSLEN)
                //     printf("Job array greater than full\n");
                // if (i <= JOBSLEN)
                //     printf("this shouldnt work but\n");
                if (i >= JOBSLEN)
                    printf("Job array full\n");

                else if (JOBQ->count >= JOBQ->size)
                    printf("Job queue full; try again after more jobs complete\n");
                else
                {
                    command = start_finder(strstr(line, "submit") + 6);
                    JOBS[i] = create_job(command, i);
                    queue_insert(JOBQ, JOBS + i);
                    printf("Job %d added to the queue\n", i++);
                }
            }
            else if (strcmp(command, "showjobs") == 0)
                list_jobs(JOBS, i, command);
        }
    }
    kill(0, SIGINT);
}

//Checks for jobs
void *complete_jobs(void *arg){
    job *jp; //job pointer

    CURRENT = 0;
    for (;;){
        if (JOBQ->count > 0 && CURRENT < P_value){
            jp = queue_delete(JOBQ);
            pthread_create(&jp->tid, NULL, complete_job, jp);
            pthread_detach(jp->tid);
        }
        sleep(1); // make sure jobs arent starting while current jobs still going
    }
    return NULL;
}

// fork/exec to complete job
void *complete_job(void *arg){
    char **args; // array to do
    pid_t pid;
    job *jp;     // job pointer

    jp = (job *)arg;

    jp->status = "working";
    ++CURRENT;
    
    pid = fork();
    //child
    if (pid == 0){
        dup2(open_log(jp->fnout), STDOUT_FILENO); // .out
        dup2(open_log(jp->fnerr), STDERR_FILENO); // .err
        args = get_args(jp->cmd);
        execvp(args[0], args);
        fprintf(stderr, "Error: command execution failed for \"%s\"\n", args[0]);
        perror("execvp");
        exit(EXIT_FAILURE);
    }
    //parent
    else if (pid > 0){
        waitpid(pid, &jp->estatus, WUNTRACED);
        jp->status = "complete";

        if (!WIFEXITED(jp->estatus))
            fprintf(stderr, "Child process %d did not terminate normally!\n", pid);
    }
    else{
        fprintf(stderr, "Error: process fork failed\n");
        perror("fork");
        exit(EXIT_FAILURE);
    }

    --CURRENT;
    return NULL;
}


