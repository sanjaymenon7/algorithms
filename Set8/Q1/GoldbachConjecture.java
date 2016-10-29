import java.io.*;
import java.util.*;

public class GoldbachConjecture
{
	public static void main(String[] args) 
	{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		int caseCount;
		try
		{
			caseCount = Integer.parseInt(bufferReader.readLine());
			PrimeNumberCollection primeNumCollection = new PrimeNumberCollection();
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				int goldbachNumber = Integer.parseInt(bufferReader.readLine());
				List<Integer> result = primeNumCollection.getPrimeCollection(goldbachNumber);
				
				bufferWriter.write(String.format("Case #%d:", caseNumber +1));
				for (Iterator iterator = result.iterator(); iterator.hasNext();)
				{
					Integer integer = (Integer) iterator.next();
					bufferWriter.write(String.format(" %d", integer));
				}
				bufferWriter.write("\n");
			}
			bufferWriter.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}

class PrimeNumberCollection
{
	boolean[] primeNotPrimeDetail;
	int[] primeSequence;
	int endPrimeNumberIndex;
	public PrimeNumberCollection() 
	{
		primeSieve();
	}
	
	/*
	 * source :http://stackoverflow.com/questions/586284/finding-prime-numbers-with-the-sieve-of-eratosthenes-originally-is-there-a-bet
	 * */
	private void primeSieve()
	{
		primeNotPrimeDetail = new boolean[100000000+1];
		Arrays.fill(primeNotPrimeDetail, true);
		primeNotPrimeDetail[0] = primeNotPrimeDetail[1] = false;
		primeSequence = new int[10000000+1];
		
		/*Source: http://introcs.cs.princeton.edu/java/14array/PrimeSieve.java.html
		 * */
		for(int i = 2; i*i<= 100000000; i++)
		{
			if(primeNotPrimeDetail[i])
			{
				for(int j = i ; i*j<=100000000;j++)
				{
					primeNotPrimeDetail[i*j] = false;
				}
			}
		}
		endPrimeNumberIndex = 0;
		for(int i = 2; i <=100000000;i++)
		{
			if(primeNotPrimeDetail[i])
				primeSequence[endPrimeNumberIndex++] = i;
		}
		/*primeSequence[0] = 2;
		primeNotPrimeDetail[2] = true;
		endPrimeNumberIndex = 1;
		int primeCandidate = 1;
		boolean isPrime = false;
		while((primeCandidate +=2) < 1000)
		{
			isPrime = true;
			for(int i = 0;i<endPrimeNumberIndex;i++)
			{
				if(primeCandidate % primeSequence[i] ==0)
				{
					isPrime = false;
	                break;
				}
			}
			if(isPrime)
			{
				primeSequence [endPrimeNumberIndex++] = primeCandidate;
	            primeNotPrimeDetail[primeCandidate] = true;
	        }
		}*/
	}
	public List<Integer> getPrimeCollection(int goldbachNumber)
	{
		if(goldbachNumber % 2 == 0)
		{
			List<Integer> result = new ArrayList<Integer>();
			for (int i = 0; i < endPrimeNumberIndex; i++) 
			{
				int primeOne = primeSequence[i];
				int primeTwoIndex = goldbachNumber - primeOne;
				if(primeTwoIndex >= 0 && primeTwoIndex <=endPrimeNumberIndex && primeNotPrimeDetail[primeTwoIndex])
				{
					result.add(primeOne);
					result.add(goldbachNumber - primeOne);
					Collections.sort(result);
					break;
				}
			}
			return result;
		}
		else
		{
			List<Integer> result = new ArrayList<Integer>();
			boolean foundSeq = false;
			for (int i = 0; i < endPrimeNumberIndex; i++) 
			{
				int primeOne = primeSequence[i];
				for (int j = 0; j < endPrimeNumberIndex; j++) 
				{
					int primeTwo = primeSequence[j];
					int primeThreeIndex = goldbachNumber - primeOne - primeTwo;
					if(primeThreeIndex >= 0 && primeThreeIndex <= endPrimeNumberIndex && primeNotPrimeDetail[primeThreeIndex])
					{
						result.add(primeOne);
						result.add(primeTwo);
						result.add(primeThreeIndex);
						Collections.sort(result);
						foundSeq = true;
						break;
					}
				}
				if(foundSeq)
					break;
			}
			return result;
		}
	}
}
