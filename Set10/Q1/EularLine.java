import java.io.*;
import java.math.*;
import java.text.DecimalFormat;
import java.util.*;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.*;
public class EularLine
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
				ShapeTriangle triangle = new ShapeTriangle();
				triangles[caseNumber] = triangle;
				for (int i = 0; i < 3; i++) 
				{
					String[] coordinate = bufferReader.readLine().split("\\s+");
					triangle.vertices.add(new Point2D.Double(Double.parseDouble(coordinate[0]), Double.parseDouble(coordinate[1])));
				}
				
				triangle.processAllPoints();
				if(caseNumber < caseCount -1)
					bufferReader.readLine();
			}
			
			DecimalFormat f = new DecimalFormat("#.########");
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				ShapeTriangle triangle = triangles[caseNumber];
				bufferWriter.write(String.format("Case #%d:\n", caseNumber + 1));
				bufferWriter.write(String.format("%s %s\n", f.format(triangle.centroid.getX()), f.format(triangle.centroid.getY())));
				bufferWriter.write(String.format("%s %s\n", f.format(triangle.orthocenter.getX()), f.format(triangle.orthocenter.getY())));
				bufferWriter.write(String.format("%s %s\n", f.format(triangle.circumcenter.getX()), f.format(triangle.circumcenter.getY())));
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
	Point2D.Double centroid;
	Point2D.Double orthocenter;
	Point2D.Double circumcenter;
	
	public ShapeTriangle()
	{
		vertices = new ArrayList<Point2D.Double>();
	}
	
	public void processAllPoints()
	{
		this.calculateCentroid();
		this.calculateCircumcentre();
		this.calculateOrthocentre();
	}
	
	private void calculateCentroid()
	{
		double X = 0.0d;
		double Y = 0.0d;
		for (Iterator iterator = vertices.iterator(); iterator.hasNext();) 
		{
			Point2D.Double v = (Point2D.Double) iterator.next();
			X += v.getX();
			Y += v.getY();
		}
		
		this.centroid = new Point2D.Double(X/3, Y/3);
	}
	
	private void calculateOrthocentre()
	{
		Line2D.Double p1,p2;
		p1 = new Line2D.Double();
		p2 = new Line2D.Double();
		for (int i = 0; i < 2; i++) 
		{
			Point2D.Double v1 = vertices.get((i+2)%3);
			Point2D.Double v2 = getIntersectionOfPerpendicular(vertices.get(i), vertices.get((i+1)%3), vertices.get((i+2)%3));
			
			if(i == 0)
			{
				p1 = new Line2D.Double(v1, v2);
			}
			else
			{
				p2 = new Line2D.Double(v1, v2);
			}	
		}
		
		/*source:http://www.ahristov.com/tutorial/geometry-games/intersection-lines.html*/
		double x1 = p1.getX1();
		double y1 = p1.getY1();
		double x2 = p1.getX2();
		double y2 = p1.getY2();
		double x3 = p2.getX1();
		double y3 = p2.getY1();
		double x4 = p2.getX2();
		double y4 = p2.getY2();
		double D = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
		
		double xi = 0.0d;
		double yi = 0.0d;
		if(D!= 0.0d)
		{
			xi = ((x3-x4)*(x1*y2 - y1*x2)-(x1-x2)*(x3*y4 - y3*x4))/D;
			yi = ((y3-y4)*(x1*y2 - y1*x2)-(y1-y2)*(x3*y4 - y3*x4))/D;	
		}
		
		
		this.orthocenter = new Point2D.Double(xi, yi);
		
	}
	
	private void calculateCircumcentre()
	{
		/*
		 * source: formula from https://en.wikipedia.org/wiki/Circumscribed_circle#Triangle_centers_on_the_circumcircle_of_triangle_ABC
		 * */
		Point2D.Double A = vertices.get(0);
		Point2D.Double B = vertices.get(1);
		Point2D.Double C = vertices.get(2);
		
		double xA = A.getX();
		double yA = A.getY();
		double xB = B.getX();
		double yB = B.getY();
		double xC = C.getX();
		double yC = C.getY();
		
		double D =2*(xA*(yB - yC) + xB*(yC - yA) + xC*(yA - yB));
		
		double xi = ((Math.pow(xA, 2)+ Math.pow(yA, 2))*(yB - yC) + (Math.pow(xB, 2) + Math.pow(yB, 2))*(yC - yA) + (Math.pow(xC, 2)+ Math.pow(yC, 2))*(yA - yB)) / D;
		double yi = ((Math.pow(xA, 2)+ Math.pow(yA, 2))*(xC - xB) + (Math.pow(xB, 2) + Math.pow(yB, 2))*(xA - xC) + (Math.pow(xC, 2)+ Math.pow(yC, 2))*(xB - xA)) / D;
		
		this.circumcenter = new Point2D.Double(xi, yi);
	}
	
	private Point2D.Double getIntersectionOfPerpendicular(Point2D.Double A, Point2D.Double B, Point2D.Double C)
	{
		double Ax = A.getX();
		double Bx = B.getX();
		double Cx = C.getX();
		double Ay = A.getY();
		double By = B.getY();
		double Cy = C.getY();

		double t = ((Cx-Ax)*(Bx-Ax)+(Cy-Ay)*(By-Ay))/(Math.pow((Bx-Ax), 2)+ Math.pow((By-Ay), 2));
		double Dx = Ax + (t * (Bx-Ax));
		double Dy = Ay + (t * (By-Ay));
		return new Point2D.Double(Dx,Dy);
	}
	
	
}
