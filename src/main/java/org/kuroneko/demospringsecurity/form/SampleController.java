package org.kuroneko.demospringsecurity.form;

import org.kuroneko.demospringsecurity.account.AccountContext;
import org.kuroneko.demospringsecurity.account.AccountRepository;
import org.kuroneko.demospringsecurity.account.CurrentAccount;
import org.kuroneko.demospringsecurity.account.UserAccount;
import org.kuroneko.demospringsecurity.book.BookRepository;
import org.kuroneko.demospringsecurity.common.SecurityLogger;
import org.kuroneko.demospringsecurity.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.concurrent.Callable;

@Controller
public class SampleController {

    @Autowired
    private SampleService sampleService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/")
    public String index(Model model, @CurrentAccount Account account){
        if (account == null) {
            model.addAttribute("message", "Hello Spring Security");
        }else {
            model.addAttribute("message", "Hello Spring Security, " + account.getUsername());
        }
        return "index";
    }
    //누구나 접근 가능
    @GetMapping("/info")
    public String info(Model model){

        model.addAttribute("message", "Hello Infomation");
        return "info";
    }
    //Principal만 접근 가능
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal){
        sampleService.dashboard();
        model.addAttribute("message", "Hello " + principal.getName() + " This is Dashboard Site");
        return "dashboard";
    }
    //admin만 접근 가능
    @GetMapping("/admin")
    public String admin(Model model, Principal principal){

        model.addAttribute("message", "Hello " + principal.getName() + " This is Admin Site");
        return "index";
    }
    //user만 접근 가능
    @GetMapping("/user")
    public String user(Model model, Principal principal){
        model.addAttribute("message", "Hello " + principal.getName() + " This is User Site");
        model.addAttribute("books", bookRepository.findCurrentUserBooks());

        return "user";
    }

    @GetMapping("/async-handler")
    @ResponseBody
    public Callable<String> asyncHandler(){
        SecurityLogger.log("====MVC===");

        return () -> {
            SecurityLogger.log("====Callable===");
            return "Async Handler";
        };
    }

    @GetMapping("/async-service")
    @ResponseBody
    public String asyncService() throws InterruptedException {
        SecurityLogger.log("====MVC by Async Service before ===");
        sampleService.asyncService();
        SecurityLogger.log("====MVC by Aync Service after===");
        return "Ayanc Service";
    }

    @GetMapping("/access-denied")
    public String access_denied(Model model, Principal principal){
        model.addAttribute("name", principal.getName());
        return "access-denied";
    }
}
