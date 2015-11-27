package ua.antontereshin.areacalculation.model.figure.impl;

import ua.antontereshin.areacalculation.model.figure.AbstractFigure;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Anton on 05.11.2015.
 */
public class RectangleFigure extends AbstractFigure {

    @Override
    public String getName() {
        return "Прямоугольник";
    }

    @Override
    public String getAreaScript() {
        return "function(args){return args[0] * args[1];}";
    }

    @Override
    public List<String> getParameters() { return Arrays.asList("Высота", "Ширина"); }

    @Override
    public String getImage() {
        return "rectangle.png";
    }

}
