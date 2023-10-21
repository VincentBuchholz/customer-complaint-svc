package dk.vv.customer.complaint.svc.Routes;
import com.atlassian.jira.rest.client.api.domain.Issue;
import dk.vv.customer.complaint.svc.Configuration;
import dk.vv.customer.complaint.svc.pojos.Complaint;
import dk.vv.customer.complaint.svc.processors.SendMailProcessor;
import dk.vv.customer.complaint.svc.processors.SetJiraConstantsProcessor;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.component.jira.JiraConstants;
import org.apache.camel.quarkus.core.FastCamelContext;
import org.jboss.logging.Logger;

import java.util.Date;


@ApplicationScoped
public class RouteBuilderImpl extends EndpointRouteBuilder {

    private final Logger logger;

    private final CamelContext camelContext;

    private final Configuration configuration;


    private final SendMailProcessor sendMailProcessor;

    private final SetJiraConstantsProcessor setJiraConstantsProcessor;



    @Inject
    public RouteBuilderImpl(CamelContext context, Logger logger, CamelContext camelContext, Configuration configuration, SendMailProcessor sendMailProcessor, SetJiraConstantsProcessor setJiraConstantsProcessor) {
        super(context);
        this.logger = logger;
        this.camelContext = camelContext;
        this.configuration = configuration;
        this.sendMailProcessor = sendMailProcessor;
        this.setJiraConstantsProcessor = setJiraConstantsProcessor;
    }

    @Override
    public void configure() throws Exception {

        ((FastCamelContext)this.camelContext).setName(configuration.contextName());

        // Receive complaints from API
        from(configuration.routes().initRoute().in()).routeId(configuration.routes().initRoute().in())
                .process(e->{
                    logger.info(String.format("Received [%s] starting processing",e.getIn().getBody()));

                })

                // Unmarshal the json to a java object
                .unmarshal().json(Complaint.class)

                .multicast().parallelProcessing()
                    .to(configuration.routes().sendMail().in())
                    .to(configuration.routes().handleComplaintsMain().in())
                .end()



                ;

        ///////////////////////
        // Send mails logic //
        /////////////////////

        from(configuration.routes().sendMail().in()).routeId(configuration.routes().sendMail().routeId())

                // Send mail
                .process(sendMailProcessor)

                .process(e->{
                    logger.info(String.format("mail: sent mail to [%s]",e.getIn().getBody(Complaint.class).getEmail()));
                })

        ;

          //////////////////////////////
         // Handle complaints logic //
        /////////////////////////////

        from(configuration.routes().handleComplaintsMain().in()).routeId(configuration.routes().handleComplaintsMain().routeId())

                // Format body to create a JIRA task
                .process(setJiraConstantsProcessor)


                // Create new JIRA task
                .to(configuration.routes().addIssue().out())

                .process(e->{
                    logger.info(String.format("JIRA: created a new Jira task with key: [%s]",e.getIn().getBody(Issue.class).getKey()));
                })



        ;


    }
}
