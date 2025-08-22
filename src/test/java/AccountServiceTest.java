import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountServiceTest {
    @Test
    void test_two_account_null() {
        // Given
        Account from = null;
        Account to = null;
        double amount = 0;
        // When
        TransferService transferService = new TransferService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transferService.transfer(from, to, amount));
        // Then
        assertEquals("Accounts cannot be null", exception.getMessage());
    }

    @Test
    void test_from_account_null() {
        // Given
        Account from = null;
        Account to = new Account("123", 2);
        double amount = 0;
        // When
        TransferService transferService = new TransferService();
        // Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transferService.transfer(from, to, amount));
        assertEquals("Accounts cannot be null", exception.getMessage());
    }

    @Test
    void test_to_account_null() {
        // Given
        Account from = new Account("123", 2);
        Account to = null;
        double amount = 0;
        // When
        TransferService transferService = new TransferService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transferService.transfer(from, to, amount));
        // Then
        assertEquals("Accounts cannot be null", exception.getMessage());
    }

    @Test
    void test_amount_negative() {
        // Given
        Account from = new Account("123", 2);
        Account to = new Account("1234", 3);
        double amount = -1;
        // When
        TransferService transferService = new TransferService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transferService.transfer(from, to, amount));
        // Then
        assertEquals("Transfer amount must be positive", exception.getMessage());
    }

    @Test
    void test_amount_zero() {
        // Given
        Account from = new Account("123", 2);
        Account to = new Account("1234", 3);
        double amount = 0;
        // When
        TransferService transferService = new TransferService();
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> transferService.transfer(from, to, amount));
        // Then
        assertEquals("Transfer amount must be positive", exception.getMessage());
    }

    @Test
    void test_from_withdraw_balance_less_amount() {
        // Given
        Account from = new Account("123", 0);
        Account to = new Account("1234", 3);
        double amount = 1;
        // When
        TransferService transferService = new TransferService();
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> transferService.transfer(from, to, amount));
        // Then
        assertEquals("Insufficient balance", exception.getMessage());
    }

    @Test
    void test_from_withdraw_balance_equal_amount() {
        // Given
        Account from = new Account("123", 1);
        Account to = new Account("1234", 3);
        double amount = 1;
        // When
        TransferService transferService = new TransferService();
        transferService.transfer(from, to, amount);
        // Then
        assertEquals(0, from.getBalance());
    }

    @Test
    void test_from_withdraw_balance_more_amount() {
        // Given
        Account from = new Account("123", 2);
        Account to = new Account("1234", 3);
        double amount = 1;
        // When
        TransferService transferService = new TransferService();
        transferService.transfer(from, to, amount);
        // Then
        assertEquals(1, from.getBalance());
        assertEquals(4, to.getBalance());
    }

    @Test
    void test_to_deposit() {
        // Given
        Account from = new Account("123", 2);
        Account to = new Account("1234", 3);
        double amount = 1;
        // When
        TransferService transferService = new TransferService();
        transferService.transfer(from, to, amount);
        // Then
        assertEquals(1, from.getBalance());
        assertEquals(4, to.getBalance());
    }

}