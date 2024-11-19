#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

void bubble_sort(int arr[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

void insertion_sort(int arr[], int n) {
    for (int i = 1; i < n; i++) {
        int key = arr[i];
        int j = i - 1;
        while (j >= 0 && arr[j] > key) {
            arr[j + 1] = arr[j];
            j--;
        }
        arr[j + 1] = key;
    }
}

int main() {
    int n;
    printf("Enter the number of integers to sort: ");
    scanf("%d", &n);

    int *arr = (int *)malloc(n * sizeof(int));
    if (arr == NULL) {
        perror("Memory allocation failed");
        exit(1);
    }

    printf("Enter the integers:\n");
    for (int i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }

    pid_t pid = fork();

    if (pid < 0) {
        perror("Fork failed");
        free(arr);
        exit(1);
    }

    if (pid == 0) {
        // Child process
        printf("Child process (PID: %d): Sorting using insertion sort...\n", getpid());
        insertion_sort(arr, n);
        printf("Child process: Sorted integers: ");
        for (int i = 0; i < n; i++) {
            printf("%d ", arr[i]);
        }
        printf("\n");

        // Simulate orphan state
        sleep(5);  // Delay child exit to let parent exit first
        printf("Child process exiting...\n");
        free(arr);
        exit(0);
    } else {
        // Parent process
        printf("Parent process (PID: %d): Sorting using bubble sort...\n", getpid());
        bubble_sort(arr, n);
        printf("Parent process: Sorted integers: ");
        for (int i = 0; i < n; i++) {
            printf("%d ", arr[i]);
        }
        printf("\n");

        // Simulate zombie state
        sleep(10);  // Delay calling wait() to create a zombie state
        int status;
        wait(&status);  // Wait for child process
        printf("Parent process: Child process finished with status %d\n", WEXITSTATUS(status));

        printf("Parent process exiting...\n");
        free(arr);
    }

    return 0;
}
