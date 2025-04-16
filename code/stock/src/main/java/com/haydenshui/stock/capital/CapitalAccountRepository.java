package com.haydenshui.stock.capital;

import com.haydenshui.stock.lib.entity.account.CapitalAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA repository interface for capital accounts.
 * <p>
 * This interface provides database operations for the {@code CapitalAccount} entity,
 * supporting basic CRUD operations and methods to query accounts by account number and bank account number.
 * </p>
 *
 * @author Hzeristo
 * @version 1.0
 * @since 2025-03-04
 */
@Repository
public interface CapitalAccountRepository extends JpaRepository<CapitalAccount, Long> {

    /**
     * Finds a capital account by its account number.
     * <p>
     * This method retrieves a capital account using its unique account number ({@code capitalAccountNumber}).
     * </p>
     *
     * @param capitalAccountNumber The capital account number, must not be null and must be unique.
     * @return An {@code Optional<CapitalAccount>} containing the account if found, otherwise {@code Optional.empty()}.
     *
     * @throws IllegalArgumentException if {@code capitalAccountNumber} is null.
     * @see CapitalAccount
     */
    Optional<CapitalAccount> findByCapitalAccountNumber(String capitalAccountNumber);

    /**
     * Finds capital accounts by bank account number.
     * <p>
     * This method retrieves capital accounts associated with the given bank account number ({@code bankAccountNumber}).
     * </p>
     *
     * @param bankAccountNumber The bank account number, must not be null and must be unique.
     * @return An {@code List<CapitalAccount>} containing the accounts; may be empty if no accounts found.
     *
     * @throws IllegalArgumentException if {@code bankAccountNumber} is null.
     * @see CapitalAccount
     */
    List<CapitalAccount> findByBankAccountNumber(String bankAccountNumber);
}
