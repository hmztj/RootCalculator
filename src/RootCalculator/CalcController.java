package RootCalculator;

import java.awt.event.*;

import org.jfree.data.xy.XYSeries;

/* 
   The Controller coordinates interactions
   between the View and Model
 */
public class CalcController {

    // Declared model and view objects (variables)
    private final CalcView theView;
    private final CalcModel theModel;
    private XYSeries rootLine, generalSeries;
    int sIndex;

    // Constructor for controller class takes model and view as arguements
    public CalcController(CalcView theView, CalcModel theModel) {

        // initializing view and model objects.
        this.theView = theView;
        this.theModel = theModel;

        // initialize actions listeners for view class.
        this.theView.addCalculateListener(new CalculateListener());
        this.theView.addMethodComboListener(new MethodComboListener());
        this.theView.addFunctionComboListener(new FunctionComboListener());
        this.theView.addClearButtonListener(new ClearButtonListener());

    }

    // inner class to implement action performed method for clear button
    class ClearButtonListener implements ActionListener {

        // Clears Everything on the screen and reset all components with their
        // respective variables
        @Override
        public void actionPerformed(ActionEvent e) {

            theView.resetProgram();
            theModel.clearData();

        }

    }

    class FunctionComboListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (theView.getFunctionsIndex() == 1) {

                theView.getGraphDataSet().removeAllSeries();
                generalSeries = new XYSeries("general");
                for (int i = -5; i <= 15; i++) {
                    double x;
                    x = i * 0.1;
                    generalSeries.addOrUpdate(x, x - (x * x));
                }
                theView.plotGraph(generalSeries);

            } else if (theView.getFunctionsIndex() == 2) {

                theView.getGraphDataSet().removeAllSeries();
                generalSeries = new XYSeries("general");
                for (int i = -8; i <= 8; i++) {
                    double x;
                    x = i * 0.1;
                    generalSeries.addOrUpdate(x, Math.log(x + 1) + 1);
                }
                theView.plotGraph(generalSeries);

            } else if (theView.getFunctionsIndex() == 3) {

                theView.getGraphDataSet().removeAllSeries();
                generalSeries = new XYSeries("general");
                for (int i = -1; i <= 19; i++) {
                    double x;
                    x = i * 0.1;
                    generalSeries.addOrUpdate(x, Math.exp(x) - 3 * x);
                }
                theView.plotGraph(generalSeries);

            }

        }

    }

    // inner class to implement action performed method for methods
    // combobox(specifically for newton method)
    class MethodComboListener implements ActionListener {

        // checks if newton method is selected and then perform action accordingly
        @Override
        public void actionPerformed(ActionEvent e) {

            if (theView.getMethodsIndex() == 3) {

                theView.setColumnNames(true);
                theView.setSecondTextField(false); // makes textfield for second input unusable for the user.

            } else {

                theView.setColumnNames(false);
                theView.setSecondTextField(true);
            }
        }
    }

    /*
     * inner class to implement Action Performed Method for calculate Button (ALL
     * CALCULATIONS OCCUR WHEN THIS BUTTON IS CLICKED)
     */
    class CalculateListener implements ActionListener {

        /*
         * call values from view class and store them in local variables to pass them
         * into the model class for final calculations
         */
        @Override
        public void actionPerformed(ActionEvent e) {

            // local Variables to store user values.
            double xOne, xTwo, f_xOne, f_xTwo;
            int decimalplaces;

            int function_index, method_index; // stores function and method index respectively.

            /*
             * clear tables,arrays and graphs, if user press the button again for next
             * calculation. so new values can be parse into the respective variables and
             * objects.
             */
            theView.clearGraphTableDataset();
            theModel.clearData();

            // try and catch Block to catch any exceptions.
            try {

                xOne = theView.get_xOne();
                xTwo = theView.get_xTwo();

                function_index = theView.getFunctionsIndex();
                method_index = theView.getMethodsIndex();

                decimalplaces = theView.getDecimalPlaces();

                f_xOne = theModel.f(xOne, function_index);
                f_xTwo = theModel.f(xTwo, function_index);

                System.out.println(xOne + ":" + xTwo + ":" + f_xOne + ":" + f_xTwo);

                // if else statements to catch calculation related errors.
                if (function_index == 0) {

                    // tell user that they have not selected any function. Stops application from
                    // going further.
                    theView.resetTable();
                    theView.displayErrorMessage("Please Select a Function");

                } else if (method_index == 0) {

                    // tell user that they have not selected any Method. Stops application from
                    // going further.
                    theView.resetTable();
                    theView.displayErrorMessage("Please Select a Method");

                } else if (decimalplaces < Math.pow(10, -16)) {

                    // tells user the decimal places have gone out of range. (Double number limit)
                    theView.resetTable();
                    theView.resetDecimalPlace();
                    theView.displayErrorMessage("Decimal Places Out of Range\nPlease, choose value between 1-16.");

                } else {

                    // decides which method to call from model class depending on selected index.
                    switch (method_index) {
                        case 1: // Bisection Method

                            // checks arithmatic requirement for bisection method
                            if (f_xOne * f_xTwo <= 0) {

                                // returns value to model class
                                theModel.BisectionMethod(xOne, xTwo, function_index, decimalplaces);

                                // sets calculated value in the view class
                                theView.setRootTextField(theModel.getRoot());

                                rootLine = new XYSeries("root");
                                rootLine.addOrUpdate(theModel.getRoot(), 0.6);
                                rootLine.addOrUpdate(theModel.getRoot(), -0.6);

                                // pass data to populate table in view class
                                theView.setArrayTable(theModel.getArrayList());

                                // pass data to plot graph in view class.
                                // theView.plotGraph(generalSeries);
                                theView.plotGraph(theModel.getSeries());
                                theView.plotGraph(theModel.getInitialRange());
                                theView.plotGraph(rootLine);

                                break;

                            } else {

                                // shows error if calculation is not possible
                                theView.resetInputFields();
                                theView.displayErrorMessage(
                                        "Values entered does not converge\n     => f(x1)*f(x2) > 0");
                                break;

                            }

                        case 2: // Secant Method

                            // returns value to model class
                            //

                            theModel.SecantMethod(xOne, xTwo, function_index, decimalplaces);

                            // sets calculated value in the view class
                            theView.setRootTextField(theModel.getRoot());

                            rootLine = new XYSeries("root");
                            rootLine.addOrUpdate(theModel.getRoot(), 0.6);
                            rootLine.addOrUpdate(theModel.getRoot(), -0.6);

                            // pass data to populate table in view class
                            theView.setArrayTable(theModel.getArrayList());

                            // pass data to plot graph in view class.
                            // theView.plotGraph(generalSeries);
                            theView.plotGraph(theModel.getSeries());
                            theView.plotGraph(theModel.getInitialRange());
                            theView.plotGraph(rootLine);

                            break;
                        // } else {
                        // theView.resetInputFields();
                        // theView.displayErrorMessage("solution not possible");

                        // break;
                        // }

                        case 3: // Newton Raphson's Method

                            // returns value to model class
                            theModel.NewtonMethod(xOne, function_index, decimalplaces);

                            // sets calculated value in the view class
                            theView.setRootTextField(theModel.getRoot());

                            rootLine = new XYSeries("root");
                            rootLine.addOrUpdate(theModel.getRoot(), 0.6);
                            rootLine.addOrUpdate(theModel.getRoot(), -0.6);

                            theView.setLinkedListTable(theModel.getLinkedList());

                            // pass data to plot graph in view class.
                            // theView.plotGraph(generalSeries);
                            theView.plotGraph(theModel.getSeries());
                            theView.plotGraph(theModel.getInitialRange());
                            theView.plotGraph(rootLine);

                            break;

                        case 4: // False Position Method

                            // checks necassary condition for false position method
                            if (f_xOne * f_xTwo < 0) {

                                // returns value to model class
                                theModel.FalsePostion(xOne, xTwo, function_index, decimalplaces);

                                // sets calculated value in the view class
                                theView.setRootTextField(theModel.getRoot());

                                rootLine = new XYSeries("root");
                                rootLine.addOrUpdate(theModel.getRoot(), 0.6);
                                rootLine.addOrUpdate(theModel.getRoot(), -0.6);

                                theView.setLinkedListTable(theModel.getLinkedList());

                                // pass data to plot graph in view class.
                                // theView.plotGraph(generalSeries);
                                theView.plotGraph(theModel.getSeries());
                                theView.plotGraph(theModel.getInitialRange());
                                theView.plotGraph(rootLine);

                                break;

                            } else {

                                // shows error if condition didn't match.
                                theView.resetInputFields();
                                theView.displayErrorMessage(
                                        "Values entered does not converge\n     " + "=> f(x1)*f(x2) > 0");
                                break;

                            }
                        default:

                            break;

                    }
                }
            } catch (NumberFormatException ex) {

                System.out.println(ex);
                theView.resetInputFields();
                theView.displayErrorMessage("Please Enter Valid Entries (numbers only)");

            } catch (ArrayIndexOutOfBoundsException ex) {

                theModel.clearData();
                System.out.println(ex);
                theView.displayErrorMessage("Array Index Out of Bounds");

            } catch (NullPointerException ex) {

                System.out.println(ex);
                theView.displayErrorMessage("Null Pointer Exception Occured");

            } catch (Exception ex) {

                System.out.println(ex);
                theView.displayErrorMessage("Unknown Error Occured");
            }
        }
    }
}
