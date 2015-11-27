package ua.antontereshin.areacalculation.web.controllers.statistics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.antontereshin.areacalculation.model.figure.Figure;
import ua.antontereshin.areacalculation.model.figure.FigureProducer;
import ua.antontereshin.areacalculation.model.request.GetFigureStatisticsRequest;
import ua.antontereshin.areacalculation.model.response.GetFigureStatisticsResponse;

/**
 * Created by Anton on 04.11.2015.
 */
@RestController
@RequestMapping("statistics")
public class StatisticsRestController {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsRestController.class);

    @Autowired
    private FigureProducer figureProducer;

    @RequestMapping(value = "get", method = RequestMethod.POST)
    @ResponseBody
    private GetFigureStatisticsResponse getStatistics(@RequestBody GetFigureStatisticsRequest request) {
        LOG.info("-> updateStatistics. " + request);
        GetFigureStatisticsResponse response = new GetFigureStatisticsResponse();
        Figure figure = figureProducer.getFigureById(request.getFigureId());
        if (figure != null) {
            response.setAmount(figure.incAndGetTimes());
            LOG.info("<- updateStatistics. " + response);
        }
        return response;
    }



}
