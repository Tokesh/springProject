package com.example.springquartz;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@RestController
public class AccountSchedulerController {
    private final Logger logger = LoggerFactory.getLogger(AccountSchedulerController.class);

    @Autowired
    private Scheduler scheduler;
//
//    @GetMapping("/schedule-job")
//    public String scheduleJob(@RequestParam("msg") String message) throws SchedulerException {
//        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.put("message", message);
//        JobKey jobKey = new JobKey("MessagePrintingJob");
//        JobDetail jobDetail = JobBuilder.newJob()
//                .withIdentity(UUID.randomUUID().toString())
//                .setJobData(jobDataMap)
//                .withDescription("Simple message printing Job.")
//                .ofType(AccountJob.class)
//                .storeDurably()
//                .build();
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .forJob(jobDetail)
////                .startAt(Date.from(Instant.now().plus(30, ChronoUnit.SECONDS)))
//                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(10))
//                .withDescription("Simple message printing Job trigger.")
//                .build();
//        logger.info("Scheduling printing message Job with message :{}", message);
//        scheduler.scheduleJob(jobDetail, trigger);
//        return "Job scheduled!!";
//    }
    @GetMapping("/schedule-job")
    public String scheduleJob() throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob()
                .withIdentity("CreateAccountJob")
                .withDescription("Job to create new accounts every 10 seconds.")
                .ofType(CreateAccountJob.class)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("CreateAccountTrigger")
                .withDescription("Trigger to create new accounts every 10 seconds.")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(5))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        return "Job scheduled!";
    }

    @GetMapping("/schedule-job-gatling")
    public String scheduleJobGatling() throws SchedulerException {
        UUID uuid = UUID.randomUUID();
        JobDetail jobDetail = JobBuilder.newJob()
                .withIdentity("CreateAccountJob " + uuid)
                .withDescription("Job to create new accounts every 10 seconds.")
                .ofType(CreateAccountJob.class)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("CreateAccountTrigger " + uuid)
                .withDescription("Trigger to create new accounts every 10 seconds.")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
        return "Job scheduled!";
    }

}
