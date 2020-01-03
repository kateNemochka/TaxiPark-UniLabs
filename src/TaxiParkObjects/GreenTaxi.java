package TaxiParkObjects;

import java.io.Serializable;


public class GreenTaxi extends Taxi implements Serializable {
    public GreenTaxi() {
        super();
    }

    public GreenTaxi(String brand, String regNumber, String color,
                        int productionYear, int registrationYear) {
        super(brand, regNumber, color, productionYear, registrationYear);
    }

    public GreenTaxi(String driver, String brand, String regNumber, String color,
                        int productionYear, int registrationYear) {
        super(driver, brand, regNumber, color, productionYear, registrationYear);
    }

    @Override
    public String getTaxiType() {
        return "Green";
    }
}
