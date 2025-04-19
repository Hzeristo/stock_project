package com.haydenshui.stock.securities;

import com.haydenshui.stock.lib.entity.account.CorporateSecuritiesAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link CorporateSecuritiesAccount} entities.
 * <p>
 * This repository provides methods to perform CRUD operations and specific queries
 * related to corporate securities accounts.
 * </p>
 */
@Repository
public interface CorporateSecuritiesAccountRepository extends JpaRepository<CorporateSecuritiesAccount, Long> {

    /**
     * Finds a corporate securities account by its unique account number.
     *
     * @param accountNumber the unique account number
     * @return an {@link Optional} containing the found account, or empty if not found
     */
    Optional<CorporateSecuritiesAccount> findByAccountNumber(String accountNumber);

    /**
     * Finds a corporate securities account by the authorized trader's ID card number.
     *
     * @param idCardNo the ID card number of the authorized trader
     * @return an {@link Optional} containing the found account, or empty if not found
     */
    Optional<CorporateSecuritiesAccount> findByIdCardNo(String idCardNo);

    /**
     * Finds a corporate securities account by the corporation's registered name.
     *
     * @param corporationName the registered name of the corporation
     * @return an {@link Optional} containing the found account, or empty if not found
     */
    Optional<CorporateSecuritiesAccount> findByCorporationName(String corporationName);

    /**
     * Finds a corporate securities account by its business license number.
     *
     * @param businessLicenseNumber the business license number of the corporation
     * @return an {@link Optional} containing the found account, or empty if not found
     */
    Optional<CorporateSecuritiesAccount> findByBusinessLicenseNumber(String businessLicenseNumber);

    /**
     * Finds all corporate securities accounts associated with a specific authorized trader.
     *
     * @param authorizedTraderName the name of the authorized trader
     * @return a list of {@link CorporateSecuritiesAccount} entities matching the criteria
     */
    List<CorporateSecuritiesAccount> findByAuthorizedTraderName(String authorizedTraderName);
}
