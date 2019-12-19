import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.List;

public class StreetNode implements Node {

	private Point coord;
	private Node previous;
	private double distFromSource;
	private Point end_p;

	private Map<Point, List<Point>> adjMap;


	public void printSelf()
	{
		System.out.println(coord.X +", "+ coord.Y); 
	}

	@Override
	public double getDist()
	{
		return distFromSource;
	}

	@Override
	public double getOverallDist()
	{
		return distFromSource + dist(end_p);
	}
	public Point getPoint()
	{
		return this.coord;
	}

	@Override
	public Node getPrevious()
   	{
		return previous;
	}

	public Point getCoord()
	{
		return coord;
	}

	public StreetNode(Point p, Node previous, double distFromSource, Map<Point, List<Point>> adjMap, Point end_p)
   	{

		this.coord = p;
		this.end_p = end_p;
		this.previous = previous;
		this.distFromSource = distFromSource;

		this.adjMap = adjMap;
	}

	@Override
	public boolean isFinal() 
	{
		return coord.equals(end_p);
	}

	@Override
	public Collection<Node> next() 
	{
		Collection<Node> nodes = new ArrayList<>();
		
		//All the possible moves
		for (Point p : adjMap.get(coord)) 
			nodes.add(new StreetNode(p, this, distFromSource + dist(p), adjMap, end_p));

		return nodes;
	}

	public double dist(Point p1) 
	{
		return Math.sqrt( Math.pow(p1.X - coord.X,2) + Math.pow(p1.Y - coord.Y,2) );
	}

	@Override
	public int hashCode() 
	{
		return coord.hashCode();
	}

	@Override 
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		StreetNode that = (StreetNode) o;

		return coord.equals(that.coord);
	}

}

