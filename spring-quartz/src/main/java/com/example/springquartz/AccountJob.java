package com.example.springquartz;

import com.example.Controller.AccountController;
import com.example.domain.dto.AccountCreateDTO;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class AccountJob implements Job {

    Logger logger = LoggerFactory.getLogger(AccountJob.class);
    @Autowired
    private AccountController accountController;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String message = jobDataMap.get("message").toString();
        logger.info("Scheduler is printing message:: {}", message);
    }

}
