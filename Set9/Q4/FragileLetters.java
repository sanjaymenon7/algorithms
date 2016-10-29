import java.io.*;
import java.math.*;
import java.util.*;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.*;
public class FragileLetters 
{
	public static void main(String[] args)
	{
		BufferedReader bufferReader = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bufferWriter = new BufferedWriter(new OutputStreamWriter(System.out));
		int caseCount;
		try
		{
			caseCount = Integer.parseInt(bufferReader.readLine());
			Letters[] letters = new Letters[caseCount];
			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				int numberOfVertices = Integer.parseInt(bufferReader.readLine());
				Letters letter = new Letters();
				letters[caseNumber] = letter;
				for (int i = 0; i < numberOfVertices; i++) 
				{
					String[] input =  bufferReader.readLine().split("\\s+");
					Point2D.Float v = new Point2D.Float(Float.parseFloat(input[0]), Float.parseFloat(input[1]));
					letter.vertices.add(v);
				}
				letter.calculateViablePositions();
				if(caseNumber < caseCount -1)
					bufferReader.readLine();
			}

			for (int caseNumber = 0; caseNumber < caseCount; caseNumber++) 
			{
				Letters letter = letters[caseNumber];
				bufferWriter.write(String.format("Case #%d: %d\n", caseNumber + 1, letter.countOfVaiablePositions));
			}

			bufferWriter.flush();	
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}

class Letters
{
	List<Point2D.Float> vertices;
	int countOfVaiablePositions;
	public Letters()
	{
		vertices = new ArrayList<Point2D.Float>();
	}

	public void calculateViablePositions()
	{
		int size = vertices.size();
		double[] Xs = new double[size];
		double[] Ys = new double[size];
		int index=0;
		double X = 0.0d;
		double Y = 0.0d;
		for (Iterator iterator = vertices.iterator(); iterator.hasNext();) 
		{
			Point2D.Float  pt= (Point2D.Float) iterator.next();
			X += pt.getX();
			Y += pt.getY();
			Xs[index] = pt.getX();
			Ys[index] = pt.getY();
			index++;
		}
		Point2D.Float centroid = new Point2D.Float((float)X/size, (float)Y/size);
		//System.out.printf("Centroid: {%f,%f}\n",centroid.getX(), centroid.getY());
		/*create a polygon out of the points and choose a random point inside the polygon*/
		Path2D.Double path = new Path2D.Double();
		path.moveTo(Xs[0], Ys[0]);
		for (int i = 1; i < size; i++) 
		{
			path.lineTo(Xs[i], Ys[i]);
		}

		Rectangle2D rec = path.getBounds2D();
		double x, y;
	    do {
	        x = rec.getX() + rec.getWidth() * Math.random();
	        y = rec.getY() + rec.getHeight() * Math.random();
	    } while(!path.contains(x,y));
	    Point2D.Float randomPointInPolygon = new Point2D.Float((float)x,(float)y);

		//System.out.printf("Random internal point: {%f,%f}\n",randomPointInPolygon.getX(), randomPointInPolygon.getY());
		 
		for (int i = 0; i < size; i++)
		{
			boolean possible = true;
			Point2D.Float v1 = vertices.get(i);
			Point2D.Float v2 = vertices.get((i + 1)%size);

			/*point of intersection from perpendicular*/
			if(!inRange(v1, v2, centroid))
				continue;

			Line2D.Float edge = new Line2D.Float(v1, v2);

			/*check if any other points are collinear and also that all points are on the same side of the line*/
			for (int j = 0; j < size; j++) 
			{
				int relativePositionOfPointInPolygon = edge.relativeCCW(randomPointInPolygon);
				if(j != i && j != ((i + 1)%size))
				{
					if(ccw(v1, v2, vertices.get(j)) == 0)
					{
						possible = false;
						break;
					}
					int relativePosition = edge.relativeCCW(vertices.get(j));
					if(relativePosition != relativePositionOfPointInPolygon)
					{
						possible = false;
						break;
					}
				}
			}

			if(possible)
			{
				countOfVaiablePositions++;
				//System.out.printf("POssibel side {%f,%f}{%f,%f}\n", v1.getX(), v1.getY(), v2.getX(), v2.getY());
			}

		}
	}

	public int ccw(Point2D a, Point2D b, Point2D c) {
		double area2 = (b.getX()-a.getX())*(c.getY()-a.getY()) - (b.getY()-a.getY())*(c.getX()-a.getX());
		if      (area2 < 0) return -1;
		else if (area2 > 0) return +1;
		else                return  0;
	}

	/*
	 * Source : http://stackoverflow.com/questions/17581738/check-if-a-point-projected-on-a-line-segment-is-not-outside-it
	 * */
	public boolean inRange(Point2D.Float A, Point2D.Float B, Point2D.Float C)
	{
		double Ax = A.getX();
		double Bx = B.getX();
		double Cx = C.getX();
		double Ay = A.getY();
		double By = B.getY();
		double Cy = C.getY();
		
		double dx = Ax - Bx;
		double dy = Ay - By;
		double innerProduct = (Cx - Bx)*dx + (Cy - By)*dy;
		return 0 <= innerProduct && innerProduct <= dx*dx + dy*dy;
	}
	public Point2D.Float getIntersectionOfPerpendicular(Point2D.Float A, Point2D.Float B, Point2D.Float C)
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
		return new Point2D.Float((float)Dx,(float)Dy);
	}
}