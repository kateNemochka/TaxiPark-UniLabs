package TaxiParkObjects;

import java.io.Serializable;


public class StandardTaxi extends Taxi implements Serializable {
    public StandardTaxi() {
        super();
    }

    public StandardTaxi(String brand, String regNumber, String color,
                        int productionYear, int registrationYear) {
        super(brand, regNumber, color, productionYear, registrationYear);
    }

    public StandardTaxi(String driver, String brand, String regNumber, String color,
                        int productionYear, int registrationYear) {
        super(driver, brand, regNumber, color, productionYear, registrationYear);
    }

    @Override
    public String getTaxiType() {
        return "Standard";
    }
}
