package Graphics;

import TaxiParkObjects.Tariffs;
import TaxiParkObjects.TaxiPark;

public abstract class ConstantsClass {
    public static TaxiPark taxiPark = new TaxiPark(15);
    public static Tariffs tariff = taxiPark.getTariff();
    public static int numOfRides = 0;
    public static double profitOfGone = 0;

    public static final String DataDefaultPath="D:\\Projects\\IdeaProjects\\TaxiPark\\src\\Data\\TAXI_PARK_DATA.taxi";
    public static String DataCurrentPath = null;

}
