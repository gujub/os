#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

// Define the dimensions of the matrices
#define M 3  // Rows of matrix A
#define N 3 // Columns of matrix A and Rows of matrix B
#define P 3  // Columns of matrix B

// Matrices
int A[M][N];
int B[N][P];
int C[M][P];

// Structure to pass data to threads
typedef struct {
    int row;
    int col;
} thread_data_t;

// Thread function to perform matrix multiplication for a specific element
void* multiply(void* arg) {
    thread_data_t* data = (thread_data_t*) arg;
    int row = data->row;
    int col = data->col;
    C[row][col] = 0;  // Initialize the result

    // Perform the matrix multiplication for a single element
    for (int k = 0; k < N; k++) {
        C[row][col] += A[row][k] * B[k][col];
    }

    pthread_exit(NULL);
}

int main() {
    pthread_t threads[M * P];         // One thread for each element of the result matrix
    thread_data_t thread_data[M * P]; // To store thread parameters (row and column)

    // Initialize matrices A and B
    printf("Matrix A:\n");
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < N; j++) {
            A[i][j] = rand() % 10;  // Random numbers between 0 and 9
            printf("%d ", A[i][j]);
        }
        printf("\n");
    }

    printf("\nMatrix B:\n");
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < P; j++) {
            B[i][j] = rand() % 10;  // Random numbers between 0 and 9
            printf("%d ", B[i][j]);
        }
        printf("\n");
    }

    // Create threads for each element in result matrix C
    int thread_idx = 0;
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < P; j++) {
            thread_data[thread_idx].row = i;
            thread_data[thread_idx].col = j;
            pthread_create(&threads[thread_idx], NULL, multiply, (void*)&thread_data[thread_idx]);
            thread_idx++;
        }
    }

    // Join all threads
    for (int i = 0; i < M * P; i++) {
        pthread_join(threads[i], NULL);
    }

    // Display the result matrix C
    printf("\nResultant Matrix C (A * B):\n");
    for (int i = 0; i < M; i++) {
        for (int j = 0; j < P; j++) {
            printf("%d ", C[i][j]);
        }
        printf("\n");
    }

    return 0;
}