package vehiclecreation;

import vehiclesmodules.Rent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RentsLoader implements Loader<Rent>{


    @Override
    public List<Rent> load(String path) {
        List<Rent> rent = new ArrayList<>();
        try {
            Scanner  scanner = new Scanner(new File(path));
            while (scanner.hasNext()) {
                rent.add(new RentCreator().create(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return rent;
    }
}
