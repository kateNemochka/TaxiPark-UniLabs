package TaxiParkObjects;

import java.io.Serializable;


public class Taxi implements Serializable {
    private static int nextID = 0;
    private int taxiID;
    private String driver;
    private double profit;
    private double totalDistance;
    private TechnicalPassport techPassport;


    //*- Constructors + Inner classes -*\\
    public Taxi() {
        taxiID = ++nextID;
        driver = null;
        profit = 0.0;
        totalDistance = 0.0;
        techPassport = new TechnicalPassport();
    }

    public Taxi(String brand, String regNumber, String color,
                int productionYear, int registrationYear) {
        taxiID = ++nextID;
        driver = null;
        profit = 0.0;
        totalDistance = 0.0;
        techPassport = new TechnicalPassport(brand, regNumber, color,
                productionYear, registrationYear);
    }

    public Taxi(String driver, String brand, String regNumber, String color,
                int productionYear, int registrationYear) {
        taxiID = ++nextID;
        this.driver = driver;
        profit = 0.0;
        totalDistance = 0.0;
        techPassport = new TechnicalPassport(brand, regNumber, color,
                productionYear, registrationYear);
    }


    private static class TechnicalPassport implements Serializable {
        private String brand;
        private String regNumber;
        private String color;
        private int productionYear;
        private int registrationYear;


        TechnicalPassport() {
            brand = null;
            regNumber = null;
            color = null;
            productionYear = 0;
            registrationYear = 0;
        }

        TechnicalPassport(String brand, String regNumber, String color,
                          int productionYear, int registrationYear) {
            this.brand = brand;
            this.regNumber = regNumber;
            this.color = color;
            this.productionYear = productionYear;
            this.registrationYear = registrationYear;
        }

        public void setProductionData(String brand, String color, int productionYear) {
            this.brand = brand;
            this.color = color;
            this.productionYear = productionYear;
        }

        public void setRegistrationData(String regNumber, int registrationYear) {
            this.regNumber = regNumber;
            this.registrationYear = registrationYear;
        }

        public void info() {
            System.out.printf("Brand: %s\nColor: %s\nProduction Year: %d\n" +
                            "Registration Number: %s\nRegistration Year: %d\n",
                    brand, color, productionYear, regNumber, registrationYear);
        }

    }


    //*- Getters and Setters -*\\
    public int getID() {
        return taxiID;
    }
    public static void setNextID(int id) {
        nextID = id;
    }

    public String getTaxiType() {
        return "Basic";
    }

    public String getRegNumber() {
        return techPassport.regNumber;
    }
    public String getBrand() {
        return techPassport.brand;
    }
    public String getColor() {
        return techPassport.color;
    }
    public int getProductionYear() {
        return techPassport.productionYear;
    }
    public int getRegistrationYear() {
        return techPassport.registrationYear;
    }

    public String getDriver() {
        return driver;
    }
    public void setDriver(String driverName) {
        driver = driverName;
    }

    public double getProfit() {
        return profit;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public double getAverageFare() {
        return profit / totalDistance;
    }

    public double getFare(Tariffs tariff) {
        return tariff.getFare(this.getClass());
    }


    //*- Modifying object -*\\
    protected void addProfit(double profit) {
        this.profit += profit;
    }

    protected void addDistance(double distance) {
        this.totalDistance += distance;
    }

    public void addRideBasic(Tariffs tariff, double distance){
        double fare = tariff.calculateStandard(distance, 0);
        profit += fare;
        totalDistance += distance;
    }
    public void addNightRideBasic(Tariffs tariff, double distance) {
        double fare = tariff.calculateStandard(distance, 1);
        profit += fare;
        totalDistance += distance;
    }

    public void addRide(Tariffs tariff, double distance) {
        double fare = tariff.calculate(this.getClass(), distance, 0);
        profit += fare;
        totalDistance += distance;
    }
    public void addNightRide(Tariffs tariff, double distance) {
        double fare = tariff.calculate(this.getClass(), distance, 1);
        profit += fare;
        totalDistance += distance;
    }

    public void reset() {
        profit = 0.0;
        totalDistance = 0.0;
    }


    //*- Object information output -*\\
    public void printTaxiInfo() {
        System.out.printf("========\nTAXI ID: %d\n--------\n", taxiID);
        System.out.println(getTaxiType());
        System.out.printf("Driver: %s\nTotal profit: %.2f\nTotal distance: %.2f\n--------\n",
                driver, profit, totalDistance);

    }
    public void printCarInfo() {
        techPassport.info();
    }
    public void printInfo() {
        printTaxiInfo();
        printCarInfo();
        System.out.println("--------");
    }

    @Override
    public String toString() {
        return "Taxi{" +
                "taxiId=" + taxiID +
                ", driver='" + driver + '\'' +
                ", profit=" + profit +
                ", totalDistance=" + totalDistance +
                '}';
    }



    public static void main(String[] args) {
        Tariffs tariff = new Tariffs(5.0);

        Taxi t1 = new Taxi();

        Taxi t2 = new Taxi("John Smith", "Nissan", "ВН0987КА",
                "blue", 2008, 2016);

        Taxi t3 = new Taxi("Citroen", "АК5609АН",
                "black", 2010, 2017);

        t1.printInfo();
        t2.printInfo();
        t3.printInfo();

        t3.setDriver("Kora Nem");
        System.out.println("");
        t3.printInfo();
        System.out.println();

        t2.addRide(tariff, 23);
        t1.addNightRide(tariff, 10);

        t1.printTaxiInfo();
        System.out.println(t1.getAverageFare());

        t2.printTaxiInfo();
        System.out.println(t2.getAverageFare());

        t3.printTaxiInfo();
        System.out.println(t3.getAverageFare());
    }
}
