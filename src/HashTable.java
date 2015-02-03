import java.util.LinkedList;
import java.io.IOException;
import java.io.FileWriter;

// -------------------------------------------------------------------------
/**
 *  Incomplete hashtable (cannot remove) that uses chaining and quadratic probing.
 *  The hashtable will chain if and only iff the element at the probe slot is
 *  equivalent to the element being inserted. This works because of the naure of
 *  the elements being inserted. Knowing that their will be duplicate last names
 *  and all equal last names will hash to the same spot, it makes it easy to retreive
 *  a chain of all the players that have the same last name.
 *
 *  @author Mack Hasz
 *  @version Jun 27, 2014
 */
public class HashTable<T extends Hashable> {

    LinkedList<T>[]         Table;        // stores data objects
    slotState[] Status;       // stores corresponding status values
    int         Size;         // dimension of Table
    int         Usage;        // # of data objects stored in Table
    FileWriter  Log;          // target of logged output
    boolean     loggingOn;    // write output iff this is true

    // Construct hash table with specified size.
    // Pre:
    //      Sz is a positive integer of the form 2^k.
    // Post:
    //      this.Table is an array of dimension Sz; all entries are null
    //      this.Status is an array of dimension Sz; all entries are EMPTY
    //      this.Usage == 0
    //      this.Log == null
    //      this.loggingOn == false
    //
    public HashTable(int Sz) {

        Table =  new LinkedList[Sz];

        for (int i = 0;i < Sz; i++) {
            Table[i] = new LinkedList<T>();
        }
        Status = new slotState[Sz];

        for (int i=0; i < Sz;i++) {
            Status[i] = slotState.EMPTY;
        }

        Size = Sz;
        Usage = 0;
        Log = null;
        loggingOn = false;
    }


    // Attempt insertion of Elem.
    // Pre:
    //      Elem is a proper object of type T
    // Post:
    //      If Elem already occurs in Table (according to equals()):
    //         this.Table is unchanged
    //         this.Usage is unchanged
    //      Otherwise:
    //         Elem is added to Table
    //         this.Usage is incremented
    //      If loggingOn == true:
    //         indices accessed during search are written to Log and
    //         success/failure message is written to Log
    // Return: reference to inserted object or null if insertion fails
    //
    public T Insert(T Elem) throws IOException {
        int homeSlot = Elem.Hash() % Size;

        if (Status[homeSlot] == slotState.EMPTY) {
            Table[homeSlot].add(Elem);
            Status[homeSlot] = slotState.TAKEN;
            Usage++;
            return Table[homeSlot].peekLast();
        }

        int probSlot = homeSlot;
        int k = 1;

        while (Status[probSlot] == slotState.TAKEN && k <= Size) {
            if (Table[probSlot].peekFirst().equals(Elem))
            {
                Table[probSlot].add(Elem);
                Status[probSlot] = slotState.TAKEN;
                Usage++;
                return Table[probSlot].peekLast();
            }

            int prob = homeSlot + quadProbe(k);
            probSlot = prob % Size;
            k++;
        }

        // no slots found
        if (k >= Size) {
            return null;
        }

        Table[probSlot].add(Elem);
        Status[probSlot] = slotState.TAKEN;
        Usage++;

        return Table[probSlot].peekLast();
    }

    private int quadProbe(int k) {
        int ret = k * k;
        ret += k;
        return (ret / 2);
    }

    public LinkedList<T> findChain(T Elem) {
        int homeSlot = Elem.Hash() % Size;

        // none are ever removed so empty means null
        if (Status[homeSlot] == slotState.EMPTY) {
            return null;
        }

        int probSlot = homeSlot;
        int k = 1;

        while (Status[probSlot] == slotState.TAKEN && k <= Size) {
            if (Table[probSlot].peekFirst().equals(Elem))
            {
                return Table[probSlot];
            }

            int prob = homeSlot + quadProbe(k);
            probSlot = prob % Size;
            k++;
        }

        // no slots found
        if (k >= Size) {
            return null;
        }

        return Table[probSlot];
    }

    public void display() {
        for (int i = 0; i < Size; i++) {
            if (Status[i] == slotState.TAKEN) {
                System.out.print(i + ": ");
                Object[] stuff = Table[i].toArray();
                for (int j=0;j < stuff.length; j++) {
                    System.out.print(stuff[j]+ " ");
                }
                System.out.println();
            } else {
                System.out.println(i + ": Empty");
            }
        }
    }

    public enum slotState {EMPTY, TAKEN};
}