package com.uplingo.uplingo_authorization_server.ui.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

  @GetMapping("/login")
  public String login() {
    return "login";
  }
}
