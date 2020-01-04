package GraphicInterface;

import javax.swing.*;
import java.io.*;

import TaxiParkObjects.TaxiPark;
import static GraphicInterface.ConstantsClass.*;


public class DataStorage {

    public static void save(String filepath) {
        // declaring output streams variables
        ObjectOutputStream out = null;
        FileOutputStream fileOut = null;
        String path = null;
        String filename = null;

        // splitting path into path to file and filename if filepath is a real path
        if (filepath.contains("\\")) {
            path = filepath.substring(0, filepath.lastIndexOf("\\"));
            filename = filepath.substring(filepath.lastIndexOf("\\")+1, filepath.length());
        }

        // creating file stream
        try {
            fileOut = new FileOutputStream(filepath);
        } catch (IOException ex) {
            File dir = new File(path);
            dir.mkdirs();
            File file = new File(dir, filename);
            try {
                file.createNewFile(); // if file already exists will do nothing
                fileOut = new FileOutputStream(file, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // creating object stream and writing into file
        try {
            out = new ObjectOutputStream(new BufferedOutputStream(fileOut));
            out.writeObject(taxiPark);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void load(String filepath) {

        //filepath = filepath.substring(0, filepath.length()-2);
        ObjectInputStream in = null;

        try {
            in = new ObjectInputStream(new BufferedInputStream(
                    new FileInputStream(filepath)));
            try {
                taxiPark = (TaxiPark) in.readObject();
                tariff = taxiPark.getTariff();
                numOfRides = 0;
                profitOfGone = 0;
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(new JFrame(), "No any files have been saved!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) {
        taxiPark.addTaxi(2, "John Smith","Nissan", "АА9087КН",
                "red", 2015, 2015);
        taxiPark.addTaxi(0, "Citroen", "АВ4523ВА",
                "blue", 2009, 2014);
        taxiPark.addTaxi(1, "Ford", "АІ7834АК",
                "white", 2011, 2016);
        taxiPark.addTaxi(0, "Jack", "Reno", "АК4973ВА",
                "gray", 2016, 2016);

        taxiPark.addRandomRides(50);


        System.out.println("\n\n=====================TAXI PARK BEFORE SAVE=====================");
        taxiPark.printTaxiParkInfoBrief();


        save(DataDefaultPath);

        taxiPark.addTaxi(2,"BMW", "АА1001АА",
                "gray", 2018, 2019);
        taxiPark.addTaxi(0, "Anonymous Driver", "Daewoo", "АК9080ВА",
                "blue", 2016, 2016);
        taxiPark.setDriver(3, "Mark");
        taxiPark.setDriver(4, "Kate");
        taxiPark.addTaxi(1, "Tom", "Toyota", "АА8877АА",
                "white", 2018, 2018);


        taxiPark.addRandomRides(200);

        System.out.println("\n\n=====================CHANGES AFTER SAVE=====================");
        taxiPark.printTaxiParkInfoBrief();


        load(DataDefaultPath);

        System.out.println("\n\n=====================SAVED DATA IS LOADED=====================");
        taxiPark.printTaxiParkInfoBrief();
    }
}
