package com.journal.florist;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Primary
@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "com.journal")
public class ApplicationProperties {

    @NotNull
    private Resource rootReportLocation;

    @NotNull
    private Resource compileReportLocation;
}
