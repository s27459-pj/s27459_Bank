package pw.karczewski.bank.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import pw.karczewski.bank.model.Account;

@Repository
public class AccountRepository {
    private List<Account> accounts = new ArrayList<>();

    public List<Account> findAll() {
        return accounts;
    }

    public Optional<Account> findById(int id) {
        return accounts.stream().filter(account -> account.getId() == id).findFirst();
    }

    public List<Account> findAboveBalance(double balance) {
        return accounts.stream().filter(account -> account.getBalance() > balance).toList();
    }

    public Account create(Account account) {
        account.setId(accounts.size());
        accounts.add(account);
        return account;
    }

    public void deleteAll() {
        accounts.clear();
    }
}
