package pflegeplattform.service.data.entities;

import org.springframework.data.annotation.Id;
import pflegeplattform.service.data.enumeration.UserRole;

import javax.validation.constraints.NotNull;

/**
 * Created by phil on 12.01.17.
 */

public class User {
    @Id
    private long id;

    @NotNull
    private String email;

    @NotNull
    private String surname;

    @NotNull
    private String firstname;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private UserRole userRole;

    // Public methods

    public User() { }

    public User(long id) {
        this.id = id;
    }

    /**
     * Constructor with all parameters
     *
     * @param email email adress
     * @param surname lastname
     * @param firstname firstname
     * @param username username
     * @param password password
     */

    public User(String email, String surname, String firstname, String username, String password, UserRole userRole) {
        this.email = email;
        this.surname = surname;
        this.firstname = firstname;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}