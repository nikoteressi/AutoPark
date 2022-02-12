package application.service;

import application.entity.Orders;
import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.orm.EntityManager;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class OrdersService {
    @AutoWired
    EntityManager entityManager;

    @InitMethod
    public void init() {
        try {
            File file = new File("src/main/java/application/csvfiles/orders.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String csvString = scanner.nextLine();
                String[] tokens = csvString.split(",");
                int vehicle_id = Integer.parseInt(tokens[0]);
                int grm = Integer.parseInt(tokens[2]);
                int oil = Integer.parseInt(tokens[4]);
                int filter = Integer.parseInt(tokens[6]);
                int shaft = Integer.parseInt(tokens[8]);
                int sleeve = Integer.parseInt(tokens[10]);
                int joint = Integer.parseInt(tokens[12]);
                int axis = Integer.parseInt(tokens[14]);
                int glow_plug = Integer.parseInt(tokens[16]);
                Orders orders = Orders.builder().vehicle_id(vehicle_id).oil(oil).filter(filter).shaft(shaft).sleeve(sleeve).joint(joint).axis(axis).glow_plug(glow_plug).grm(grm).build();
                entityManager.save(orders);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Orders> get(Long id) {return entityManager.get(id, Orders.class);}

    public List<Orders> getAll() {return  entityManager.getAll(Orders.class);}

    public Long save(Orders o) {
        Orders orders = Orders.builder().vehicle_id(o.getVehicle_id()).oil(o.getOil()).filter(o.getFilter()).shaft(o.getShaft()).sleeve(o.getSleeve()).joint(o.getJoint()).axis(o.getAxis()).glow_plug(o.getGlow_plug()).grm(o.getGrm()).build();
        entityManager.save(orders);
        return orders.getId();
    }
}
