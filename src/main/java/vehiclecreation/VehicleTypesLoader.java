package vehiclecreation;
import vehiclesmodules.Vehicle;
import vehiclesmodules.VehicleType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VehicleTypesLoader implements Loader<VehicleType> {
    @Override
    public List<VehicleType> load(String path) {
        List<VehicleType> types = new ArrayList<>();
        try {
          Scanner  scanner = new Scanner(new File(path));
            while (scanner.hasNext()) {
                types.add(new VehicleTypesCreator().create(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return types;
    }
}
