import java.io.*;
import java.lang.reflect.Array;
import java.math.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.geom.*;
public class FallingWater
{
	public static void main(String[] args)
	{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		int caseCount;
		try
		{
			caseCount = Integer.parseInt(bufferReader.readLine());
			Waterfall[] waterfalls = new Waterfall[caseCount];
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				Waterfall waterfall = new Waterfall();
				waterfalls[caseNumber] = waterfall;
				
				String[] input = bufferReader.readLine().split("\\s+");
				int numberOfLedges = Integer.parseInt(input[0]);
				Point2D.Double source = new Point2D.Double(Double.parseDouble(input[1]), Double.parseDouble(input[2]));
				waterfall.source = source;
				for (int i = 0; i < numberOfLedges; i++)
				{
					String[] coordinates = bufferReader.readLine().split("\\s+");
					double x1 = Double.parseDouble(coordinates[0]);
					double y1 = Double.parseDouble(coordinates[1]);
					double x2 = Double.parseDouble(coordinates[2]);
					double y2 = Double.parseDouble(coordinates[3]);
					if(x1< x2)
					{
						waterfall.ledges.add(new Line2D.Double(x1, y1, x2, y2));
					}
					else
					{
						waterfall.ledges.add(new Line2D.Double(x2, y2, x1, y1));
					}		
				}
				
				waterfall.calculateFlow();
				if(caseNumber < caseCount -1)
					bufferReader.readLine();
			}
			
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				Waterfall waterfall = waterfalls[caseNumber];
				Arrays.sort(waterfall.endpointsX);
				bufferWriter.write(String.format("Case #%d:", caseNumber + 1));
				for (int i = 0; i<waterfall.endpointsX.length; i++) 
				{
					bufferWriter.write(String.format(" %d", waterfall.endpointsX[i]));
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

class Waterfall
{
	List<Line2D.Double> ledges;
	Point2D.Double source;
	Integer[] endpointsX;
	public Waterfall()
	{
		ledges = new ArrayList<Line2D.Double>();
	}
	
	public void calculateFlow()
	{
		List<Point2D.Double> waterSource = new ArrayList<Point2D.Double>();
		Map<String, Point2D.Double> trackSources = new HashMap<String, Point2D.Double>();
		Map<Integer, Integer> endX = new HashMap<Integer, Integer>();
		waterSource.add(this.source);
		trackSources.put(createKeyForPoint(this.source), this.source);
		while(waterSource.size() > 0)
		{
			Point2D.Double currentSource = waterSource.remove(0);
			double yMax = 0.0d;
			Line2D.Double interceptingLedge = new Line2D.Double();
			for (Iterator iterator = ledges.iterator(); iterator.hasNext();)
			{
				Line2D.Double thisLedge = (Line2D.Double) iterator.next();
				
				if(pointDropsOnLedge(currentSource, thisLedge))
				{
					Point2D.Double p1 = (Point2D.Double)thisLedge.getP1();
					Point2D.Double p2 = (Point2D.Double)thisLedge.getP2();
					double y = p2.y + ((currentSource.x-p2.x)*(p1.y-p2.y)/(p1.x-p2.x));
					if(y > yMax)
					{
						yMax = y;
						interceptingLedge = thisLedge;
					}
				}
			}
			if(yMax == 0.0d)
			{
				//this.endpointsX.add(currentSource.x);
				endX.put((int)currentSource.x, (int)currentSource.x);
			}
			else
			{
				Point2D.Double p1 = (Point2D.Double)interceptingLedge.getP1();
				Point2D.Double p2 = (Point2D.Double)interceptingLedge.getP2();
				if((p1.y - p2.y) < -0.00001d)
				{
					String keyP1 = createKeyForPoint(p1);
					if(!trackSources.containsKey(keyP1))
					{
						trackSources.put(keyP1, p1);
						waterSource.add(p1);
					}
					if(Math.abs(p2.x - currentSource.x) < 0.00001d)
					{
						String keyP2 = createKeyForPoint(p2);
						if(!trackSources.containsKey(keyP2))
						{
							trackSources.put(keyP2, p2);
							waterSource.add(p2);
						}
					}
				}
				else if((p1.y - p2.y) > 0.00001)
				{
					String keyP2 = createKeyForPoint(p2);
					if(!trackSources.containsKey(keyP2))
					{
						trackSources.put(keyP2, p2);
						waterSource.add(p2);
					}
					if(Math.abs(p1.x - currentSource.x) < 0.00001d)
					{
						String keyP1 = createKeyForPoint(p1);
						if(!trackSources.containsKey(keyP1))
						{
							trackSources.put(keyP1, p1);
							waterSource.add(p1);
						}
					}
				}
				else
				{
					String keyP1 = createKeyForPoint(p1);
					String keyP2 = createKeyForPoint(p2);
					if(!trackSources.containsKey(keyP2))
					{
						trackSources.put(keyP2, p2);
						waterSource.add(p2);
					}
					if(!trackSources.containsKey(keyP1))
					{
						trackSources.put(keyP1, p1);
						waterSource.add(p1);
					}
				}
			}
		}
		
		this.endpointsX = endX.keySet().toArray(new Integer[0]);
	}
	
	private String createKeyForPoint(Point2D.Double p)
	{
		return p.getX()+"_"+p.getY();
	}
	
	private boolean pointDropsOnLedge(Point2D.Double currentSource, Line2D.Double thisLedge)
	{
		Point2D.Double p1 = (Point2D.Double)thisLedge.getP1();
		Point2D.Double p2 = (Point2D.Double)thisLedge.getP2();
		
		if(((currentSource.x - p1.x) >=0.0d) && ((p2.x - currentSource.x) >= 0.0d))
		{
			double y = p2.y + ((currentSource.x-p2.x)*(p1.y-p2.y)/(p1.x-p2.x));
			
			if((y - currentSource.y) <= -0.00001d)
				return true;
			else
				return false;
		}
		else
		{
			return false;
		}
	}
}
