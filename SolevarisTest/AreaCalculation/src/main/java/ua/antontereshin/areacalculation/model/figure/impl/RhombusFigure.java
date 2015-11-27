package ua.antontereshin.areacalculation.model.figure.impl;

import ua.antontereshin.areacalculation.model.figure.AbstractFigure;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Anton on 06.11.2015.
 */
public class RhombusFigure extends AbstractFigure {

    @Override
    public String getName() {
        return "Ромб";
    }

    @Override
    public String getAreaScript() {
        return "function (args) { return (args[0] * args[1]) / 2; }";
    }

    @Override
    public List<String> getParameters() { return Arrays.asList("Диагональ X", "Диагональ Y"); }

    @Override
    public String getImage() {
        return "rhombus.jpg";
    }
}
