package RootCalculator;

public class SNodeModel {
  // Instance variables.
  private final String[] element = new String[6];
  private SNodeModel next;
  /** Creates a node with null references to its element and next node. */
  public SNodeModel() {
    this("","","","","","", null);
  }
  /** Creates a node with the given element and next node.
   * @param x1 @param fx1 @param x2 @param fx2 @param x3 @param fx3 @param n */
  public SNodeModel(String x1, String fx1,String x2,String fx2,String x3, String fx3,  SNodeModel n) {
    
      element[0] = x1;
      element[1] = fx1;
      element[2] = x2;
      element[3] = fx2;
      element[4] = x3;
      element[5] = fx3;
     
    
      next = n;
  }
  // Accessor methods:
  public String[] getElement() {
    
      return element; 
      
  }
  public SNodeModel getNext() { 
    return next;
  }
 
  public void setNext(SNodeModel newNext) {
    next = newNext; 
  }
}
