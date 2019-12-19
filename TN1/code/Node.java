import java.util.Collection;

public interface Node {

	public Point getCoord();
	public double dist(Point p);
	public boolean isFinal();
	public Node getPrevious();
	public Collection<Node> next();
	public double getDist();
	public void printSelf();
	public double getOverallDist();
}
