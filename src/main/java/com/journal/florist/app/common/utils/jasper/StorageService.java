package com.journal.florist.app.common.utils.jasper;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
@Component
public class StorageService {

    private final String ROOT_DIR = "report/";

    public InputStream getJrxmlFile(String fileReportName) throws IOException {

        if (fileReportName.endsWith(".jrxml")) {
            return new ClassPathResource(ROOT_DIR.concat(fileReportName)).getInputStream();
        }

        String concat = fileReportName.concat(".jrxml");

        return new ClassPathResource(ROOT_DIR.concat(concat)).getInputStream();
    }

    public Resource getJasperFile(String fileReportName) {
        return new ClassPathResource(ROOT_DIR.concat(fileReportName + ".jasper"));
    }

}
