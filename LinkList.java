// Alex Nguyen
// atn170001

import java.io.BufferedWriter;
import java.io.IOException;

public class LinkList<T> {
    private Node<T> head;
    static final int NUM_LEADER_CATEGORIES = 5;

    /**
     * Default constructor for linked list
     */
    public LinkList()
    {
        head = null;
    }

    /**
     * Overloaded constructor for linked list
     * @param n head node of linked list
     */
    public LinkList(Node<T> n)
    {
        head = n;
    }

    /**
     * Adds node to end of linked list
     * @param n node to add to end of list
     */
    public void appendList(Node<T> n)
    {
        if(this.head != null)
        {
            Node<T> iter = this.head;
            while(iter.getNext() != null)
            {
                iter = iter.getNext();
            }
            iter.setNext(n);
            //iter.getNext().setNext(null);
            return;
        }
        else
        {
            this.head = n;
            //head.setNext(null);
            return;
        }
    }

    /**
     * Returns head of linked list
     * @return head of linked list
     */
    public Node<T> getHead()
    {
        return head;
    }

    /**
     * Sets head of linked list
     * @param h head of linked list
     */
    public void setHead(Node<T> h)
    {
        this.head = h;
    }

    /**
     * Displays players' data in file recursively
     * @param h head node of list
     * @param bWriter buffered writer that has the output file
     */
    public void displayPlayerData(Node<T> h, BufferedWriter bWriter) throws IOException
    {
        try {
            if(h == null) // Check if no node in list
                return;
            else
            {  
                // Display all stats of player
                int numAtBats = ((Player)h.getData()).calculateNumAtBats(((Player)h.getData()).getHits(),((Player)h.getData()).getOuts(), ((Player)h.getData()).getStrikeouts());
                int numPlateAppearances = ((Player)h.getData()).calculatePlateAppearances(((Player)h.getData()).getHits(), ((Player)h.getData()).getOuts(), ((Player)h.getData()).getStrikeouts(), ((Player)h.getData()).getWalks(), ((Player)h.getData()).getHitByPitches(), ((Player)h.getData()).getSacrifices());
                bWriter.write(((Player)h.getData()).getName() + "\t");
                bWriter.write(numAtBats + "\t");
                bWriter.write(((Player)h.getData()).getHits() + "\t");
                bWriter.write(((Player)h.getData()).getWalks() + "\t");
                bWriter.write(((Player)h.getData()).getStrikeouts() + "\t");
                bWriter.write(((Player)h.getData()).getHitByPitches() + "\t");
                bWriter.write(((Player)h.getData()).getSacrifices() + "\t");
                bWriter.write(String.format("%.3f", ((Player)h.getData()).calculateBattingAverage(((Player)h.getData()).getHits(), numAtBats)) + "\t");
                bWriter.write(String.format("%.3f", ((Player)h.getData()).calculateOnBasePercentage(((Player)h.getData()).getHits(), ((Player)h.getData()).getWalks(), ((Player)h.getData()).getHitByPitches(), numPlateAppearances)));
                bWriter.write("\n");
                h = h.getNext();
                displayPlayerData(h, bWriter); // Recursive call of display function
            }
        }
        catch(IOException exp)
        {
            throw exp;
        }
    }

    /**
     * Gets size of linked list
     * @param head head of linked list
     * @return size of linked list
     */
    public int getListSize(Node<T> head)
    {   
        int numNodes = 0;
        while(head != null)
        {
            numNodes++;
            head = head.getNext();
        }
        return numNodes;
    }

    /**
     * Deletes linked list
     */
    public void destroyLinkList()
    {
        head = null;
    }

    /**
     * Sorts linked list of players alphabetically by name
     * @param head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortAlphabetically(Node<T> head)
    {  
        boolean swapped = true;
        
        if(head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else
        {
            while(swapped)
            {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while(iter != null)
                {
                    // Compare the name of players
                    if(((((Player)head.getData()).getName()).toLowerCase()).compareTo((((Player)iter.getData()).getName()).toLowerCase()) > 0)
                    {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        }
    } 

    /**
     * Sorts linked list in desending order by players' number of hits
     * @param head head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortDescendingHits(Node<T> head)
    {
        boolean swapped = true;
        if(head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else
        {
            while(swapped)
            {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while(iter != null)
                {
                    // Compare hits stat of players
                    if(((Player)head.getData()).getHits() < ((Player)iter.getData()).getHits())
                    {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        }        
    }

    /**
     * Sorts linked list in desending order by players' number of outs
     * @param head head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortDescendingOuts(Node<T> head)
    {
        boolean swapped = true;
        if(head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else
        {
            while(swapped)
            {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while(iter != null)
                {
                    // Compare outs stat of players
                    if(((Player)head.getData()).getOuts() > ((Player)iter.getData()).getOuts())
                    {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        } 
    }

    /**
     * Sorts linked list in desending order by players' number of strikeouts
     * @param head head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortAscendingStrikeouts(Node<T> head)
    {
        boolean swapped = true;
        if(head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else
        {
            while(swapped)
            {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while(iter != null)
                {
                    // Compare strikeouts stat of players
                    if(((Player)head.getData()).getStrikeouts() > ((Player)iter.getData()).getStrikeouts())
                    {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        } 
    }

    /**
     * Sorts linked list in desending order by players' number of walks
     * @param head head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortDescendingWalks(Node<T> head)
    {
        boolean swapped = true;
        if(head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else
        {
            while(swapped)
            {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while(iter != null)
                {
                    // Compare walks stat of players
                    if(((Player)head.getData()).getWalks() < ((Player)iter.getData()).getWalks())
                    {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        } 
    }

    /**
     * Sorts linked list in desending order by players' number of hit by pitches
     * @param head head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortDescendingHitByPitches(Node<T> head)
    {
        boolean swapped = true;
        if(head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else
        {
            while(swapped)
            {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while(iter != null)
                {
                    // Compare hit by pitches stat of players
                    if(((Player)head.getData()).getHitByPitches() < ((Player)iter.getData()).getHitByPitches())
                    {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        } 
    }

    /**
     * Sorts linked list in desending order by players' number of sacrifices
     * @param head head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortDescendingSacrifices(Node<T> head)
    {
        boolean swapped = true;
        if(head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else
        {
            while(swapped)
            {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while(iter != null)
                {
                    // Compare sacrifices stat of players
                    if(((Player)head.getData()).getSacrifices() < ((Player)iter.getData()).getSacrifices())
                    {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        } 
    }

    /**
     * Sorts linked list in desending order by players' batting average
     * @param head head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortDescendingBattingAverage(Node<T> head)
    {
        boolean swapped = true;
        if(head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else
        {
            int headAtBats, iterAtBats;
            double headBattingAverage, iterBattingAverage;
            while(swapped)
            {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while(iter != null)
                {
                    // Compare batting average stat of players
                    headAtBats = ((Player)head.getData()).calculateNumAtBats(((Player)head.getData()).getHits(), ((Player)head.getData()).getOuts(), ((Player)head.getData()).getStrikeouts());          
                    iterAtBats = ((Player)iter.getData()).calculateNumAtBats(((Player)iter.getData()).getHits(), ((Player)iter.getData()).getOuts(), ((Player)iter.getData()).getStrikeouts());
                    headBattingAverage = ((Player)head.getData()).calculateBattingAverage(((Player)head.getData()).getHits(), headAtBats);
                    iterBattingAverage = ((Player)iter.getData()).calculateBattingAverage(((Player)iter.getData()).getHits(), iterAtBats);
                    if(headBattingAverage < iterBattingAverage)
                    {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        } 
    }

    /**
     * Sorts linked list in desending order by players' on base percentage
     * @param head head of linked list to sort
     * @return head node of list
     */
    public Node<T> sortDescendingOnBasePercentage(Node<T> head)
    {
        boolean swapped = true;
        if(head == null || head.getNext() == null) // If only 1 node in list or none
            return head;
        else
        {
            int headPlateAppearances, iterPlateAppearances;
            double headOnBasePercentage, iterOnBasePercentage;
            while(swapped)
            {
                swapped = false;
                Node<T> iter = getHead().getNext();
                head = getHead();
                while(iter != null)
                {
                    // Compare on-base percentage stat of players
                    headPlateAppearances = ((Player)head.getData()).calculatePlateAppearances(((Player)head.getData()).getHits(), ((Player)head.getData()).getOuts(), ((Player)head.getData()).getStrikeouts(), ((Player)head.getData()).getWalks(), ((Player)head.getData()).getHitByPitches(), ((Player)head.getData()).getSacrifices());         
                    iterPlateAppearances = ((Player)iter.getData()).calculatePlateAppearances(((Player)iter.getData()).getHits(), ((Player)iter.getData()).getOuts(), ((Player)iter.getData()).getStrikeouts(), ((Player)iter.getData()).getWalks(), ((Player)iter.getData()).getHitByPitches(), ((Player)iter.getData()).getSacrifices());
                    headOnBasePercentage = ((Player)head.getData()).calculateOnBasePercentage(((Player)head.getData()).getHits(), ((Player)head.getData()).getWalks(), ((Player)head.getData()).getHitByPitches(), headPlateAppearances);
                    iterOnBasePercentage = ((Player)iter.getData()).calculateOnBasePercentage(((Player)iter.getData()).getHits(), ((Player)iter.getData()).getWalks(), ((Player)iter.getData()).getHitByPitches(), iterPlateAppearances);
                    if(headOnBasePercentage < iterOnBasePercentage)
                    {
                        // Swap
                        Node<T> temp = new Node<>();
                        temp.setData(head.getData());
                        head.setData(iter.getData());
                        iter.setData(temp.getData());
                        swapped = true;
                    }
                    head = head.getNext();
                    iter = iter.getNext();
                }
            }
            return getHead();
        } 
    } 
}
