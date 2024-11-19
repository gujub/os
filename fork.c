#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/wait.h>

// Function to perform bubble sort
void bubbleSort(int arr[], int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - 1 - i; j++) {
            if (arr[j] > arr[j + 1]) {
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}

// Function to perform selection sort
void selectionSort(int arr[], int n) {
    for (int i = 0; i < n - 1; i++) {
        int minIndex = i;
        for (int j = i + 1; j < n; j++) {
            if (arr[j] < arr[minIndex]) {
                minIndex = j;
            }
        }
        int temp = arr[minIndex];
        arr[minIndex] = arr[i];
        arr[i] = temp;
    }
}

// Function to display the array
void displayArray(int arr[], int n) {
    for (int i = 0; i < n; i++) {
        printf("%d ", arr[i]);
    }
    printf("\n");
}

int main() {
    int n;

    // Accept the number of elements to be sorted
    printf("Enter the number of integers to be sorted: ");
    scanf("%d", &n);

    int arr[n];
    printf("Enter the integers: ");
    for (int i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }

    // Forking a child process
    pid_t pid = fork();

    if (pid < 0) {
        // Fork failed
        perror("Fork failed");
        exit(1);
    } else if (pid == 0) {
        // Child process
        printf("Child process (PID: %d) - Performing selection sort\n", getpid());
        selectionSort(arr, n);
        printf("Child process sorted array: ");
        displayArray(arr, n);
        
        // Child process exits and becomes a zombie if parent is sleeping
        printf("Child process exiting\n");
        exit(0); // Child process exits here (becomes zombie for a while)
    } else {
        // Parent process
        printf("Parent process (PID: %d) - Performing bubble sort\n", getpid());
        bubbleSort(arr, n);
        printf("Parent process sorted array: ");
        displayArray(arr, n);

        // Parent waits for the child process to complete
        wait(NULL); // This removes the zombie state

        // Simulating orphan process (parent sleeping, child exits)
        sleep(5);
        printf("Parent process exiting\n");
    }

    return 0;
}
