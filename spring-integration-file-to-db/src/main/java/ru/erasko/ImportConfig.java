package ru.erasko;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.file.FileReadingMessageSource;


import java.io.File;

@Configuration
public class ImportConfig {
    private static final Logger logger = LoggerFactory.getLogger(ImportConfig.class);

    @Value("${source-dir-path}")
    private String sourceDirectoryPath;
    @Value("${dest-dir-path}")
    private String destDirectoryPath;

    @Bean
    public MessageSource<File> sourceDirectory() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(sourceDirectoryPath));
        messageSource.setAutoCreateDirectory(true);
        return messageSource;
    }

//    @Bean
//    public JpaExecutor jpaExecutor() {
//        JpaExecutor jpaExecutor = new JpaExecutor();
//        return jpaExecutor;
//    }

//    @Bean
//    public IntegrationFlow jpaFlow(JpaExecutor jpaExecutor) {
//        return IntegrationFlows.from(new JpaPollingChannelAdapter(jpaExecutor))
//                .split()
//                .transform()
//    }

}
