/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RootCalculator;

public class SNode {
  // Instance variables:
  private final String[] element = new String[6];
  private SNode next;
  /** Creates a node with null references to its element and next node. */
  public SNode() {
    this("","","","","","", null);
  }
  /** Creates a node with the given element and next node.
   * @param x1 @param fx1 @param x2 @param fx2 @param x3 @param fx3 @param n */
  public SNode(String x1, String fx1,String x2,String fx2,String x3, String fx3,  SNode n) {
    
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
  public SNode getNext() { 
    return next;
  }
 
  public void setNext(SNode newNext) {
    next = newNext; 
  }
}
