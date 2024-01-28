package pw.karczewski.bank.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(int id) {
        super("Account with id " + id + " not found");
    }
}
