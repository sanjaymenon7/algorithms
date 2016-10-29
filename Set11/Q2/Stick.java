package sticks2;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sreenath on 10.07.15.
 */
public class Stick {

    int number;
    int score;
    int numOfDepsOfThisStick;
    List<Stick> dependencies;

    public Stick(int number, int score) {
        this.number = number;
        this.score = score;
        dependencies = new LinkedList<Stick>();
    }
}
