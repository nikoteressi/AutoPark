package application.mechanik;

import application.infrastructure.core.Context;
import application.infrastructure.threads.annotations.Schedule;
import application.vehiclecreation.ParserVehicleFromDB;
import application.vehiclesmodules.Vehicle;

import java.util.List;

public class CheckVehicleFromDBThread {

    public CheckVehicleFromDBThread() {
    }
    @Schedule(delta = 10000)
    public void checkVehicle(Context context) {
        context.getObject(Workroom.class).checkAllVehicle(parseVehicleFromDB(context));
    }
    private List<Vehicle> parseVehicleFromDB(Context context) {
        return context.getObject(ParserVehicleFromDB.class).fillVehicles();
    }

}
