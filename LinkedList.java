public class LinkedList<T> {
    private Node<T> head;

    public LinkedList()
    {
        head = null;
    }

    public LinkedList(Node<T> n)
    {
        head = n;
    }

    public void appendList(Node<T> n)
    {
        if(head != null)
        {
            Node<T> iter = head;
            while(iter.getNext() != null)
            {
                iter = iter.getNext();
            }
            iter = n;
        }
        else
        {
            head = n;
            head.setNext(null);
        }
    }

    public void addNodeToBeginning(Node<T> n)
    {
        n.setNext(head);
        head = n;
    }
}