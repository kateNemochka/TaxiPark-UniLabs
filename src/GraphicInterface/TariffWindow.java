package GraphicInterface;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static GraphicInterface.ConstantsClass.tariff;
import static GraphicInterface.ConstantsClass.taxiPark;

public class TariffWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel baseFareLabel, comfortFareLabel, ecoFareLabel, nightFareLabel;
    private JTextField basicTextField, comfTextField, greenTextField, nightTextField;
    private JLabel warningLabel;

    public TariffWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width/2;
        int height = screenSize.height/2;
        this.setLocation(width - 200,height - 250);

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        updateFareValues();

        // styling warning message
        warningLabel.setForeground(Color.RED);
        warningLabel.setFont(new Font(warningLabel.getFont().getName(), Font.BOLD, 10));



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
        // -=* CHECKS *=-
        // only digits/numbers are allowed in this text field
        basicTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                filter(basicTextField, e);
            }
        });
        comfTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                filter(comfTextField, e);
            }
        });
        greenTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                filter(greenTextField, e);
            }
        });
        nightTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                filter(nightTextField, e);
            }
        });

        // unblocking blocked element when nothing is being entered in it
        basicTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                basicTextField.setEditable(true);
            }
        });
        comfTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                comfTextField.setEditable(true);
            }
        });
        greenTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                greenTextField.setEditable(true);
            }
        });
        nightTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                nightTextField.setEditable(true);
            }
        });
    }

    private void onOK() {
        String basic = basicTextField.getText();
        String comfort = comfTextField.getText();
        String green = greenTextField.getText();
        String night = nightTextField.getText();
        if (!basic.isEmpty())
            tariff.setBasicFare(Double.parseDouble(basic));
        if (!comfort.isEmpty())
            tariff.setComfort(Double.parseDouble(comfort));
        if (!green.isEmpty())
            tariff.setGreen(Double.parseDouble(green));
        if (!night.isEmpty())
            tariff.setNight(Double.parseDouble(night));

        taxiPark.setTariff(tariff);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    // updating fare values of current tariff object
    private void updateFareValues() {
        baseFareLabel.setText(String.valueOf(tariff.getBasicFare()));
        comfortFareLabel.setText(String.valueOf(tariff.getComfort()));
        ecoFareLabel.setText(String.valueOf(tariff.getGreen()));
        nightFareLabel.setText(String.valueOf(tariff.getNight()));
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
        TariffWindow dialog = new TariffWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
