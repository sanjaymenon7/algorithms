import java.io.*;
import java.math.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.geom.*;
public class TemplonianExcavation 
{

	public static void main(String[] args) 
	{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		int caseCount;
		try
		{
			caseCount = Integer.parseInt(bufferReader.readLine());
			ShapeTriangle[] triangles = new ShapeTriangle[caseCount];
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				String[] input = bufferReader.readLine().split("\\s+");
				ShapeTriangle triangle = new ShapeTriangle();
				triangles[caseNumber] = triangle;
				
				triangle.vertices.add(new Point2D.Double(Double.parseDouble(input[0]), Double.parseDouble(input[1])));
				triangle.vertices.add(new Point2D.Double(Double.parseDouble(input[2]), Double.parseDouble(input[3])));
				triangle.vertices.add(new Point2D.Double(Double.parseDouble(input[4]), Double.parseDouble(input[5])));
				
				triangle.solveForCandelabra();
			}
			
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				ShapeTriangle triangle = triangles[caseNumber];
				bufferWriter.write(String.format("Case #%d: %d\n", caseNumber + 1, triangle.solution));
			}

			bufferWriter.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}
class ShapeTriangle
{
	List<Point2D.Double> vertices;
	int solution;
	public ShapeTriangle()
	{
		vertices = new ArrayList<Point2D.Double>();
	}
	
	public void solveForCandelabra()
	{
		double quotient = calculateQuotientAreaClosestMultipleOf3();
		this.solution = (int)calculateQuotientClosestMultipleOf3(quotient) * 3;
	}
	private double calculateQuotientAreaClosestMultipleOf3()
	{
		Point2D.Double v1 = this.vertices.get(0);
		Point2D.Double v2 = this.vertices.get(1);
		Point2D.Double v3 = this.vertices.get(2);
		Line2D.Double l = new Line2D.Double(v2, v3);
		
		double perpendicularDistance = l.ptLineDist(v1);
		double base = Math.sqrt(Math.pow((v2.getX() - v3.getX()), 2) + Math.pow((v2.getY() - v3.getY()), 2));
		
		double area = perpendicularDistance * base / 2;
		
		return calculateQuotientClosestMultipleOf3(area);
	}
	
	private double calculateQuotientClosestMultipleOf3(double number)
	{
		//DecimalFormat f = new DecimalFormat("0.0###############");
		//System.out.printf("number: %f\n", number);
		double q = number / 3;
		double floor = Math.floor(q);
		double ceiling = Math.ceil(q);
		//System.out.printf("q: %s,floor: %s,ceileing: %s\n", f.format(q), f.format(floor), f.format(ceiling));
		double diffToFloor = (number-(floor*3));
		double diffToCeiling = ((ceiling*3)-number);
		//System.out.printf("diff to floor %s\n", f.format(diffToFloor));
		//System.out.printf("diff to ceiling %s\n", f.format(diffToCeiling));
		if( Math.abs(diffToFloor - diffToCeiling) < 0.0000001d)
		{
			//System.out.println("taking ceiling\n");
			return ceiling;
		}
		if((number-(floor*3)) > ((ceiling*3)-number))
		{
			//if(ceiling != 0.0d)
			//System.out.println("taking ceiling\n");
				return ceiling;
			//else
				//return floor;
		}
		else
		{
			//if(floor != 0.0d)
			//System.out.println("taking floor\n");
				return floor;
			//else
				//return ceiling;
		}
	}
}
