package com.journal.florist.app.common.utils.jasper;

import com.journal.florist.ApplicationProperties;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class StorageService {
    private final ApplicationProperties properties;

    public StorageService(ApplicationProperties properties) throws IOException {
        this.properties = properties;
    }

    public InputStream getJrxmlFile(String fileReportName) throws IOException {

        if (fileReportName.endsWith(".jrxml")) {
            return new ClassPathResource("report/"
                    .concat(fileReportName)).getInputStream();
//            return new FilePagetClass().getResourceAsStream(
//                    properties
//                            .getRootReportLocation()
//                            .toString()
//                            .concat(fileReportName));
        }

        String concat = fileReportName.concat(".jrxml");

        return new ClassPathResource("report/"
                .concat(concat)).getInputStream();
    }

    public Resource getJasperFile(String fileReportName) throws IOException {
        //        Path jasper = reportDirectory.resolve(fileReportName + ".jasper");
        return new ClassPathResource(
                properties
                        .getRootReportLocation()
                        .toString()
                        .concat(fileReportName + ".jasper"));
    }

    public Path getCompileReportPath() {
        try {
            return Paths.get(properties.getCompileReportLocation().getURI().getPath());
        } catch (IOException e) {
            throw new RuntimeException("Directory is not found");
        }
    }
}
