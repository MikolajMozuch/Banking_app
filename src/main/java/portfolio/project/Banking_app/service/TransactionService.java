package portfolio.project.Banking_app.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import portfolio.project.Banking_app.entity.Account;
import portfolio.project.Banking_app.entity.Transaction;
import portfolio.project.Banking_app.exception.InsufficientFundsException;
import portfolio.project.Banking_app.repository.AccountRepository;
import portfolio.project.Banking_app.repository.TransactionRepository;

import java.math.BigDecimal;

/**
 * Service class responsible for handling transactions between accounts.
 */
@Slf4j
@Service
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    /**
     * Makes a transaction between two accounts.
     * <p>
     * This method transfers a specified amount of money from the sender's account to the receiver's account.
     * It first retrieves the sender and receiver accounts from the repository based on their IDs. Then, it checks
     * if the sender's balance is sufficient to perform the transaction. If so, it deducts the specified amount from
     * the sender's balance and adds it to the receiver's balance. Finally, it saves the transaction details and logs
     * the success message.
     *
     * @param senderAccountNumber   The String of the sender's account.
     * @param receiverAccountNumber The String of the receiver's account.
     * @param amount                The amount of money to transfer.
     * @throws InsufficientFundsException      If the sender's balance is insufficient to perform the transaction.
     * //@throws TransactionOperationException If the transaction cannot be completed for other reasons.
     */
    @Transactional
    public void makeTransaction(String senderAccountNumber, String receiverAccountNumber, BigDecimal amount) {
        // Retrieve sender and receiver accounts from the repository
        Account sender = accountRepository.findByAccountNumber(senderAccountNumber);
        Account receiver = accountRepository.findByAccountNumber(receiverAccountNumber);
        // Check if sender's balance is sufficient
        if (sender.getBalance().compareTo(amount) >= 0) {
            // Deduct amount from sender's balance
            sender.setBalance(sender.getBalance().subtract(amount));
            // Add amount to receiver's balance
            receiver.setBalance(receiver.getBalance().add(amount));
            // Save transaction details
            transactionRepository.save(Transaction.builder()
                    .sender(sender)
                    .receiver(receiver)
                    .amount(amount)
                    .build());
            // Log success message
            log.info("Transaction successful: {} transferred from account {} to account {}", amount, senderAccountNumber, receiverAccountNumber);
        } else {
            // Throw exception if sender doesn't have sufficient balance
            throw new InsufficientFundsException("Sender doesn't have sufficient balance.");
        }
    }

}