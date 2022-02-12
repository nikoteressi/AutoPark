package application.mechanik;

import application.infrastructure.core.annotations.AutoWired;
import application.vehiclesmodules.Vehicle;

import java.util.*;

public class MechanicService implements Fixer{
    @AutoWired
    ParserBreakingFromFile parser;
    private static String[] details = {
            "Фильтр", "Втулка", "Вал", "Ось", "Свеча", "Масло", "ГРМ", "ШРУС"};

    public MechanicService() {
    }

    public ParserBreakingFromFile getParser() {
        return parser;
    }

    public void setParser(ParserBreakingFromFile parser) {
        this.parser = parser;
    }

    public static String[] getDetails() {
        return details;
    }

    public static void setDetails(String[] details) {
        MechanicService.details = details;
    }

    public Map<String, Integer> detectBreakdowns(Vehicle vehicle) {
        Map<String, Integer> result = fillBreakdownsMap(vehicle);
        parser.writeBreakings(result, vehicle);
        return result;
    }

    private Map<String, Integer> fillBreakdownsMap(Vehicle vehicle) {
        Map<String, Integer> breakdowns = new HashMap<>();
        int sum = 0;
        for (String s : details) {
            int randomNumber = (int) (Math.random() * 2);
            breakdowns.put(s, randomNumber);
            sum += randomNumber;
            if (randomNumber > 0) vehicle.setBroken(true);
        }
        vehicle.setSumOfBrokenParts(sum);
        return breakdowns;
    }

    public void repair(Vehicle vehicle) {
        List<String> result = new ArrayList<>();
        String[] details = vehicle.getBrokenParts().split(",");
        int counter = 0;
        for (int i = 1; i < details.length; i += 2) {
            if (Integer.parseInt(details[i]) != 0) {
                details[i] = "0";
                counter++;
            }
        }
        if (counter > 0) {
            System.out.println("Vehicle '" + vehicle.getModel() + "' was fixed");
            vehicle.setBroken(false);
            parser.clearBreackings(result, vehicle);
        }
        if (counter == 0) System.out.println("Vehicle is healthy");
    }

    public boolean isBroken(Vehicle vehicle) {
        return parser.checkVehicle(vehicle);
    }
}
