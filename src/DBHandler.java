import java.util.Scanner;
import java.util.*;
import java.io.*;


// -------------------------------------------------------------------------
/**
 *  Class used to import a file of players into a txt file database and
 *  retrieve player's information from said file.
 *
 *  @author Mack Hasz (mrh17)
 *  @version Jun 25, 2014
 */
public class DBHandler
{

    // ----------------------------------------------------------
    /**
     *  Imports players from a reader file, places them in their subsequent index
     *  and writes them to the database file.
     *
     * @param reader
     * @param writer
     * @param tree
     * @param table
     * @param chains
     * @throws IOException
     */
    public int importPlayers(RandomAccessFile reader, BufferedWriter writer,
        AVL<MiniRecord> tree, HashTable<MiniRecord> table, LinkedList<String> chains[]) throws IOException
    {
        int count = 0;
        Scanner scan;
        String line = reader.readLine();
        long offset = 0;
        while (line != null) {
            //System.out.println("Line:   " + line);

            scan = new Scanner(line);
            scan.useDelimiter("\t");

            String params[] = new String[18];

            int i = 0;
            while (scan.hasNext()) {
                params[i] = scan.next();
                i++;
            }

            Record temp = new Record(offset,
                parseInt(params[0]),
                params[1],
                params[2],
                params[3],
                params[4],
                params[5],
                params[6],
                params[7],
                params[8],
                params[9],
                params[10],
                params[11],
                params[12],
                params[13],
                params[14],
                params[15],
                params[16],
                params[17]
                );

            //System.out.println("Record: " + temp);

            line += System.getProperty("line.separator");
            writer.write(line);

            tree.insert(new MiniRecord(temp.getOffset(), temp.getPlayer_ID(), temp.getFirst_name(), temp.getLast_name()));

            table.Insert(new MiniRecord(temp.getOffset(), temp.getPlayer_ID(), temp.getFirst_name(), temp.getLast_name()));

            int index = fetchYear(temp.getMLB_debut()) - 1869;
            if (index != -1) {
                chains[index].add(temp.getPlayer_ID());
            }


            offset = reader.getFilePointer();
            line = reader.readLine();
            count++;
            scan.close();
        }
        return count;
    }

    // parses data for a year
    private int fetchYear(String date)
    {
        if (date == null || date.equals("")) {
            return -1;
        }

        Scanner temp = new Scanner(date);
        if (date.contains("-")) {
            temp.useDelimiter("-");

            String year = temp.next(), month = temp.next();

            temp.close();
            return parseInt(year);
        } else {
            temp.useDelimiter("/");

            String month = temp.next(), day = temp.next(),
                    year = temp.next();

            temp.close();
            return parseInt(year);
        }
    }

    // ----------------------------------------------------------
    /**
     * Fetch's a player in the database based on the offset
     *
     * @param off
     * @param reader
     * @return the player if found, null if offsets dont match
     * @throws IOException
     */
    public Record fetchPlayer(long off, RandomAccessFile reader) throws IOException {

        reader.seek(off);
        String line = reader.readLine();
        Scanner scan = new Scanner(line);
        //System.out.println(line);
        scan.useDelimiter("\t");

        String params[] = new String[18];

        int i = 0;
        while (scan.hasNext()) {
            params[i] = scan.next();
            i++;
        }

        //System.out.println(off);
        Record temp = new Record(off,
            parseInt(params[0]),
            params[1],
            params[2],
            params[3],
            params[4],
            params[5],
            params[6],
            params[7],
            params[8],
            params[9],
            params[10],
            params[11],
            params[12],
            params[13],
            params[14],
            params[15],
            params[16],
            params[17]
            );

            scan.close();
            return temp;
    }

    // parseInt fnction
    private int parseInt(String s) {
        if (s == null || s.equals("") || !s.matches("[0-9]+")) {
            return -1;
        } else {
            return Integer.parseInt(s);
        }
    }
}
