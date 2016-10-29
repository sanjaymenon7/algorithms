package packingcases;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by sreenath on 16.06.15.
 */
public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferReader.readLine());

        for (int tc=1; tc <= t; tc++) {
            String[] inputString = bufferReader.readLine().split("\\s+");
            int height = Integer.parseInt(inputString[0]);
            int numOfBoxes = Integer.parseInt(inputString[1]);
            boolean heightAttained = false;

            int arrayLength = 3*numOfBoxes;
            Box[] boxes = new Box[arrayLength];
            int i=0;
            while (i<arrayLength-1) {
                String[] boxDesc = bufferReader.readLine().split("\\s+");
                int[] dimensions = new int[3];
                dimensions[0] = Integer.parseInt(boxDesc[0]);
                dimensions[1] = Integer.parseInt(boxDesc[1]);
                dimensions[2] = Integer.parseInt(boxDesc[2]);

                if (dimensions[0] >= height || dimensions[1] >= height || dimensions[2] >= height) {
                    heightAttained = true;
                    break;
                }

                // To make sure that width is always less than length
                Arrays.sort(dimensions);

                // Add the different boxesations
                Box b1 = new Box(dimensions[0], dimensions[1], dimensions[2]);
                Box b2 = new Box(dimensions[1], Integer.min(dimensions[0], dimensions[2]), Integer.max(dimensions[0], dimensions[2]));
                Box b3 = new Box(dimensions[2], Integer.min(dimensions[0], dimensions[1]), Integer.max(dimensions[0], dimensions[1]));

                // Put the boxes into array
                boxes[i] = b1;
                boxes[i+1] = b2;
                boxes[i+2] = b3;

                i=i+3;
            }

            if (heightAttained) {
                bufferWriter.write("Case #" + tc + ": yes\n");
            } else {
                Arrays.sort(boxes, Collections.reverseOrder());
                int msh[] = new int[arrayLength];

                // Initialize the max height array
                for (i=0; i<arrayLength-1; i++) {
                    msh[i] = boxes[i].height;
                }

                for (i = 1; (i < arrayLength && !heightAttained); i++ ) {
                    for (int j = 0; j < i; j++) {
                        if ((boxes[i].width < boxes[j].width) && (boxes[i].length < boxes[j].length) && (msh[i] < msh[j] + boxes[i].height)) {
                            msh[i] = msh[j] + boxes[i].height;
                            if (msh[i] >= height) {
                                heightAttained = true;
                                break;
                            }
                        }
                    }
                }

                if (heightAttained) {
                    bufferWriter.write("Case #" + tc + ": yes\n");
                } else {
                    bufferWriter.write("Case #" + tc + ": no\n");
                }
            }
            bufferWriter.flush();
            if (tc != t) {
                bufferReader.readLine();
            }
        }
    }
}


