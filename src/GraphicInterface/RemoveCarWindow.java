package GraphicInterface;

import javax.swing.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static GraphicInterface.ConstantsClass.taxiPark;

public class RemoveCarWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane optionsScroll;

    public RemoveCarWindow() {
        optionsScroll.setVisible(true);
        JCheckBox[] checkBoxes = generateCheckBoxes();
        ButtonGroup cbg = new ButtonGroup();
        for (int i = 0; i < checkBoxes.length; ++i) {
            cbg.add(checkBoxes[i]);
            optionsScroll.add(checkBoxes[i]);
        }

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);



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




    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }


    private static Map<String, Integer> generateCheckLabels() {
        /*id,type,money,dist, regnumber,brand,color,prodyear,regyear*/
        /*ID - TYPE - MONEY - DIST - REGNUMBER*/
        String[][] taxiInfo = taxiPark.getCarsTabledInformation();
        Map<String, Integer> labels = new HashMap<String, Integer>();
        for (String[] car : taxiInfo) {
            String label = car[0] + "-" + car[1] + "UAH-" + car[2] + "km-" + car[3] + "-" + car[4];
            labels.put(label, Integer.parseInt(car[0]));
        }
        System.out.println(labels);
        return labels;
    }
    private JCheckBox[] generateCheckBoxes() {
        Set<String> labels = generateCheckLabels().keySet();
        JCheckBox[] checkBoxes = new JCheckBox[labels.size()];
        int row = 0;
        for (String l : labels) {
            checkBoxes[row] = new JCheckBox(l, false);
            checkBoxes[row].setVisible(true);
        }
        System.out.println(checkBoxes.length);
        return checkBoxes;
    }


    public static void main(String[] args) {
        RemoveCarWindow dialog = new RemoveCarWindow();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
