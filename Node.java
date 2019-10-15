// Alex Nguyen
// atn170001

public class Node<T> implements Comparable<Node<T>>{

    // Private data members
    private T data;
    private Node<T> next;

    /**
     * Default constructor of node
     */
    public Node()
    {
        data = null;
        next = null;
    }

    /**
     * Constructor of specific node
     * @param d payload to give node
     */
    public Node(T d)
    {
        data = d;
    }

    /**
     * Construct node with next position
     * @param d payload to give node
     * @param n next node to set
     */
    public Node(T d, Node<T> n)
    {
        data = d;
        next = n;
    }

    /**
     * Returns data of node
     * @return data of node
     */
    public T getData()
    {
        return data;
    }

    /**
     * Returns next node
     * @return next node
     */
    public Node<T> getNext()
    {
        return next;
    }

    /**
     * Sets payload of node
     * @param payload data of node
     */
    public void setData(T payload)
    {
        data = payload;
    }

    /**
     * Sets next node
     * @param n next node
     */
    public void setNext(Node<T> n)
    {
        next = n;
    }

    /**
     * Overrided toString method to display player's data
     * @return player's data
     */
    @Override
    public String toString()
    {
        int numAtBats = ((Player)data).calculateNumAtBats(((Player)data).getHits(),((Player)data).getOuts(), ((Player)data).getStrikeouts());
        int numPlateAppearances = ((Player)data).calculatePlateAppearances(((Player)data).getHits(), ((Player)data).getOuts(), ((Player)data).getStrikeouts(), ((Player)data).getWalks(), ((Player)data).getHitByPitches(), ((Player)data).getSacrifices());
        return  ((Player) data).getName() + "\t"
                + numAtBats + "\t"
                + ((Player)data).getHits() + "\t"
                + ((Player)data).getWalks() + "\t"
                + ((Player)data).getStrikeouts() + "\t"
                + ((Player)data).getHitByPitches() + "\t"
                + ((Player)data).getSacrifices() + "\t"
                + String.format("%.3f", ((Player)data).calculateBattingAverage(((Player)data).getHits(), numAtBats)) + "\t"
                + String.format("%.3f", ((Player)data).calculateOnBasePercentage(((Player)data).getHits(), ((Player)data).getWalks(), ((Player)data).getHitByPitches(), numPlateAppearances))
                + "\n";
    }

    /**
     * Compares names of 2 node objects
     * @param obj Node to compare to
     * @return number to determine if names are equal or not
     */
    @Override
    public int compareTo(Node<T> obj)
    {
        int result = ((Player)this.data).getName().toLowerCase().compareTo(((Player)obj.getData()).getName().toLowerCase());
        if(result == 0)
        {
            return 0;
        }
        else
        {
            return result;
        }
    }

    /**
     * Overloaded compareTo to compare names of players by inputing specific name
     * @param name name of player to compare to
     * @return number to determine if names are equal or not
     */
    public int compareTo(String name)
    {
        int result = ((Player)this.data).getName().toLowerCase().compareTo(name.toLowerCase());
        if(result == 0)
        {
            return 0;
        }
        else
        {
            return result;
        }
    }
}
