package org.kuroneko.demospringsecurity;

import org.kuroneko.demospringsecurity.account.AccountService;
import org.kuroneko.demospringsecurity.book.BookRepository;
import org.kuroneko.demospringsecurity.domain.Account;
import org.kuroneko.demospringsecurity.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableAsync
@SpringBootApplication
public class Application {

    @Autowired
    BookRepository bookRepository;
    @Autowired
    AccountService accountService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public ApplicationRunner applicationRunner() {
        return new ApplicationRunner() {
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Account springUser = createAccount("springUser");
                Account hibernateUser = createAccount("hibernateUser");
                Book springBook = createBook("springBook", springUser);
                Book hibernateBook = createBook("hibernateBook", hibernateUser);
            }
        };
    }

    private Book createBook(String title, Account account){
        Book book = new Book();
        book.setTitle(title);
        book.setAuther(account);
        return bookRepository.save(book);
    }

    private Account createAccount(String username) {
        Account account = new Account();
        account.setUsername(username);
        account.setPassword("1234");
        account.setRole("USER");

        return accountService.createNew(account);
    }

}
