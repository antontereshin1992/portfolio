package ua.antontereshin.areacalculation.model.figure;

import java.util.List;

/**
 * Created by Anton on 05.11.2015.
 */

public interface Figure {

    Long getId();

    Integer getTimes();

    Integer incAndGetTimes();

    /**
     *
     * @return Name of instance
     */
    String getName();

    /**
     *
     * @return Javasript function to calculate the formula area (String). Have to sent to View.
     * For example: function(args){ return args[0] * args[1]; }
     */
    String getAreaScript();

    /**
     *
     * @return List parameters for javasript. Have to sent to View
     */
    List<String> getParameters();

    /**
     *
     * @return Picture for display the type of figure. Have to sent to View
     */
    String getImage();
}
