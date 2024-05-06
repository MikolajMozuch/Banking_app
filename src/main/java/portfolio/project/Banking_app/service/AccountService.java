package portfolio.project.Banking_app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import portfolio.project.Banking_app.entity.Account;
import portfolio.project.Banking_app.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Service class for managing accounts.
 */
@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Saves the provided account.
     *
     * @param account The account to save.
     * @return The saved account.
     */
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    /**
     * Retrieves an account by its ID.
     *
     * @param id The ID of the account to retrieve.
     * @return The account if found, otherwise null.
     */
    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElse(null);
    }

    /**
     * Retrieves all accounts.
     *
     * @return List of all accounts.
     */
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    /**
     * Deletes an account by its ID.
     *
     * @param id The ID of the account to delete.
     */
    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    /**
     * Deposits an amount into the account specified by the ID.
     *
     * @param accountId The ID of the account to deposit into.
     * @param amount    The amount to deposit.
     */
    public void deposit(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            BigDecimal currentBalance = account.getBalance();
            account.setBalance(currentBalance.add(amount));
            accountRepository.save(account);
        }
    }

    /**
     * Withdraws an amount from the account specified by the ID.
     *
     * @param accountId The ID of the account to withdraw from.
     * @param amount    The amount to withdraw.
     * @throws IllegalArgumentException If there are insufficient funds.
     */
    public void withdraw(Long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            BigDecimal currentBalance = account.getBalance();
            if (currentBalance.compareTo(amount) >= 0) {
                account.setBalance(currentBalance.subtract(amount));
                accountRepository.save(account);
            } else {
                throw new IllegalArgumentException("Insufficient funds");
            }
        }
    }

    /**
     * Checks the balance of the account specified by the ID.
     *
     * @param accountId The ID of the account to check balance.
     * @return The balance of the account.
     * @throws IllegalArgumentException If the account is not found.
     */
    public BigDecimal checkBalance(Long accountId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            return account.getBalance();
        } else {
            throw new IllegalArgumentException("Account not found");
        }
    }
}

