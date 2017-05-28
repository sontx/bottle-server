package com.blogspot.sontx.bottle.server.model.service.schedule;

import lombok.extern.log4j.Log4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

@Log4j
@Component
@PropertySource("classpath:bottle-config.properties")
public final class JobScheduler {
    @Value("schedule.chat.interval")
    private int chatInterval;
    @Value("schedule.message.interval")
    private int messageInterval;

    private Scheduler scheduler;

    @EventListener(ContextRefreshedEvent.class)
    public void contextRefreshedEvent() {
        if (scheduler != null)
            return;
        try {
            schedule();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    private void schedule() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        Trigger trigger1 = newTrigger()
                .withIdentity("trigger1", "delete")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(chatInterval)
                        .repeatForever())
                .build();
        JobDetail deleteChatJob = newJob(DeleteChatJob.class)
                .withIdentity("chat", "delete")
                .build();
        scheduler.scheduleJob(deleteChatJob, trigger1);

        Trigger trigger2 = newTrigger()
                .withIdentity("trigger2", "delete")
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(messageInterval)
                        .repeatForever())
                .build();
        JobDetail deleteMessageJob = newJob(DeleteMessageJob.class)
                .withIdentity("message", "delete")
                .build();
        scheduler.scheduleJob(deleteMessageJob, trigger2);

        log.debug("Scheduler is started");
    }
}
