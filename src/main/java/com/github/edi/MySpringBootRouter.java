package com.github.edi;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MySpringBootRouter extends EndpointRouteBuilder {

    @Value("input.dir")
    private String inputDir;

    @Override
    public void configure() throws Exception {
        from(file(inputDir).includeExt("edi")).routeId("edi-route")
                .log(LoggingLevel.INFO, "EDI-input: ${body}")
                .to("smooks://edi-to-xml-smooks-config.xml")
                .log(LoggingLevel.INFO, "EDI-xml: ${body}")
                .to("smooks://xml-to-edi-smooks-config.xml")
                .log(LoggingLevel.INFO, "EDI-output: ${body}");

        from(file(inputDir).includeExt("edifact")).routeId("edifact-route")
                .log(LoggingLevel.INFO, "EDIFACT-input: ${body}")
                .to("smooks://edifact-to-xml-smooks-config.xml")
                .log(LoggingLevel.INFO, "EDIFACT-xml: ${body}")
                .to("smooks://xml-to-edifact-smooks-config.xml")
                .log(LoggingLevel.INFO, "EDIFACT-output: ${body}");
    }

}
