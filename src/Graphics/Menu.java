package Graphics;


import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

import TaxiParkObjects.TaxiPark;
import static Graphics.ConstantsClass.*;

public class Menu extends JMenuBar {
    JMenu menu;
    JMenuItem itemOpen, itemSave, itemSaveAs, itemLoad, itemClear, itemHelp, itemReload;

    Menu() {
        menu=new JMenu("Меню");

        itemSave = new JMenuItem("Зберегти");
        itemSaveAs = new JMenuItem("Зберегти як");
        itemOpen = new JMenuItem("Відкрити");
        itemLoad = new JMenuItem("Завантажити локальні дані");
        itemClear = new JMenuItem("Очистити локальні дані");
        itemReload = new JMenuItem("Обнулити");
        itemHelp = new JMenuItem("Допомога");

        menu.add(itemSave);
        menu.add(itemSaveAs);
        menu.add(itemOpen);
        menu.add(itemLoad);
        menu.add(itemClear);
        menu.add(itemReload);
        menu.add(itemHelp);
        this.add(menu);

        itemLoad.addActionListener(e -> {
            File file = new File(DataDefaultPath);
            if (!file.exists() && !file.isFile())
            {
                JOptionPane.showMessageDialog(new JFrame(), "Не знайдено попередньо збереженого файлу!");
            }
            else {
                DataStorage.load(DataDefaultPath);
                JOptionPane.showMessageDialog(new JFrame(), "Натисність кнопку UPDATE, щоб оновити дані");
            }
        });
        itemClear.addActionListener(e -> {
            File file = new File(DataDefaultPath);
            if (file.exists() && file.isFile()) {
                file.delete();
                JOptionPane.showMessageDialog(new JFrame(), "Локальні дані очищено!");
            }
        });
        itemOpen.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Оберіть дані таксопарку");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Taxi park data, .taxi", "taxi");
            jfc.addChoosableFileFilter(filter);

            int returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                DataCurrentPath = jfc.getSelectedFile().getPath();
                DataStorage.load(DataCurrentPath);
                System.out.println(jfc.getSelectedFile().getPath());
            }
            else {
                System.out.println("No file has been chosen");
            }
        });
        itemSave.addActionListener(e -> {
            if (DataCurrentPath == null) {
                DataStorage.save(DataDefaultPath);
                JOptionPane.showMessageDialog(new JFrame(), "Файл збережено!\n"+DataDefaultPath);
            } else {
                DataStorage.save(DataCurrentPath);
                JOptionPane.showMessageDialog(new JFrame(), "Файл збережено!\n"+DataCurrentPath);
            }
        });
        itemSaveAs.addActionListener(e -> {
            JFileChooser jfc = new JFileChooser();
            jfc.setDialogTitle("Збереження даних таксопарку");
            jfc.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Taxi park data, .taxi", "taxi");
            jfc.addChoosableFileFilter(filter);
            int saveTrigger = jfc.showSaveDialog(itemSaveAs);
            if (saveTrigger == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                if (file == null) {
                    return;
                }
                if (!file.getName().toLowerCase().endsWith(".taxi")) {
                    file = new File(file.getParentFile(), file.getName() + ".taxi");
                }
                try {
                    DataStorage.save(file.getPath());
                    DataCurrentPath = file.getPath();
                    JOptionPane.showMessageDialog(new JFrame(), "Файл збережено!\n"+file.getPath());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        itemReload.addActionListener(e -> {

            taxiPark = new TaxiPark(15);
            tariff = taxiPark.getTariff();
            numOfRides = 0;
            profitOfGone = 0;
            JOptionPane.showMessageDialog(new JFrame(), "Натисність кнопку UPDATE, щоб оновити дані");
        });
        itemHelp.addActionListener(e -> {
            JOptionPane.showMessageDialog(new JFrame(),
                    "ЗБЕРЕГТИ - збереження поточного відкритого файлу\n" +
                            "ЗБЕРЕГТИ ЯК - збереження файлу будь-де у файловій системі з обраним ім'ям\n" +
                            "ВІДКРИТИ - відкрити файл будь-де\n" +
                            "ЗАВАНТАЖИТИ ЛОКАЛЬНІ ДАНІ - відкриття файлу в папці з програмою\n" +
                            "ОЧИСТИТИ ЛОКАЛЬНІ ДАНІ - видалення файлу з локальними даними\n" +
                            "ОБНУЛИТИ - очищення даних запущеного таксопарку");
        });
    }

    public static void main(String args[])
    {
        JFrame f = new JFrame("Тест меню");
        f.setJMenuBar(new Menu());
        f.setLocation(200, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.pack();
    }

}
