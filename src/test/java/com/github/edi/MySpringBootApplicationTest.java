package com.github.edi;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWith;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
public class MySpringBootApplicationTest {

	@Autowired
	private CamelContext camelContext;

	@Autowired
	private ProducerTemplate producerTemplate;

	@Test
	public void ediToXmlAndBackWithSmooks() throws Exception {

		MockEndpoint ediToXmlMock = camelContext.getEndpoint("mock:edi-to-xml", MockEndpoint.class);
		ediToXmlMock.reset();

		AdviceWith.adviceWith(camelContext, "edi-route",
				// intercepting an exchange on route
				r -> {
					// replacing consumer with direct component
					r.replaceFromWith("direct:edi-start");
					// mock all endpoints
					r.weaveById("edi-to-xml").after().to(ediToXmlMock);
					r.weaveById("xml-to-edi").after().to(ediToXmlMock);
				}
		);

		// setting expectations
		ediToXmlMock.expectedMessageCount(2);

		// invoking consumer
		producerTemplate.sendBody("direct:edi-start",
				IOUtils.resourceToString("input-message.edi",
						null,
						this.getClass().getClassLoader()));

		// asserting mock is satisfied
		ediToXmlMock.assertIsSatisfied();

		//TODO: Add asserts that checks results of both translations
		//ediToXmlMock.getExchanges()
	}

	@Test
	public void edifactToXmlAndBackWithSmooks() throws Exception {

		MockEndpoint ediToXmlMock = camelContext.getEndpoint("mock:edi-to-xml", MockEndpoint.class);
		ediToXmlMock.reset();

		AdviceWith.adviceWith(camelContext, "edifact-route",
				// intercepting an exchange on route
				r -> {
					// replacing consumer with direct component
					r.replaceFromWith("direct:edifact-start");
					// mock all endpoints
					r.weaveById("edifact-to-xml").after().to(ediToXmlMock);
					r.weaveById("xml-to-edifact").after().to(ediToXmlMock);
				}
		);

		// setting expectations
		ediToXmlMock.expectedMessageCount(2);

		// invoking consumer
		producerTemplate.sendBody("direct:edifact-start",
				IOUtils.resourceToString("input-message.edifact",
						null,
						this.getClass().getClassLoader()));

		// asserting mock is satisfied
		ediToXmlMock.assertIsSatisfied();

		//TODO: Add asserts that checks results of both translations
		//ediToXmlMock.getExchanges()
	}

}
