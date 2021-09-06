/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RootCalculator;

/**
 *
 * @author Hamza
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class RootCalculator {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        CalcView theView = new CalcView();
        centreWindow(theView);
        CalcModel theModel = new CalcModel();
        CalcController theController = new CalcController(theView, theModel);
        theView.setVisible(true);

    }

    public static void centreWindow(JFrame frame) {

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
        frame.setLocation(x, y);

    }

}
