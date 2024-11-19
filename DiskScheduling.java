import java.util.*;

public class DiskScheduling{
  
  public static void sstf(int[] requests, int head) {
    List<Integer> sequence = new ArrayList<>();
    boolean[] processed = new boolean[requests.length];
    int totalMovement = 0;
    int currentHead = head;

    System.out.println("\nSSTF Disk Scheduling:");
    System.out.println("Movement Table:");
    System.out.printf("%-15s %-15s %-15s\n", "Head Position", "Request", "Movement");

    for (int i = 0; i < requests.length; i++) {
      int minDistance = Integer.MAX_VALUE;
      int closestRequest = -1;
      for (int j = 0; j < requests.length; j++) {
        if (!processed[j] && Math.abs(requests[j] - currentHead) < minDistance) {
          minDistance = Math.abs(requests[j] - currentHead);
          closestRequest = j;
        }
      }

      if (closestRequest != -1) {
        sequence.add(requests[closestRequest]);
        System.out.printf("%-15d %-15d %-15d\n", currentHead, requests[closestRequest],
            Math.abs(requests[closestRequest] - currentHead));
        totalMovement += Math.abs(requests[closestRequest] - currentHead);
        currentHead = requests[closestRequest];
        processed[closestRequest] = true;
      }

    }

    System.out.println("Total head movement: " + totalMovement);

  }

  public static void scan(int[] requests, int head, int diskSize) {
    List<Integer> sequence = new ArrayList<>();
    Arrays.sort(requests);
    int totalMovement = 0;
    int currentHead = head;

    System.out.println("\nSCAN Disk Scheduling:");
    System.out.println("Movement Table:");
    System.out.printf("%-15s %-15s %-15s\n", "Head Position", "Request", "Movement");

    // Separate requests into two parts: below and above the current head
    List<Integer> below = new ArrayList<>();
    List<Integer> above = new ArrayList<>();
    for (int request : requests) {
      if (request < currentHead) {
        below.add(request);
      } else {
        above.add(request);
      }
    }

    // Move in the current direction (upwards)
    for (int request : above) {
      sequence.add(request);
    }

    // Include the edge of the disk (if required) and switch direction
    sequence.add(diskSize - 1);
    for (int i = below.size() - 1; i >= 0; i--) {
      sequence.add(below.get(i));
    }

    // Calculate total movement and print the movement table
    for (int request : sequence) {
      System.out.printf("%-15d %-15d %-15d\n", currentHead, request, Math.abs(request - currentHead));
      totalMovement += Math.abs(request - currentHead);
      currentHead = request;
    }

    System.out.println("Total head movement: " + totalMovement);
  }

  public static void cLook(int[] requests, int head) {
    List<Integer> sequence = new ArrayList<>();
    Arrays.sort(requests);
    int totalMovement = 0;
    int currentHead = head;

    System.out.println("\nC-Look Disk Scheduling:");
    System.out.println("Movement Table:");
    System.out.printf("%-15s %-15s %-15s\n", "Head Position", "Request", "Movement");

    // Moving towards the highest request
    for (int request : requests) {
      if (request >= currentHead) {
        sequence.add(request);
      }
    }

    // Move to the lowest request
    for (int request : requests) {
      if (request < currentHead) {
        sequence.add(request);
      }
    }

    for (int request : sequence) {
      System.out.printf("%-15d %-15d %-15d\n", currentHead, request, Math.abs(request - currentHead));
      totalMovement += Math.abs(request - currentHead);
      currentHead = request;
    }

    System.out.println("Total head movement: " + totalMovement);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    // Input disk size
    System.out.println("Enter the disk size (e.g., 200): ");
    int diskSize = scanner.nextInt();

    // Input the number of disk requests
    System.out.println("Enter the number of disk requests: ");
    int n = scanner.nextInt();

    // Input the disk requests
    int[] requests = new int[n];
    System.out.println("Enter the disk requests (space-separated): ");
    for (int i = 0; i < n; i++) {
      requests[i] = scanner.nextInt();
    }

    // Input the initial head position
    System.out.println("Enter the initial head position: ");
    int head = scanner.nextInt();

    // Running the algorithms
    sstf(requests, head);
    scan(requests, head, diskSize);
    cLook(requests, head);

    scanner.close();
  }
  // 200
  // 5
  // 98 183 37 122 14
  // 50
}