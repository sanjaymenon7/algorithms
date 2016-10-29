package packingcases;

/**
 * Created by sreenath on 16.06.15.
 */
public class Box implements Comparable<Box> {

    public int width;
    public int length;
    public int height;

    // width should always be less than length.
    public Box(int height, int width, int length) {
        this.width = width;
        this.length = length;
        this.height = height;
    }

    @Override
    public int compareTo(Box o) {
        int myArea = width * length;
        int otherArea = o.width * o.length;
        return Integer.compare(myArea, otherArea);
    }
}
