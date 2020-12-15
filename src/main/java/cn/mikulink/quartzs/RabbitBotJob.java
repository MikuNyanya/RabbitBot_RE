package cn.mikulink.quartzs;

import cn.mikulink.quartzs.jobs.JobMain;
import cn.mikulink.quartzs.jobs.JobTimeRabbit;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * create by MikuLink on 2019/12/3 12:27
 * for the Reisen
 * <p>
 * 定时任务
 */
public class RabbitBotJob {
    private static final Logger logger = LoggerFactory.getLogger(RabbitBotJob.class);

    //每小时执行一次
    private static final String CRON_1H = "0 0 0/1 * * ?";
    //每分钟执行一次
    private static final String CRON_1MIN = "0 0/1 * * * ?";

    /**
     * 启动定时任务
     */
    public void jobStart() {
        try {
            //一小时跑一次的
            job_1h();
            //一分钟跑一次的
            job_1min();
        } catch (SchedulerException sEx) {
            logger.error("定时器任务启动异常:" + sEx.toString(), sEx);
        }
    }

    private void job_1h() throws SchedulerException {
        //创建一个JobDetail
        JobDetail jobDetail = JobBuilder.newJob(JobTimeRabbit.class)
                .withDescription("整点报时 JobDetail")
                .withIdentity("Time Rabbit JobDetail", "Rabbit Job")
                .build();

        //创建一个trigger触发规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("整点报时 Trigger")
                .startAt(new Date())
                .withIdentity("Time Rabbit Trigger", "Rabbit Job")
//                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10, 5))   //写死间隔
                .withSchedule(CronScheduleBuilder.cronSchedule(CRON_1H)) //cron表达式
                .build();


        //创建一个调度器，也就是一个Quartz容器
        //声明一个scheduler的工厂schedulerFactory
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //通过schedulerFactory来实例化一个Scheduler
        Scheduler scheduler = schedulerFactory.getScheduler();
        //将Job和Trigger注册到scheduler容器中
        scheduler.scheduleJob(jobDetail, trigger);

        //启动定时器
        scheduler.start();
    }

    private void job_1min() throws SchedulerException {
        //创建一个JobDetail
        JobDetail jobDetail = JobBuilder.newJob(JobMain.class)
                .withDescription("主要定时任务 JobDetail")
                .withIdentity("Main Job JobDetail", "Rabbit Job")
                .build();

        //创建一个trigger触发规则
        Trigger trigger = TriggerBuilder.newTrigger()
                .withDescription("主要定时任务 Trigger")
                .startAt(new Date())
                .withIdentity("Main Job Trigger", "Rabbit Job")
//                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10, 5))   //写死间隔
                .withSchedule(CronScheduleBuilder.cronSchedule(CRON_1MIN)) //cron表达式
                .build();


        //创建一个调度器，也就是一个Quartz容器
        //声明一个scheduler的工厂schedulerFactory
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //通过schedulerFactory来实例化一个Scheduler
        Scheduler scheduler = schedulerFactory.getScheduler();
        //将Job和Trigger注册到scheduler容器中
        scheduler.scheduleJob(jobDetail, trigger);

        //启动定时器
        scheduler.start();
    }
}
