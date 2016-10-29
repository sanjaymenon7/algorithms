import java.io.*;
import java.math.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.geom.*;

import javax.swing.text.NumberFormatter;
public class Fractals 
{
	public static void main(String[] args)
	{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		int caseCount;
		try
		{
			caseCount = Integer.parseInt(bufferReader.readLine());
			ShapeFractal[] fractals = new ShapeFractal[caseCount];
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				String[] input = bufferReader.readLine().split("\\s+");
				int numberOfProductions = Integer.parseInt(input[0]);
				int recurrenceCount = Integer.parseInt(input[1]);
				int a = Integer.parseInt(input[2]);
				String startString = input[3];
				
				ShapeFractal fractal = new ShapeFractal(recurrenceCount, a, startString);
				fractals[caseNumber] = fractal;
				for (int i = 0; i < numberOfProductions; i++) 
				{
					String[] production = bufferReader.readLine().split("=>");
					String s = production[0].trim();
					char[] c= new char[1];
					s.getChars(0, 1, c, 0);
					String replaceWith = production[1].trim();
					fractal.productions.put(c[0], replaceWith);
				}
				
				fractal.walkThePath();
				
				if(caseNumber < caseCount -1)
					bufferReader.readLine();
			}
			
			DecimalFormat fSci = new DecimalFormat("0.0###############E0");
			DecimalFormat f = new DecimalFormat("0.0###############");
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				ShapeFractal fractal = fractals[caseNumber];
				bufferWriter.write(String.format("Case #%d:\n", caseNumber + 1));
				for (Iterator iterator = fractal.points.iterator(); iterator.hasNext();) {
					Point2D.Double point = (Point2D.Double) iterator.next();
					bufferWriter.write(String.format("%s %s\n", Math.abs(point.x) < Math.pow(10, -15)?fSci.format(point.x):f.format(point.x), Math.abs(point.y) < Math.pow(10, -15)? fSci.format(point.y): f.format(point.y)));
				}
			}

			bufferWriter.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}

class ShapeFractal
{
	Map<Character, String> productions;
	int a;
	int recurrenceCount;
	String startString;
	List<Point2D.Double> points;
	public ShapeFractal(int recurrenceCount, int a, String startString)
	{
		this.productions = new HashMap<Character, String>();
		this.recurrenceCount = recurrenceCount;
		this.a = a;
		this.startString = startString;
	}
	
	public void walkThePath()
	{
		char[] path = produceInstructions();
		points = new ArrayList<Point2D.Double>();
		Point2D.Double currentPosition = new Point2D.Double(0.0d, 0.0d);
		points.add(currentPosition);
		int angle = 0;
		for (int i = 0; i < path.length; i++) 
		{
			if(path[i] == '+')
			{
				angle += a;
			}
			else if(path[i] == '-')
			{
				angle -=a;
			}
			else
			{
				double x = currentPosition.x;
				double y = currentPosition.y;
				
				double newX = x + Math.cos(angle * Math.PI /180);
				double newY = y + Math.sin(angle * Math.PI / 180);
				
				Point2D.Double newPoistion = new Point2D.Double(newX, newY);
				points.add(newPoistion);
				currentPosition = newPoistion;
			}
		}
	}
	
	public char[] produceInstructions()
	{
		StringBuffer path = new StringBuffer(startString);
		//String path = startString;
		for (int i = 0; i < recurrenceCount; i++) 
		{
			StringBuffer newPath = new StringBuffer();
			char[] ch = new char[path.length()];
			path.getChars(0, path.length(), ch, 0);
			for (int j = 0; j < path.length(); j++) 
			{
				char c = ch[j];
				if(productions.containsKey(c))
				{
					newPath.append(productions.get(c));
				}
				else
				{
					newPath.append(c);
				}
			}
			path = newPath;
		}
		//System.out.println(path);
		char[] resultPath = new char[path.length()];
		path.getChars(0, path.length(), resultPath, 0);
		return resultPath;
	}
}