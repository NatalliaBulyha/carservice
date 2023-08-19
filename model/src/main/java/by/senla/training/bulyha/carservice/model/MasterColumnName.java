package by.senla.training.bulyha.carservice.model;

import by.senla.training.bulyha.carservice.model.enums.MasterColumnNameEnum;

public class MasterColumnName {
    private MasterColumnNameEnum name;

    public MasterColumnName(MasterColumnNameEnum name) {
        this.name = name;
    }

    public MasterColumnNameEnum getName() {
        return name;
    }

    public void setName(MasterColumnNameEnum name) {
        this.name = name;
    }
}
