package portfolio.project.Banking_app;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import portfolio.project.Banking_app.entity.Account;
import portfolio.project.Banking_app.repository.AccountRepository;
import portfolio.project.Banking_app.service.AccountService;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link AccountService}.
 */
class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    private Account testAccount;
    /**
     * Set up method to initialize mocks and create a test account.
     */
    @BeforeEach
    void setUp() {
        // Initialize mocks
        MockitoAnnotations.openMocks(this);

        // Create a test account
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setAccountNumber("123456789");
        testAccount.setPin("1234");
        testAccount.setAccountLoginNumber("login123");
        testAccount.setAccountLoginPassword("password123");
        testAccount.setBalance(BigDecimal.valueOf(1000));

        // Stubbing behavior for accountRepository
        when(accountRepository.findById(anyLong())).thenReturn(Optional.of(testAccount));
    }


    /**
     * Test method to verify the behavior of saving an account.
     */
    @Test
    @DisplayName("Saving Account")
    void saveAccount_ShouldReturnSavedAccount() {
        // Arrange
        when(accountRepository.save(testAccount)).thenReturn(testAccount);

        // Act
        Account savedAccount = accountService.saveAccount(testAccount);

        // Assert
        assertNotNull(savedAccount, "Saved account should not be null");
        assertEquals(testAccount, savedAccount, "Saved account should be equal to the test account");
    }

    /**
     * Test method to verify the behavior of retrieving an account by ID when the ID exists.
     */
    @Test
    @DisplayName("Retrieving Account by ID (Existing)")
    void getAccountById_ExistingId_ShouldReturnAccount() {
        // Act
        Account retrievedAccount = accountService.getAccountById(1L);

        // Assert
        assertNotNull(retrievedAccount, "Retrieved account should not be null");
        assertEquals(testAccount, retrievedAccount, "Retrieved account should be equal to the test account");
    }
    /**
     * Test method to verify the behavior of retrieving an account by ID when the ID doesn't exist.
     */
    @Test
    @DisplayName("Retrieving Account by ID (Non-Existing)")
    void getAccountById_NonExistingId_ShouldReturnNull() {
        // Arrange
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Account retrievedAccount = accountService.getAccountById(2L);

        // Assert
        assertNull(retrievedAccount, "Retrieved account should be null for non-existing ID");
    }

    /**
     * Test method to verify the behavior of depositing an amount into an account.
     */
    @Test
    @DisplayName("Depositing Amount")
    void deposit_ShouldIncreaseBalance() {
        // Arrange
        BigDecimal depositAmount = BigDecimal.valueOf(500);
        BigDecimal expectedBalance = testAccount.getBalance().add(depositAmount);

        // Act
        accountService.deposit(1L, depositAmount);

        // Assert
        assertEquals(expectedBalance, testAccount.getBalance(), "Balance should increase after deposit");
    }

    /**
     * Test method to verify the behavior of withdrawing an amount from an account with sufficient funds.
     */
    @Test
    @DisplayName("Withdrawing Sufficient Funds")
    void withdraw_SufficientFunds_ShouldDecreaseBalance() {
        // Arrange
        BigDecimal withdrawAmount = BigDecimal.valueOf(500);
        BigDecimal expectedBalance = testAccount.getBalance().subtract(withdrawAmount);

        // Act
        accountService.withdraw(1L, withdrawAmount);

        // Assert
        assertEquals(expectedBalance, testAccount.getBalance(), "Balance should decrease after withdrawal");
    }

    /**
     * Test method to verify the behavior of withdrawing an amount from an account with insufficient funds.
     */
    @Test
    @DisplayName("Withdrawing Insufficient Funds")
    void withdraw_InsufficientFunds_ShouldThrowException() {
        // Arrange
        BigDecimal withdrawAmount = BigDecimal.valueOf(1500);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> accountService.withdraw(1L, withdrawAmount),
                "Exception should be thrown for insufficient funds");
    }

    /**
     * Test method to verify the behavior of checking the balance of an existing account.
     */
    @Test
    @DisplayName("Checking Balance of Existing Account")
    void checkBalance_ExistingAccount_ShouldReturnBalance() {
        // Act
        BigDecimal balance = accountService.checkBalance(1L);

        // Assert
        assertEquals(testAccount.getBalance(), balance, "Balance should be equal to the test account's balance");
    }

    /**
     * Test method to verify the behavior of checking the balance of a non-existing account.
     */
    @Test
    @DisplayName("Checking Balance of Non-Existing Account Should Throw Exception")
    void checkBalance_NonExistingAccount_ShouldThrowException() {
        // Arrange - Stubbing behavior for accountRepository
        when(accountRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            accountService.checkBalance(2L);
        }, "Expected IllegalArgumentException was not thrown");
    }
}
