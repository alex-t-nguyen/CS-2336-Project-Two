# CS-2336-Project-Two
Mario Super Sluggers 2
Details:
- Classes
  - Use good programming practice for classes – proper variable access, mutators and accessors,
proper constructors, etc.
  - Remember that classes exist to be used by other people. Just because you don’t use it in the
program doesn’t mean it shouldn’t be coded and available for others to use in theirs.
   - As with previous projects, you are open to design the classes as you see fit with the minimum
requirements listed below
  - Both classes must be of your own design and implementation. 
  - Requirements
    - Linked list class (-10 points if missing)
      - Named LinkList.java
      - Members
        - Head pointer
        - Recursive print function
        - A sort function of your own design
    - Node class
      - Named Node.java
      - Must be a generic class (-10 points if not)
      - Payload variable
      - Next pointer
    - Player class (-5 points if missing)
      - Named Player.java
      - Design class to hold stats
        - Do not hold stats that are calculated from other stats
        - Use functions to calculate when needed
        - This prevents stale data
- Start the program by prompting the user for the input filename
  - This would normally be hardcoded in an application, but zyLabs requires a filename for multiple
test files
• Nodes will be added to the end of the linked list
• Stats will be calculated for the following categories:
o Batting Average (BA)
▪ Batting average = hits / at-bats
o On-base percentage (OB%)
▪ On-base percentage = (hits + walks + hit by pitch) / plate appearances
o Strikeouts (K)
o Walks (BB)
o Hit by Pitch (HBP)
o Hits (H)
• Calculate all stats per person and record the highest value for each category
o There may be ties for the leaders
o If there is a tie, output all names for tied value
• All data must be held and manipulated in a linked list (-10 points if not)
• Any global variables used must be constant
• Use as few variables as possible
Input: All input will be read from a file. Each player’s data will be listed on separate lines in the file and will follow
the same format.
• Format: <name><space><batting record>
o Mario HOOKWSHHKOHPWWHO
• The name will be a single word. 
• The batting record will be a series of capital letters representing various results during a baseball game
o H – hit
o O – out
o K – strikeout
o W – walk
o P – hit by pitch
o S – sacrifice
• Walks, sacrifices and hit by pitches are not considered an at-bat
• Batting records may contain invalid characters.
o If an invalid character is encountered, disregard it
o Invalid characters are not counted as an at-bat
• Each line in the file will end in a newline (except the last line of the file which may or may not have a
newline)
• The batting record for each person may not have the same number of results
• The input file may have multiple entries for the same person
o Combine that data into one node for the player
Output:
• All output will be written to a file – leaders.txt
• All values are displayed to 3 decimal places
• Display each player’s data in the following order with a tab between each field
o Player name
o At-bats
o Hits
o Walks
o Strikeouts
o Hits by pitch
o Sacrifices
o Batting average
o On-base percentage
o Write a newline after the on-base percentage (there is no tab before the newline)
• Output the player data by name in alphabetical order (A to Z)
• After the player data table, display an additional newline and the LEAGUE LEADERS header
• Display the top 3 leaders for each category
• Because of ties all places may not be awarded.
o For example, if there is a 3 way tie for first, there would not be a second or third place.
o When displaying the leader list, separate each name with a comma and a space
• League Leader Order
o Batting Average
o On-Base Percentage
o Hits
o Walks
o Strikeouts
o Hit By Pitch
• League Leader Output Format
o <CATEGORY><newline>
▪ All caps
o <value><tab><first leader list><newline>
o <value><tab><second leader list><newline>
▪ Second leader list is optional
▪ No second place if first place has 3 or more ties
o <value><tab><third leader list><newline>
▪ Third leader list is optional
▪ No third place if first or second place has a tie
o <newline>
