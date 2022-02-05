package application.service;

import application.entity.Rents;
import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.orm.EntityManager;
import application.vehiclecreation.ParserVehicleFromFile;
import application.vehiclecreation.RentCreator;
import application.vehiclesmodules.Rent;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class RentsService {
    @AutoWired
    EntityManager entityManager;

    @InitMethod
    public void init() {
        ParserVehicleFromFile parser = new ParserVehicleFromFile();
        List<Rent> rents = new ArrayList<>();
        try {
            Scanner  scanner = new Scanner(new File("src/main/java/application/csvfiles/rents.csv"));
            String csvString = "";
            Rent rent = new Rent();
            while (scanner.hasNext()) {
                csvString = scanner.nextLine();
                String[] parameters = csvString.split(",");
                if (parameters.length < 2) throw new InputMismatchException("Incorrect data in .csv file");
                String date = parameters[1];
                int id = Integer.parseInt(parameters[0]);
                double rentCost = Double.parseDouble(parameters[2]);
                rent.setRentCost(rentCost);
                rent.setRentDate(date);
                Rents ren = Rents.builder().rent_cost(rent.getRentCost()).rent_date(rent.getRentDate()).vehicle_id(id).build();
                entityManager.save(ren);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Optional<Rents> get(Long id) {return entityManager.get(id, Rents.class);}

    public List<Rents> getAll() {return  entityManager.getAll(Rents.class);}

    public Long save(Rents r) {
        Rents rent = Rents.builder().id(r.getId()).rent_cost(r.getRent_cost()).rent_date(r.getRent_date()).vehicle_id(r.getVehicle_id()).build();
        entityManager.save(rent);
        return rent.getId();
    }
}
