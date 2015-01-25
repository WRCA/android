package info.jiangchuan.wrca.models;

import java.io.Serializable;

/**
 * Created by jiangchuan on 1/13/15.
 */
public class Place implements Serializable {
    static final long serialVersionUID = 42L;

    private String name;
    private String location;
    private String time;
    private String description;
    private String thumbnailUrl;
    private String contact;

    public Place() {

    }
    public Place(String name, String location, String time, String description, String thumbnailUrl, String contact) {
        this.name = name;
        this.location = location;
        this.time = time;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
        this.contact = contact;
    }

    public void setTitle(String name) {
        this.name = name;
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
        return name;
    }
}
