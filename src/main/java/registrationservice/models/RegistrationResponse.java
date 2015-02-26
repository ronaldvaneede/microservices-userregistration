package registrationservice.models;

public class RegistrationResponse {

    private String id;
    private String emailAddress;
    
    public RegistrationResponse(String id, String emailAddress) {
        this.id = id;
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
