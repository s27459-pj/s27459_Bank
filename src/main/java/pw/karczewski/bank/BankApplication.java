package pw.karczewski.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import pw.karczewski.bank.model.Account;
import pw.karczewski.bank.repository.AccountRepository;

@SpringBootApplication
public class BankApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(BankApplication.class, args);

		var accountRepository = context.getBean(AccountRepository.class);
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
		accountRepository.create(new Account(
				2,
				"73071152547",
				20000,
				Account.Currency.EUR,
				"Maria",
				"Nowak"));
		accountRepository.create(new Account(
				3,
				"81073143371",
				30020,
				Account.Currency.USD,
				"Janusz",
				"Kowalski"));
	}

}
