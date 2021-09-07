package RootCalculator;

public class SLinkedList {

    protected SNode head;

    public SLinkedList() {
        head = new SNode();
    }

    //adds different calculated values from methods at assigned space.
    public void add(String x1, String fx1, String x2, String fx2, String x3, String fx3) {
        SNode tail;
        tail = head;

        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        //insert new node at end of list
        tail.setNext(new SNode(x1, fx1, x2, fx2, x3, fx3, null));

    }

    //add a new node after position of curnode.
    public boolean isEmpty() {
        return head == null;
    }

    //clears out the whole list.
    public void clearList() {
        
        head.setNext(null);
       
    }
}
