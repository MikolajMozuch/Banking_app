package portfolio.project.Banking_app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.project.Banking_app.entity.Account;
import portfolio.project.Banking_app.exception.IncorrectPinException;
import portfolio.project.Banking_app.exception.ResourceNotFoundException;
import portfolio.project.Banking_app.repository.AccountRepository;
import portfolio.project.Banking_app.service.AccountService;
import portfolio.project.Banking_app.service.TransactionService;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * Controller class for handling operations related to bank accounts.
 * This controller provides endpoints for managing account PINs, deposits, withdrawals, and PIN verification.
 */
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    // Stuff not yet Done, Transaction History, Transaction.
    //Login Set up, Errors.
    //Search for specific Transaction By Account number/ name/ amount.
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionService transactionService;

    /**
     * Checks if the provided PIN matches the PIN associated with the account.
     *
     * @param id  The ID of the account.
     * @param pin The PIN to check.
     * @return ResponseEntity with true if the PIN matches, false otherwise.
     * @throws ResourceNotFoundException If the account with the given ID is not found.
     */
    @PutMapping("/{id}/check-pin")
    public ResponseEntity<Boolean> checkPin(@PathVariable Long id, @RequestParam String pin) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            return ResponseEntity.ok(account.getPin().equals(pin));
        } else {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
    }

    /**
     * Updates the PIN of the account with the given ID.
     *
     * @param id     The ID of the account.
     * @param oldPin The old PIN.
     * @param newPin The new PIN to set.
     * @return ResponseEntity with no content if the PIN is updated successfully.
     * @throws ResourceNotFoundException If the account with the given ID is not found.
     * @throws IncorrectPinException     If the old PIN provided is incorrect.
     */
    @PutMapping("/{id}/update-pin")
    public ResponseEntity<Void> updatePin(@PathVariable Long id, @RequestParam String oldPin, @RequestParam String newPin) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getPin().equals(oldPin)) {
                account.setPin(newPin);
                accountRepository.save(account);
                return ResponseEntity.noContent().build();
            } else {
                throw new IncorrectPinException("Incorrect old PIN provided.");
            }
        } else {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
    }

    /**
     * Creates a new PIN for the account with the given ID.
     *
     * @param id     The ID of the account.
     * @param newPin The new PIN to set.
     * @return ResponseEntity with no content if the PIN is created successfully.
     * @throws ResourceNotFoundException If the account with the given ID is not found.
     */
    @PutMapping("/{id}/create-pin")
    public ResponseEntity<Void> createPin(@PathVariable Long id, @RequestParam String newPin) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setPin(newPin);
            accountRepository.save(account);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
    }

    /**
     * Deposits a specified amount of money into the account with the given ID.
     *
     * @param id     The ID of the account.
     * @param amount The amount to deposit.
     * @return ResponseEntity with no content if the deposit is successful.
     * @throws ResourceNotFoundException If the account with the given ID is not found.
     */
    @PutMapping("/{id}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable Long id, @RequestParam BigDecimal amount) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            account.setBalance(account.getBalance().add(amount));
            accountRepository.save(account);
            return ResponseEntity.noContent().build();
        } else {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
    }

    /**
     * Withdraws a specified amount of money from the account with the given ID.
     *
     * @param id     The ID of the account.
     * @param pin    The PIN associated with the account.
     * @param amount The amount to withdraw.
     * @return ResponseEntity with no content if the withdrawal is successful.
     * @throws ResourceNotFoundException If the account with the given ID is not found.
     * @throws IncorrectPinException     If the PIN provided is incorrect.
     * @throws IllegalArgumentException  If the withdrawal amount exceeds the account balance.
     */
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable Long id, @RequestParam String pin, @RequestParam BigDecimal amount) {
        Optional<Account> optionalAccount = accountRepository.findById(id);
        if (optionalAccount.isPresent()) {
            Account account = optionalAccount.get();
            if (account.getPin().equals(pin)) {
                BigDecimal newBalance = account.getBalance().subtract(amount);
                if (newBalance.compareTo(BigDecimal.ZERO) >= 0) {
                    account.setBalance(newBalance);
                    accountRepository.save(account);
                    return ResponseEntity.noContent().build();
                } else {
                    throw new IllegalArgumentException("Insufficient funds.");
                }
            } else {
                throw new IncorrectPinException("Incorrect PIN provided.");
            }
        } else {
            throw new ResourceNotFoundException("Account not found with id: " + id);
        }
    }

    /**
     * Retrieves the balance of the account with the given ID.
     *
     * @param id The ID of the account.
     * @return ResponseEntity containing the balance of the account.
     * @throws ResourceNotFoundException If the account with the given ID is not found.
     */
    @GetMapping("/{id}/balance")
    public ResponseEntity<BigDecimal> checkBalance(@PathVariable Long id) {
        // Implementation for checkBalance method
        BigDecimal balance = accountService.checkBalance(id);
        return ResponseEntity.ok(balance);
    }
}