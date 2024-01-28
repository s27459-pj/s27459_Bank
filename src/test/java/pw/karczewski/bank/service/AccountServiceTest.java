package pw.karczewski.bank.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pw.karczewski.bank.exception.AccountNotFoundException;
import pw.karczewski.bank.exception.ValidationException;
import pw.karczewski.bank.model.Account;
import pw.karczewski.bank.repository.AccountRepository;

public class AccountServiceTest {
    private AccountRepository accountRepository;
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        accountRepository = new AccountRepository();
        accountService = new AccountService(accountRepository);
    }

    @Test
    public void shouldGetAllAccounts() {
        accountRepository.create(new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski"));
        accountRepository.create(new Account(
                1,
                "67110861675",
                20000,
                Account.Currency.PLN,
                "Lukasz",
                "Nowak"));

        var accounts = accountService.getAll();
        assertEquals(2, accounts.size());
    }

    @Test
    public void shouldGetAccountsAboveBalance() {
        accountRepository.create(new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski"));
        accountRepository.create(new Account(
                1,
                "67110861675",
                20000,
                Account.Currency.PLN,
                "Lukasz",
                "Nowak"));

        var accounts = accountService.getAboveBalance(10000);
        assertEquals(1, accounts.size());
        assertEquals(1, accounts.get(0).getId());
    }

    @Test
    public void shouldGetAccountById() {
        accountRepository.create(new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski"));

        assertDoesNotThrow(() -> accountService.getById(0));
    }

    @Test
    public void shouldThrowAccountNotFoundException() {
        assertThrows(AccountNotFoundException.class, () -> accountService.getById(0));
    }

    @Test
    public void shouldCreateAccount() {
        var account = new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski");

        assertDoesNotThrow(() -> accountService.createAccount(account));
        assertEquals(1, accountRepository.findAll().size());
    }

    @Test
    public void shouldValidatePeselLength() {
        var account = new Account(
                0,
                "5301629",
                1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski");

        assertThrows(ValidationException.class, () -> accountService.createAccount(account));
    }

    @Test
    public void shouldValidatePositiveBalance() {
        var account = new Account(
                0,
                "53040136293",
                -1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski");

        assertThrows(ValidationException.class, () -> accountService.createAccount(account));
    }

    @Test
    public void shouldValidateCurrency() {
        var account = new Account(
                0,
                "53040136293",
                1000,
                null,
                "Jan",
                "Kowalski");

        assertThrows(ValidationException.class, () -> accountService.createAccount(account));
    }

    @Test
    public void shouldValidateFirstName() {
        var account = new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                null,
                "Kowalski");

        assertThrows(ValidationException.class, () -> accountService.createAccount(account));
    }

    @Test
    public void shouldValidateLastName() {
        var account = new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                "Jan",
                null);

        assertThrows(ValidationException.class, () -> accountService.createAccount(account));
    }
}
