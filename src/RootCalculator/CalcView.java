/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RootCalculator;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

/* Creates GUI Elements and returns all the neccassary values from user input */
public class CalcView extends JFrame { // Main Frame (all elements reside here)

    // Start of Declaration for all elements e.g buttons, panel, textfields...
    private final JComboBox functions = new JComboBox();// combo box for function selection
    private final JComboBox Methods = new JComboBox(); // combo boc for method selection

    private final JLabel xOneLabel = new JLabel("X₁:"); // label for first input value
    private final JTextField xOneInputField = new JTextField(6); // textfield for first input value

    private final JLabel xTwoLabel = new JLabel("X₂:"); // label for second input value
    private final JTextField xTwoInputField = new JTextField(6); // textfield for second input value

    private final JLabel decimalLabel = new JLabel("Decimal Points:"); // label for decimal places
    private final JTextField decimalTextField = new JTextField(8); // textfield for decimal places

    private final JButton calculateButton = new JButton("Calculate"); // calculate button
    private final JTextField rootTextField = new JTextField(18); // textfield to show final answer
    private final JButton clearButton = new JButton("Clear"); // clear button to clear all data and reset program

    private final DefaultTableModel tableModel = new DefaultTableModel(); // model for JTable
    private final JTable calcTable = new JTable(tableModel); // table
    private final JScrollPane scroll = new JScrollPane(calcTable); // scroll pane for table
    private final TableColumnModel columnModel = calcTable.getColumnModel();
    private final Object[] columnNames = { "No.", "X₁", "F(X₁)", "X₂", "F(X₂)", "Root Approx", "f(Root)" };// column
                                                                                                           // names

    // Contrain variable to set coordinates for components in the panel or mainframe
    private final GridBagConstraints gbc = new GridBagConstraints(); // constraint for setting coordinates

    // Graph Related Variables
    private final NumberAxis xAxis = new NumberAxis("X"); // x-Axis label
    private final NumberAxis yAxis = new NumberAxis("f(x)"); // y-Axis label
    private final XYSplineRenderer renderer = new XYSplineRenderer(); // renderer to draw smooth line graph
    private final XYSeriesCollection graphDataSet = new XYSeriesCollection(); // dataset for xy values
    private final XYPlot plot = new XYPlot(graphDataSet, xAxis, yAxis, renderer); // plot method to create chart(graph)

    // Panels , Some Extra Panels to encapsulate the smaller components with the
    // larger ones.
    private final JPanel ComboPanel = new JPanel();
    private final JPanel TextFieldPanel = new JPanel();
    private final JPanel ButtonsPanel = new JPanel();
    private final JPanel DataPanel = new JPanel(new GridBagLayout());
    private final JPanel tablePanel = new JPanel(new BorderLayout());
    private final JPanel LEFTPANEL = new JPanel(new BorderLayout());
    private final JPanel RIGHTPANEL = new JPanel(new BorderLayout());

    // End of Declaration for all elements e.g buttons, panel, textfields...
    // Constructor for JFrame and including elements
    CalcView() {

        super("Root Finder");

        // set attribute of the mainframe
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(1100, 603));
        // this.setResizable(false);

        // declares border around different Panels
        TitledBorder calculationBorder, tableBorder, answerBorder, graphBorder, inputFieldBorder;
        tableBorder = BorderFactory.createTitledBorder("Table");
        calculationBorder = BorderFactory.createTitledBorder("Calculation");
        answerBorder = BorderFactory.createTitledBorder("root");
        graphBorder = BorderFactory.createTitledBorder("Graph");
        inputFieldBorder = BorderFactory.createTitledBorder("Enter Values");

        // Sets attributes of graph and its features.
        xAxis.setAutoRangeIncludesZero(false);
        yAxis.setAutoRangeIncludesZero(true);
        plot.setDomainZeroBaselineVisible(true);
        plot.setRangeZeroBaselineVisible(true);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        plot.setAxisOffset(new RectangleInsets(4, 4, 4, 4));

        // Creates Graph using plot and title arguements
        JFreeChart chart = new JFreeChart("", plot);
        ChartPanel chartpanel = new ChartPanel(chart);
        chartpanel.setMouseWheelEnabled(true);
        chartpanel.setPreferredSize(new Dimension(100, 450));

        // sets column names
        tableModel.setColumnIdentifiers(columnNames);

        // set first column size
        columnModel.getColumn(0).setWidth(45);
        columnModel.getColumn(0).setPreferredWidth(45);
        columnModel.getColumn(0).setMaxWidth(45);

        // set border around all panels
        chartpanel.setBorder(graphBorder);
        tablePanel.setBorder(tableBorder);
        DataPanel.setBorder(calculationBorder);
        rootTextField.setBorder(answerBorder);
        TextFieldPanel.setBorder(inputFieldBorder);

        // sets preffered size of combo boxes i.e functions and methods and other
        // componenets if needed
        functions.setPreferredSize(new Dimension(180, 24));
        Methods.setPreferredSize(new Dimension(180, 24));
        tableModel.setRowCount(50);

        rootTextField.setEditable(false);

        // Adds components to functions combo box
        functions.addItem("Select Function");
        functions.addItem("x-x^2");
        functions.addItem("ln(x+1)+1");
        functions.addItem("e^x-3x");

        // Adds components to methods Combo Box.
        Methods.addItem("Select Method");
        Methods.addItem("Bisection Method");
        Methods.addItem("Secant Method");
        Methods.addItem("Newton Raphson's Method");
        Methods.addItem("False Position Method");

        // Adds different components to panel for a better gui handling
        ComboPanel.add(functions);
        ComboPanel.add(Methods);
        TextFieldPanel.add(xOneLabel);
        TextFieldPanel.add(xOneInputField);
        TextFieldPanel.add(xTwoLabel);
        TextFieldPanel.add(xTwoInputField);
        TextFieldPanel.add(decimalLabel);
        TextFieldPanel.add(decimalTextField);
        ButtonsPanel.add(calculateButton);
        ButtonsPanel.add(rootTextField);
        ButtonsPanel.add(clearButton);
        tablePanel.add(scroll);

        // Sets coordinates for components in the frame.
        // top
        gbc.insets = new Insets(4, 5, 4, 4);
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NONE;
        DataPanel.add(ComboPanel, gbc);

        // mid
        gbc.weightx = 1;
        gbc.weighty = 0.01;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        DataPanel.add(TextFieldPanel, gbc);

        // bottom
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        DataPanel.add(ButtonsPanel, gbc);

        // This part adds all components to the mainframe after setting all the
        // coordinates.
        LEFTPANEL.add(DataPanel, BorderLayout.NORTH);
        LEFTPANEL.add(chartpanel, BorderLayout.CENTER);
        RIGHTPANEL.add(tablePanel);
        LEFTPANEL.setPreferredSize(new Dimension(500, 800));

        this.add(LEFTPANEL, BorderLayout.WEST);
        this.add(RIGHTPANEL, BorderLayout.CENTER);

        // End of setting up the components --------
    }

    public void setColumnNames(Boolean e) {

        // flag checks if newton method is selected and changes the table values
        // accordingly.
        if (e == true) {
            Object[] columnNames2 = { "No.", "X₁", "F(X₁)", "Root Approx", "f(Root)" };
            tableModel.setColumnIdentifiers(columnNames2);
            columnModel.getColumn(0).setWidth(45);
            columnModel.getColumn(0).setPreferredWidth(45);
            columnModel.getColumn(0).setMaxWidth(45);
        } else {

            tableModel.setColumnIdentifiers(columnNames);
            columnModel.getColumn(0).setWidth(45);
            columnModel.getColumn(0).setPreferredWidth(45);
            columnModel.getColumn(0).setMaxWidth(45);
        }
    }

    // gets Value from series and place them in the dataset
    public void plotGraph(XYSeries series) {

        graphDataSet.addSeries(series);

    }

    // Gets the value of the selected item from functions combo box
    public int getFunctionsIndex() {

        return functions.getSelectedIndex();

    }

    // Gets the value of the selected item from Methods combo box
    public int getMethodsIndex() {

        return Methods.getSelectedIndex();

    }

    // gets user input value from the textfield and store it in the double variable
    public double get_xOne() {

        return Double.parseDouble(xOneInputField.getText());

    }

    public double get_xTwo() {

        if (Methods.getSelectedIndex() != 3) {

            return Double.parseDouble(xTwoInputField.getText());

        }

        return 0;

    }

    // this method sets the second value textfield as uneditable for newton
    // raphson's method.
    // and make it editable if other methods are selected.
    public void setSecondTextField(boolean e) {

        if (e == false) {

            xTwoInputField.setText("");
        }
        xTwoInputField.setEditable(e);

    }

    // returns decimal accuracy entered by user.
    public int getDecimalPlaces() {

        return Integer.parseInt(decimalTextField.getText());

    }

    // Sets the final calculated answer in the textfield.
    public void setRootTextField(double solution) {

        // converts the final value to user selected number of decimal places
        String str = String.format("%." + Integer.parseInt(decimalTextField.getText()) + "f", solution);
        rootTextField.setText(str);

    }

    public void setLinkedListTable(SLinkedList thelist) {

        int index;

        // row data for 7 columns
        Object[] rowData = new Object[7];
        index = 0;

        SNode temp;
        if (thelist.isEmpty()) {
            System.out.println("List is empty");
        } else {
            temp = thelist.head;
            temp = temp.getNext();
            while (temp != null) {

                rowData[0] = index + 1;
                rowData[1] = temp.getElement()[0];
                rowData[2] = temp.getElement()[1];
                rowData[3] = temp.getElement()[2];
                rowData[4] = temp.getElement()[3];
                rowData[5] = temp.getElement()[4];
                rowData[6] = temp.getElement()[5];
                tableModel.addRow(rowData);
                temp = temp.getNext();
                index++;

            }

            tableModel.setRowCount(50);
            // adds empty rows to the table to fill up the empty space.
            // if (index < 40) {

            // while (index < 50) {

            // rowData = null;
            // tableModel.addRow(rowData);
            // index++;

            // }
            // }

        }

    }

    // Gets all data from the array structure and populates it into the JTable
    public void setArrayTable(String[][] arraydata) {

        int index;

        // row data for 7 columns
        Object[] rowData = new Object[7];

        index = 0;
        // adds data to the table from the array.
        while (arraydata[index][0] != null) {

            rowData[0] = index + 1; // iteration number
            rowData[1] = arraydata[index][0]; // x1
            rowData[2] = arraydata[index][1]; // f(x1)
            rowData[3] = arraydata[index][2]; // x2
            rowData[4] = arraydata[index][3]; // f(x2)
            rowData[5] = arraydata[index][4]; // xnew
            rowData[6] = arraydata[index][5]; // f(xnew)

            tableModel.addRow(rowData);
            index++;

        }

        tableModel.setRowCount(50);
        // adds empty rows to the table to fill up the empty space.
        // if (index < 40) {

        // while (index < 50) {

        // rowData = null;
        // tableModel.addRow(rowData);
        // index++;

        // }
        // }
    }

    // Clears the previous dataset when new functions or method is being called on.
    public void clearGraphTableDataset() {

        graphDataSet.removeAllSeries();
        tableModel.getDataVector().removeAllElements();
        tableModel.fireTableDataChanged();

    }

    // Corresponds to clear button, clears all data when this method gets called.
    public void resetProgram() {

        graphDataSet.removeAllSeries();
        functions.setSelectedIndex(0);
        Methods.setSelectedIndex(0);
        xOneInputField.setText("");
        xTwoInputField.setText("");
        decimalTextField.setText("");
        rootTextField.setText("");
        tableModel.getDataVector().removeAllElements();
        tableModel.fireTableDataChanged();
        tableModel.setRowCount(50);

    }

    public XYSeriesCollection getGraphDataSet() {
        return graphDataSet;
    }

    public void resetInputFields() {

        xOneInputField.setText("");
        xTwoInputField.setText("");
        decimalTextField.setText("");
        tableModel.setRowCount(50);

    }

    public void resetTable() {

        tableModel.setRowCount(50);

    }

    public void resetDecimalPlace() {

        decimalTextField.setText("");

    }

    void addFunctionComboListener(ActionListener listenForFunction) {
        functions.addActionListener(listenForFunction);
    }

    // Action Listener for selected method.
    void addMethodComboListener(ActionListener listenForMethod) {

        Methods.addActionListener(listenForMethod);

    }

    // Action Listener for clearButton
    void addClearButtonListener(ActionListener listenForCLearButton) {

        clearButton.addActionListener(listenForCLearButton);

    }

    // ActionListener for calculate Button, (All calculations Happen when this
    // button is clicked)
    void addCalculateListener(ActionListener listenForCalcButton) {

        calculateButton.addActionListener(listenForCalcButton);

    }

    // Display any error occured during runtime or in calculation process.
    void displayErrorMessage(String errorMessage) {

        JOptionPane.showMessageDialog(this, errorMessage);

    }
}
