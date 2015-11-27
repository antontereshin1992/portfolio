package ua.antontereshin.areacalculation.model.figure;

import java.io.*;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Anton on 05.11.2015.
 */
public abstract class AbstractFigure implements Figure, Serializable {

    private final long id = new Random().nextLong();
    private Integer times = 0;

    private static final Lock LOCK = new ReentrantLock();

    private final String ROOT_DIRECTORY = "./";

    public AbstractFigure() {
    }

    public AbstractFigure(Integer times) {
        this.times = times;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public final Integer incAndGetTimes() {
        synchronized (LOCK) {
            return ++times;
        }
    }

    @Override
    public Integer getTimes() {
        synchronized (LOCK) {
            return times;
        }
    }

    @Override
    public String toString() {
        return getName() + " {" +
                "id=" + getId() +
                ", times=" + times +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractFigure)) return false;

        AbstractFigure that = (AbstractFigure) o;

        return getId() == that.getId();

    }

    @Override
    public int hashCode() {
        return (int) (getId() ^ (getId() >>> 32));
    }

    public void init() {
        try (FileInputStream fis = new FileInputStream(ROOT_DIRECTORY + getClass().getSimpleName());
             ObjectInputStream oin = new ObjectInputStream(fis)) {
            AbstractFigure figure = (AbstractFigure) oin.readObject();
            times = figure.getTimes();
        } catch (FileNotFoundException ignore) {
            times = 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void destroy() {
        try (FileOutputStream fos = new FileOutputStream(ROOT_DIRECTORY + getClass().getSimpleName());
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
