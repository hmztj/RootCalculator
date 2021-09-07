/*
 * Filename: RootCalculator
 * Path: https://github.com/hmztj/RootCalculator
 * Created Date: Monday, September 6th 2021, 10:11:07 pm
 * Author: Hamza
 */ 

package RootCalculator;

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
