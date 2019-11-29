package ie.gmit.ds;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.protobuf.ByteString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

    @NotNull
    private int userId;
    @NotBlank @Length(min=2, max=255)
    private String userName;
    @Pattern(regexp=".+@.+\\.[a-z]+")
    private String email;
    private ByteString hashedPassword;
    private ByteString salt;
    private String password;

    public User() {
        // Needed for Jackson deserialisation
    }

    public User(int userId, String userName, String email, ByteString hashedPassword, ByteString Salt) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public User(int userId, String userName, String email) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
    }
    @XmlElement
    @JsonProperty
    public String getUserName() {
        return userName;
    }
    @XmlElement
    @JsonProperty
    public String getEmail() {
        return email;
    }
    @XmlElement
    @JsonProperty
    public String getPassword() {
        return password;
    }
    @XmlElement
    @JsonProperty
    public ByteString getHashedPassword() {
        return hashedPassword;
    }
    @XmlElement
    @JsonProperty
    public ByteString getSalt() {
        return salt;
    }
    @XmlElement
    @JsonProperty
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", UserName=" + userName + ", email="
                + email + ", hashedPassword=" + hashedPassword + "]";
    }
}
