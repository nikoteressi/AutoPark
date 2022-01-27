package application.washing;

import application.exceptions.NoVehicleException;
import application.vehiclesmodules.Vehicle;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class WashingQueue<T extends Vehicle> {
    private static final int FIRST_AMOUNT_OF__ARRAY_ELEMENT = 10;
    private static Vehicle[] queue;
    private static int counterForElements;
    private int indexForRemoveOperation;

    public WashingQueue() {
        queue = new Vehicle[FIRST_AMOUNT_OF__ARRAY_ELEMENT];
    }

    public static boolean add(Vehicle element) {
        if (counterForElements == queue.length) {
            queue = Arrays.copyOf(queue, queue.length + FIRST_AMOUNT_OF__ARRAY_ELEMENT);
        }
        queue[counterForElements] = element;
        counterForElements++;
        System.out.println("vehiclescollection.Vehicle " + element.getModel() + "got in the queue for a car wash");
        return true;
    }


    public Vehicle remove() {
        if (queue[0] == null) throw new NoSuchElementException();
        if (counterForElements % 10 == 0) {
            queue = Arrays.copyOf(queue, queue.length - FIRST_AMOUNT_OF__ARRAY_ELEMENT);
            indexForRemoveOperation = 0;
        }
        Vehicle firstElement = queue[indexForRemoveOperation];
        indexForRemoveOperation++;
        counterForElements--;
        return firstElement;
    }

    public static void startWash() throws NoVehicleException {
        if (queue.length == 0) {
            throw new NoVehicleException("No vehicles in the queue");
        }
        System.out.println("Washing was started");
        for (int i = 0; i < counterForElements; i++) {
            System.out.println("vehiclescollection.Vehicle " + queue[i].getModel() + " was washed");
            queue[i].setWashed(true);
        }
        queue = new Vehicle[0];
    }

    public int size() {
        return counterForElements;
    }
}
