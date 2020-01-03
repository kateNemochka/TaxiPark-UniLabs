package TaxiParkObjects;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class TaxiPark implements Serializable {
    private int numberOfCars = 0;
    private int nextID = 0;
    private Map<Integer, Taxi> cars;
    private Tariffs tariff;
    private static final Random random = new Random();
    private static final Map<Integer, Class> taxiTypes = new HashMap<Integer, Class>() {{
        put(0, StandardTaxi.class); put(1, ComfortTaxi.class); put(2, GreenTaxi.class);
    }};


    public TaxiPark(double fare) {
        cars = new LinkedHashMap<Integer, Taxi>();
        tariff = new Tariffs(fare);

    }

    public void addRandomCar() {
        String[] brands = new String[]{"Mazda", "BMW", "Nissan", "Suzuki", "Audi", "Daewoo", "Ford", "Toyota", "Seat"};
        String[] colors = new String[]{"red", "silver", "black", "yellow", "blue", "white", "gray"};
        String[] regionCodes = new String[]{"AA", "AI", "BH", "BC", "CB", "BI", "AE", "AT", "BK"};

        int type = random.nextInt(3);
        int registrationYear = 2019 - random.nextInt(5);
        int productionYear = registrationYear - random.nextInt(5);
        String regNumber = regionCodes[random.nextInt(regionCodes.length)]
                + (random.nextInt(8999) + 1000)
                + regionCodes[random.nextInt(regionCodes.length)];
        String brand = brands[random.nextInt(brands.length)];
        String color = colors[random.nextInt(colors.length)];

        addTaxi(type, "some driver", brand, regNumber, color, productionYear, registrationYear);
    }

    public void addTaxi(int type, String brand, String regNumber, String color,
                        int productionYear, int registrationYear) {
        numberOfCars++;
        switch (type) {
            case (0):
                cars.put(++nextID, new StandardTaxi(brand, regNumber, color, productionYear, registrationYear));
                break;
            case (1):
                cars.put(++nextID, new ComfortTaxi(brand, regNumber, color, productionYear, registrationYear));
                break;
            case (2):
                cars.put(++nextID, new GreenTaxi(brand, regNumber, color, productionYear, registrationYear));
                break;
        }
    }

    public void addTaxi(int type, String driver, String brand, String regNumber, String color,
                        int productionYear, int registrationYear) {
        numberOfCars++;
        switch (type) {
            case (0):
                cars.put(++nextID, new StandardTaxi(driver, brand, regNumber, color, productionYear, registrationYear));
                break;
            case (1):
                cars.put(++nextID, new ComfortTaxi(driver, brand, regNumber, color, productionYear, registrationYear));
                break;
            case (2):
                cars.put(++nextID, new GreenTaxi(driver, brand, regNumber, color, productionYear, registrationYear));
                break;
        }
    }

    public int removeTaxi(int taxiID) {
        numberOfCars--;
        cars.remove(taxiID);
        return taxiID;
    }

    public void setDriver(int taxiID, String driver) {
        Taxi taxi = cars.get(taxiID);
        taxi.setDriver(driver);
        cars.put(taxiID, taxi);
    }

    public Taxi getTaxi(int taxiID) {
        return cars.get(taxiID);
    }

    public Map<Integer, Taxi> getCars() {
        return cars;
    }

    public int getNumberOfCars() {
        return numberOfCars;
    }
    public int getNumberOfCars(int type) {
        return getTaxisOfType(type).size();
    }

    public ArrayList<Integer> getTaxiIDs() {
        return new ArrayList<Integer>(cars.keySet());
    }

    public Tariffs getTariff() {
        return tariff;
    }
    public void setTariff(Tariffs tariff) {
        this.tariff = tariff;
    }

    public void addRide(int type, double distance, boolean night) {
        ArrayList<Taxi> taxisOfType = (ArrayList<Taxi>) getTaxisOfType(type)
                .stream()
                .filter(taxi -> taxi.getDriver() != null)
                .collect(Collectors.toList());
        if (taxisOfType.size() == 0) {
            if (getTaxisOfType(type).size() > taxisOfType.size()) {
                System.out.printf("There are no type %d cars with drivers. " +
                        "Find some new drivers!\n", type);
            }
            else {
                System.out.printf("There are no any type %d cars. Add some new cars!", type);
            }
        }
        else {
            int index = random.nextInt(taxisOfType.size());
            Taxi taxi = taxisOfType.get(index);
            int taxiID = taxi.getID();
            if (night) {
                taxi.addNightRide(tariff, distance);
            } else {
                taxi.addRide(tariff, distance);
            }
            cars.put(taxiID, taxi);
        }
    }

    public void printTaxiParkInfoBrief() {
        cars.values().forEach(Taxi::printTaxiInfo);
    }
    public void printTaxiParkInfo() {
        cars.values().forEach(Taxi::printTaxiInfo);
    }
    public void printTaxisInfoBrief(ArrayList<Taxi> taxis) {
        taxis.forEach(Taxi::printTaxiInfo);
    }

    // * створювати колекцію, яка містить тільки унікальні елементи *
    public Set<String> getAvailableTaxiTypes() {
        return getCars().values().stream()
                .map(Taxi::getTaxiType)
                .collect(Collectors.toSet());
    }

    // ФІЛЬТРУВАННЯ об'єктів колекції за вибраною ознакою
    public ArrayList<Taxi> getTaxisOfType(int type) {
        return (ArrayList<Taxi>) cars.values()
                .stream()
                .filter(taxi -> taxi.getClass() == taxiTypes.get(type))
                .collect(Collectors.toList());
    }

    public String getTaxiInformationBrief(int taxiID) {
        Taxi taxi = getTaxi(taxiID);
        return "--------\nTAXI ID: "+ taxiID +"\n========\n"
                + taxi.getTaxiType() + "\n"
                + "Driver: " + taxi.getDriver()
                + "\nTotal Profit: " + String.valueOf(taxi.getProfit())
                + "\nTotal Distance: " + String.valueOf(taxi.getTotalDistance());
    }

    public String[][] getCarsTabledInformation() {
        /*id,type,money,dist, regnumber,brand,color,prodyear,regyear*/
        String[][] info = new String[cars.size()][9];
        int row = 0;
        for (Taxi taxi : cars.values()) {
            info[row][0] = String.valueOf(taxi.getID());
            info[row][1] = String.valueOf(taxi.getTaxiType());
            info[row][2] = String.valueOf(taxi.getProfit());
            info[row][3] = String.valueOf(taxi.getTotalDistance());
            info[row][4] = String.valueOf(taxi.getRegNumber());
            info[row][5] = String.valueOf(taxi.getBrand());
            info[row][6] = String.valueOf(taxi.getColor());
            info[row][7] = String.valueOf(taxi.getProductionYear());
            info[row][8] = String.valueOf(taxi.getRegistrationYear());
            ++row;
        }
        return info;
    }

    public ArrayList<Taxi> getTaxisWithoutDriver() {
        return (ArrayList<Taxi>) cars.values()
                .stream()
                .filter(taxi -> taxi.getDriver() == null)
                .collect(Collectors.toList());
    }

    // ВИДАЛЕННЯ з колекції об'єктів, які не відповідають заданому критерію
    public void removeTaxisWithDistanceOver(double distance) {
        ArrayList<Integer> IDs = (ArrayList<Integer>) cars.values().stream()
                .filter(taxi -> taxi.getTotalDistance() > distance)
                .map(Taxi::getID)
                .collect(Collectors.toList());
        for (int id : IDs) {
            cars.remove(id);
        }
    }

    // REDUCE - визначати для колекції суму заданої кількісної ознаки
    public double getTotalProfit() {
        return cars.values().stream()
                .mapToDouble(Taxi::getProfit)
                .reduce(0, (a, b) -> a + b);
    }
    public double getTotalDistance() {
        return cars.values().stream()
                .mapToDouble(Taxi::getTotalDistance)
                .reduce(0, Double::sum);
    }

    public void addRandomRides(int numOfRides) {
        while (numOfRides-- > 0) {
            int type = random.nextInt(3);
            double distance = Math.round(random.nextDouble()*random.nextInt(50));
            boolean night = random.nextBoolean();
            addRide(type, distance, night);
        }
    }

    // метод сортування об'єктів за кількома вибраними ознаками
    // - анонімний клас
    // - лямбда-вираз
    // - посилання на метод
    public void lambdaSort() {
        cars = getCars().entrySet().stream()
                .sorted(Comparator.comparingDouble(e -> e.getValue().getProfit()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
    }
    public void refSort() {
        List<Taxi> taxis = new ArrayList<>(cars.values());
        cars.clear();
        taxis.stream()
                .sorted(Comparator.comparing(Taxi::getProfit))
                .forEachOrdered(t -> cars.put(t.getID(), t));
    }
    public void anonymousSort() {
        ArrayList<Taxi> taxis = new ArrayList<>(cars.values());
        cars.clear();
        taxis.sort(new Comparator<Taxi>() {
            @Override
            public int compare(Taxi t1, Taxi t2) {
                if (t1.getFare(getTariff()) == t2.getFare(getTariff())) {
                    if (t1.getProfit() == t2.getProfit()) {
                        return 0;
                    }
                    else {
                        return (t1.getProfit() < t2.getProfit()) ? -1 : 1;
                    }
                }
                else {
                    return (t1.getFare(getTariff()) < t2.getFare(getTariff())) ? -1 : 1;
                }
            }
        });
        taxis.forEach(t -> cars.put(t.getID(), t));
    }



    public static void main(String[] args) {
        TaxiPark taxiPark = new TaxiPark(4);
        taxiPark.addTaxi(2, "John Smith","Nissan", "АА9087КН",
                "red", 2015, 2015);
        taxiPark.addTaxi(0, "Citroen", "АВ4523ВА",
                "blue", 2009, 2014);
        taxiPark.addTaxi(1, "Ford", "АІ7834АК",
                "white", 2011, 2016);
        taxiPark.addTaxi(2,"BMW", "АА1001АА",
                "gray", 2018, 2019);
        taxiPark.addTaxi(0, "Anonymous Driver", "Daewoo", "АК9080ВА",
                "blue", 2016, 2016);
        taxiPark.addTaxi(0, "Jack", "Reno", "АК4973ВА",
                "gray", 2016, 2016);

        taxiPark.addRandomRides(50);

        System.out.println("\n==== BEFORE HAVING ENOUGH DRIVERS ====");
        taxiPark.printTaxiParkInfoBrief();

        System.out.println("\n*** Taxis without drivers ***");
        taxiPark.printTaxisInfoBrief(taxiPark.getTaxisWithoutDriver());

        taxiPark.setDriver(3, "Mark");
        taxiPark.setDriver(4, "Kate");

        taxiPark.addRandomRides(100);

        System.out.println("\n==== 2 MORE DRIVERS ADDED ====");
        taxiPark.printTaxiParkInfoBrief();

        System.out.printf("\n==-==-==-==-==-==-==-==-==-==-==-==-==\n" +
                        "TOTAL TAXI PARK PROFIT: %.2f\n" +
                        "==-==-==-==-==-==-==-==-==-==-==-==-==\n\n",
                taxiPark.getTotalProfit());

        System.out.println("Standard Taxis");
        System.out.println(taxiPark.getTaxisOfType(0));

        taxiPark.setDriver(taxiPark.getTaxisWithoutDriver().get(0).getID(), "Karl");

        taxiPark.addTaxi(1, "Tom", "Toyota", "АА8877АА",
                "white", 2018, 2018);

        System.out.println("\n==== ADDED NEW CAR, ALL CARS WITH DRIVERS ====");
        taxiPark.printTaxiParkInfoBrief();

        taxiPark.addRandomRides(200);

        System.out.println("\n==== AFTER 200 RIDES ====");
        System.out.printf("==-==-==-==-==-==-==-==-==-==-==-==-==\n" +
                        "TOTAL TAXI PARK PROFIT: %.2f\n" +
                        "==-==-==-==-==-==-==-==-==-==-==-==-==\n\n",
                taxiPark.getTotalProfit());
        taxiPark.printTaxiParkInfoBrief();

        System.out.println("\n==== REMOVING CARS WITH MORE THAN 3000 KM DISTANCE ====");
        taxiPark.removeTaxisWithDistanceOver(2500);
        taxiPark.printTaxiParkInfoBrief();

        System.out.println("\n==== AFTER LAMBDA SORT BY PROFIT ====");
        taxiPark.lambdaSort();
        taxiPark.printTaxiParkInfoBrief();
        System.out.println("\n==== AFTER ANONYMOUS SORT BY FARE AND PROFIT ====");
        taxiPark.anonymousSort();
        taxiPark.printTaxiParkInfoBrief();
        System.out.println("\n==== AFTER REFERENCE SORT BY PROFIT ====");
        taxiPark.refSort();
        taxiPark.printTaxiParkInfoBrief();

    }
}