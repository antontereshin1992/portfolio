package ua.antontereshin.areacalculation.utils;

/**
 * Created by Anton on 04.11.2015.
 */
public final class Utils {

    private Utils() {
    }

    public static String getGeneralCauseMessage(Throwable th){
        if (th.getCause() == null) {
            return th.getMessage();
        }
        return  getGeneralCauseMessage(th.getCause());
    }

}
