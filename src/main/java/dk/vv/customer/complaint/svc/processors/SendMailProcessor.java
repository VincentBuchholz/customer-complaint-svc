package dk.vv.customer.complaint.svc.processors;

import dk.vv.customer.complaint.svc.Configuration;
import dk.vv.customer.complaint.svc.pojos.Complaint;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.logging.Logger;

public class SendMailProcessor implements Processor
{

    private final Mailer mailer;

    public SendMailProcessor(Mailer mailer) {
        this.mailer = mailer;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        Complaint complaint = exchange.getIn().getBody(Complaint.class);

        StringBuilder mailBody = new StringBuilder();
        mailBody.append("Dear Mr/Mrs ");
        mailBody.append(complaint.getLastname());
        mailBody.append("\n\n");
        mailBody.append("Thank you for reaching out to us, we have received your request \n");
        mailBody.append("and a member of customer service team will get back to you as  \n");
        mailBody.append("soon as possible.\n\n");
        mailBody.append("Kind regards\n");
        mailBody.append("VV solutions");

        Mail mail = new Mail();

        mail.addTo(complaint.getEmail());
        mail.setText(mailBody.toString());
        mail.setSubject("Thanks for your message");

        mailer.send(mail);

    }
}
