package cables;

import java.io.*;

/**
 * Created by sreenath on 24.05.15.
 */
public class Main3 {

    public static void main(String[] args) {
        //initialize reader and writer
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        //output string
        String outputFormat = new String("%s\n");

        try {
            int t = Integer.parseInt(bufferReader.readLine());

            for (int tc=0; tc<t; tc++) {
                String[] testCase = bufferReader.readLine().split("\\s+");
                double distance = Double.parseDouble(testCase[0]);
                double posts = Double.parseDouble(testCase[1]);
                double u = Double.parseDouble(testCase[2]);
                double v = Double.parseDouble(testCase[3]);

                double spaceBetweenPosts = distance / (posts - 1);

                int postsInCanyon = 0, postsInLeft = 0, postsInRight = 0;

                // Find number of posts to the left, right and in the canyon by normal distribution.
                for (double i=0; i<=distance; i=i+spaceBetweenPosts) {
                    if (i<=u) {
                        postsInLeft++;
                    } else if (i>u && i<v) {
                        postsInCanyon++;
                    } else {
                        postsInRight++;
                    }
                }

                // Initially assume that all the posts in the canyon are assigned to the right side.
                // At every iteration, the new posts in left are incremented and the new posts in right side are decremented
                double leftNewPosts = 0, rightNewPosts = postsInCanyon;
                double leftDist = u, rightDist = distance - v;
                double minDist = 0;
                if (postsInCanyon == 0) {   // No posts in canyon. We are good.
                    minDist = spaceBetweenPosts;
                } else {
                    if (leftDist > 0 && rightDist > 0) {    // If canyon falls in the middle
                        while (rightNewPosts >= 0) {
                            double leftSpace = spaceBetweenPosts, rightSpace = spaceBetweenPosts;
                            if (leftNewPosts > 0) {
                                // The idea is one post is always put at the starting, so we need to arranges n-1 posts in the remaining distance.
                                leftSpace = leftDist / (postsInLeft + leftNewPosts - 1); 
                            }
                            if (rightNewPosts > 0) {
                                rightSpace = rightDist / (postsInRight + rightNewPosts - 1);
                            }
                            minDist = Math.max(minDist, Math.min(leftSpace, rightSpace));
                            leftNewPosts++;
                            rightNewPosts--;
                        }
                    } else if (leftDist == 0) { // If canyon is in the beginning.
                        if (v-u == 0) {
                            minDist = rightDist / (posts - 1);
                        } else {
                            minDist = Math.min(rightDist / (posts - 2), v - u);
                        }
                    } else if (rightDist == 0) { // If canyon is at the end.
                        if (v-u == 0) {
                            minDist = leftDist / (posts - 1);
                        } else {
                            minDist = Math.min(leftDist/(posts - 2), v - u);
                        }
                    } else {

                    }
                }

                bufferWriter.write(String.format(outputFormat, "Case #" + (tc + 1) + ": " + minDist));
                bufferWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
