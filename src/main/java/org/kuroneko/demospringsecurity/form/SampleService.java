package org.kuroneko.demospringsecurity.form;

import org.kuroneko.demospringsecurity.common.SecurityLogger;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.security.RolesAllowed;

@Service
public class SampleService {

    @PreAuthorize("hasRole('USER')")
    public void dashboard() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        System.out.println("=======================");
        System.out.println(principal.getUsername());
    }

    @Async
    public void asyncService() throws InterruptedException {
        System.out.println("Async Service Message");
        SecurityLogger.log("Async in Called");
    }
}
