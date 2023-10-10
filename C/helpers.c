#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <fcntl.h>
#include "helpers.h"

//job template
job create_job(char *cmd, int jid){
    job j;
    j.jid = jid;
    j.cmd = get_copy(cmd);
    j.status = "waiting";
    j.estatus = -1;
    sprintf(j.fnout, "%d.out", j.jid);
    sprintf(j.fnerr, "%d.err", j.jid);
    return j;
}

//list from jobs, displaying each jID, command, and status.
void list_jobs(job *jobs, int n, char *mode){
    int i;
    if (jobs != NULL && n != 0){
        if (strcmp(mode, "showjobs") == 0){
            for (i = 0; i < n; ++i)
            {
                if (strcmp(jobs[i].status, "complete") != 0)
                    printf("Job ID: %d\nCommand: %s\nStatus: %s\n\n", jobs[i].jid, jobs[i].cmd, jobs[i].status);
            }
        }
    }
}

queue *queue_init(int n){
    queue *q = malloc(sizeof(queue));
    q->size = n;
    q->buffer = malloc(sizeof(job *) * n);
    q->start = 0;
    q->end = 0;
    q->count = 0;
    return q;
}

int queue_insert(queue *q, job *jp){
    if ((q == NULL) || (q->count == q->size))
        return -1;
    q->buffer[q->end % q->size] = jp;
    q->end = (q->end + 1) % q->size;
    ++q->count;

    return q->count;
}

job *queue_delete(queue *q){
    if ((q == NULL) || (q->count == 0))
        return (job *)-1;

    job *j = q->buffer[q->start];
    q->start = (q->start + 1) % q->size;
    --q->count;
    return j;
}

void queue_destroy(queue *q){
    free(q->buffer);
    free(q);
}

//get line. If eof, return -1. Else return chars read.
int get_line(char *s, int n){
    int i, c;
    for (i = 0; i < n - 1 && (c = getchar()) != '\n'; ++i)
    {
        if (c == EOF)
            return -1;
        s[i] = c;
    }
    s[i] = '\0';
    return i;
}

// space checker
int is_space(char c){
    return (c == ' ' || c == '\t' || c == '\n');
}

// iterate until finds start
char *start_finder(char *s){
    int i;
    i = 0;
    while (is_space(s[i]))
        ++i;
    return s + i;
}

//copy string s
char *get_copy(char *s){
    int i, c;
    char *copy;

    i = -1;
    copy = malloc(sizeof(char) * strlen(s));
    while ((c = s[++i]) != '\0')
        copy[i] = c;
    copy[i] = '\0';

    return copy;
}

//copy strring s until end or newline
char *get_copy_until_newline(char *s){
    int i, c;
    char *copy;

    i = -1;
    copy = malloc(sizeof(char) * strlen(s));
    while ((c = s[++i]) != '\0' && c != '\n')
        copy[i] = c;
    copy[i] = '\0';

    return copy;
}

//return string from array.
char **get_args(char *line){
    char *copy = malloc(sizeof(char) * (strlen(line) + 1));
    strcpy(copy, line);

    char *arg;
    char **args = malloc(sizeof(char *));
    int i = 0;
    while ((arg = strtok(copy, " \t")) != NULL){
        args[i] = malloc(sizeof(char) * (strlen(arg) + 1));
        strcpy(args[i], arg);
        args = realloc(args, sizeof(char *) * (++i + 1));
        copy = NULL;
    }
    args[i] = NULL;
    return args;
}

// Open log and return file descriptor.
int open_log(char *fn){
    int fd;
    if ((fd = open(fn, O_CREAT | O_APPEND | O_WRONLY, 0755)) == -1)
    {
        fprintf(stderr, "Error: failed to open \"%s\"\n", fn);
        perror("open");
        exit(EXIT_FAILURE);
    }
    return fd;
}
