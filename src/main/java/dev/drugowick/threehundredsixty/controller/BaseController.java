package dev.drugowick.threehundredsixty.controller;

import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

/**
 * This is a base controller responsible for basic info that EVERY page must have.
 */
public class BaseController {

    @ModelAttribute("username")
    public String username(Principal principal) {
        return principal.getName();
    }
}
