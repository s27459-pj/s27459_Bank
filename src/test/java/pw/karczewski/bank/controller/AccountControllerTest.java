package pw.karczewski.bank.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pw.karczewski.bank.model.Account;
import pw.karczewski.bank.repository.AccountRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AccountControllerTest {
    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void cleanUp() {
        accountRepository.deleteAll();
    }

    @Test
    void shouldGetAllAccounts() {
        var account1 = new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski");
        accountRepository.create(account1);
        var account2 = new Account(
                1,
                "67110861675",
                20000,
                Account.Currency.PLN,
                "Lukasz",
                "Nowak");
        accountRepository.create(account2);

        webTestClient.get()
                .uri("/accounts")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Account.class)
                .hasSize(2)
                .contains(account1, account2);
    }

    @Test
    void shouldGetAccountsAboveBalance() {
        var account1 = new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski");
        accountRepository.create(account1);
        var account2 = new Account(
                1,
                "67110861675",
                25000,
                Account.Currency.PLN,
                "Lukasz",
                "Nowak");
        accountRepository.create(account2);
        var account3 = new Account(
                2,
                "73071152547",
                20000,
                Account.Currency.EUR,
                "Maria",
                "Nowak");
        accountRepository.create(account3);
        var account4 = new Account(
                3,
                "81073143371",
                30020,
                Account.Currency.USD,
                "Janusz",
                "Kowalski");
        accountRepository.create(account4);

        webTestClient.get()
                .uri("/accounts/above_balance/20000")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Account.class)
                .hasSize(2)
                .contains(account2, account4);
    }

    @Test
    void shouldGetAccountById() {
        var account = new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski");
        accountRepository.create(account);

        webTestClient.get()
                .uri("/accounts/0")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Account.class)
                .isEqualTo(account);
    }

    @Test
    void shouldRespondWithNotFound() {
        webTestClient.get()
                .uri("/accounts/0")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldCreateAccount() throws JsonProcessingException {
        var account = new Account(
                0,
                "53040136293",
                1000,
                Account.Currency.PLN,
                "Jan",
                "Kowalski");
        var accountJSON = objectMapper.writeValueAsString(account);

        var createdAccount = webTestClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(accountJSON)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Account.class)
                .returnResult()
                .getResponseBody();
        assertNotNull(createdAccount);
        assertEquals(account.getPesel(), createdAccount.getPesel());
        assertEquals(account.getBalance(), createdAccount.getBalance());
        assertEquals(account.getCurrency(), createdAccount.getCurrency());
        assertEquals(account.getFirstName(), createdAccount.getFirstName());
        assertEquals(account.getLastName(), createdAccount.getLastName());
    }

    @Test
    void shouldReturnBadRequestForValidationError() {
        var account = new Account(
                0,
                "53040136293",
                1000,
                null,
                "Jan",
                "Kowalski");

        webTestClient.post()
                .uri("/accounts")
                .bodyValue(account)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
