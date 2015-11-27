package ua.antontereshin.areacalculation.model.response;

/**
 * Created by Anton on 04.11.2015.
 */
public class GetFigureStatisticsResponse {

    private Integer amount;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "GetFigureStatisticsResponse{" +
                "amount=" + amount +
                '}';
    }
}
