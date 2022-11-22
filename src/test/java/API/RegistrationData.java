package API;

public class RegistrationData {
    private String email;
    private String password;

    public RegistrationData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
