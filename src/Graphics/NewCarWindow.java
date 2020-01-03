package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

public class NewCarWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox typesComboBox;
    private JTextField brandTextField, colorTextField, regNumTextField;
    private JTextField prodYearTextField, regYearTextField, driverTextField;
    private JLabel warningLabel;
    private int type;

    public NewCarWindow() {
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
        // selecting a type of taxi from combo box
        typesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = typesComboBox.getSelectedIndex();
                System.out.println(type);
            }
        });


        // -=* CHECKS *=-
        // only digits/numbers are allowed in this text field
        regYearTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                integerFilter(regYearTextField, e);
            }
        });
        prodYearTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                integerFilter(prodYearTextField, e);
            }
        });
        // checks for the correct year value, unblocking blocked element
        prodYearTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                try {
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    if (Integer.parseInt(prodYearTextField.getText()) > year) {
                        warningLabel.setText("<html>Рік не може бути пізнішим<br>за поточний</html>");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Format Error at prodYearTextField");
                    prodYearTextField.setEditable(true);
                }
            }
        });
        regYearTextField.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent event) {
                try {
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    if (Integer.parseInt(prodYearTextField.getText()) > year) {
                        warningLabel.setText("<html>Рік не може бути пізнішим<br>за поточний</html>");
                    }
                } catch (NumberFormatException ex) {
                    System.out.println("Format Error at regYearTextField");
                    regYearTextField.setEditable(true);
                }
            }
        });

    }

    private void onOK() {
        boolean error = false;
        try {
            ConstantsClass.taxiPark.addTaxi(type, driverTextField.getText(), brandTextField.getText(),
                    regNumTextField.getText(), colorTextField.getText(),
                    Integer.parseInt(prodYearTextField.getText()),
                    Integer.parseInt(regYearTextField.getText()));
        }
        catch (NumberFormatException nfe) {
            warningLabel.setText("Не всі поля заповнені!");
            error = true;
        }
        if (driverTextField.getText().isEmpty() || brandTextField.getText().isEmpty() ||
                brandTextField.getText().isEmpty() || regNumTextField.getText().isEmpty() ||
                colorTextField.getText().isEmpty()) {
            error = true;
            warningLabel.setText("Не всі поля заповнені!");
        }
        int year = Calendar.getInstance().get(Calendar.YEAR);
        if (Integer.parseInt(prodYearTextField.getText()) > year ||
                Integer.parseInt(regYearTextField.getText()) > year) {
            error = true;
            warningLabel.setText("<html>Рік не може бути пізнішим<br>за поточний</html>");
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
    private void integerFilter(JTextField tf, KeyEvent ke) {
        String value = tf.getText();
        int l = value.length();
        if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9')
                || ke.getKeyChar() == '.' || ke.getKeyChar() == '\b'
                || ke.getKeyChar() == '\n') {
            tf.setEditable(true);
            warningLabel.setText("");
        } else {
            tf.setEditable(false);
            warningLabel.setText("Вводьте в поле лише цифри");
        }
    }


    public static void main(String[] args) {
        NewCarWindow dialog = new NewCarWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
