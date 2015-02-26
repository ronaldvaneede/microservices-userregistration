package registrationservice.notifications;

public class UserDeletionNotification extends UserNotification {

    public UserDeletionNotification(String id, String emailAddress) {
        super(id, emailAddress);
    }

    @Override
    public String toString() {
        return "UserDeletionNotification{" +
                "id='" + getId() + '\'' +
                ", emailAddress='" + getEmailAddress() + '\'' +
                '}';
    }
}
