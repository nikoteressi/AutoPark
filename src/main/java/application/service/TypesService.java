package application.service;

import application.entity.Types;
import application.infrastructure.core.annotations.AutoWired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.orm.EntityManager;
import application.vehiclecreation.*;
import application.vehiclesmodules.VehicleType;
import java.util.List;
import java.util.Optional;

public class TypesService {
    @AutoWired
    EntityManager entityManager;

    @InitMethod
    public void init() {
        VehicleTypesLoader loader = new VehicleTypesLoader();
        List<VehicleType> types = loader.load("src/main/java/application/csvfiles/types.csv");
        for (VehicleType t : types) {
            Types type = Types.builder().id((long)t.getId()).name(t.getTypeName()).coefTaxes(t.getTaxCoefficient()).build();
            entityManager.save(type);
        }
    }

    public Optional<Types> get(Long id) {return entityManager.get(id, Types.class);}

    public List<Types> getAll() {return  entityManager.getAll(Types.class);}

    public Long save(Types type) {
        Types object = Types.builder().id(type.getId()).name(type.getName()).coefTaxes(type.getCoefTaxes()).build();
        entityManager.save(object);
        return type.getId();
    }
}
