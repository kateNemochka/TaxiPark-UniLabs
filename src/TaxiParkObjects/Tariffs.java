package TaxiParkObjects;

import java.io.Serializable;

public class Tariffs implements Serializable {
    private double basicFare;
    private double night;
    private double comfort;
    private double green;

    public Tariffs() {
        basicFare = 0.0;
        night = 0.0;
        comfort = 0.0;
        green = 0.0;
    }

    public Tariffs(double basicFare) {
        this.basicFare = basicFare;
        this.night = basicFare * 0.25;
        this.comfort = basicFare * 2.0;
        this.green = basicFare * 0.8;
    }

    public Tariffs(double basicFare, double night, double comfort, double green) {
        this.basicFare = basicFare;
        this.night = night;
        this.comfort = comfort;
        this.green = green;
    }


    public double getBasicFare() {
        return basicFare;
    }
    public void setBasicFare(double fare) {
        basicFare = fare;
    }

    public double getNight() {
        return night;
    }
    public void setNight(double night) {
        this.night = night;
    }

    public double getComfort() {
        return comfort;
    }
    public void setComfort(double comfort) {
        this.comfort = comfort;
    }

    public double getGreen() {
        return green;
    }
    public void setGreen(double green) {
        this.green = green;
    }

    public double getFare(Class<?> taxiType) {
        if (taxiType == ComfortTaxi.class) {
            return getBasicFare();
        }
        else if (taxiType == ComfortTaxi.class) {
            return getComfort();
        }
        else if (taxiType == GreenTaxi.class) {
            return getGreen();
        }
        return 0.0;
    }


    public double calculateStandard(double distance, int isNight) {
        return (basicFare + isNight * night) * distance;
    }

    public double calculateComfort(double distance, int isNight) {
        return (comfort + isNight * night) * distance;
    }

    public double calculateGreen(double distance, int isNight) {
        return (green + isNight * night) * distance;
    }

    public double calculate(Class<?> taxiType, double distance, int isNight) {
        if (taxiType == ComfortTaxi.class) {
            return (basicFare + isNight * night) * distance;
        }
        else if (taxiType == ComfortTaxi.class) {
            return (comfort + isNight * night) * distance;
        }
        else if (taxiType == GreenTaxi.class) {
            return (green + isNight * night) * distance;
        }
        return 0.0;
    }


    public void printInfo() {
        System.out.printf("\nFare per kilometer\n\tBasic: %.2f\n\tComfort: %.2f\n" +
                        "\tGreen: %.2f\n\tNight Extra Pay: %.2f\n\n",
                basicFare, comfort, green, night);
    }


    public static void main(String[] args) {
        Tariffs t1 = new Tariffs(5.0);
        Tariffs t2 = new Tariffs(4.0, 1.0, 3.0, 2.0);

        t1.printInfo();
        t2.printInfo();

        double distance = 5.5;
        System.out.println("Calculations check");
        System.out.printf("T1 Basic: %.2f\n",       t1.calculateStandard(distance,0));
        System.out.printf("T2 Basic: %.2f\n",       t2.calculateStandard(distance,0));
        System.out.printf("T1 Basic Night: %.2f\n", t1.calculateStandard(distance,1));
        System.out.printf("T1 Comfort: %.2f\n",     t1.calculateComfort(distance,0));
        System.out.printf("T1 Green: %.2f\n",       t1.calculateGreen(distance,0));
        System.out.printf("T1 Green Night: %.2f\n", t1.calculateGreen(distance,1));

        t1.setGreen(4.0);

        t1.printInfo();

        System.out.println(t1.getFare(GreenTaxi.class));
    }
}
