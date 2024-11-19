import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BankersAlgorithm {
    private int numProcesses;
    private int numResources;
    private int[] available;
    private int[][] maximum;   // matrix
    private int[][] allocation; //matrix
    private int[][] need;       //matrix
    private boolean[] finished;
    private String[] processes;

    // Constructor with corrected signature
    public BankersAlgorithm(String[] processes, int[] available, int[][] maximum, int[][] allocation) {
        this.processes = processes;
        this.numProcesses = processes.length;
        this.numResources = available.length;
        this.available = available.clone();
        this.maximum = deepCopy(maximum);
        this.allocation = deepCopy(allocation);

        // Calculate need matrix
        this.need = new int[numProcesses][numResources];
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                need[i][j] = maximum[i][j] - allocation[i][j];
            }
        }

        this.finished = new boolean[numProcesses];
    }

    public boolean isSafeState() {
        int[] work = available.clone();
        boolean[] finish = new boolean[numProcesses];
        List<String> safeSequence = new ArrayList<>();

        // Find a safe sequence
        while (true) {
            // Find an unfinished process whose needs can be satisfied
            boolean found = false;
            for (int p = 0; p < numProcesses; p++) {
                if (!finish[p] && canAllocate(p, work)) {
                    // Add the resources allocated to process p back to work
                    for (int j = 0; j < numResources; j++) {
                        work[j] += allocation[p][j];
                    }
                    safeSequence.add(processes[p]);
                    finish[p] = true;
                    found = true;
                    break;
                }
            }

            if (!found) {
                break;
            }
        }

        // Check if all processes are finished
        boolean isSafe = true;
        for (boolean f : finish) {
            isSafe &= f;
        }

        if (isSafe) {
            System.out.println("System is in a safe state!");
            System.out.println("Safe sequence: " + String.join(" -> ", safeSequence));
        } else {
            System.out.println("System is NOT in a safe state!");
        }

        return isSafe;
    }

    private boolean canAllocate(int process, int[] work) {
        for (int i = 0; i < numResources; i++) {
            if (need[process][i] > work[i]) {
                return false;
            }
        }
        return true;
    }

    public void printState() {
        System.out.println("\nCurrent State:");
        System.out.println("Processes: " + Arrays.toString(processes));
        System.out.println("Available resources: " + Arrays.toString(available));

        System.out.println("\nMaximum resources:");
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Process " + processes[i] + ": " + Arrays.toString(maximum[i]));
        }

        System.out.println("\nAllocated resources:");
        for (int i = 0; i < numProcesses; i++) {
            System.out.println("Process " + processes[i] + ": " + Arrays.toString(allocation[i]));
        }
    }

    private int[][] deepCopy(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) {
            copy[i] = matrix[i].clone();
        }
        return copy;
    }

    public static void main(String[] args) {
        // Example data
        String[] processes = {"P0", "P1", "P2", "P3", "P4"};
        int[] available = {0, 0, 0};  // Available resources

        int[][] maximum = {
                {7, 5, 3},  // P0
                {3, 2, 2},  // P1
                {9, 0, 2},  // P2
                {2, 2, 2},  // P3
                {4, 3, 3}   // P4
        };

        int[][] allocation = {
                {0, 1, 0},  // P0
                {2, 0, 0},  // P1
                {3, 0, 2},  // P2
                {2, 1, 1},  // P3
                {0, 0, 2}   // P4
        };

        BankersAlgorithm banker = new BankersAlgorithm(processes, available, maximum, allocation);
        banker.printState();
        banker.isSafeState();
    }
}
