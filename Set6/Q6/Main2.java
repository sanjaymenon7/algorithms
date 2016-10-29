package story;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by sreenath on 10.06.15.
 */
public class Main2 {

    public static void main(String[] args) {

        // initialize reader and writer
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            int t = Integer.parseInt(bufferReader.readLine());

            for (int tc=0; tc<t; tc++) {
                String[] inputArray = bufferReader.readLine().split("\\s+");
                int n = Integer.parseInt(inputArray[0]);
                int m = Integer.parseInt(inputArray[1]);
                Queue<Chapter>[] chapters = new Queue[n];

                int sumOfChapters = 0;
                for (int i=1; i<=n; i++) {
                    int numOfChapters = Integer.parseInt(bufferReader.readLine());
                    sumOfChapters = sumOfChapters + numOfChapters;
                    Queue queue = new LinkedList<Chapter>();
                    for (int j=1; j<=numOfChapters; j++) {
                        Chapter ch = new Chapter(i, j);
                        queue.add(ch);
                    }
                    chapters[i-1] = queue;
                }

                HashMap<Integer, List<Chapter>> deps = new HashMap<Integer, List<Chapter>>();
                for (int i=0; i<m; i++) {
                    String[] depArray = bufferReader.readLine().split("\\s+");
                    int c1 = Integer.parseInt(depArray[0]);
                    int p1 = Integer.parseInt(depArray[1]);
                    //String key = depArray[2].concat("_").concat(depArray[3]);
                    Integer key = Integer.parseInt(depArray[2]) * 100 + Integer.parseInt(depArray[3]);
                    if (deps.containsKey(key)) {
                        List<Chapter> dependencies = deps.get(key);
                        dependencies.add(new Chapter(c1, p1));
                    } else {
                        List<Chapter> dependencies = new LinkedList<Chapter>();
                        dependencies.add(new Chapter(c1, p1));
                        deps.put(key, dependencies);
                    }
                }

                int totalNumber = numOfSeqs(chapters, deps, sumOfChapters);
                System.out.println("Case #" + (tc+1) + ": " + totalNumber);
                if (tc != t-1) {
                    bufferReader.readLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int numOfSeqs(Queue<Chapter>[] chapters, HashMap<Integer, List<Chapter>> deps, int sumOfChapters) {
        int numOfChars = chapters.length;
        int totalSeqs = 0;
        LinkedList<Integer> sequence;
        for (int i=0; i<numOfChars; i++) {
            sequence = new LinkedList<Integer>();
            // Remove the root element from the ith queue.
            Chapter ch = chapters[i].remove();
            if (!deps.containsKey(ch.getHashCode())) {
                sequence.add(ch.getHashCode());
                int t = recurseChapters(chapters, deps, sumOfChapters, sequence, 0);
                totalSeqs = totalSeqs + t;
            }
            // Add the element back in.
            LinkedList chList = (LinkedList) chapters[i];
            chList.addFirst(ch);
        }
        return totalSeqs;
    }

    public static int recurseChapters(Queue<Chapter>[] chapters, HashMap<Integer, List<Chapter>> deps, int sumOfChapters, LinkedList<Integer> sequence, int totalSeqs) {
        // We have ordered all the chapters. Hence found a correct sequence.
        if (sequence.size() == sumOfChapters) {
            return totalSeqs + 1;
        }

        int numOfChars = chapters.length;
        for (int i=0; i<numOfChars; i++) {
            Integer lastElement = sequence.peekLast();
            if (getCharacter(lastElement) == i+1) {
                // character same as the last element in the queue.
                continue;
            }

            // Get the head of the queue.
            Chapter chObj = chapters[i].peek();
            if (chObj==null) {
                continue;
            }

            // Check the dependencies
            List<Chapter> dependencies = deps.get(chObj.getHashCode());
            boolean isDependencySatisfied = true;
            if (dependencies != null) {
                for(Chapter chptr: dependencies) {
                    if (!sequence.contains(chptr.getHashCode())) {
                        // This dependency is not satisfied.
                        isDependencySatisfied = false;
                        break;
                    }
                }
            }

            if (isDependencySatisfied) {
                // Everything satisfied. Add the chapter to sequence.
                sequence.add(new Integer(chObj.getHashCode()));
                chapters[i].remove();
                totalSeqs = recurseChapters(chapters, deps, sumOfChapters, sequence, totalSeqs);
                // For backtracking. Reset the values.
                sequence.remove(new Integer(chObj.getHashCode()));
                LinkedList chList = (LinkedList) chapters[i];
                chList.addFirst(chObj);
            }
        }
        return totalSeqs;
    }

    public static int getCharacter(Integer chapter) {
       return chapter/100;
    }

}
