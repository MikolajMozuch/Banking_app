package portfolio.project.Banking_app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Data Transfer Object (DTO) for the Account entity.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    /**
     * The unique identifier of the account.
     */
    private Long id;

    /**
     * The account number.
     */
    @NotBlank(message = "Account number is required")
    private String accountNumber;

    /**
     * The PIN (Personal Identification Number) associated with the account.
     * Must be a 4-digit number.
     */
    @NotBlank(message = "PIN is required")
    @Pattern(regexp = "\\d{4}", message = "PIN must be a 4-digit number")
    private String pin;

    /**
     * The login number for accessing the account. Login on Website
     */
    @NotBlank(message = "Account login number is required")
    private String accountLoginNumber;

    /**
     * The login password for accessing the account. Login on Website
     */
    @NotBlank(message = "Account login password is required")
    private String accountLoginPassword;
    /**
     * The balance of the account.
     */
    @NotNull(message = "Balance is required")
    private BigDecimal balance;
}
