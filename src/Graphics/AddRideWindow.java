package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static Graphics.ConstantsClass.*;

public class AddRideWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JCheckBox nightCheckBox;
    private JTextField distanceTextField;
    private JComboBox typesComboBox;
    private JButton calculateButton;
    private JLabel warningLabel;
    private JLabel costLabel;

    public AddRideWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
        this.setLocation(width - 200,height - 250);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        // styling warning message
        warningLabel.setForeground(Color.RED);
        warningLabel.setFont(new Font(warningLabel.getFont().getName(), Font.BOLD, 10));

        // if there are no any cars in taxi park, button "Calculation" is not enabled
        if (taxiPark.getAvailableTaxiTypes().size() == 0) {
            calculateButton.setEnabled(false);
        } else {
            DefaultComboBoxModel model = new DefaultComboBoxModel();
            if (taxiPark.getAvailableTaxiTypes().contains("Standard"))
                model.addElement("Стандарт");
            if (taxiPark.getAvailableTaxiTypes().contains("Comfort"))
                model.addElement("Комфорт");
            if (taxiPark.getAvailableTaxiTypes().contains("Green"))
                model.addElement("Green");

            typesComboBox.setModel(model);
        }


        // DEFAULT LISTENERS
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        // CUSTOM LISTENERS
        // button that calculates ride cost before adding ride to some taxi park's taxi
        calculateButton.addActionListener(e -> {
            if (!distanceTextField.getText().isEmpty()) {
                double cost = -1.0;
                double dist = Double.parseDouble(distanceTextField.getText());
                int night = nightCheckBox.isSelected() ? 1 : 0;
                if (typesComboBox.getSelectedItem().equals("Стандарт"))
                    cost = tariff.calculateStandard(dist, night);
                if (typesComboBox.getSelectedItem().equals("Комфорт"))
                    cost = tariff.calculateComfort(dist, night);
                if (typesComboBox.getSelectedItem().equals("Green"))
                    cost = tariff.calculateGreen(dist, night);

                costLabel.setText(String.valueOf(cost));
            }
        });

        // -=* CHECKS *=-
        // only digits/numbers are allowed in this text field
        distanceTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                filter(distanceTextField, e);
            }
        });
        // unblocking blocked element when nothing is being entered in it
        distanceTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                distanceTextField.setEditable(true);
                warningLabel.setText(null);
            }
        });

    }

    private void onOK() {
        boolean error = false;
        if (taxiPark.getAvailableTaxiTypes().size() == 0) {
            dispose();
        }
        if (!distanceTextField.getText().isEmpty()) {
            double dist = Double.parseDouble(distanceTextField.getText());
            boolean night = nightCheckBox.isSelected();
            if (typesComboBox.getSelectedItem().equals("Стандарт"))
                taxiPark.addRide(0, dist, night);
            if (typesComboBox.getSelectedItem().equals("Комфорт"))
                taxiPark.addRide(1, dist, night);
            if (typesComboBox.getSelectedItem().equals("Green"))
                taxiPark.addRide(2, dist, night);
            numOfRides++;
        }
        else {
            warningLabel.setText("Заповніть поле \"Відстань\"!");
            error = true;
        }
        if (!error) {
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    // filter that allows to use digits and dot symbol only
    private void filter(JTextField tf, KeyEvent ke) {
        String value = tf.getText();
        int l = value.length();
        if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')
                || ke.getKeyChar() == '.' || ke.getKeyChar() == '\b'
                || ke.getKeyChar() == '\n') {
            tf.setEditable(true);
            warningLabel.setText("");
        } else {
            tf.setEditable(false);
            warningLabel.setText("Вводьте в поле лише цифри або крапку");
        }
    }

    public static void main(String[] args) {
        AddRideWindow dialog = new AddRideWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
