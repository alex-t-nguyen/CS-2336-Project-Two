import java.io.*;
import java.util.*;


public class readfile {

    Scanner fileReader;
    Scanner input = new Scanner(System.in);

     public void openFile() {
        //String filename = "stats.txt";
        String inputFilename;

        System.out.print("Enter input filename: ");

        inputFilename = input.nextLine();
        File inFile = new File(inputFilename);

         try {
                fileReader = new Scanner(inFile);
         }
         catch (FileNotFoundException e)
         {
             System.out.println("File could not be opened!");
         }
    }
    
    public void readFile() {
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
            String[] nameArray = new String[nameSize];
            String[] battingRecordArray = new String[battingRecordSize];

            for(int i = 0; i < nameSize; i++)
                nameArray[i] = name.substring(i, i+1);

            for(int i = 0; i < battingRecordSize; i++)
                battingRecordArray[i] = battingRecord.substring(i, i+1);

            for(int i = 0; i < nameSize; i++)
                System.out.print(name.substring(i, i+1));

            System.out.println();
            for(int i = 0; i < battingRecordSize; i++)
                System.out.print(battingRecord.substring(i, i+1));
            System.out.println();
            //x++; //TEST WHILE LOOP
        }
    }

    public void closeFile() {
        fileReader.close();
        input.close();
    }
}
