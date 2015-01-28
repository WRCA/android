package info.jiangchuan.wrca.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiangchuan on 1/25/15.
 */
public class User implements Serializable{

    // account
    String name;
    String email;
    String password;
    String token;

    boolean login;

    // preference
    boolean autoLogin; // auto login
    boolean silent;    // no sound
    boolean notification = true; // require notifications

    // user saved events
    private List<Event> events = new ArrayList<Event>();

    // saved notifications
    private List<Notification> notifications = new ArrayList<Notification>();


    public boolean requireNotification() {
        return notification;
    }

    public void setNotification(boolean notification) {
        this.notification = notification;
    }

    public boolean isSilent() {
        return silent;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
    }
}
