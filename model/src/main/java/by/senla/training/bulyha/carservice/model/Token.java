package by.senla.training.bulyha.carservice.model;

import javax.persistence.Entity;

@Entity
public class Token extends IdEntity {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
