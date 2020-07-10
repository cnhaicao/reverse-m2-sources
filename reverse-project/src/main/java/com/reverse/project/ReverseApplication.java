package com.reverse.project;

import com.reverse.project.task.sources.ReverseSourcesTask;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * reverse spring boot application
 *
 * @author guoguoqiang
 * @since 2020年07月02日
 */
@Slf4j
@SpringBootApplication
public class ReverseApplication {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(ReverseApplication.class, args);
        ReverseSourcesTask<ReverseSourceContext> reverseSourcesTask = (ReverseSourcesTask<ReverseSourceContext>) applicationContext.getBean(ReverseSourcesTask.class);
        ReverseSourceContext context = new ReverseSourceContext();
        context.setM2Dir("./docs/m2-source-example");
        context.setScanDir("./docs/m2-source-example");
        reverseSourcesTask.execute(context);
        log.info("执行成功，耗时:{}", (context.getEndTime().getTime() - context.getBeginTime().getTime()));
    }

}
