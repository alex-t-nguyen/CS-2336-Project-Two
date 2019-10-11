// Alex Nguyen
// atn170001
import java.util.*;
import java.io.*;
public class Main {
    static final double THRESHOLD = 0.00000001;
	public static void main(String[] args) throws IOException
	{
		Scanner fileReader = null;
		Scanner input = new Scanner(System.in);
        String inputFilename;
        
        inputFilename = input.nextLine();
        File inFile = new File(inputFilename);

        //try {
            fileReader = new Scanner(inFile);
        //}
        //catch (FileNotFoundException e)
        //{
            input.close();
            //throw e;
        //}
        LinkList<Player> list = new LinkList<>();

        while(fileReader.hasNextLine())
        {
            Player player = null;
            boolean multipleEntries = false;
            String line = fileReader.nextLine();
            String name = line.substring(0, line.indexOf(' '));
            if(list.getHead() == null)  // If no node in linked list
            {
                player = new Player();
                player.setName(name);
            }
            else
            {
                for(Node<Player> n = list.getHead(); n != null; n = n.getNext())    // Checks for duplicate names in file
                {
                    if(n.getData().getName().equals(name)) // If duplicate names in files, combine into 1 player by adding stats to first occurence of player
                    {   
                        player = n.getData();
                        multipleEntries = true;
                    }
                    if(multipleEntries)
                        break;
                    else if(n.getNext() == null) // If no duplicate names in file, create new player
                    {
                        player = new Player();
                        player.setName(name);
                    }
                    else
                        continue;
                }
            }
            String battingRecord = line.substring(line.indexOf(' ') + 1, line.length());

            int battingRecordSize = 0;

            for(int i = 0; i < battingRecord.length(); i++)
                battingRecordSize++;
            char[] battingRecordArray = new char[battingRecordSize];

            for(int i = 0; i < battingRecordSize; i++)
                battingRecordArray[i] = battingRecord.charAt(i);
            for(int i = 0; i < battingRecordArray.length; i++)
            {
                switch(battingRecordArray[i])
                {
                    case 'H': 
                        player.setHits(player.getHits() + 1);
                        break;
                    case 'O': 
                        player.setOuts(player.getOuts() + 1);
                        break;
                    case 'K':
                        player.setStrikeOuts(player.getStrikeouts() + 1);
                        break;
                    case 'W':
                        player.setWalks(player.getWalks() + 1);
                        break;
                    case 'P':
                        player.setHitByPitches(player.getHitByPitches() + 1);
                        break;
                    case 'S':
                        player.setSacrifices(player.getSacrifices() + 1);
                        break;
                    default:
                        break;
                }
            }
            if(!multipleEntries)
            {
                Node<Player> node = new Node<>(player);
                list.appendList(node);
            }
        }
        // Outputs to file in append mode to prevent overwriting lines
        FileWriter output = new FileWriter("leaders.txt");
        BufferedWriter bWriter = new BufferedWriter(output);

        // Sort list alphabetically
        list.sortAlphabetically(list.getHead());
        //list.destroyLinkList();
        // Print player data in alphabetical order
        //System.out.println(list.getHead().getData().getName());
        list.displayPlayerData(list.getHead(), bWriter);

        // Print leaders
        //list.displayLeaders(list.getHead());
        int totalNumLeaders;
        int firstCounter, secondCounter;
        int listSize;

        /*
        LinkList<Player> names = new LinkList<>();
        Node<Player> test = list.getHead();
        Node<Player> cnt = list.getHead();
        System.out.println("list before: " + list.getListSize(list.getHead()));
        System.out.println("names before: " + names.getListSize(names.getHead()));
        while(cnt != null)
        {
            System.out.println(cnt.getData().getName());
            cnt = cnt.getNext();
        }

        names.appendList(list.getHead());
        System.out.println("list after: " + list.getListSize(list.getHead()));
        System.out.println("names after: " + names.getListSize(names.getHead()));
        cnt = names.getHead();
        while(cnt != null)
        {
            System.out.println(cnt.getData().getName());
            cnt = cnt.getNext();
        }
        names.destroyLinkList();
        */
        //System.out.println(names.getListSize(names.getHead()));
        bWriter.write("\n");
        bWriter.write("LEAGUE LEADERS");
        //list.displayLeaders(list.getHead());
        
        listSize = list.getListSize(list.getHead());
        Node<Player> head = list.getHead();
        for(int i = 0; i <= 5; i++)
        {
            totalNumLeaders = 0;
            firstCounter = 1;
            secondCounter = 1;
            head = list.getHead();

            switch(i)
            {
                /* 
                    STEPS FOR DISPLAYING LEADERS AND DEALING WITH TIES     
                    1) Create a counter for total number of leaders
                    2) Create a counter for each place on leaderboard (1st, 2nd, 3rd)
                    3) Display head of list b/c 1st leader will always be displayed
                    4) Loop through list and compare iter to head
                        a) If iter = head, then display iter and increment 1st place counter
                        b) Stop iter at node where iter != head, set head to iter
                    5) Add 1st place counter to total number of leaders counter
                    6) If total number of leaders counter <= 2
                        a) Loop through list starting at new head
                        b) If iter = head, then display iter and increment 2nd place counter
                        c) Stop iter at node where iter != head, set head to iter
                        Else do not display anything (b/c 1st place leaders >= 3)
                    7) Add 2nd place counter to total number of leaders counter
                    8) If total number of leaders counter <= 2
                        a) Loop through list starting at new head
                        b) If iter = head, then display iter (Don't need 3rd place counter b/c there can be unlimited ties for 3rd)
                        c) Stop iter at node where iter = null                  
                
                /*
                    NEED TO DISPLAY TIES IN ALPHABETICAL
                    1) Create a new linked list at beginning of case to contain all tied players for 1st
                    2) Don't print out first person as leader
                        a) Put first person as leader into linked list and append following tied players to list
                    3) Alphabetically sort the list
                    4) Loop through list and print out the tied players for first
                    5) Destroy the list with deconstructor
                    6) Repeat for 2nd ties and 3rd ties
                */
                case 0:
                    {
                        // Sorts the list in descending order based on batting average
                        list.sortDescendingBattingAverage(head);
                        ArrayList<String> firstLeaders = new ArrayList<>();
                        bWriter.write("\nBATTING AVERAGE\n");
                        int numAtBats = head.getData().calculateNumAtBats(head.getData().getHits(), head.getData().getOuts(), head.getData().getStrikeouts());
                        
                        // Print out 1st leader and their batting average
                        firstLeaders.add(head.getData().getName());
                        bWriter.write(String.format("%.3f", head.getData().calculateBattingAverage(head.getData().getHits(), numAtBats)) + "\t");
                        //bWriter.write(String.format("%.3f", head.getData().calculateBattingAverage(head.getData().getHits(), numAtBats)) + "\t" + head.getData().getName());
                        Node<Player> iter = head.getNext();
                        //System.out.println(list.getListSize(head));
                        int iterNumAtBats;
                        double iterBattingAverage, headBattingAverage;
                        boolean tie = false;
                        // Loop through list to get all batting averages for first
                        while(iter != null)
                        {
                            // Calculates the batting average of 1st place leader and all other players
                            iterNumAtBats = iter.getData().calculateNumAtBats(iter.getData().getHits(), iter.getData().getOuts(), iter.getData().getStrikeouts());
                            iterBattingAverage = iter.getData().calculateBattingAverage(iter.getData().getHits(), iterNumAtBats);
                            headBattingAverage = head.getData().calculateBattingAverage(head.getData().getHits(), numAtBats);

                            // If batting average of other players is equal to 1st place leader print out player's name
                            if(iterBattingAverage == headBattingAverage)
                            {
                                firstLeaders.add(iter.getData().getName());
                                //bWriter.write(", " + iter.getData().getName()); // <------ Append node to new ties linked list instead of printing name
                                firstCounter++;
                                tie = true;
                            }

                            // If not equal stop looping and set head to player where batting average was not equal (a.k.a 2nd place leader)
                            else
                            {
                                head = iter; // Move head to 2nd place leader
                                iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                //System.out.println("test");
                                break;
                            }
                            iter = iter.getNext(); // Iterates to next player if there is a tie
                        }
                        totalNumLeaders += firstCounter; // Increase total number of leaders
                        list.sortArrayListNames(firstLeaders);
                        //System.out.println(head.getData().getName());
                        for(int j = 0; j < firstLeaders.size(); j++)
                        {
                            if(j == 0)
                                bWriter.write(firstLeaders.get(j));
                            else
                                bWriter.write(", " + firstLeaders.get(j));
                        }
                        if(totalNumLeaders <= 2) // Check if there are 3 or more leaders already
                        {
                            ArrayList<String> secondLeaders = new ArrayList<>();
                            //System.out.println(names.getListSize(names.getHead()));
                            // -----------> Deconstruct list to clear it of 1st place tied players by calling deconstruct function
                            numAtBats = head.getData().calculateNumAtBats(head.getData().getHits(), head.getData().getOuts(), head.getData().getStrikeouts());
                            headBattingAverage = head.getData().calculateBattingAverage(head.getData().getHits(), numAtBats);
                            if(listSize > 2)
                                secondLeaders.add(head.getData().getName());
                               // bWriter.write("\n" + String.format("%.3f", headBattingAverage) + "\t" + head.getData().getName()); // <-------- Append Node to ties linked list instead of printing
                            else if(!tie && listSize > 1)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + String.format("%.3f", headBattingAverage) + "\t" + head.getData().getName());
                            else
                            { 
                                bWriter.write("\n");
                                break;
                            }
                            //System.out.println(names.getHead().getData().getName());
                            // Print out 2nd leader and their batting average
                            bWriter.write("\n" + String.format("%.3f", headBattingAverage) + "\t");
                            //bWriter.write("\n" + String.format("%.3f", headBattingAverage) + "\t" + head.getData().getName());
                            while(iter != null)
                            {
                                // Calculates the batting average of 2nd place leader and all other players
                                iterNumAtBats = iter.getData().calculateNumAtBats(iter.getData().getHits(), iter.getData().getOuts(), iter.getData().getStrikeouts());
                                iterBattingAverage = iter.getData().calculateBattingAverage(iter.getData().getHits(), iterNumAtBats);
                                headBattingAverage = head.getData().calculateBattingAverage(head.getData().getHits(), numAtBats);
                                
                                // If batting average of other players is equal to 1st place leader print out player's name
                                if(iterBattingAverage == headBattingAverage)
                                {
                                    secondLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName()); // <------ Append node to new ties linked list instead of printing name
                                    secondCounter++;
                                }

                                // If not equal stop looping and set head to player where batting average was not equal (a.k.a 3rd place leader)
                                else
                                {
                                    head = iter; // Move head to 3rd place leader
                                    iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                    break;
                                }
                                iter = iter.getNext(); // Iterates to next player if there is a tie
                            }
                            totalNumLeaders += secondCounter; // Increase total number of leaders
                            list.sortArrayListNames(secondLeaders);
                            //System.out.println(names.getHead().getData().getName());
                            for(int j = 0; j < secondLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(secondLeaders.get(j));
                                else
                                    bWriter.write(", " + secondLeaders.get(j));
                            }
                        }
                        else // If ties for 1st were >= 3 do not print out anything and end loop for that stat
                        { 
                            bWriter.write("\n");
                            break;
                        }
                        
                        if(totalNumLeaders <= 2) // Check if there are already 3 or more leaders already
                        {
                            ArrayList<String> thirdLeaders = new ArrayList<>();
                            // -----------> Deconstruct list to clear it of 1st place tied players by calling deconstruct function
                            numAtBats = head.getData().calculateNumAtBats(head.getData().getHits(), head.getData().getOuts(), head.getData().getStrikeouts());
                            headBattingAverage = head.getData().calculateBattingAverage(head.getData().getHits(), numAtBats);

                            thirdLeaders.add(head.getData().getName());
                            // Print out 3rd leader and their batting average
                            bWriter.write("\n" + String.format("%.3f", headBattingAverage) + "\t");
                            //bWriter.write("\n" + String.format("%.3f", headBattingAverage) + "\t" + head.getData().getName()); // <------ Append node to new ties linked list instead of printing name
                            while(iter != null)
                            {

                                // Calculates the batting average of 3rd place leader and all other players
                                iterNumAtBats = iter.getData().calculateNumAtBats(iter.getData().getHits(), iter.getData().getOuts(), iter.getData().getStrikeouts());
                                iterBattingAverage = iter.getData().calculateBattingAverage(iter.getData().getHits(), iterNumAtBats);
                                headBattingAverage = head.getData().calculateBattingAverage(head.getData().getHits(), numAtBats);
                                
                                // If batting average of other players is equal to 3rd place leader print out player's name
                                if(iterBattingAverage == headBattingAverage)
                                {
                                    thirdLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName()); // <------ Append node to new ties linked list instead of printing name
                                }

                                // If not equal stop looping and print out nothing because there are no more leaders after 3rd
                                else
                                { 
                                    //bWriter.write("\n");
                                    break;
                                }
                                iter = iter.getNext();
                            }
                            list.sortArrayListNames(thirdLeaders);
                            for(int j = 0; j < thirdLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(thirdLeaders.get(j));
                                else
                                    bWriter.write(", " + thirdLeaders.get(j));
                            }
                            //bWriter.write("\n");
                        }
                        bWriter.write("\n");
                        break;
                    }
                
                case 1:
                    {
                        list.sortDescendingOnBasePercentage(head);
                        ArrayList<String> firstLeaders = new ArrayList<>();
                        bWriter.write("\nON-BASE PERCENTAGE\n");
                        int numPlateAppearances = head.getData().calculatePlateAppearances(head.getData().getHits(), head.getData().getOuts(), head.getData().getStrikeouts(), head.getData().getWalks(), head.getData().getHitByPitches(), head.getData().getSacrifices());
                        
                        // Print out 1st leader and their on-base percentage
                        firstLeaders.add(head.getData().getName());
                        bWriter.write(String.format("%.3f", head.getData().calculateOnBasePercentage(head.getData().getHits(), head.getData().getWalks(), head.getData().getHitByPitches(), numPlateAppearances)) + "\t");
                        //bWriter.write(String.format("%.3f", head.getData().calculateOnBasePercentage(head.getData().getHits(), head.getData().getWalks(), head.getData().getHitByPitches(), numPlateAppearances)) + "\t" + head.getData().getName());
                        Node<Player> iter = head.getNext();
                        int iterNumPlateAppearances;
                        double iterOnBasePercentage, headOnBasePercentage;
                        boolean tie = false;
                        // Loop through list to get all on-base percentage for first
                        while(iter != null)
                        {
                            // Calculates the on-base percentage of 1st place leader and all other players
                            iterNumPlateAppearances = iter.getData().calculatePlateAppearances(iter.getData().getHits(), iter.getData().getOuts(), iter.getData().getStrikeouts(), iter.getData().getWalks(), iter.getData().getHitByPitches(), iter.getData().getSacrifices());
                            iterOnBasePercentage = iter.getData().calculateOnBasePercentage(iter.getData().getHits(), iter.getData().getWalks(), iter.getData().getHitByPitches(), iterNumPlateAppearances);
                            headOnBasePercentage = head.getData().calculateOnBasePercentage(head.getData().getHits(), head.getData().getWalks(), head.getData().getHitByPitches(), numPlateAppearances);
                            //System.out.println(iterOnBasePercentage);
                            // If on-base percentage of other players is equal to 1st place leader print out player's name
                            if(Math.abs(iterOnBasePercentage - headOnBasePercentage) <= THRESHOLD)
                            {
                                firstLeaders.add(iter.getData().getName());
                                //System.out.println(iterOnBasePercentage);
                                //bWriter.write(", " + iter.getData().getName());
                                firstCounter++;
                                tie = true;
                            }

                            // If not equal stop looping and set head to player where on-place percentage was not equal (a.k.a 2nd place leader)
                            else
                            {
                                head = iter; // Move head to 2nd place leader
                                iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                break;
                            }
                            iter = iter.getNext(); // Iterates to next player if there is a tie
                        }
                        list.sortArrayListNames(firstLeaders);
                        for(int j = 0; j < firstLeaders.size(); j++)
                        {
                            if(j == 0)
                                bWriter.write(firstLeaders.get(j));
                            else
                                bWriter.write(", " + firstLeaders.get(j));
                        }
                        totalNumLeaders += firstCounter; // Increase total number of leaders
                        if(totalNumLeaders <= 2) // Check if there are 3 or more leaders already
                        {
                            ArrayList<String> secondLeaders = new ArrayList<>();
                            numPlateAppearances = head.getData().calculatePlateAppearances(head.getData().getHits(), head.getData().getOuts(), head.getData().getStrikeouts(), head.getData().getWalks(), head.getData().getHitByPitches(), head.getData().getSacrifices());
                            headOnBasePercentage = head.getData().calculateOnBasePercentage(head.getData().getHits(), head.getData().getWalks(), head.getData().getHitByPitches(), numPlateAppearances);

                            if(listSize > 2)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + String.format("%.3f", headOnBasePercentage) + "\t" + head.getData().getName());
                            else if(!tie && listSize > 1)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + String.format("%.3f", headOnBasePercentage) + "\t" + head.getData().getName());
                            else
                            { 
                                bWriter.write("\n");
                                break;
                            }

                            bWriter.write("\n" + String.format("%.3f", headOnBasePercentage) + "\t");
                            // Print out 2nd leader and their on-base percentage
                            while(iter != null)
                            {
                                // Calculates the on-base percentage of 2nd place leader and all other players
                                iterNumPlateAppearances = iter.getData().calculatePlateAppearances(iter.getData().getHits(), iter.getData().getOuts(), iter.getData().getStrikeouts(), iter.getData().getWalks(), iter.getData().getHitByPitches(), iter.getData().getSacrifices());
                                iterOnBasePercentage = iter.getData().calculateOnBasePercentage(iter.getData().getHits(), iter.getData().getWalks(), iter.getData().getHitByPitches(), iterNumPlateAppearances);
                                headOnBasePercentage = head.getData().calculateOnBasePercentage(head.getData().getHits(), head.getData().getWalks(), head.getData().getHitByPitches(), numPlateAppearances);
                                
                                // If on-base percentage of other players is equal to 1st place leader print out player's name
                                if(Math.abs(iterOnBasePercentage - headOnBasePercentage) <= THRESHOLD)
                                {
                                    secondLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                    secondCounter++;
                                }

                                // If not equal stop looping and set head to player where on-base percentage was not equal (a.k.a 3rd place leader)
                                else
                                {
                                    head = iter; // Move head to 3rd place leader
                                    iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                    break;
                                }
                                iter = iter.getNext(); // Iterates to next player if there is a tie
                            }
                            totalNumLeaders += secondCounter; // Increase total number of leaders
                            list.sortArrayListNames(secondLeaders);
                            //System.out.println(head.getData().getName());
                            for(int j = 0; j < secondLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(secondLeaders.get(j));
                                else
                                    bWriter.write(", " + secondLeaders.get(j));
                            }
                        }
                        else // If ties for 1st were >= 3 do not print out anything and end loop for that stat
                        { 
                            bWriter.write("\n");
                            break;
                        }
                        if(totalNumLeaders <= 2) // Check if there are already 3 or more leaders already
                        {
                            numPlateAppearances = head.getData().calculatePlateAppearances(head.getData().getHits(), head.getData().getOuts(), head.getData().getStrikeouts(), head.getData().getWalks(), head.getData().getHitByPitches(), head.getData().getSacrifices());
                            headOnBasePercentage = head.getData().calculateOnBasePercentage(head.getData().getHits(), head.getData().getWalks(), head.getData().getHitByPitches(), numPlateAppearances);

                            // Print out 3rd leader and their on-base percentage
                            ArrayList<String> thirdLeaders = new ArrayList<>();
                            thirdLeaders.add(head.getData().getName());
                            bWriter.write("\n" + String.format("%.3f", headOnBasePercentage) + "\t");
                            //bWriter.write("\n" + String.format("%.3f", headOnBasePercentage) + "\t" + head.getData().getName());
                            while(iter != null)
                            {

                                // Calculates the on-base percentage of 3rd place leader and all other players
                                iterNumPlateAppearances = iter.getData().calculatePlateAppearances(iter.getData().getHits(), iter.getData().getOuts(), iter.getData().getStrikeouts(), iter.getData().getWalks(), iter.getData().getHitByPitches(), iter.getData().getSacrifices());
                                iterOnBasePercentage = iter.getData().calculateOnBasePercentage(iter.getData().getHits(), iter.getData().getWalks(), iter.getData().getHitByPitches(), iterNumPlateAppearances);
                                headOnBasePercentage = head.getData().calculateOnBasePercentage(head.getData().getHits(), head.getData().getWalks(), head.getData().getHitByPitches(), numPlateAppearances);
                                
                                // If on-base percentage of other players is equal to 3rd place leader print out player's name
                                if(Math.abs(iterOnBasePercentage - headOnBasePercentage) <= THRESHOLD)
                                {
                                    thirdLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                }

                                // If not equal stop looping and print out nothing because there are no more leaders after 3rd
                                else
                                { 
                                    //bWriter.write("\n");
                                    break;
                                }
                                iter = iter.getNext();
                            }
                            list.sortArrayListNames(thirdLeaders);
                            //System.out.println(head.getData().getName());
                            for(int j = 0; j < thirdLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(thirdLeaders.get(j));
                                else
                                    bWriter.write(", " + thirdLeaders.get(j));
                            }
                            //bWriter.write("\n");
                        }
                        bWriter.write("\n");
                        break;
                    }
                
                case 2:
                    {
                        list.sortDescendingHits(head);
                        ArrayList<String> firstLeaders = new ArrayList<>();
                        bWriter.write("\nHITS\n");
                        
                        // Print out 1st leader and their hits
                        firstLeaders.add(head.getData().getName());
                        bWriter.write(head.getData().getHits() + "\t");
                        Node<Player> iter = head.getNext();
                        int iterNumHits, headNumHits;
                        boolean tie = false;
                        // Loop through list to get all hits for first
                        while(iter != null)
                        {
                            // Gets the number of hits of 1st place leader and all other players
                            iterNumHits = iter.getData().getHits();
                            headNumHits = head.getData().getHits();

                            // If number of hits of other players is equal to 1st place leader print out player's name
                            if(iterNumHits == headNumHits)
                            {
                                firstLeaders.add(iter.getData().getName());
                                //bWriter.write(", " + iter.getData().getName());
                                firstCounter++;
                                tie = true;
                            }
                            // If not equal stop looping and set head to player where number of hits was not equal (a.k.a 2nd place leader)
                            else
                            {
                                head = iter; // Move head to 2nd place leader
                                iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                break;
                            }
                            iter = iter.getNext(); // Iterates to next player if there is a tie
                        }
                        totalNumLeaders += firstCounter; // Increase total number of leaders
                        list.sortArrayListNames(firstLeaders);
                        for(int j = 0; j < firstLeaders.size(); j++)
                        {
                            if(j == 0)
                                bWriter.write(firstLeaders.get(j));
                            else
                                bWriter.write(", " + firstLeaders.get(j));
                        }
                        if(totalNumLeaders <= 2) // Check if there are 3 or more leaders already
                        {
                            headNumHits = head.getData().getHits();
                            ArrayList<String> secondLeaders = new ArrayList<>();
                            if(listSize > 2)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + head.getData().getHits() + "\t" + head.getData().getName());
                            else if(!tie && listSize > 1)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + head.getData().getHits() + "\t" + head.getData().getName());
                            else
                            { 
                                bWriter.write("\n");
                                break;
                            }
                            bWriter.write("\n" + head.getData().getHits() + "\t");
                            // Print out 2nd leader and their number of hits
                            //bWriter.write("\n" + head.getData().getHits() + "\t" + head.getData().getName());
                            while(iter != null)
                            {
                                // Gets the number of 2nd place leader and all other players
                                iterNumHits = iter.getData().getHits();
                                headNumHits = head.getData().getHits();
                                
                                // If number of hits of other players is equal to 1st place leader print out player's name
                                if(iterNumHits == headNumHits)
                                {
                                    secondLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                    secondCounter++;
                                }

                                // If not equal stop looping and set head to player where number of hits was not equal (a.k.a 3rd place leader)
                                else
                                {
                                    head = iter; // Move head to 3rd place leader
                                    iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                    break;
                                }
                                iter = iter.getNext(); // Iterates to next player if there is a tie
                            }
                            totalNumLeaders += secondCounter; // Increase total number of leaders
                            list.sortArrayListNames(secondLeaders);
                            //System.out.println(head.getData().getName());
                            for(int j = 0; j < secondLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(secondLeaders.get(j));
                                else
                                    bWriter.write(", " + secondLeaders.get(j));
                            }
                        }
                        else // If ties for 1st were >= 3 do not print out anything and end loop for that stat
                        { 
                            bWriter.write("\n");
                            break;
                        }
                        if(totalNumLeaders <= 2) // Check if there are already 3 or more leaders already
                        {
                            headNumHits = head.getData().getHits();
                            ArrayList<String> thirdLeaders = new ArrayList<>();
                            // Print out 3rd leader and their number of hits
                            thirdLeaders.add(head.getData().getName());
                            bWriter.write("\n" + headNumHits + "\t");
                            //bWriter.write("\n" + headNumHits + "\t" + head.getData().getName());
                            while(iter != null)
                            {

                                // Gets the number of hits of 3rd place leader and all other players
                                iterNumHits = iter.getData().getHits();
                                headNumHits = head.getData().getHits();
                                
                                // If the number of hits of other players is equal to 3rd place leader print out player's name
                                if(iterNumHits == headNumHits)
                                {
                                    thirdLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                }

                                // If not equal stop looping and print out nothing because there are no more leaders after 3rd
                                else
                                { 
                                    //bWriter.write("\n");
                                    break;
                                }
                                iter = iter.getNext();
                            }
                            list.sortArrayListNames(thirdLeaders);
                            //System.out.println(head.getData().getName());
                            for(int j = 0; j < thirdLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(thirdLeaders.get(j));
                                else
                                    bWriter.write(", " + thirdLeaders.get(j));
                            }
                            //bWriter.write("\n");
                        }
                        bWriter.write("\n");
                        break;
                    }
                
                case 3:
                    {
                        list.sortDescendingWalks(head);
                        ArrayList<String> firstLeaders = new ArrayList<>();
                        bWriter.write("\nWALKS\n");

                        // Print out 1st leader and their walks
                        firstLeaders.add(head.getData().getName());
                        bWriter.write(head.getData().getWalks() + "\t");
                        //bWriter.write(head.getData().getWalks() + "\t" + head.getData().getName());
                        Node<Player> iter = head.getNext();
                        int iterNumWalks, headNumWalks;
                        boolean tie = false;
                        // Loop through list to get all walks for first
                        while(iter != null)
                        {
                            // Gets the number of walks of 1st place leader and all other players
                            iterNumWalks = iter.getData().getWalks();
                            headNumWalks = head.getData().getWalks();

                            // If number of walks of other players is equal to 1st place leader print out player's name
                            if(iterNumWalks == headNumWalks)
                            {
                                firstLeaders.add(iter.getData().getName());
                                //bWriter.write(", " + iter.getData().getName());
                                firstCounter++;
                                tie = true;
                            }
                            // If not equal stop looping and set head to player where number of walks was not equal (a.k.a 2nd place leader)
                            else
                            {
                                head = iter; // Move head to 2nd place leader
                                iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                break;
                            }
                            iter = iter.getNext(); // Iterates to next player if there is a tie
                        }
                        totalNumLeaders += firstCounter; // Increase total number of leaders
                        list.sortArrayListNames(firstLeaders);
                        for(int j = 0; j < firstLeaders.size(); j++)
                        {
                            if(j == 0)
                                bWriter.write(firstLeaders.get(j));
                            else
                                bWriter.write(", " + firstLeaders.get(j));
                        }
                        if(totalNumLeaders <= 2) // Check if there are 3 or more leaders already
                        {
                            ArrayList<String> secondLeaders = new ArrayList<>();
                            headNumWalks = head.getData().getWalks();

                            if(listSize > 2)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + head.getData().getWalks() + "\t" + head.getData().getName());
                            else if(!tie && listSize > 1)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + head.getData().getWalks() + "\t" + head.getData().getName());
                            else
                            { 
                                bWriter.write("\n");
                                break;
                            }
                            // Print out 2nd leader and their number of walks
                            bWriter.write("\n" + head.getData().getWalks() + "\t");
                            //bWriter.write("\n" + head.getData().getWalks() + "\t" + head.getData().getName());
                            while(iter != null)
                            {
                                // Gets the number of 2nd place leader and all other players
                                iterNumWalks = iter.getData().getWalks();
                                headNumWalks = head.getData().getWalks();
                                
                                // If number of walks of other players is equal to 1st place leader print out player's name
                                if(iterNumWalks == headNumWalks)
                                {
                                    secondLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                    secondCounter++;
                                }

                                // If not equal stop looping and set head to player where number of walks was not equal (a.k.a 3rd place leader)
                                else
                                {
                                    head = iter; // Move head to 3rd place leader
                                    iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                    break;
                                }
                                iter = iter.getNext(); // Iterates to next player if there is a tie
                            }
                            totalNumLeaders += secondCounter; // Increase total number of leaders
                            list.sortArrayListNames(secondLeaders);
                            for(int j = 0; j < secondLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(secondLeaders.get(j));
                                else
                                    bWriter.write(", " + secondLeaders.get(j));
                            }
                        }
                        else // If ties for 1st were >= 3 do not print out anything and end loop for that stat
                        { 
                            bWriter.write("\n");
                            break;
                        }
                        if(totalNumLeaders <= 2) // Check if there are already 3 or more leaders already
                        {
                            ArrayList<String> thirdLeaders = new ArrayList<>();
                            headNumWalks = head.getData().getWalks();

                            // Print out 3rd leader and their number of walks
                            thirdLeaders.add(head.getData().getName());
                            bWriter.write("\n" + headNumWalks + "\t");
                            //bWriter.write("\n" + headNumWalks + "\t" + head.getData().getName());
                            while(iter != null)
                            {

                                // Gets the number of walks of 3rd place leader and all other players
                                iterNumWalks = iter.getData().getWalks();
                                headNumWalks = head.getData().getWalks();
                                
                                // If the number of walks of other players is equal to 3rd place leader print out player's name
                                if(iterNumWalks == headNumWalks)
                                {
                                    thirdLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                }

                                // If not equal stop looping and print out nothing because there are no more leaders after 3rd
                                else
                                { 
                                    //bWriter.write("\n");
                                    break;
                                }
                                iter = iter.getNext();
                            }
                            list.sortArrayListNames(thirdLeaders);
                            //System.out.println(head.getData().getName());
                            for(int j = 0; j < thirdLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(thirdLeaders.get(j));
                                else
                                    bWriter.write(", " + thirdLeaders.get(j));
                            }
                            //bWriter.write("\n");
                        }
                        bWriter.write("\n");
                        break;
                    }
                
                case 4:
                    {
                        list.sortDescendingStrikeouts(head);
                        ArrayList<String> firstLeaders = new ArrayList<>();
                        bWriter.write("\nSTRIKEOUTS\n");

                        // Print out 1st leader and their strikeous
                        firstLeaders.add(head.getData().getName());
                        bWriter.write(head.getData().getStrikeouts() + "\t");
                        //bWriter.write(head.getData().getStrikeouts() + "\t" + head.getData().getName());
                        Node<Player> iter = head.getNext();
                        int iterNumStrikeouts, headNumStrikeouts;
                        boolean tie = false;
                        // Loop through list to get all strikeouts for first
                        while(iter != null)
                        {
                            // Gets the number of strikeouts of 1st place leader and all other players
                            iterNumStrikeouts = iter.getData().getStrikeouts();
                            headNumStrikeouts = head.getData().getStrikeouts();

                            // If number of strikeouts of other players is equal to 1st place leader print out player's name
                            if(iterNumStrikeouts == headNumStrikeouts)
                            {
                                firstLeaders.add(iter.getData().getName());
                                //bWriter.write(", " + iter.getData().getName());
                                firstCounter++;
                                tie = true;
                            }
                            // If not equal stop looping and set head to player where number of strikeouts was not equal (a.k.a 2nd place leader)
                            else
                            {
                                head = iter; // Move head to 2nd place leader
                                iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                break;
                            }
                            iter = iter.getNext(); // Iterates to next player if there is a tie
                        }
                        totalNumLeaders += firstCounter; // Increase total number of leaders
                        list.sortArrayListNames(firstLeaders);
                        for(int j = 0; j < firstLeaders.size(); j++)
                        {
                            if(j == 0)
                                bWriter.write(firstLeaders.get(j));
                            else
                                bWriter.write(", " + firstLeaders.get(j));
                        }
                        if(totalNumLeaders <= 2) // Check if there are 3 or more leaders already
                        {
                            ArrayList<String> secondLeaders = new ArrayList<>();
                            headNumStrikeouts = head.getData().getStrikeouts();

                            if(listSize > 2)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + head.getData().getStrikeouts() + "\t" + head.getData().getName());
                            else if(!tie && listSize > 1)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + head.getData().getStrikeouts() + "\t" + head.getData().getName());
                            else
                            {
                                bWriter.write("\n");
                                break;
                            }
                            // Print out 2nd leader and their number of strikeouts
                            bWriter.write("\n" + head.getData().getStrikeouts() + "\t");
                            //bWriter.write("\n" + head.getData().getStrikeouts() + "\t" + head.getData().getName());
                            while(iter != null)
                            {
                                // Gets the number of 2nd place leader and all other players
                                iterNumStrikeouts = iter.getData().getStrikeouts();
                                headNumStrikeouts = head.getData().getStrikeouts();
                                
                                // If number of strikeouts of other players is equal to 1st place leader print out player's name
                                if(iterNumStrikeouts == headNumStrikeouts)
                                {
                                    secondLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                    secondCounter++;
                                }

                                // If not equal stop looping and set head to player where number of strikeouts was not equal (a.k.a 3rd place leader)
                                else
                                {
                                    head = iter; // Move head to 3rd place leader
                                    iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                    break;
                                }
                                iter = iter.getNext(); // Iterates to next player if there is a tie
                            }
                            totalNumLeaders += secondCounter; // Increase total number of leaders
                            list.sortArrayListNames(secondLeaders);
                            for(int j = 0; j < secondLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(secondLeaders.get(j));
                                else
                                    bWriter.write(", " + secondLeaders.get(j));
                            }
                        }
                        else // If ties for 1st were >= 3 do not print out anything and end loop for that stat
                        { 
                            bWriter.write("\n");
                            break;
                        }
                        if(totalNumLeaders <= 2) // Check if there are already 3 or more leaders already
                        {
                            ArrayList<String> thirdLeaders = new ArrayList<>();
                            headNumStrikeouts = head.getData().getStrikeouts();

                            // Print out 3rd leader and their number of strikeouts
                            thirdLeaders.add(head.getData().getName());
                            bWriter.write("\n" + headNumStrikeouts + "\t");
                            //bWriter.write("\n" + headNumStrikeouts + "\t" + head.getData().getName());
                            while(iter != null)
                            {

                                // Gets the number of strikeouts of 3rd place leader and all other players
                                iterNumStrikeouts = iter.getData().getStrikeouts();
                                headNumStrikeouts = head.getData().getStrikeouts();
                                
                                // If the number of strikeouts of other players is equal to 3rd place leader print out player's name
                                if(iterNumStrikeouts == headNumStrikeouts)
                                {
                                    thirdLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                }

                                // If not equal stop looping and print out nothing because there are no more leaders after 3rd
                                else
                                { 
                                    //bWriter.write("\n");
                                    break;
                                }
                                iter = iter.getNext();
                            }
                            list.sortArrayListNames(thirdLeaders);
                            //System.out.println(head.getData().getName());
                            for(int j = 0; j < thirdLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(thirdLeaders.get(j));
                                else
                                    bWriter.write(", " + thirdLeaders.get(j));
                            }
                            //bWriter.write("\n");
                        }
                        bWriter.write("\n");
                        break;
                    }
                
                case 5:
                    {
                        list.sortDescendingHitByPitches(head);
                        ArrayList<String> firstLeaders = new ArrayList<>();
                        bWriter.write("\nHIT BY PITCH\n");

                        // Print out 1st leader and their hit by pitches
                        firstLeaders.add(head.getData().getName());
                        bWriter.write(head.getData().getHitByPitches() + "\t");
                        //bWriter.write(head.getData().getHitByPitches() + "\t" + head.getData().getName());
                        Node<Player> iter = head.getNext();
                        int iterNumHitByPitches, headNumHitByPitches;
                        boolean tie = false;
                        // Loop through list to get all hit by pitches for first
                        while(iter != null)
                        {
                            // Gets the number of hit by pitches of 1st place leader and all other players
                            iterNumHitByPitches = iter.getData().getHitByPitches();
                            headNumHitByPitches = head.getData().getHitByPitches();

                            // If number of hit by pitches of other players is equal to 1st place leader print out player's name
                            if(iterNumHitByPitches == headNumHitByPitches)
                            {
                                firstLeaders.add(iter.getData().getName());
                                //bWriter.write(", " + iter.getData().getName());
                                firstCounter++;
                                tie = true;
                            }
                            // If not equal stop looping and set head to player where number of hit by pitches was not equal (a.k.a 2nd place leader)
                            else
                            {
                                head = iter; // Move head to 2nd place leader
                                iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                break;
                            }
                            iter = iter.getNext(); // Iterates to next player if there is a tie
                        }
                        totalNumLeaders += firstCounter; // Increase total number of leaders
                        list.sortArrayListNames(firstLeaders);
                        for(int j = 0; j < firstLeaders.size(); j++)
                        {
                            if(j == 0)
                                bWriter.write(firstLeaders.get(j));
                            else
                                bWriter.write(", " + firstLeaders.get(j));
                        }
                        if(totalNumLeaders <= 2) // Check if there are 3 or more leaders already
                        {
                            ArrayList<String> secondLeaders = new ArrayList<>();
                            headNumHitByPitches = head.getData().getHitByPitches();

                            if(listSize > 2)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + head.getData().getHitByPitches() + "\t" + head.getData().getName());
                            else if(!tie && listSize > 1)
                                secondLeaders.add(head.getData().getName());
                                //bWriter.write("\n" + head.getData().getHitByPitches() + "\t" + head.getData().getName());
                            else
                            {
                                bWriter.write("\n\n");
                                break;
                            }
                            // Print out 2nd leader and their number of hit by pithes
                            bWriter.write("\n" + head.getData().getHitByPitches() + "\t");
                            //bWriter.write("\n" + head.getData().getHitByPitches() + "\t" + head.getData().getName());
                            while(iter != null)
                            {
                                // Gets the number of 2nd place leader and all other players
                                iterNumHitByPitches = iter.getData().getHitByPitches();
                                headNumHitByPitches = head.getData().getHitByPitches();
                                
                                // If number of hit by pitches of other players is equal to 1st place leader print out player's name
                                if(iterNumHitByPitches == headNumHitByPitches)
                                {
                                    secondLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                    secondCounter++;
                                }

                                // If not equal stop looping and set head to player where number of hit by pitches was not equal (a.k.a 3rd place leader)
                                else
                                {
                                    head = iter; // Move head to 3rd place leader
                                    iter = iter.getNext(); // Move iterator to next player after 2nd place leader
                                    break;
                                }
                                iter = iter.getNext(); // Iterates to next player if there is a tie
                            }
                            totalNumLeaders += secondCounter; // Increase total number of leaders
                            list.sortArrayListNames(secondLeaders);
                            for(int j = 0; j < secondLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(secondLeaders.get(j));
                                else
                                    bWriter.write(", " + secondLeaders.get(j));
                            }
                            
                        }
                        else // If ties for 1st were >= 3 do not print out anything and end loop for that stat
                        { 
                            bWriter.write("\n");
                            break;
                        }
                        if(totalNumLeaders <= 2) // Check if there are already 3 or more leaders already
                        {
                            ArrayList<String> thirdLeaders = new ArrayList<>();
                            headNumHitByPitches = head.getData().getHitByPitches();

                            // Print out 3rd leader and their number of hit by pitches
                            thirdLeaders.add(head.getData().getName());
                            bWriter.write("\n" + headNumHitByPitches + "\t");
                            //bWriter.write("\n" + headNumHitByPitches + "\t" + head.getData().getName());
                            while(iter != null)
                            {

                                // Gets the number of hit by pitches of 3rd place leader and all other players
                                iterNumHitByPitches = iter.getData().getHitByPitches();
                                headNumHitByPitches = head.getData().getHitByPitches();
                                
                                // If the number of hit by pitches of other players is equal to 3rd place leader print out player's name
                                if(iterNumHitByPitches == headNumHitByPitches)
                                {
                                    thirdLeaders.add(iter.getData().getName());
                                    //bWriter.write(", " + iter.getData().getName());
                                }

                                // If not equal stop looping and print out nothing because there are no more leaders after 3rd
                                else
                                { 
                                    //bWriter.write("\n");
                                    break;
                                }
                                iter = iter.getNext();
                            }
                            list.sortArrayListNames(thirdLeaders);
                            //System.out.println(head.getData().getName());
                            for(int j = 0; j < thirdLeaders.size(); j++)
                            {
                                if(j == 0)
                                    bWriter.write(thirdLeaders.get(j));
                                else
                                    bWriter.write(", " + thirdLeaders.get(j));
                            }
                            //bWriter.write("\n");
                        }
                        bWriter.write("\n\n");
                        break;
                    }
                default:
                    {
                        System.out.println("\n\nCounter is too high!\n");
                        break;
                    }
                    
            }
        }
        bWriter.close();
        // Close files
        bWriter.close();
        fileReader.close();
        input.close();

    }
    
}   
