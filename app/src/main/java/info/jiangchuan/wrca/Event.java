package info.jiangchuan.wrca;

import java.io.Serializable;

/**
 * Created by jiangchuan on 1/3/15.
 */
public class Event implements Serializable {

    static final long serialVersionUID = 42L;

    private String title;
    private String location;
    private String time;
    private String description;
    private String thumbnailUrl;
    private String contact;

    public Event() {

    }
    public Event(String title, String location, String time, String description, String thumbnailUrl, String contact) {
        this.title = title;
        this.location = location;
        this.time = time;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.contact = contact;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getContact() {
        return contact;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }
}

