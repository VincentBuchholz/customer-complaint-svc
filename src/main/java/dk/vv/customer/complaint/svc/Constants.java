package dk.vv.customer.complaint.svc;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String JIRA_IN_PROGRESS_STATUS = "In Progress";

    public static final String JIRA_DONE_STATUS = "Done";

    public static final int JIRA_TYPE_DELIVERY = 10008;
    public static final int JIRA_TYPE_PAYMENT = 10010;
    public static final int JIRA_TYPE_CANCELLATION = 10009;
    public static final int JIRA_TYPE_OTHER = 10011;
    public static final int JIRA_TYPE_RETURN = 10007;

    public static final int COMPLAINT_CATEGORY_DELIVERY = 1;
    public static final int COMPLAINT_CATEGORY_PAYMENT = 2;
    public static final int COMPLAINT_CATEGORY_CANCELLATION = 3;
    public static final int COMPLAINT_CATEGORY_OTHER = 4;
    public static final int COMPLAINT_CATEGORY_RETURN = 5;

    public static final HashMap<Integer, Integer> CATEGORY_JIRA_TYPE_MAP = new HashMap<>(){{
        put(COMPLAINT_CATEGORY_DELIVERY, JIRA_TYPE_DELIVERY);
        put(COMPLAINT_CATEGORY_PAYMENT, JIRA_TYPE_PAYMENT);
        put(COMPLAINT_CATEGORY_CANCELLATION, JIRA_TYPE_CANCELLATION);
        put(COMPLAINT_CATEGORY_OTHER, JIRA_TYPE_OTHER);
        put(COMPLAINT_CATEGORY_RETURN, JIRA_TYPE_RETURN);
    }};

}
