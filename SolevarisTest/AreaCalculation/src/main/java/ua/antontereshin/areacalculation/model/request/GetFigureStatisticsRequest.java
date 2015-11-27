package ua.antontereshin.areacalculation.model.request;

/**
 * Created by Anton on 04.11.2015.
 */
public class GetFigureStatisticsRequest  {

    private Long figureId;

    public Long getFigureId() {
        return figureId;
    }

    public void setFigureId(Long figureId) {
        this.figureId = figureId;
    }

    @Override
    public String toString() {
        return "GetFigureStatisticsRequest{" +
                "figureId='" + figureId + '\'' +
                '}';
    }
}


