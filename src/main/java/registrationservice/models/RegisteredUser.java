package registrationservice.models;

import org.springframework.data.annotation.Id;

public class RegisteredUser {

    @Id
    private String id;
    private String emailAddress;
    private String password;

    public RegisteredUser() {}

    public RegisteredUser(String id, String emailAddress, String password) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "RegisteredUser{" +
                "id='" + id + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", password='*****'" +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RegisteredUser that = (RegisteredUser) o;

        if (emailAddress != null ? !emailAddress.equals(that.emailAddress) : that.emailAddress != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return emailAddress != null ? emailAddress.hashCode() : 0;
    }
}
