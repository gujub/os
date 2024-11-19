import java.util.*;

public class PageReplacementAlgorithms {
    public static int fifo(int[] pages, int capacity) {
        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> set = new HashSet<>();
        int pageFaults = 0;
        System.out.println("\nFIFO Page Replacement Process:");
        System.out.println("Frames   | Page | Fault");
        for (int page : pages) {
            if (!set.contains(page)) {
                if (queue.size() == capacity) {
                    int removedPage = queue.poll();
                    set.remove(removedPage);
                }
                queue.add(page);
                set.add(page);
                pageFaults++;
                printFrames(queue, page, true);
            } else {
                printFrames(queue, page, false);
            }
        }
        return pageFaults;
    }

    public static int lru(int[] pages, int capacity) {
        List<Integer> list = new ArrayList<>();
        int pageFaults = 0;
        System.out.println("\nLRU Page Replacement Process:");
        System.out.println("Frames   | Page | Fault");

        for (int page : pages) {
            if (!list.contains(page)) {
                if (list.size() == capacity) {
                    list.remove(0);  // Remove the least recently used page
                }
                list.add(page);
                pageFaults++;
                printFrames(list, page, true);  // Print frame after adding
            } else {
                // Page is already in the list
                list.remove((Integer) page);  // Remove the page and add it again
                list.add(page);
                printFrames(list, page, false);  // Print frame when page is hit
            }
        }
        return pageFaults;
    }

    public static int optimal(int[] pages, int capacity) {
        Set<Integer> set = new HashSet<>();
        int pageFaults = 0;
        System.out.println("\nOptimal Page Replacement Process:");
        System.out.println("Frames   | Page | Fault");
        for (int i = 0; i < pages.length; i++) {
            if (!set.contains(pages[i])) {
                if (set.size() == capacity) {
                    int farthest = i + 1, indexToReplace = -1;
                    for (int page : set) {
                        int j;
                        for (j = i + 1; j < pages.length; j++) {
                            if (pages[j] == page)
                                break;
                        }
                        if (j > farthest) {
                            farthest = j;
                            indexToReplace = page;
                        }
                    }
                    set.remove(indexToReplace != -1 ? indexToReplace : set.iterator().next());
                }
                set.add(pages[i]);
                pageFaults++;
                printFrames(set, pages[i], true);
            } else {
                printFrames(set, pages[i], false);
            }
        }
        return pageFaults;
    }

    public static void printFrames(Collection<Integer> frames, int page, boolean isFault) {
        System.out.printf("%-10s| %-4d | %-5s\n", frames, page, isFault ? "Yes" : "No");
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of frames (capacity):");
        int capacity = scanner.nextInt();
        System.out.println("Enter the number of pages in the reference string:");
        int n = scanner.nextInt();
        int[] pages = new int[n];
        System.out.println("Enter the reference string (space-separated page numbers):");
        for (int i = 0; i < n; i++) {
            pages[i] = scanner.nextInt();
        }
        System.out.println("FIFO Page Faults: " + fifo(pages, capacity));
        System.out.println("LRU Page Faults: " + lru(pages, capacity));
        System.out.println("Optimal Page Faults: " + optimal(pages, capacity));
        scanner.close();
    }
}
