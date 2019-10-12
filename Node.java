// Alex Nguyen
// atn170001

public class Node<T>{

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
}
