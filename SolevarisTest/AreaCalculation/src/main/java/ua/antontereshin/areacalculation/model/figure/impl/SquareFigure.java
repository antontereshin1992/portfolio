package ua.antontereshin.areacalculation.model.figure.impl;

import ua.antontereshin.areacalculation.model.figure.AbstractFigure;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Anton on 05.11.2015.
 */
public class SquareFigure extends AbstractFigure {

    @Override
    public String getName() {
        return "Квадрат";
    }

    @Override
    public String getAreaScript() {
        return "function(args){return args[0] * args[0];}";
    }

    @Override
    public List<String> getParameters() {
        return Arrays.asList("Сторона");
    }

    @Override
    public String getImage() {
        return "square.png";
    }

}
