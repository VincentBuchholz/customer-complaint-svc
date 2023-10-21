package dk.vv.customer.complaint.svc.processors;

import dk.vv.customer.complaint.svc.Configuration;
import dk.vv.customer.complaint.svc.Constants;
import dk.vv.customer.complaint.svc.pojos.Complaint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.jira.JiraConstants;
import org.jboss.logging.Logger;

import java.util.Date;

public class SetJiraConstantsProcessor implements Processor {


    private final Configuration configuration;

    public SetJiraConstantsProcessor(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public void process(Exchange exchange) throws Exception {
        Complaint complaint = exchange.getIn().getBody(Complaint.class);
        exchange.getIn().setHeader(JiraConstants.ISSUE_PROJECT_KEY, configuration.jira().project());
        exchange.getIn().setHeader(JiraConstants.ISSUE_SUMMARY, "Customer inquiry - " + (new Date()));
        exchange.getIn() .setHeader(JiraConstants.JIRA_URL,configuration.jira().url());
        exchange.getIn().setHeader(JiraConstants.ISSUE_TYPE_ID, Constants.CATEGORY_JIRA_TYPE_MAP.get(complaint.getCategory()));
        exchange.getIn().setBody(createJiraDescription(complaint));
    }

    private String createJiraDescription(Complaint complaint) {
        StringBuilder descriptionBuilder = new StringBuilder();
        descriptionBuilder.append("Email: ").append(complaint.getEmail()).append("\n");
        descriptionBuilder.append("Description: ").append(complaint.getDescription()).append("\n");

        addFieldIfNotEmpty(descriptionBuilder, "Name", complaint.getName());
        addFieldIfNotEmpty(descriptionBuilder, "Lastname", complaint.getLastname());
        addFieldIfNotEmpty(descriptionBuilder, "Order Number", complaint.getOrderNumber());
        return descriptionBuilder.toString();
    }
    private void addFieldIfNotEmpty(StringBuilder builder, String fieldName, String value) {
        if (value != null && !value.isEmpty()) {
            builder.append(fieldName).append(": ").append(value).append("\n");
        }
    }
}
