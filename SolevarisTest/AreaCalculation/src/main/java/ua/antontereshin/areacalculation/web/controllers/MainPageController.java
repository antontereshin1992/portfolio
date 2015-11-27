package ua.antontereshin.areacalculation.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.antontereshin.areacalculation.model.figure.FigureProducer;

/**
 * Created by Anton on 05.11.2015.
 */
@Controller
public class MainPageController {

    private static final String MAIN_PAGE = "index";

    @Autowired
    private FigureProducer figureProducer;

    @RequestMapping(value = {"/","/index"}, method = RequestMethod.GET)
    public ModelAndView getMainPage(ModelAndView modelAndView){

        modelAndView.setViewName(MAIN_PAGE);
        modelAndView.addObject("figures",figureProducer.getFigures());

        return modelAndView;
    }
}
