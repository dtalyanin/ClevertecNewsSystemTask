package generators.factories;

public class BearerTokenFactory {

    public static String getAdminToken() {
        return "Bearer admin";
    }

    public static String getJournalistToken() {
        return "Bearer journalist";
    }

    public static String getSubscriberToken() {
        return "Bearer subscriber";
    }

    public static String getEmptyToken() {
        return "";
    }
}
