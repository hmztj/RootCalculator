package RootCalculator;

public class SLinkedListModel {

    protected SNodeModel head;

    public SLinkedListModel() {
        head = new SNodeModel();
    }

    //adds different calculated values from methods at assigned space.
    public void add(String x1, String fx1, String x2, String fx2, String x3, String fx3) {
        SNodeModel tail;
        tail = head;

        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        //insert new node at end of list
        tail.setNext(new SNodeModel(x1, fx1, x2, fx2, x3, fx3, null));

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
