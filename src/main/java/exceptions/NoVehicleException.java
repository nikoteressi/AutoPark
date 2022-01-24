package exceptions;

public class NoVehicleException extends Exception{
    public NoVehicleException() {
    }

    public NoVehicleException(String message) {
        super(message);
    }
}
