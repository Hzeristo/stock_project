package com.haydenshui.stock.securities;
 
import com.haydenshui.stock.lib.entity.account.IndividualSecuritiesAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

/**
 * JPA repository interface for securities accounts.
 * <p>
 * This interface provides database operations for the {@code SecuritiesAccount} entity,
 * supporting basic CRUD operations and methods to query accounts by account number and ID card number.
 * </p>
 *
 * @author Hzeristo
 * @version 1.0
 * @since 2025-03-04
 */
@Repository
public interface IndividualSecuritiesAccountRepository extends JpaRepository<IndividualSecuritiesAccount, Long> {

    /**
     * Finds a securities account by its account number.
     *
     * @param accountNumber The securities account number, must not be null and must be unique.
     * @return An {@code Optional<SecuritiesAccount>} containing the account if found, otherwise {@code Optional.empty()}.
     *
     * @throws IllegalArgumentException if {@code accountNumber} is null.
     */
    Optional<IndividualSecuritiesAccount> findByAccountNumber(String accountNumber);

    /**
     * Finds a securities account by ID card number.
     *
     * @param idCardNo The ID card number associated with the account, must not be null and must be unique.
     * @return An {@code Optional<SecuritiesAccount>} containing the account if found, otherwise {@code Optional.empty()}.
     *
     * @throws IllegalArgumentException if {@code idCardNo} is null.
     */
    Optional<IndividualSecuritiesAccount> findByIdCardNo(String idCardNo);

    /**
     * Finds a securities account by individual employer.
     *
     * @param individualEmployer The individual employer associated with the account, must not be null.
     * @return A list of {@code IndividualSecuritiesAccount} containing the account if found, otherwise {@code Optional.empty()}.
     *
     * @throws IllegalArgumentException if {@code individualEmployer} is null.
     */
    List<IndividualSecuritiesAccount> findByIndividualEmployer(String individualEmployer);
}
