import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class PizzaGUIFrame extends JFrame {
    private JRadioButton crustOption1, crustOption2, crustOption3;
    private JComboBox sizeOptions = new JComboBox();
    private JCheckBox toppingsOption1, toppingsOption2, toppingsOption3, toppingsOption4, toppingsOption5, toppingsOption6;
    private JPanel optionsPanel, sizeOptionsPanel, toppingsOptionsPanel, crustOptionsPanel, receiptPanel, guiOptions, centerPanel;
    private JTextArea receipt;

    public PizzaGUIFrame() {
        Dimension baseScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float applicationScaleFactorWidth = 1f/3f;
        float applicationScaleFactorHeight = 2f/5f;
        Dimension applicationSize = new Dimension(
                (int)(baseScreenSize.width * applicationScaleFactorWidth),
                (int)(baseScreenSize.height * applicationScaleFactorHeight)
        );

        super.setTitle("Pizza Order");
        super.setSize(applicationSize.width, applicationSize.height);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());

        centerPanel = new JPanel();
        SetupOptions(centerPanel);
        SetupReceiptPanel(centerPanel);
        centerPanel.setLayout(new FlowLayout());
        super.add(centerPanel, BorderLayout.CENTER);


        SetupGUIOptions();

        super.setLocationRelativeTo(null);
    }

    /**
     * sets up the options panel,
     * makes the main method cleaner.
     * these are the options that are used to customize the pizza
     * @param panel the panel that the options will be added to
     */
    private void SetupOptions(JPanel panel){
        optionsPanel = new JPanel();
        JPanel leftPanel, rightPanel;
        leftPanel = new JPanel();
        rightPanel = new JPanel();

        SetupCrustOptions(leftPanel);
        SetupSizeOptions(leftPanel);
        SetupToppingsOptions(rightPanel);

        leftPanel.setLayout(new GridLayout(0, 1));

        optionsPanel.setLayout(new FlowLayout());
        optionsPanel.add(leftPanel);
        optionsPanel.add(rightPanel);

        panel.add(optionsPanel);
    }

    /**
     * sets up the crust options panel,
     * part of the options panel, makes the main method cleaner
     * @param optionsPanel the panel that the crust options will be added to
     */
    private void SetupCrustOptions(JPanel optionsPanel){
        crustOptionsPanel = new JPanel();
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Crust");

        ButtonGroup crustOptionsGroup = new ButtonGroup();

        crustOption1 = new JRadioButton("Thin");
        crustOption2 = new JRadioButton("Regular");
        crustOption3 = new JRadioButton("Deep Dish");

        crustOptionsGroup.add(crustOption1);
        crustOptionsGroup.add(crustOption2);
        crustOptionsGroup.add(crustOption3);

        crustOptionsPanel.add(crustOption1);
        crustOptionsPanel.add(crustOption2);
        crustOptionsPanel.add(crustOption3);

        crustOptionsPanel.setBorder(titledBorder);
        crustOptionsPanel.setLayout(new GridLayout(0, 1));

        optionsPanel.add(crustOptionsPanel);
    }

    /**
     * sets up the size options panel,
     * part of the options panel, makes the main method cleaner
     * @param optionsPanel the panel that the size options will be added to
     */
    private void SetupSizeOptions(JPanel optionsPanel){
        sizeOptionsPanel = new JPanel();
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Size");

        sizeOptions.addItem("Small");
        sizeOptions.addItem("Medium");
        sizeOptions.addItem("Large");
        sizeOptions.addItem("Super");

        sizeOptionsPanel.setBorder(titledBorder);
        sizeOptionsPanel.add(sizeOptions);

        optionsPanel.add(sizeOptionsPanel);
    }

    /**
     * sets up the toppings options panel,
     * part of the options panel, makes the main method cleaner
     * @param optionsPanel the panel that the toppings options will be added to
     */
    private void SetupToppingsOptions(JPanel optionsPanel){
        toppingsOptionsPanel = new JPanel();
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Toppings");

        toppingsOption1 = new JCheckBox("Pepperoni");
        toppingsOption2 = new JCheckBox("Sausage");
        toppingsOption3 = new JCheckBox("Mushrooms");
        toppingsOption4 = new JCheckBox("Onions");
        toppingsOption5 = new JCheckBox("34,000 eggs");
        toppingsOption6 = new JCheckBox("Entire Salmon");

        toppingsOptionsPanel.add(toppingsOption1);
        toppingsOptionsPanel.add(toppingsOption2);
        toppingsOptionsPanel.add(toppingsOption3);
        toppingsOptionsPanel.add(toppingsOption4);
        toppingsOptionsPanel.add(toppingsOption5);
        toppingsOptionsPanel.add(toppingsOption6);
        toppingsOptionsPanel.setBorder(titledBorder);

        toppingsOptionsPanel.setLayout(new GridLayout(0, 1));

        optionsPanel.add(toppingsOptionsPanel);
    }

    /**
     * sets up the receipt panel
     * makes the main method cleaner
     * @param panel the panel that the receipt will be added to
     */
    private void SetupReceiptPanel(JPanel panel){
        receiptPanel = new JPanel();

        receipt = new JTextArea();
        receipt.setEditable(false);
        receipt.setPreferredSize(new Dimension(300, 300));

        receiptPanel.add(receipt);

        receipt.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));//ensures the text formatting properly works

        panel.add(receiptPanel);
    }

    /**
     * sets up the gui options panel
     * makes the main method cleaner
     *
     * gui options include the order, clear, and quit buttons
     */
    private void SetupGUIOptions(){
        guiOptions = new JPanel();

        JButton Order, Clear, Quit;

        Order = new JButton("Order");
        Clear = new JButton("Clear");
        Quit = new JButton("Quit");

        guiOptions.add(Order);
        guiOptions.add(Clear);
        guiOptions.add(Quit);

        Order.addActionListener(new OrderListener());
        Clear.addActionListener(new ClearListener());
        Quit.addActionListener(e -> System.exit(0));

        super.add(guiOptions, BorderLayout.PAGE_END);
    }

    /**
     * handles the complex process of printing the receipt
     * also calculates the subtotal, tax, and total
     * contains several methods, this was done so that this class could be moved to a separate file with minimal changes
     */
    private class OrderListener implements ActionListener {
        private int receiptWidth;
        private double subTotal, tax, total;
        @Override
        public void actionPerformed(ActionEvent e) {
            subTotal = 0.00;
            receiptWidth = receipt.getWidth()/12;
            receipt.setText("");

            receipt.append("=".repeat(receiptWidth * receipt.getFont().getSize()) + "\n");//line for formatting

            //print our crust and size options
            PrintSize();
            PrintLine("Crust: ", crustOption1.getText(), null);

            //print our toppings options
            String toppingsText = "";
            List<String> toppings = GetToppings();
            if(!toppings.isEmpty()){
                receipt.append("Toppings: \n");
                for(String topping : toppings){
                    PrintLine(topping, "", 1.00, 4);
                    subTotal += 1.00;
                }
            }
            else{
                toppingsText += "No toppings\n";
            }
            receipt.append(toppingsText);

            //print our sub total and tax
            PrintLine("SubTotal", "", subTotal);
            PrintLine("Tax", "", tax = subTotal * 0.075);

            receipt.append("-".repeat(receiptWidth * receipt.getFont().getSize()) + "\n");//separator line
            //print the total
            PrintLine("Total", "", total = subTotal + tax);

            receipt.append("=".repeat(receiptWidth * receipt.getFont().getSize()) + "\n");//line for formatting
        }

        /**
         * prints the size of the pizza based on the selected option, also adds the price to the total for calculation
         * makes the main method cleaner
         */
        private void PrintSize(){
            if(sizeOptions.getSelectedItem().toString().equals("Small")){
                PrintLine("Size: ", "Small", 8.00);
                subTotal += 8.00;
            }
            else if(sizeOptions.getSelectedItem().toString().equals("Medium")){
                PrintLine("Size: ", "Medium", 12.00);
                subTotal += 12.00;
            }
            else if(sizeOptions.getSelectedItem().toString().equals("Large")){
                PrintLine("Size: ", "Large", 16.00);
                subTotal += 16.00;
            }
            else if(sizeOptions.getSelectedItem().toString().equals("Super")){
                PrintLine("Size: ", "Super", 20.00);
                subTotal += 20.00;
            }
        }

        /**
         * gets a string list of the selected toppings, makes the main method cleaner
         * @return
         */
        private List<String> GetToppings(){
            List<String> toppings = new ArrayList<>();
            if(toppingsOption1.isSelected()){
                toppings.add(toppingsOption1.getText());
            }
            if(toppingsOption2.isSelected()){
                toppings.add(toppingsOption2.getText());
            }
            if(toppingsOption3.isSelected()){
                toppings.add(toppingsOption3.getText());
            }
            if(toppingsOption4.isSelected()){
                toppings.add(toppingsOption4.getText());
            }
            if(toppingsOption5.isSelected()){
                toppings.add(toppingsOption5.getText());
            }
            if(toppingsOption6.isSelected()){
                toppings.add(toppingsOption6.getText());
            }
            return toppings;
        }

        /**
         * Prints a formatted line to the receipt
         * @param title name of the option that is being printed, left aligned
         * @param option name of the choice that is being printed, left aligned
         * @param price price of the option being printed, right aligned
         */
        private void PrintLine(String title, String option, Double price){
            String formatString = "%-"+ receiptWidth +"s%10.2f%n";
            receipt.append(String.format(formatString, title + option, price));
        }

        /**
         * Prints a formatted line to the receipt with an indent
         * @param title name of the option that is being printed, left aligned
         * @param option name of the choice that is being printed, left aligned
         * @param price price of the option being printed, right aligned
         * @param indent number of spaces to indent the line
         */
        private void PrintLine(String title, String option, Double price, int indent){
            String formatString = " ".repeat(indent) + "%-"+(receiptWidth - indent)+"s%10.2f%n";
            receipt.append(String.format(formatString, title + option, price));
        }
    }

    /**
     * clears the receipt and all user choices from the screen
     */
    private class ClearListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            receipt.setText("");
            crustOption1.setSelected(false);
            crustOption2.setSelected(false);
            crustOption3.setSelected(false);
            sizeOptions.setSelectedIndex(0);
            toppingsOption1.setSelected(false);
            toppingsOption2.setSelected(false);
            toppingsOption3.setSelected(false);
            toppingsOption4.setSelected(false);
            toppingsOption5.setSelected(false);
            toppingsOption6.setSelected(false);
        }
    }
}
