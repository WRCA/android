package info.jiangchuan.wrca.models;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class Result {
    int status;
    String message;

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
