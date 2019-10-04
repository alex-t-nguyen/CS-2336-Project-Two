/**
 * Alex Nguyen
 * atn170001
 * 8/27/2019
 * CE 2336.003
 */

import java.io.*;
import java.util.*;

 public class Main {
     // Constants for array size
     static final int BATTING_RECORD_SIZE = 30;

     // Main function
     public static void main(String[] args) throws IOException {

        // Stat arrays
        double[] battingAverage = new double[BATTING_RECORD_SIZE];
        double[] onBasePercentage = new double[BATTING_RECORD_SIZE];
        int[] strikeouts = new int[BATTING_RECORD_SIZE];
        int[] walks = new int[BATTING_RECORD_SIZE];
        int[] hitByPitch = new int[BATTING_RECORD_SIZE];
        int[] hits = new int[BATTING_RECORD_SIZE];

        // Variables for number of sacrifices and outs
        int numSacrifices, numOuts;

        // Allow user to input filename through console
        BufferedReader br = null;
        boolean exist;
        File inFile;
        Scanner input;
        
        input = new Scanner(System.in);

        // Prompt user for input filename
        //System.out.print("Enter input filename: ");
        String inputFilename = input.nextLine();
        br = new BufferedReader(new FileReader(inputFilename));
        
        // Create file variable
        inFile = new File(inputFilename);
        
        int nameSize = 0;
        
        // Create scanner object to open file
        String text = br.readLine();
        // Counts number of players in file
        while(text != null)
        {
            nameSize++;
            text = br.readLine();
        }
        br.close();

        // Reopen file to read through again
        Scanner fileReader = null;
        fileReader = new Scanner(inFile);

         // Variables to store the substring of player's name and batting record
         String name, battingRecord;

         // Declare player's batting records array
         char[] battingRecordArray;
         
         String[] namesListTemp = new String[nameSize];
         int playerIndex = 0;

         // Number of at-bats
         int totalAtBats = 0, plateAppearances = 0, numBattingRecord = 0;

         // Trace through file until end of file
         while(fileReader.hasNextLine())
         {
             numSacrifices = numOuts = 0;
            // Reads in line in file
            String line = fileReader.nextLine();

            // Parses the player's name substring from the line
            name = line.substring(0, line.indexOf(' '));
            namesListTemp[playerIndex] = name;

            // Parses the player's batting records substring from the line
            battingRecord = line.substring(line.indexOf(' ') + 1, line.length());
            
            // Size of player's batting records array
            numBattingRecord = battingRecord.length();

            // Initialize player's batting records array with new size
            battingRecordArray = new char[numBattingRecord];

            // Transfer player's batting records to designated array
            for(int i = 0; i < battingRecord.length(); i++)
            {
                battingRecordArray[i] = battingRecord.charAt(i);
            }

            // Puts each stat of player's batting record in its designated array
            parseBattingRecord(battingRecordArray, hits, strikeouts, walks, hitByPitch, playerIndex);
            
            // Calculates the number of sacrifices and outs of player
            for(int i = 0; i < battingRecordArray.length; i++)
            {
                if(battingRecordArray[i] == 'S')
                    numSacrifices++;
                else if(battingRecordArray[i] == 'O')
                    numOuts++;
            }
            
            // Calculates the total number of at-bats of player
            totalAtBats = calculateNumAtBats(hits, numOuts, strikeouts, playerIndex);

            // Calculates the batting average of player
            battingAverage[playerIndex] =  calculateBattingAverage(hits[playerIndex], totalAtBats);

            // Calculates the number of plate appearances of player
            plateAppearances = calculatePlateAppearances(hits, numOuts, strikeouts, walks, hitByPitch, numSacrifices, playerIndex);

            // Calculates the on-base-percentage of player
            onBasePercentage[playerIndex] = calculateOnBasePercentage(hits, walks, hitByPitch, plateAppearances, playerIndex);

            // Increment index of player getting data of
            playerIndex++;
            
         }

         // Stores players in new array of size 'numNames' (no extra spaces in array)
         String[] namesList = new String[nameSize];
         for(int i = 0; i < namesList.length; i++)
         {
             namesList[i] = namesListTemp[i];
         }

        // Closes the input files
        fileReader.close();
        input.close();

        // Open output file
         File outFile = new File("leaders.txt");
         BufferedWriter bWriter = null;
         
         // Displays each player's data and the leaders of each stat
            FileWriter output = new FileWriter(outFile);
            bWriter = new BufferedWriter(output);
            displayPlayerData(bWriter, battingAverage, onBasePercentage, hits, walks, strikeouts, hitByPitch, namesList);
            displayLeaders(bWriter, battingAverage, onBasePercentage, hits, walks, strikeouts, hitByPitch, namesList);
            bWriter.close();
     }

     /**
      * Parses batting record for one player
      * @param battingRecord the char array containing the batting record
      * @param h array for hit stat
      * @param k array for strikeout stat
      * @param w array for walk stat
      * @param p array for hit by pitch stat
      * @param arrayIndex index of specific array
      */
      public static void parseBattingRecord(char[] battingRecordArray, int[] h, int[] k, int[] w, int[] p, int arrayIndex)
      {
        // Counter for each stat
        int hit = 0, strikeout = 0, walk = 0, hitByPitch = 0;
        char stat;
          for(int i = 0; i < battingRecordArray.length; i++)
          {
              stat = battingRecordArray[i];
 
              switch(stat)
              {
                  case 'H': hit++; break;  

                  case 'K': strikeout++; break;

                  case 'W': walk++; break;

                  case 'P': hitByPitch++; break;

                  default: break;
              }
          }
          // Puts number of each stat into array at player's index
          h[arrayIndex] = hit;
          k[arrayIndex] = strikeout;
          w[arrayIndex] = walk;              
          p[arrayIndex] = hitByPitch;
      }

      /**
       * Displays the player's name and stats
       * @param bWriter file to write player data to
       * @param h array for hit stat
       * @param k array for strikeout stat
       * @param w array for walk stat
       * @param p array for hit by pitch stat
       * @param names array for player's names
       */
      public static void displayPlayerData(BufferedWriter bWriter, double[] battingAverage, double[] onBasePercentage, int[] h, int[] w, int[] k, int[] p, String[] names) throws IOException {
            for(int i = 0; i < names.length; i++)
            {
                bWriter.write(names[i]);
                bWriter.newLine();
                bWriter.write("BA: " + String.format("%.3f", battingAverage[i]));
                bWriter.newLine();
                bWriter.write("OB%: " + String.format("%.3f", onBasePercentage[i]));
                bWriter.newLine();
                bWriter.write("H: " + String.valueOf(h[i]));
                bWriter.newLine();
                bWriter.write("BB: " + String.valueOf(w[i]));
                bWriter.newLine();
                bWriter.write("K: " + String.valueOf(k[i]));
                bWriter.newLine();
                bWriter.write("HBP: " + String.valueOf(p[i]));
                bWriter.newLine();
                bWriter.newLine();

            }
      }

      /**
       * @param bWriter file to write leaders data to
       * @param battingAverage batting average array
       * @param onBasePercentage on base percentage array
       * @param h hits stat array
       * @param w walks stat array
       * @param k strikeouts stat array
       * @param p hit by pitch stat array
       * @param names array of player's names
       */
      public static void displayLeaders(BufferedWriter bWriter, double[] battingAverage, double[] onBasePercentage, int[] h, int[] w, int[] k, int[] p, String[] names) throws IOException {
            // Print out leaders header
            bWriter.write("LEAGUE LEADERS");
            bWriter.newLine();

            // Number of stats to find
            final int NUM_STATS = 6;

            // Go through each stat array and print out the leaders of the stat and the stat itself
            for(int i = 0; i < NUM_STATS; i++)
            {
                switch(i)
                {
                    // Prints out leader for batting average
                    case 0: 
                    {
                        bWriter.write("BA: ");
                        int[] tieList = new int[names.length];
                        double highest = getLeader(battingAverage);
                        int t = 0;
                        for(int j = 0; j < names.length; j++)
                        {
                            // Get indexes of names of leaders who tied
                            if(battingAverage[j] == highest)
                            {
                                tieList[t] = j;
                                t++;
                            }
                        }
                        if(t > 1)
                        {
                            for(int j = 0; j < t; j++)
                            {
                                if(j == t - 1) // If name is the last one in list of ties
                                    bWriter.write(names[tieList[j]] + " ");
                                else
                                    bWriter.write(names[tieList[j]] + ", ");
                            } 
                        }
                        else
                            // Displays name of player
                            bWriter.write(names[tieList[0]] + " ");
                        bWriter.write(String.format("%.3f", highest));
                        bWriter.newLine();
                        break;
                    }

                    // Prints out leader for on base percentage
                    case 1:
                    {
                        bWriter.write("OB%: ");
                        int[] tieList = new int[names.length];
                        double highest = getLeader(onBasePercentage);
                        int t = 0;
                        for(int j = 0; j < names.length; j++)
                        {
                            // Get indexes of names of leaders who tied
                            if(onBasePercentage[j] == highest)
                            {
                                tieList[t] = j;
                                t++;
                            }
                        }
                        if(t > 1)
                        {
                            for(int j = 0; j < t; j++)
                            {
                                if(j == t - 1) // If name is the last one in list of ties
                                    bWriter.write(names[tieList[j]] + " ");
                                else
                                    bWriter.write(names[tieList[j]] + ", ");
                            } 
                        }
                        else
                            // Displays name of player
                            bWriter.write(names[tieList[0]] + " ");
                        bWriter.write(String.format("%.3f", highest));
                        bWriter.newLine();
                        break;
                    }

                    // Prints out leader for number of hits
                    case 2:
                    {
                        bWriter.write("H: ");
                        int[] tieList = new int[names.length];
                        int highest = getLeader(h);
                        int t = 0;
                        for(int j = 0; j < names.length; j++)
                        {
                            // Get indexes of names of leaders who tied
                            if(h[j] == highest)
                            {
                                tieList[t] = j;
                                t++;
                            }
                        }
                        if(t > 1)
                        {
                            for(int j = 0; j < t; j++)
                            {
                                if(j == t - 1) // If name is the last one in list of ties
                                    bWriter.write(names[tieList[j]] + " ");
                                else
                                    bWriter.write(names[tieList[j]] + ", ");
                            } 
                        }
                        else
                            // Displays name of player
                            bWriter.write(names[tieList[0]] + " ");
                        bWriter.write(String.valueOf(highest));
                        bWriter.newLine();
                        break;
                    }
                    
                    // Prints out leader for number of walks
                    case 3:
                    {
                        bWriter.write("BB: ");
                        int[] tieList = new int[names.length];
                        int highest = getLeader(w);
                        int t = 0;
                        for(int j = 0; j < names.length; j++)
                        {
                            // Get indexes of names of leaders who tied
                            if(w[j] == highest)
                            {
                                tieList[t] = j;
                                t++;
                            }
                        }
                        if(t > 1)
                        {
                            for(int j = 0; j < t; j++)
                            {
                                if(j == t - 1) // If name is the last one in list of ties
                                    bWriter.write(names[tieList[j]] + " ");
                                else
                                    bWriter.write(names[tieList[j]] + ", ");
                            } 
                        }
                        else
                            // Displays name of player
                            bWriter.write(names[tieList[0]] + " ");
                        bWriter.write(String.valueOf(highest));
                        bWriter.newLine();
                        break;
                    }

                    // Prints out leader for number of strikeouts
                    case 4:
                    {
                        bWriter.write("K: ");

                        int[] tieList = new int[names.length];
                        int highest = getLeader(k);
                        int lowest = highest;
                        int t = 0;
                        // Get lowest number of strikeouts 
                        for(int l = 0; l < names.length; l++)
                        {
                            if(k[l] < lowest)
                                lowest = k[l];   
                        }
                        
                        for(int j = 0; j < names.length; j++)
                        {
                            // Get indexes of names of leaders who tied
                            if(k[j] == lowest)
                            {
                                tieList[t] = j;
                                t++;
                            }
                        }
                        if(t > 1)
                        {
                            for(int j = 0; j < t; j++)
                            {
                                if(j == t - 1) // If name is the last one in list of ties
                                    bWriter.write(names[tieList[j]] + " ");
                                else
                                    bWriter.write(names[tieList[j]] + ", ");
                            } 
                        }
                        else
                            // Displays name of player
                            bWriter.write(names[tieList[0]] + " ");       
                        bWriter.write(String.valueOf(lowest));
                        bWriter.newLine();
                        break;
                    }

                    // Prints out leader for hit by pitch
                    case 5:
                    {
                        bWriter.write("HBP: ");
                        int[] tieList = new int[names.length];
                        int highest = getLeader(p);
                        int t = 0;
                        for(int j = 0; j < names.length; j++)
                        {
                            // Get indexes of names of leaders who tied
                            if(p[j] == highest)
                            {
                                tieList[t] = j;
                                t++;
                            }
                        }
                        if(t > 1)
                        {
                            for(int j = 0; j < t; j++)
                            {
                                if(j == t - 1) // If name is the last one in list of ties
                                    bWriter.write(names[tieList[j]] + " ");
                                else
                                    bWriter.write(names[tieList[j]] + ", ");
                            } 
                        }
                        else
                            // Displays name of player
                            bWriter.write(names[tieList[0]] + " ");
                        bWriter.write(String.valueOf(highest));
                        bWriter.newLine();
                        break;
                    }

                    // Goes on to next stat if letter is incorrect
                    default: break;
                }
            }
        }
    
    /**
     * Calculates the batting average
     * @param h number of hits
     * @param numAtBats number of bats
     * @return the batting average of player
     */
    public static double calculateBattingAverage(int h, int numAtBats)
    {
        if(numAtBats == 0)
            return 0;
        else
            return (double)h / numAtBats;
    }

    /**
     * Calculates the on base percentage
     * @param h hits array
     * @param w walks array
     * @param p hit by pitch array
     * @param plateAppearances number of plate appearances
     * @param arrayIndex index of player
     * @return the on base percentage of player
     */
    public static double calculateOnBasePercentage(int[] h, int[] w, int[] p, int plateAppearances, int arrayIndex)
    {
        return (double)(h[arrayIndex] + w[arrayIndex] + p[arrayIndex]) / plateAppearances;
    }

    /**
     * Calculates the number of at bats
     * @param hits hits array
     * @param out number of outs
     * @param strikeouts strikeouts array
     * @param playerIndex index of player
     * @return the number of at bats of player
     */
    public static int calculateNumAtBats(int[] hits, int out, int[] strikeouts, int playerIndex)
    {
        return hits[playerIndex] + out + strikeouts[playerIndex];
    }

    /**
     * Calcultes the number of plate appearances
     * @param hits hits array
     * @param out number of outs
     * @param strikeouts strikeouts array
     * @param walks walks array
     * @param hitByPitch hit by pitch array
     * @param sacrifice number of sacrifices
     * @param playerIndex index of player
     * @return the number of plate appearances of player
     */
    public static int calculatePlateAppearances(int[] hits, int out, int[] strikeouts, int[] walks, int[] hitByPitch, int sacrifice, int playerIndex)
    {
        return hits[playerIndex] + out + strikeouts[playerIndex] + walks[playerIndex] + hitByPitch[playerIndex] + sacrifice;
    }

    /**
     * Gets the leader of hits, strikeouts, walks, hit by pitch and sacrifices
     * @param stat arrays for hits, strikeouts, walks, hit by pitch, and sacrifices
     * @return the highest number for hits, strikeouts, walks, hit by pitch, and sacrifices stats
     */
    public static int getLeader(int[] stat) {
        int highest = stat[0];
        for(int i = 1; i < stat.length; i++)
        {
            if(highest < stat[i])
                highest = stat[i];
        }
        return highest;
    }

    /** 
     * Overloaded getLeader method
     * Gets the leader for batting average and on base percentage
     * @param stat arrays for batting average and on base percentage
     * @return the highest number of batting average and on base percentage stats
     */
    public static double getLeader(double[] stat)
    {
        double highest = stat[0];
        for(int i = 1; i < stat.length; i++)
        {
            if(highest < stat[i])
                highest = stat[i];
        }
        return highest;
    }
}