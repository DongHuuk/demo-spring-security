package org.kuroneko.demospringsecurity.account;

import org.junit.jupiter.api.Tag;
import org.springframework.security.test.context.support.WithMockUser;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@WithMockUser(username = "kuroneko", roles = "USER")
public @interface WithUser {
}
