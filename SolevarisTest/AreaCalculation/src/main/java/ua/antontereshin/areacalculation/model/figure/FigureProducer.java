package ua.antontereshin.areacalculation.model.figure;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Anton on 05.11.2015.
 */
public class FigureProducer {

    @Autowired
    private List<Figure> figures;

    public List<Figure> getFigures(){
        return figures;
    }

    public Figure getFigureById(Long id) {
        return getFigures().stream()
                .filter(figure -> figure.getId().equals(id))
                .findFirst().get();
    }

}
