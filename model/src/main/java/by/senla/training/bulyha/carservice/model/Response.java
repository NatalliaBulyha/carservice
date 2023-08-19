package by.senla.training.bulyha.carservice.model;

public class Response {
    private String userName;
    private String token;

    public Response(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "userName='" + userName + '\'' +
                ", token='" + token;
    }
}
