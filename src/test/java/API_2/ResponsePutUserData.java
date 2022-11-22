package API_2;

import java.util.Date;

public class ResponsePutUserData {
    private String name;
    private String job;
    private Date updatedAt;

    public ResponsePutUserData(String name, String job, Date updatedAt) {
        this.name = name;
        this.job = job;
        this.updatedAt = updatedAt;
    }

    public String getJob() {
        return job;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getName() {
        return name;
    }
}
