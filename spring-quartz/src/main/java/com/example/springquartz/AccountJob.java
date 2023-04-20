package com.example.springquartz;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccountJob implements Job {

    Logger logger = LoggerFactory.getLogger(AccountJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String message = jobDataMap.get("message").toString();
        logger.info("Scheduler is printing message:: {}", message);
    }
}
