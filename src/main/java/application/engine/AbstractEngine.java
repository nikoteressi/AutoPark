package application.engine;

public abstract class AbstractEngine implements Startable {
    private String engineTypeName;
    private double taxCoefficientByEngineType;

    public AbstractEngine(String engineTypeName, double taxCoefficientByEngineType) {
        this.engineTypeName = engineTypeName;
        this.taxCoefficientByEngineType = taxCoefficientByEngineType;
    }

    public String getEngineTypeName() {
        return engineTypeName;
    }

    public void setEngineTypeName(String engineTypeName) {
        this.engineTypeName = engineTypeName;
    }

    public double getTaxCoefficientByEngineType() {
        return taxCoefficientByEngineType;
    }

    public void setTaxCoefficientByEngineType(double taxCoefficientByEngineType) {
        this.taxCoefficientByEngineType = taxCoefficientByEngineType;
    }

    @Override
    public String toString() {
        return  engineTypeName + ", " +  taxCoefficientByEngineType ;
    }
}
