package pw.karczewski.bank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pw.karczewski.bank.model.Account;
import pw.karczewski.bank.service.AccountService;

@RestController
@RequestMapping("/accounts")
@AllArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<List<Account>> getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Account> getById(@PathVariable int id) {
        return ResponseEntity.ok(accountService.getById(id));
    }

    @GetMapping("/above_balance/{balance}")
    public ResponseEntity<List<Account>> getAboveBalance(@PathVariable double balance) {
        return ResponseEntity.ok(accountService.getAboveBalance(balance));
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        var newAccount = accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
    }
}
