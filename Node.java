// Alex Nguyen
// atn170001

public class Node<T>{

    private T data;
    private Node<T> next;

    public Node()
    {
        data = null;
        next = null;
    }

    public Node(T d)
    {
        data = d;
    }
    public Node(T d, Node<T> n)
    {
        data = d;
        next = n;
    }

    public T getData()
    {
        return data;
    }

    public Node<T> getNext()
    {
        return next;
    }

    public void setData(T payload)
    {
        data = payload;
    }

    public void setNext(Node<T> n)
    {
        next = n;
    }
}
