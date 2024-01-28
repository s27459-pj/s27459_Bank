package pw.karczewski.bank.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import pw.karczewski.bank.exception.AccountNotFoundException;
import pw.karczewski.bank.exception.ValidationException;
import pw.karczewski.bank.model.Account;
import pw.karczewski.bank.repository.AccountRepository;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public List<Account> getAboveBalance(double balance) {
        return accountRepository.findAboveBalance(balance);
    }

    public Account getById(int id) throws AccountNotFoundException {
        return accountRepository.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }

    public Account createAccount(Account account) throws ValidationException {
        // https://en.wikipedia.org/wiki/PESEL
        if (account.getPesel() == null || account.getPesel().isEmpty())
            throw new ValidationException("pesel", "is required");
        if (account.getPesel().length() != 11)
            throw new ValidationException("pesel", "must be 11 characters long");

        if (account.getBalance() < 0)
            throw new ValidationException("balance", "must be greater than 0");

        if (account.getCurrency() == null)
            throw new ValidationException("currency", "is required");

        if (account.getFirstName() == null || account.getFirstName().isEmpty())
            throw new ValidationException("firstName", "is required");
        if (account.getLastName() == null || account.getLastName().isEmpty())
            throw new ValidationException("lastName", "is required");

        return accountRepository.create(account);
    }
}
