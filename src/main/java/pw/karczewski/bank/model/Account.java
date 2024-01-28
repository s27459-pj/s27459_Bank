package pw.karczewski.bank.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {
    private int id;
    private String pesel;
    private double balance;
    private Currency currency;
    private String firstName;
    private String lastName;

    public enum Currency {
        PLN, EUR, USD
    }
}
