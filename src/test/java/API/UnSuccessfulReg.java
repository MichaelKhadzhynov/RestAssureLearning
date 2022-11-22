package API;

public class UnSuccessfulReg {
    private String error;

    public UnSuccessfulReg (String error){
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
