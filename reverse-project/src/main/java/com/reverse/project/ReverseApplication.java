package com.reverse.project;

import com.reverse.project.task.sources.ReverseSourcesTask;
import com.reverse.project.task.sources.context.ReverseSourceContext;
import com.reverse.project.utils.CliUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
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
        Options options = CliUtil.buildCommandlineOptions(new Options());
        CommandLine commandLine = CliUtil.parseCmdLine("java -jar reverse-project.jar", args, CliUtil.buildCommandlineOptions(options), new PosixParser());
        if (null == commandLine) {
            System.exit(-1);
        }
        final String m2Dir = commandLine.getOptionValue('m').trim();
        final String scanDir = commandLine.getOptionValue('s').trim();
        final String tmpDir = commandLine.hasOption('t') ? commandLine.getOptionValue('t').trim() : null;
        final String outputDir = commandLine.hasOption('o') ? commandLine.getOptionValue('o').trim() : null;
        ReverseSourceContext context = new ReverseSourceContext();
        context.setM2Dir(m2Dir);
        context.setScanDir(scanDir);
        context.setTmpDir(tmpDir);
        context.setOutputDir(outputDir);
        reverseSourcesTask.execute(context);
        log.info("Execute completely，taste:{}", (context.getEndTime().getTime() - context.getBeginTime().getTime()));
    }

}
