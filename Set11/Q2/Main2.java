package sticks2;

import java.io.*;
import java.util.*;

/**
 * Created by sreenath on 10.07.15.
 */
public class Main2 {

    public static void main(String[] args) {

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        try {
            int tc = Integer.parseInt(bufferReader.readLine());

            for (int t = 1; t <= tc; t++) {
                String[] inputString = bufferReader.readLine().split("\\s+");
                int numOfSticks = Integer.parseInt(inputString[0]);
                int numOfIntersections = Integer.parseInt(inputString[1]);

                String[] scores = bufferReader.readLine().split("\\s+");
                Stick[] sticks = new Stick[numOfSticks];
                for (int i=0; i<numOfIntersections; i++) {
                    String[] intersection = bufferReader.readLine().split("\\s+");
                    int top = Integer.parseInt(intersection[0]);
                    int bottom = Integer.parseInt(intersection[1]);

                    // Get the bottomStick
                    Stick bottomStick = sticks[bottom - 1];
                    if (bottomStick == null) {
                        bottomStick = new Stick(bottom, Integer.parseInt(scores[bottom - 1]));
                        sticks[bottom - 1] = bottomStick;
                    }

                    // Get the topStick
                    Stick topStick = sticks[top - 1];
                    if (topStick == null) {
                        topStick = new Stick(top, Integer.parseInt(scores[top - 1]));
                        sticks[top - 1] = topStick;
                    }

                    topStick.dependencies.add(bottomStick);
                    bottomStick.numOfDepsOfThisStick++;
                }

                Queue<Stick> zeroDepSticks = getZeroDepSticks(sticks, scores);
                int maxScore = 0;
                while (!zeroDepSticks.isEmpty()) {
                    Stick s = zeroDepSticks.poll();
                    List<Stick> deps = s.dependencies;
                    for (Stick depStick: deps) {
                        if (depStick.numOfDepsOfThisStick == 1) {
                            zeroDepSticks.add(depStick);
                        }
                        depStick.numOfDepsOfThisStick--;
                    }
                    maxScore = maxScore + s.score;
                }

                bufferWriter.write(String.format("Case #%d: %d\n", t, maxScore));
                bufferWriter.flush();
                if (t != tc) {
                    bufferReader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Queue<Stick> getZeroDepSticks(Stick[] sticks, String[] scores) {
        Queue<Stick> zeroDepSticks = new LinkedList<Stick>();
        for (int i=0; i<sticks.length; i++) {
            Stick s = sticks[i];
            if (s == null) {
                s = new Stick(i+1, Integer.parseInt(scores[i]));
                sticks[i] = s;
            }
            if (s.numOfDepsOfThisStick == 0) {
                zeroDepSticks.add(s);
            }
        }
        return zeroDepSticks;
    }
}
