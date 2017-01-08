package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.authentication.Account;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authentication/")
public class AuthController {

    @RequestMapping(value = "authenticate", method = RequestMethod.POST)
    public void authenticate() {
        // TODO: this is a temporary measure until I implement proper forms-based authentication
    }
}