package application.garage;

import application.vehiclesmodules.Vehicle;

public class Garage<T extends Vehicle> {
    private final int FIRST_AMOUNT_OF__ARRAY_ELEMENT = 10;
    private static Vehicle[] vehiclesInGarage;
    private static int counterForElements;

    public Garage() {
        vehiclesInGarage = new Vehicle[FIRST_AMOUNT_OF__ARRAY_ELEMENT];
    }

    public static boolean isEmpty() {
        return counterForElements == 0;
    }

    public static void addToGarage(Vehicle element) {
        if (counterForElements == vehiclesInGarage.length) {
            System.out.println("The application.garage.Garage is FULL");
        }
        vehiclesInGarage[counterForElements] = element;
        vehiclesInGarage[counterForElements].setGarage(true);
        counterForElements++;

        System.out.println("vehiclescollection.Vehicle " + element.getModel() + " added to application.garage");
    }

    private static Vehicle remove() {
        counterForElements--;
        Vehicle result = vehiclesInGarage[counterForElements];
        System.out.println("vehiclescollection.Vehicle " + vehiclesInGarage[counterForElements].getModel() + " was removed from application.garage");
        result.setGarage(false);
        return result;
    }

    public static void removeFromGarage() {
        for (int i = 0; i < vehiclesInGarage.length; i++) {
            if (isEmpty()) {
                System.out.println("application.garage.Garage is empty");
                return;
            }
            remove();
        }
    }
}
