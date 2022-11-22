package API;

public class SuccessReg  {
    public int id;
    public String token;

    public SuccessReg(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
