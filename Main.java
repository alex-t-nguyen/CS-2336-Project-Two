// Alex Nguyen
// atn170001

import java.util.*;
import java.io.*;
public class Main {

	public static void main(String[] args)
	{
		Scanner fileReader = null;
		Scanner input = new Scanner(System.in);
        //String filename = "stats.txt";
        String inputFilename;
        
        inputFilename = input.nextLine();
        File inFile = new File(inputFilename);

         try {
                fileReader = new Scanner(inFile);
         }
         catch (FileNotFoundException e)
         {
             System.out.println("File could not be opened!");
         }
         readFile(fileReader);
         
         fileReader.close();
         input.close();
	}
    
    public static void readFile(Scanner fileReader) {
        //int x = 1;
        while(fileReader.hasNextLine())
        {
            String line = fileReader.nextLine();
            String name = line.substring(0, line.indexOf(' '));
            String battingRecord = line.substring(line.indexOf(' ') + 1, line.length());

            int nameSize = 0, battingRecordSize = 0;
            for(int i = 0; i < name.length(); i++)
                nameSize++;

            for(int i = 0; i < battingRecord.length(); i++)
                battingRecordSize++;
            String[] nameArray = new String[nameSize]; // MIGHT NEED TO COMPARE NAMES ALPHABETICALLY
            char[] battingRecordArray = new char[battingRecordSize];

            for(int i = 0; i < nameSize; i++)
                nameArray[i] = name.substring(i, i+1);

            for(int i = 0; i < battingRecordSize; i++)
                battingRecordArray[i] = battingRecord.charAt(i);

            System.out.print(name);

            //x++; //TEST WHILE LOOP
        }
    }
}
