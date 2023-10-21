package dk.vv.customer.complaint.svc;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClientFactory;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.rabbitmq.client.ConnectionFactory;
import dk.vv.customer.complaint.svc.processors.SendMailProcessor;
import dk.vv.customer.complaint.svc.processors.SetJiraConstantsProcessor;
import io.quarkus.arc.Unremovable;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.component.dsl.JiraComponentBuilderFactory;
import org.apache.camel.component.jira.JiraConfiguration;
import org.apache.camel.component.jira.JiraEndpoint;
import org.apache.camel.component.jira.JiraEndpointUriFactory;
import org.jboss.logging.Logger;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@ApplicationScoped
public class Producers {

    @Inject
    Configuration configuration;

    @Inject
    CamelContext camelContext;

    @Inject
    Mailer mailer;


//    @Unremovable
//    @Produces
//    JiraConfiguration jiraConfiguration(){
//        return new JiraConfiguration(){{
//           setJiraUrl("https://vvsolutions.atlassian.net");
//           setUsername("solutions.by.vv@gmail.com");
//           setPassword("ATATT3xFfGF0Ftjlw6IhvoqnimOJIoWS9_Nok9TcTxry_QvEfRCVkvg2x2UYMtyYQa7j4Bzrk37TEf5UCq0k8MSikDDZpvRL5mW8dEWVKXcLPYnHPGwrf6zPC_OrJWN87Xed7pXTJoiLcVnffsfWcGJDHRgJythwuBELlnS4DiUWRXHBHSznMJs=B5F4E0C1");
//        }};
//
//    }
////
////
//    @Produces
//    @Unremovable
//    JiraRestClient jiraRestClient(JiraConfiguration jiraConfiguration) {
//        AsynchronousJiraRestClientFactory asynchronousJiraRestClientFactory = new AsynchronousJiraRestClientFactory();
//        return asynchronousJiraRestClientFactory.createWithBasicHttpAuthentication(URI.create("https://vvsolutions.atlassian.net"), "solutions.by.vv@gmail.com", "ATATT3xFfGF0Ftjlw6IhvoqnimOJIoWS9_Nok9TcTxry_QvEfRCVkvg2x2UYMtyYQa7j4Bzrk37TEf5UCq0k8MSikDDZpvRL5mW8dEWVKXcLPYnHPGwrf6zPC_OrJWN87Xed7pXTJoiLcVnffsfWcGJDHRgJythwuBELlnS4DiUWRXHBHSznMJs=B5F4E0C1");
//    }
    @Produces
    SetJiraConstantsProcessor getSetJiraConstantsProcessor(Configuration configuration){
        return new SetJiraConstantsProcessor(configuration);
    }


    @Produces
    SendMailProcessor getSendMailProcessor(Mailer mailer){
        return new SendMailProcessor(mailer);
    }





}
