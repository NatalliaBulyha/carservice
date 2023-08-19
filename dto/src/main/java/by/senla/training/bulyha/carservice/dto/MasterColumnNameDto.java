package by.senla.training.bulyha.carservice.dto;

public class MasterColumnNameDto {
    private String mastersColumnName;

    public MasterColumnNameDto(String mastersColumnName) {
        this.mastersColumnName = mastersColumnName;
    }

    public String getMastersColumnName() {
        return mastersColumnName;
    }

    public void setMastersColumnName(String mastersColumnName) {
        this.mastersColumnName = mastersColumnName;
    }

    @Override
    public String toString() {
        return "MasterColumnNameDto{" +
                "mastersColumnName='" + mastersColumnName + '\'' +
                '}';
    }
}
