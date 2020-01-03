package TaxiParkObjects;

import java.io.Serializable;


public class ComfortTaxi extends Taxi implements Serializable {
    public ComfortTaxi() {
        super();
    }

    public ComfortTaxi(String brand, String regNumber, String color,
                       int productionYear, int registrationYear) {
        super(brand, regNumber, color, productionYear, registrationYear);
    }

    public ComfortTaxi(String driver, String brand, String regNumber, String color,
                       int productionYear, int registrationYear) {
        super(driver, brand, regNumber, color, productionYear, registrationYear);
    }

    @Override
    public String getTaxiType() {
        return "Comfort";
    }
}
