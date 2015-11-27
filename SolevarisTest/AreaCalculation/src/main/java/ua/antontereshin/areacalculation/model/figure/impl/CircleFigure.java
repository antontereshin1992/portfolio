package ua.antontereshin.areacalculation.model.figure.impl;

import ua.antontereshin.areacalculation.model.figure.AbstractFigure;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Anton on 05.11.2015.
 */
public class CircleFigure extends AbstractFigure {

    @Override
    public String getName() {
        return "Круг";
    }

    @Override
    public String getAreaScript() {
        return "function(args){return " + Math.PI + " * args[0] * args[0];}";
    }

    @Override
    public List<String> getParameters() {
        return Arrays.asList("Радиус");
    }

    @Override
    public String getImage() {
        return "circle.png";
    }

}
