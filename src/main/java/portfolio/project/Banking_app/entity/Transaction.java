package portfolio.project.Banking_app.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Represents a transaction entity in the system.
 */
@Data
@AllArgsConstructor
@Entity
@Builder
@Table(name = "transactions")
public class Transaction {
    /**
     * The unique identifier of the transaction.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The amount of the transaction.
     */
    @NotBlank(message = "Amount is required")
    private BigDecimal amount;

    /**
     * The timestamp of the transaction.
     */
    @NotNull(message = "Timestamp is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    /**
     * The account associated with this transaction.
     * On sender end.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sender_id", nullable = false)
    private Account sender;

    /**
     * The account associated with this transaction.
     * On receiver end.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "receiver_id", nullable = false)
    private Account receiver;
}
