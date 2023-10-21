package dk.vv.customer.complaint.svc;


import io.smallrye.config.ConfigMapping;

@ConfigMapping(prefix = "customer.complaint.svc", namingStrategy = ConfigMapping.NamingStrategy.VERBATIM)
public interface Configuration {
    String contextName();
    Jira jira();


    interface Jira {
        String url();
        String project();
    }

    MainConfig routes();
    public interface MainConfig {

        interface InRoute {
            String in();

            String routeId();
        }

        interface OutRoute {
            String out();
        }

        InRoute initRoute();

        InRoute handleComplaintsMain();

        OutRoute addIssue();

        InRoute sendMail();




    }
}



