# MLB_Database
Database that holds MLB player records and prints relevant information written in Java

An effecient database that parses and stores MLB player information. This info can then
be rectrieved through a series of commands entered as a script file. The database uses a 
hashtable and an AVL tree to store the player information. The hash table is used to look up
players by name and the AVL tree is used to look up players by debut date. Only a small
amount of info is saved in each. To retrieve more information on a player, the program 
reads back the CSV file based on the saved offset. This saves memory and does not cost a
lot since the player's offset in the file is saved. The hash table uses the player's last 
name as a key and uses a linked list for chaining. This makes it very easy to return all 
the players with a given last name.

To query the database provide a script file which includes the following commands.

import<tab><name of player record file>
Open the specified file, parse it, add each player record it contains to the database file if that record is not already
present, and update the index structures as necessary. When the importing is completed, close the specified import file
and log a message reporting the number of records that were imported. If the specified file does not exist, log an
appropriate error message.

identify_by_name<tab><last name>
Log the record offset, first name and last name value for each record in the database file that matches the specified last
name; log an informative message if no matches are found.

show_stats_for<tab><PlayerID>
Log all the data fields in the unique player record in the database file that matches the given <PlayerID>. The
display should be well-formatted and clearly labeled. If no matching record exists, log an informative message.

show_debuts_for<tab><YYYY>
Log the record offset, first name and last name of each player whose first appearance in a MLB game was in the given
year. The display should be well-formatted and clearly labeled. If no matching record exists, log an informative
message.

show_index_for<tab>[PlayerID | PlayerName | PlayerDebut ]
Log the contents of the specified index structure in a fashion that makes the internal structure and contents of the index
clear. It is not necessary to be overly verbose here, but it would be useful to include information like key values and
file offsets where appropriate.

exit<tab>
Terminate program execution. 
