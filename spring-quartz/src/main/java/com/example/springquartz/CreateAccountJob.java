package com.example.springquartz;

import com.example.Controller.AccountController;
import com.example.domain.dto.AccountCreateDTO;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;
import java.util.Random;

public class CreateAccountJob extends QuartzJobBean {
    @Autowired
    private AccountController accountController;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Created new account in " + LocalDateTime.now());
        AccountCreateDTO accountCreateDTO = generateRandomAccount();
        this.accountController.createAccount(accountCreateDTO);
    }

    private AccountCreateDTO generateRandomAccount() {
        Random rand = new Random();
        String username = "user" + rand.nextInt(1000);
        String password = "password" + rand.nextInt(1000);
        String email = "user" + rand.nextInt(1000) + "@gmail.com";
        return new AccountCreateDTO(username, password, email);
    }
}