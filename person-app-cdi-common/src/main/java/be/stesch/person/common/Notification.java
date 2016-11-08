package be.stesch.person.common;

/**
 * @author Steve Schols
 * @since 1/09/2015
 */
public class Notification {

    private String notification;

    private boolean sent;

    public Notification(String notification) {
        this.notification = notification;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

}
