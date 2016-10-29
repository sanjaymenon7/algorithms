import java.io.*;
import java.math.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import java.awt.*;
import java.awt.geom.*;
public class Area51
{

	public static void main(String[] args) 
	{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		int caseCount;
		try
		{
			caseCount = Integer.parseInt(bufferReader.readLine());
			ShapePolygon[] polygons = new ShapePolygon[caseCount];
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				ShapePolygon polygon = new ShapePolygon();
				polygons[caseNumber] = polygon;
				int numberOfVertices = Integer.parseInt(bufferReader.readLine());
				for (int i = 0; i < numberOfVertices; i++)
				{
					String[] input = bufferReader.readLine().split("\\s+");
					polygon.vertices.add(new Point2D.Double(Double.parseDouble(input[0]), Double.parseDouble(input[1])));
				}
				
				polygon.calculateArea();
				if(caseNumber < caseCount -1)
					bufferReader.readLine();
			}
			
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				ShapePolygon polygon = polygons[caseNumber];
				bufferWriter.write(String.format("Case #%d: %f\n", caseNumber + 1, polygon.polygonArea));
			}

			bufferWriter.flush();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}

}

class ShapePolygon
{
	List<Point2D.Double> vertices;
	double polygonArea;
	public ShapePolygon()
	{
		vertices = new ArrayList<Point2D.Double>();
	}
	
	public void calculateArea()
	{
		double area = 0.0d;
	
		for (int i = 0; i < vertices.size(); i++) 
		{
			Point2D.Double v1 = vertices.get(i);
			Point2D.Double v2 = vertices.get((i+1)%vertices.size());
			
			area += (v1.getX()*v2.getY()) - (v1.getY()*v2.getX());
		}
		
		this.polygonArea = Math.abs(area / 2);
	}
}
