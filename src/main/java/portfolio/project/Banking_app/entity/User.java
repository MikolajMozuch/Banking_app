package portfolio.project.Banking_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.text.DateFormat;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.*;


/**
 * Represents a user entity in the system.
 */
@Getter
@Setter
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "username", name = "username")})
public class User {
    /**
     * The unique identifier of the user.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The username of the user. Must be unique.
     */
    @NotBlank(message = "Username is required")
    private String username;

    /**
     * The password of the user.
     */
    @NotBlank(message = "Password is required")
    private String password;

    /**
     * The full name of the user.
     */
    @NotBlank(message = "Full name is required")
    private String fullName;

    /**
     * The email of the user.
     */
    @NotBlank(message = "Email is required")
    private String email;

    /**
     * The date of birth of the user.
     */
    @NotNull(message = "Birthdate is required")
    @Past(message = "Birthdate is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private DateFormat birthdate;

    /**
     * The list of accounts associated with this user.
     */
    // Lazy loading for accounts association
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Batch fetching to reduce database round trips
    @BatchSize(size = 10) // Adjust size based on your use case
    private List<Account> accounts;
}
