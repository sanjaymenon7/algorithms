package zombie;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

// Parts of the code taken from http://www.codeproject.com/Articles/706838/Bounded-Knapsack-Algorithm
public class Zombie {

    public static void main(String[] args) throws IOException {

        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));

        int t = Integer.parseInt(bufferReader.readLine());

        for (int tc = 1; tc <= t; tc++) {
            String[] inputString = bufferReader.readLine().split("\\s+");
            int capacity = Integer.parseInt(inputString[0]);
            int numOfItems = Integer.parseInt(inputString[1]);

            List<Item> items = new LinkedList<Item>();
            for (int i=1; i<=numOfItems; i++) {
                String[] itemDesc = bufferReader.readLine().split("\\s+");
                int qty = Integer.parseInt(itemDesc[0]);
                int wgt = Integer.parseInt(itemDesc[1]);
                int value = Integer.parseInt(itemDesc[2]);
                items.add(new Item(String.valueOf(i), wgt, value, qty));
            }

            ItemCollection finalList = getFinalCollection(items, capacity);
            StringBuilder builder = new StringBuilder("Case #" + tc + ":");
            for (String itemDesc: finalList.Contents.keySet()) {
                int times = finalList.Contents.get(itemDesc);
                for (int i=0; i<times; i++) {
                    builder.append(" ");
                    builder.append(itemDesc);
                }
            }
            bufferWriter.write(builder.toString());
            bufferWriter.write("\n");
            bufferWriter.flush();
            if (tc != t) {
                bufferReader.readLine();
            }
        }
    }

    public static ItemCollection getFinalCollection(List<Item> items, int capacity) {

        Item CurrentItem;
        ItemCollection[] ic = new ItemCollection[capacity + 1];

        for (int i = 0; i <= capacity; i++) {
            ic[i] = new ItemCollection();
        }

        for (int i = 0; i < items.size(); i++) {
            for (int j = capacity; j >= 0; j--) {
                CurrentItem = items.get(i);
                if (j >= CurrentItem.Weight) {
                    int quantity = Math.min(CurrentItem.Quantity, j / CurrentItem.Weight);
                    for (int k = 1; k <= quantity; k++) {
                        ItemCollection lighterCollection = ic[j - k * CurrentItem.Weight];
                        int testValue = lighterCollection.TotalValue + k * CurrentItem.Value;
                        if (testValue > ic[j].TotalValue) {
                            ic[j] = lighterCollection.Copy();
                            ic[j].AddItem(CurrentItem, k);
                        }
                    }
                }
            }
        }
        return ic[capacity];
    }
}
