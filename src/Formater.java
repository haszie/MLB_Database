import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.io.BufferedWriter;
import java.util.Scanner;

// -------------------------------------------------------------------------
/**
 *  Class that formats and writes to the log file
 *
 *  @author Mack Hasz (mrh17)
 *  @version Jun 2, 2014
 */
public class Formater
{
    // ----------------------------------------------------------
    /**
     * Returns string of player's first and last name
     * @param player the player
     * @return the string
     */
    public String report_name_at(Record player) {
        return check(player.getFirst_name()) + " " +
                  check(player.getLast_name());
    }

    // ----------------------------------------------------------
    /**
     * Date formatter
     * @param date to be formatted
     * @return the date formatted
     */
    public String dateFormat(String date) {
        if (date == null || date.equals("")) {
            return "?? ??, ??";
        }

        Scanner temp = new Scanner(date);
        if (date.contains("-")) {
            temp.useDelimiter("-");

            String year = temp.next(), month = temp.next(),
                    day = temp.next();

            temp.close();
            return month(Integer.parseInt(month)) + " " +
                    check(day) + ", " + check(year);
        } else {
            temp.useDelimiter("/");

            String month = temp.next(), day = temp.next(),
                    year = temp.next();

            temp.close();
            return month(Integer.parseInt(month)) + " " +
                    check(day) + ", " + check(year);
        }
    }

    // ----------------------------------------------------------
    /**
     * Converts integer to String formatted month
     * @param month the month in int form
     * @return the string
     */
    public String month(int month) {
        switch (month) {
            case 1: return "January";
            case 2: return "February";
            case 3: return "March";
            case 4: return "April";
            case 5: return "May";
            case 6: return "June";
            case 7: return "July";
            case 8: return "August";
            case 9: return "September";
            case 10: return "October";
            case 11: return "November";
            case 12: return "December";
            default: return "??";
        }
    }

    // ----------------------------------------------------------
    /**
     * Checks to see if string is null
     * @param s the string to be checked
     * @return "??" if null or the string if not
     */
    public String check(String s) {
        if (s == null || s.equals("")) {
            return "??";
        } else {
            return s;
        }
    }

    // ----------------------------------------------------------
    /**
     * Checks the integer if == to -1. This means to entry was made
     * @param i the int to be checked
     * @return the formatted string
     */
    public String check(int i) {
        if (i == -1) {
            return "??";
        } else {
            return "" + i;
        }
    }

    //
    public boolean identifyByName(String name, HashTable<MiniRecord> table, BufferedWriter logBuff) throws IOException
    {
        LinkedList<MiniRecord> chain = table.findChain(new MiniRecord(0, null, name, name));

        if (chain == null) {
            return false;
        } else {
            Object[] oArray = chain.toArray();

            logBuff.write("Found " + oArray.length + " record(s):");
            logBuff.newLine();
            logBuff.newLine();

            for (int i = 0;i < oArray.length;i++) {
                MiniRecord temp = (MiniRecord)oArray[i];
                String jaunt = String.format("%11d:  %s %s", temp.getOffset(), temp.getFirst_name(), temp.getLast_name());
                logBuff.write(jaunt);
                logBuff.newLine();
            }
        }
        return true;
    }

    public boolean show_index_for_Debut(LinkedList<String>[] chains, BufferedWriter logBuff) throws IOException
    {
        if (chains == null) {
            return false;
        } else {
            for (int i = 0; i < chains.length;i++) {
                Object[] oArray = chains[i].toArray();
                if (oArray.length != 0) {
                    int year = 1869 + i;
                    logBuff.write(year + ":  [");

                    for (int j = 0;j < oArray.length; j++) {
                        String temp = (String)oArray[j];

                        if (j != oArray.length - 1) {
                            logBuff.write(temp + ", ");
                        } else {
                            logBuff.write(temp + "]");
                            logBuff.newLine();
                        }
                    }
                }
            }
        }
        return true;
    }

    public void show_index_for_Name(
        HashTable<MiniRecord> table,
        BufferedWriter logBuff) throws IOException
    {
        for (int i = 0;i < table.Size;i++) {
            Object[] oArray = table.Table[i].toArray();

            if (oArray.length != 0) {

                for (int j = 0;j < oArray.length; j++) {
                    MiniRecord temp = (MiniRecord)oArray[j];

                    if (j == 0) {
                        logBuff.write(i + ":    (" + temp.getLast_name() + ", [");
                    }
                    if (j != oArray.length - 1) {
                        logBuff.write(temp.getOffset() + ", ");
                    } else {
                        logBuff.write(temp.getOffset() + "])");
                        logBuff.newLine();
                    }
                }
            }
        }
    }

    public void show_debuts_for(int year, LinkedList<String>[] chains,
        AVL<MiniRecord> tree, BufferedWriter logBuff) throws IOException
    {
        int index = year - 1869;
        LinkedList<String> chain = chains[index];
        Object[] oArray = chain.toArray();
        if (oArray.length == 0) {
            logBuff.write("   Couldn't find any records for " + year);
        } else {
            logBuff.write("\tFound " + oArray.length + " record(s):");
            logBuff.newLine();
            logBuff.newLine();
            for (int i = 0;i < oArray.length;i++) {
                String playerID = (String) oArray[i];
                MiniRecord temp = tree.find(new MiniRecord(0, playerID, null, null));
                String jaunt = String.format("%11d:  %s %s", temp.getOffset(), temp.getFirst_name(), temp.getLast_name());
                logBuff.write(jaunt);
                logBuff.newLine();
            }
        }
    }

    public void show_stats_for(Record playa, BufferedWriter logBuff) throws IOException
    {
        logBuff.write("Lahman ID:   " + playa.getLahman_ID());
        logBuff.newLine();
        logBuff.write("Player ID:   " + playa.getPlayer_ID());
        logBuff.newLine();
        logBuff.write("Name:        " + check(playa.getFirst_name()) + " " + check(playa.getLast_name()));
        logBuff.newLine();
        logBuff.write("Born:        " + check(playa.getBirthdate()) + " " +
            check(playa.getCity_born()) + " " + check(playa.getState_born()) + ", " +
            playa.getCity_died());
        logBuff.newLine();
        logBuff.write("Died:        " + check(playa.getDeathdate()) + " " +
            check(playa.getCity_died()) + " " + check(playa.getState_died()) + ", " +
            playa.getCity_died());
        logBuff.newLine();
        logBuff.write("Weight:      " + check(playa.getWeight()));
        logBuff.newLine();
        logBuff.write("Height:      " + check(playa.getHeight()));
        logBuff.newLine();
        logBuff.write("Throws:      " + check(playa.getThrowing_hand()));
        logBuff.newLine();
        logBuff.write("Bats:        " + check(playa.getBatting_hand()));
        logBuff.newLine();
        logBuff.write("First game:  " + check(playa.getMLB_debut()));
        logBuff.newLine();
        logBuff.write("Last game:   " + check(playa.getMLB_finale()));
        logBuff.newLine();
        logBuff.newLine();
    }
}