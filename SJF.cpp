#include <iostream>
#include <stdlib.h>
using namespace std;

// class process with all the time values and functions
class Process {
    int id, bt, at, ct, tat, wt;
public:
    void input(Process*, int);
    void calc(Process*, int);
    void show(Process*, int);
    void sortArrival(Process*, int);
    void sortBurst(Process*, int, int); // New function to sort by burst time
};

// main function
int main() {
    int n;
    cout << "\nEnter the no of processes in your system:\n";
    cin >> n;
    Process* p = new Process[n];
    Process f;
    f.input(p, n);
    f.sortArrival(p, n); // Sort by arrival time first
    f.calc(p, n);
    f.show(p, n);
    return 0;
}

// taking input arrival and burst times for all processes
void Process::input(Process* p, int n) {
    for (int i = 0; i < n; i++) {
        cout << "\nEnter Arrival time for process " << i + 1 << ":\n";
        cin >> p[i].at;
        cout << "\nEnter Burst time for process " << i + 1 << ":\n";
        cin >> p[i].bt;
        p[i].id = i + 1;
    }
}

// calculating waiting, turn-around and completion time
void Process::calc(Process* p, int n) {
    int sum = 0;
    int completed = 0;
    bool* done = new bool[n]{ false };

    while (completed < n) {
        int index = -1;
        for (int i = 0; i < n; i++) {
            if (!done[i] && p[i].at <= sum) {
                if (index == -1 || p[i].bt < p[index].bt) {
                    index = i;
                }
            }
        }

        if (index != -1) {
            sum += p[index].bt;
            p[index].ct = sum;
            p[index].tat = p[index].ct - p[index].at;
            p[index].wt = p[index].tat - p[index].bt;
            done[index] = true;
            completed++;
        } else {
            sum++;
        }
    }

    delete[] done;
}

// Sorting processes with respect to arrival times (needed for synchronized input)
void Process::sortArrival(Process* p, int n) {
    for (int i = 0; i < n - 1; i++) {
        for (int j = 0; j < n - i - 1; j++) {
            if (p[j].at > p[j + 1].at) {
                swap(p[j], p[j + 1]);
            }
        }
    }
}

// display function
void Process::show(Process* p, int n) {
    cout << "Process\tArrival\tBurst\tWaiting\tTurn Around\tCompletion\n";
    for (int i = 0; i < n; i++) {
        cout << " P[" << p[i].id << "]\t " << p[i].at << "\t" << p[i].bt << "\t" << p[i].wt << "\t " << p[i].tat << "\t\t" << p[i].ct << "\n";
    }
}
