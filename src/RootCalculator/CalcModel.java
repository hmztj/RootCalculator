package RootCalculator;

import org.jfree.data.xy.XYSeries;

public class CalcModel {

    private double root = Double.MAX_VALUE; // final calculated value(root Approximation)
    private int index; // iterations counter (Number of loops to get desired root apprixmation)
    private String[][] dataArray = new String[500][8]; // Array to store calculated values.
    final private SLinkedList dataList = new SLinkedList();
    private XYSeries series, initialRange; // creates series of x and y values for graphical dataset

    public XYSeries getInitialRange() {

        return initialRange;

    }

    // Bisection Method, takes x1, x2, function index(select function), and
    // decimalPlaces as arguements.
    public void BisectionMethod(double xOne, double xTwo, int function_index, int decimalPlaces) {

        double diff, f_xOne, f_root, f_xTwo;

        initialRange = new XYSeries("X₁,X₂");
        initialRange.addOrUpdate(xOne, f(xOne, function_index));
        initialRange.addOrUpdate(xTwo, f(xTwo, function_index));

        series = new XYSeries(getSeriesName(function_index));
        series.addOrUpdate(xOne, f(xOne, function_index));
        series.addOrUpdate(xTwo, f(xTwo, function_index));

        index = 0;
        // Main loop to calculate root using Bisection Method
        do {

            if (index > 498) {
                break;
            }

            dataArray[index][0] = Double.toString(xOne);
            dataArray[index][2] = Double.toString(xTwo);

            root = (xOne + xTwo) / 2;
            f_root = f(root, function_index);

            String str_root = String.format("%." + decimalPlaces + "f", root);
            dataArray[index][4] = str_root;
            dataArray[index][5] = Double.toString(f_root);

            if (Double.isNaN(root) || Double.isNaN(f_root)) {
                break;
            }

            // add or update existing x,y coordinates
            series.addOrUpdate(root, f_root);

            f_xOne = f(xOne, function_index);
            f_xTwo = f(xTwo, function_index);

            dataArray[index][1] = Double.toString(f_xOne);
            dataArray[index][3] = Double.toString(f_xTwo);

            if (Double.isNaN(f_xOne) || Double.isNaN(f_xTwo)) {
                break;
            }

            diff = Math.abs(xTwo - xOne) / 2;

            // algorithm conditions
            if (f_xOne * f_root < 0) {

                xTwo = root;

            } else if (f_xTwo * f_root < 0) {

                xOne = root;
            }

            if (f_root == 0) {

                break;

            }
            index++;

            // checks if the difference between fucntion of given values is greater than the
            // threshold(decimal places/accuracy)
        } while (diff > Math.pow(10, -decimalPlaces));

    }

    // Secant Method, takes x1, x2, function index(select function), and decimplaces
    // as arguements.
    public void SecantMethod(double xOne, double xTwo, int function_index, int decimalPlaces) {

        // local variables for the method.
        double diff, f_xOne, f_xTwo, f_root;

        // series name based on the selected function
        initialRange = new XYSeries("X₁,X₂");
        initialRange.addOrUpdate(xOne, f(xOne, function_index));
        initialRange.addOrUpdate(xTwo, f(xTwo, function_index));

        series = new XYSeries(getSeriesName(function_index));
        series.addOrUpdate(xOne, f(xOne, function_index));
        series.addOrUpdate(xTwo, f(xTwo, function_index));

        index = 0;
        // Main loop to calculate root using Secant Method

        do {

            if (index > 498) {
                break;
            }

            dataArray[index][0] = Double.toString(xOne);
            dataArray[index][2] = Double.toString(xTwo);

            f_xOne = f(xOne, function_index);
            f_xTwo = f(xTwo, function_index);

            if (Double.isNaN(f_xOne) || Double.isNaN(f_xOne)) {
                break;
            }
            dataArray[index][1] = Double.toString(f_xOne);
            dataArray[index][3] = Double.toString(f_xTwo);

            root = xTwo - ((f_xTwo * (xTwo - xOne)) / (f_xTwo - f_xOne));
            f_root = f(root, function_index);

            String str_root = String.format("%." + decimalPlaces + "f", root);
            dataArray[index][4] = str_root;
            dataArray[index][5] = Double.toString(f_root);

            if (Double.isNaN(root) || Double.isNaN(f_root)) {
                break;
            }

            // add or update existing x,y coordinates
            series.addOrUpdate(root, f_root);

            diff = Math.abs(root - xTwo);

            xOne = xTwo;
            xTwo = root;

            if (f_root == 0) {

                break;

            }

            index++;

            // checks if the difference between fucntion of given values is greater than the
            // threshold(decimal places/accuracy)
        } while (diff > Math.pow(10, -decimalPlaces));

    }

    // Newton Method, takes x1, x2, function index(select function), and decimplaces
    // as arguements.
    public void NewtonMethod(double xOne, int function_index, int decimalPlaces) {

        // local variables for the method.
        double diff, f_xOne, f_root, x, y, dy_dx, c;

        // series name based on the selected function
        initialRange = new XYSeries("1st Tangent");
        // xyPoints.addOrUpdate(xOne, f(xOne, function_index));
        // x = xOne - f(xOne, function_index) / fprime(xOne, function_index);
        x = xOne;
        y = f(x, function_index);
        dy_dx = fprime(x, function_index);
        c = y - dy_dx * x;
        initialRange.addOrUpdate(x - x * x, (dy_dx * (x - x * x)) + c);
        initialRange.addOrUpdate(x + x * x, (dy_dx * (x + x * x)) + c);

        series = new XYSeries(getSeriesName(function_index));
        series.addOrUpdate(xOne, f(xOne, function_index));

        // Main loop to calculate root using Newton's Method
        do {

            f_xOne = f(xOne, function_index);

            // edge case to check of derivative of the function is 0/infinity or Not a
            // number - breaks the loop as no
            // further calculations are possible
            if (Double.isNaN(f_xOne) || fprime(xOne, function_index) == 0) {
                break;
            }

            root = xOne - f_xOne / fprime(xOne, function_index);
            f_root = f(root, function_index);

            String str_root = String.format("%." + decimalPlaces + "f", root);

            dataList.add(Double.toString(xOne), Double.toString(f_xOne), str_root, Double.toString(f_root), "", "");

            // edge case to check if the loop has exceeded calculation limits or root has
            // been found
            if (Double.isNaN(root) || f_root == 0 || Double.isNaN(f_root)) {

                break;

            }

            series.addOrUpdate(root, f_root);

            diff = Math.abs(xOne - root);
            xOne = root;

            // checks if the difference between fucntion of given values is greater than the
            // threshold(decimal places/accuracy)
        } while (diff > Math.pow(10, -decimalPlaces));

    }

    // False Position Method, takes x1, x2, function index(select function), and
    // decimplaces as arguements.
    public void FalsePostion(double xOne, double xTwo, int function_index, int decimalPlaces) {

        // local variables for the method.
        double diff, f_xOne, f_xTwo, f_root;

        f_xOne = f(xOne, function_index);
        f_xTwo = f(xTwo, function_index);

        // series name based on the selected function
        initialRange = new XYSeries("X₁,X₂");
        initialRange.addOrUpdate(xOne, f(xOne, function_index));
        initialRange.addOrUpdate(xTwo, f(xTwo, function_index));

        series = new XYSeries(getSeriesName(function_index));
        series.addOrUpdate(xOne, f(xOne, function_index));
        series.addOrUpdate(xTwo, f(xTwo, function_index));

        // Main loop to calculate root using False Position Method
        do {

            root = (xOne * f_xTwo - xTwo * f_xOne) / (f_xTwo - f_xOne);
            f_root = f(root, function_index);

            f_xOne = f(xOne, function_index);
            f_xTwo = f(xTwo, function_index);

            // adds calculated values into the list
            String str_root = String.format("%." + decimalPlaces + "f", root);
            dataList.add(Double.toString(xOne), Double.toString(f_xOne), Double.toString(xTwo), Double.toString(f_xTwo),
                    str_root, Double.toString(f_root));

            if (Double.isNaN(root) || Double.isNaN(f_root) || f_root == 0) {
                break;
            }

            // add or update existing x,y coordinates
            series.addOrUpdate(root, f_root);

            diff = Math.abs(xTwo - xOne);

            if (f_root * f_xOne < 0) {

                xTwo = root;
            } else if (f_root * f_xTwo < 0) {

                xOne = root;
            }

            if (f_root == 0) {

                break;
            }

            // checks if the difference between fucntion of given values is greater than the
            // threshold(decimal places/accuracy)
        } while (diff > Math.pow(10, -decimalPlaces));

    }

    /**
     * returns computed values by the selected algorithm as an Array
     * 
     * @return x1, x2, f(x1), f(x2), root, f(root)
     */
    public String[][] getArrayList() {

        return dataArray;

    }

    /**
     * clears out the list and array for new computations/data. assigns an empty
     * array to existing one. existing one is dealt by garbage collector
     */
    public void clearData() {

        String[][] emptyarray = new String[500][8];
        dataArray = emptyarray;
        dataList.clearList();

    }

    /**
     * returns computed values by the selected algorithm as a list
     * 
     * @return x1, x2, f(x1), f(x2), root, f(root)
     */
    public SLinkedList getLinkedList() {

        return dataList;

    }

    /**
     * returns the final calculated root value from the selected method.
     * 
     * @return
     */
    public double getRoot() {

        return root;

    }

    /**
     * 
     * @return series of X, Y coordinates to plot on the graph
     */
    public XYSeries getSeries() {

        return series;

    }

    public String getSeriesName(int function_index) {

        switch (function_index) {

            case 1:
                return "x-x^2";
            case 2:
                return "ln(x+1)+1";
            case 3:
                return "e^x-3x";
            default:
                return "";
        }
    }

    /**
     * returns the selected function value inside the selected method e.g. case 1 =
     * x - x^2 args = xValue, index
     * 
     */
    public double f(double xValue, int index) {

        switch (index) {

            case 1:
                return xValue - (xValue * xValue);
            case 2:
                return Math.log(xValue + 1) + 1;
            case 3:
                return Math.exp(xValue) - 3 * xValue;
            default:
                return 0;

        }
    }

    // computes the derivative of the selected function.
    public double fprime(double xValue, int function_index) {

        switch (function_index) {

            case 1:
                return 1 - 2 * xValue;
            case 2:
                return 1 / (xValue + 1);
            case 3:
                return Math.exp(xValue) - 3;
            default:
                return 0;

        }
    }
}
