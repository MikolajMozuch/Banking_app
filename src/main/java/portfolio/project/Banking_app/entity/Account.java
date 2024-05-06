package portfolio.project.Banking_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.BatchSize;

/**
 * Represents a bank account entity.
 */
@Getter
@Setter
@Entity
@Table(
        name = "accounts",
        uniqueConstraints = @UniqueConstraint(columnNames = "accountNumber")
)
public class Account {
    /**
     * The unique identifier of the account.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The account number.
     */
    @NotBlank(message = "Account number is required")
    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    /**
     * The PIN (Personal Identification Number) associated with the account.
     * Must be a 4-digit number.
     */
    @NotBlank
    @Pattern(regexp = "\\d{4}", message = "PIN must be a 4-digit number")
    private String pin;

    /**
     * The login number for accessing the account.
     */
    @NotBlank(message = "Account login number is required")
    @Column(name = "account_login_number", nullable = false, unique = true)
    private String accountLoginNumber;

    /**
     * The login password for accessing the account.
     */
    @NotBlank(message = "Account login password is required")
    private String accountLoginPassword;

    /**
     * The balance of the account.
     */
    @NotNull(message = "Balance is required")
    private BigDecimal balance;

    /**
     * The user associated with this account.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Defines the foreign key column for the associated user
    private User user;
/*
    /**
     * The list of transactions associated with this account.
     */
    // Lazy loading for transactions.
    /*
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10) // Fetch multiple transactions in fewer queries
    private List<Transaction> transactions;
*/
    @OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10) // Fetch multiple transactions in fewer queries
    private List<Transaction> sentTransactions;

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 10) // Fetch multiple transactions in fewer queries
    private List<Transaction> receivedTransactions;
}
