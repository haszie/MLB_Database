import java.util.*;
import java.io.*;


// -------------------------------------------------------------------------
/**
 *  The main class that the program is run from. It parses the commands and acts
 *  accordingly.
 *
 *  @author Mack Hasz (mrh17)
 *  @version May 28, 2014
 */
public class Main
{
    // ----------------------------------------------------------
    /**
     * The method run when the program is invoked.
     *
     * @param args the command line arguments
     */
    public static void main (String[] args) {

        AVL<MiniRecord> tree = new AVL<MiniRecord>();
        HashTable<MiniRecord> table = new HashTable<MiniRecord>(4027);
        LinkedList<String> chains[] = new LinkedList[145];

        // initialize chains
        for (int i = 0;i < 145;i++) {
            chains[i] = new LinkedList<String>();
        }

        // exit if command line arguments are not correct
        if (args.length != 3) {
            System.out.println("Invalid use of command.");
        } else {
            try {
                FileWriter database;
                BufferedWriter dbBuff;

                RandomAccessFile commands = new RandomAccessFile(args[1], "r");

                FileWriter log = new FileWriter(args[2]);
                BufferedWriter logBuff = new BufferedWriter(log);


                logBuff.write("MLBPlayer dB Parser");
                logBuff.newLine();
                logBuff.newLine();
                logBuff.write("dbFile:  " + args[0]);
                logBuff.newLine();
                logBuff.write("Script:  " + args[1]);
                logBuff.newLine();
                logBuff.write("Log:     " + args[2]);
                logBuff.newLine();
                logBuff.write("------------------------------------------");
                logBuff.newLine();
                logBuff.newLine();

                // read in commands and perform the apropriate action
                String line = commands.readLine();
                Formater f = new Formater();
                DBHandler db = new DBHandler();
                int count = 0;
                boolean exit = false;
                while (line != null && exit == false) {
                    if (!line.contains(";")) {
                        // import players
                        if (line.contains("import")) {

                            database = new FileWriter(args[0]);
                            dbBuff = new BufferedWriter(database);

                            String importFileName = line.substring(7);
                            RandomAccessFile tmp = new RandomAccessFile(importFileName, "r");
                            int total = db.importPlayers(tmp, dbBuff, tree, table, chains);
                            tmp.close();

                            dbBuff.close();
                            database.close();

                            logBuff.write("Command  " + count + ":  import\t" + importFileName);
                            logBuff.newLine();
                            logBuff.newLine();
                            logBuff.write("Imported " + total + " records from " + importFileName);
                            logBuff.newLine();
                            logBuff.write("------------------------------------------");
                            logBuff.newLine();
                            count++;
                        // identify by name command
                        } else if (line.contains("identify")) {

                            String name = line.substring(17);
                            logBuff.write("Command  " + count + ":  identify_by_name\t" + name);
                            logBuff.newLine();
                            logBuff.newLine();

                            if (f.identifyByName(name, table, logBuff) == false) {
                                logBuff.write("   Couldn't find any records for " + name);
                            }

                            logBuff.newLine();
                            logBuff.write("------------------------------------------");
                            logBuff.newLine();

                            count++;
                        // show stats for a player
                        } else if (line.contains("stats")) {
                            String playerID = line.substring(15);

                            logBuff.write("Command  " + count + ":  show_stats_for\t" + playerID);
                            logBuff.newLine();
                            logBuff.newLine();

                            MiniRecord temp = tree.find(new MiniRecord(0, playerID, null, null));

                            if (temp == null) {
                                logBuff.write("   Couldn't find record for " + playerID);
                                logBuff.newLine();
                            } else {
                                logBuff.write("\tFound record at offset " + temp.getOffset() + ":");
                                logBuff.newLine();
                                logBuff.newLine();
                                RandomAccessFile dReader = new RandomAccessFile(args[0], "r");
                                Record playa = db.fetchPlayer(temp.getOffset(), dReader);
                                dReader.close();

                                f.show_stats_for(playa, logBuff);
                            }

                            logBuff.write("------------------------------------------");
                            logBuff.newLine();
                            count++;
                        // show the players who debuted in a certain year
                        } else if (line.contains("debut")) {
                            int year = Integer.parseInt(line.substring(16));
                            logBuff.write("Command  " + count + ":  show_debuts_for\t" + year);
                            logBuff.newLine();
                            logBuff.newLine();

                            f.show_debuts_for(year, chains, tree, logBuff);

                            logBuff.newLine();
                            logBuff.write("------------------------------------------");
                            logBuff.newLine();

                            count++;
                        // display the relative index
                        } else if (line.contains("index")) {
                            logBuff.write("Command  " + count + ":  " + line);
                            logBuff.newLine();
                            logBuff.newLine();
                            // playerID
                            if (line.contains("PlayerID")) {
                                tree.display(tree.root, logBuff);
                            // playerName
                            } else if (line.contains("PlayerName")) {
                                f.show_index_for_Name(table, logBuff);
                            // playerDebuts
                            } else if (line.contains("PlayerDebut")) {
                                f.show_index_for_Debut(chains, logBuff);
                            // there was an error reading the command
                            } else {
                                logBuff.write("Error specifying type of index.");
                            }
                            logBuff.write("------------------------------------------");
                            logBuff.newLine();

                            count++;
                        // exit the program
                        } else if (line.contains("exit")) {
                            logBuff.write("Command  " + count + ":  exit");
                            logBuff.newLine();
                            logBuff.newLine();
                            logBuff.write("Exiting...");
                            logBuff.newLine();
                            logBuff.write("------------------------------------------");

                            exit = true;
                        // error reading command
                        } else {
                            logBuff.write("Error parsing command");
                            logBuff.newLine();
                        }
                    }
                    line = commands.readLine();
                }

                commands.close();
                logBuff.close();
                log.close();

            } catch (FileNotFoundException e) {
                System.err.println("Error:" + e);
            } catch (IOException e) {
                System.err.println("Writing error: " + e);
            } catch (NoSuchElementException e) {
                System.err.println("Error " + e);
            }
        }
    }
}