package registrationservice.models;

public class RegistrationRequest {

    private String emailAddress;
    private String password;

    public RegistrationRequest(String emailAddress, String password) {
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getEmailAddress() {
        return this.emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
}