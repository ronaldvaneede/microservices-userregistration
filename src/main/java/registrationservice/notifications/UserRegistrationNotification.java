package registrationservice.notifications;

public class UserRegistrationNotification extends UserNotification {

    public UserRegistrationNotification(String id, String emailAddress) {
        super(id, emailAddress);
    }

    @Override
    public String toString() {
        return "UserRegistrationNotification{" +
                "id='" + getId() + '\'' +
                ", emailAddress='" + getEmailAddress() + '\'' +
                '}';
    }
}
