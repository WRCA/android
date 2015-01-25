package info.jiangchuan.wrca.models;

import java.io.Serializable;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class Notification implements Serializable {
    private String content;
    private String time;

    public Notification(String content, String time) {
        this.content = content;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
