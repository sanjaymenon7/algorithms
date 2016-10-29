package cookies;

/**
 * Created by sreenath on 19.06.15.
 */
public class Main {

    public static void main(String[] args) {

        int maxSum = 285;
        int a[] = new int[] {45, 46, 47, 48, 49, 50};
        int n = a.length;
        boolean hash[] = new boolean[maxSum + 1];
        hash[0]=true;

//Iterate through the entire array
        for(int i=0; i<n; i++){
//When i-th element is included in sum,minimum (sum)=a[i],maximum(sum)=SUM
            for (int j = maxSum; j >= a[i]; j--) {
                //Now,if sum=j-a[i],is a possible sum value then j would also be a possible value,just add a[i]
                if (hash[j - a[i]] == true) {
                    hash[j] = true;
                }
            }
        }

//Count number of all possible sum using hash

        for(int i=0;i<=maxSum;i++) {      //remember,we just need to go upto SUM
            if(hash[i]==true) {
                System.out.println(i+1);
            }
        }
    }
}
