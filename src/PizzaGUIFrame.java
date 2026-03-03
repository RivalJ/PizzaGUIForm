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
    private JPanel optionsPanel, sizeOptionsPanel, toppingsOptionsPanel, crustOptionsPanel, receiptPanel, guiOptions;
    private JTextArea receipt;

    public PizzaGUIFrame() {
        Dimension baseScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
        float applicationScaleFactorWidth = 1f/3f;
        float applicationScaleFactorHeight = 2f/3f;
        Dimension applicationSize = new Dimension(
                (int)(baseScreenSize.width * applicationScaleFactorWidth),
                (int)(baseScreenSize.height * applicationScaleFactorHeight)
        );

        super.setTitle("Tic-Tac-Toe");
        super.setSize(applicationSize.width, applicationSize.height);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setLayout(new BorderLayout());


        SetupOptions();
        SetupReceiptPanel();
        SetupGUIOptions();

        super.setLocationRelativeTo(null);
    }

    private void SetupOptions(){
        optionsPanel = new JPanel();

        SetupCrustOptions(optionsPanel);
        SetupSizeOptions(optionsPanel);
        SetupToppingsOptions(optionsPanel);

        super.add(optionsPanel, BorderLayout.CENTER);
    }
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

        optionsPanel.add(crustOptionsPanel);
    }
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

        optionsPanel.add(toppingsOptionsPanel);
    }
    private void SetupReceiptPanel(){
        receiptPanel = new JPanel();

        receipt = new JTextArea();
        receipt.setEditable(false);
        receiptPanel.add(receipt);

        super.add(receiptPanel, BorderLayout.SOUTH);
    }
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

        super.add(guiOptions, BorderLayout.NORTH);
    }

    private class OrderListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            receipt.setText("");
            String receiptText = "";
            receiptText += "Crust: " + crustOption1.getText() + "\n";
            receiptText += "Size: " + sizeOptions.getSelectedItem() + "\n";

            receiptText += "Toppings: ";
            List<String> toppings = GetToppings();
            for(String topping : toppings){
                receiptText += "  " + topping + "\n";
            }
            receipt.setText(receiptText);
        }
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
    }
}
