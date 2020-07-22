package org.kuroneko.demospringsecurity.form;

import org.kuroneko.demospringsecurity.account.AccountContext;
import org.kuroneko.demospringsecurity.account.AccountRepository;
import org.kuroneko.demospringsecurity.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class SampleController {

    @Autowired
    private SampleService sampleService;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public String index(Model model, Principal principal){
        if (principal == null) {
            model.addAttribute("message", "Hello Spring Security");
        }else {
            model.addAttribute("message", "Hello Spring Security, " + principal.getName());
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
        return "user";
    }
}
