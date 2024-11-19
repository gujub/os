#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

int main() {
    // Forking a child process
    pid_t pid = fork();

    if (pid < 0) {
        // Fork failed
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        // Child process (This will become a zombie if parent doesn't call wait())
        printf("Child process (PID: %d) running...\n", getpid());
        sleep(2); // Simulate some work in the child
        printf("Child process (PID: %d) exiting...\n", getpid());
        exit(0);  // Child exits, potentially becoming a zombie if parent doesn't wait
    } else {
        // Parent process

        // 1. Demonstrating Zombie State
        printf("Parent process (PID: %d) sleeping, child will become a zombie...\n", getpid());
        sleep(5); // Parent intentionally waits, during which child becomes a zombie

        // Now parent waits for the child, removing the zombie state
        wait(NULL);  // This call will remove the zombie state of the child
        printf("Parent process (PID: %d) waited for child. Zombie process removed.\n", getpid());

        // 2. Demonstrating Orphan State
        printf("Parent process (PID: %d) exiting. Child will become an orphan if still running...\n", getpid());
        sleep(2); // Giving time for child to become orphan before parent exits
        exit(0);  // Parent exits, child becomes orphan if still running
    }

    return 0;
}
