#include <pthread.h>

//job struct
typedef struct job{
    int jid;        // job ID
    pthread_t tid;  // thread ID
    char *cmd;      // job command
    char *status;   // job status
    int estatus;    // job exit status
    char fnout[10]; // .out filename
    char fnerr[10]; // .err filename
} job;

//queue struct
typedef struct queue{
    int size;     
    job **buffer; // buffer
    int start;  
    int end;      
    int count;    // number of jobs in the queue
} queue;

//functions
job create_job(char *cmd, int jid);
void list_jobs(job *jobs, int n, char *mode);
queue *queue_init(int n);
int queue_insert(queue *q, job *jp);
job *queue_delete(queue *q);
void queue_destroy(queue *q);
int get_line(char *s, int n);
int is_space(char c);
char *start_finder(char *s);
char *get_copy(char *s);
char *get_copy_until_newline(char *s);
char **get_args(char *line);
int open_log(char *fn);
