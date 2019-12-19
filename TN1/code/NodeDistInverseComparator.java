import java.util.Comparator;

public class NodeDistInverseComparator implements Comparator<Node> {

	@Override
	public int compare(Node s1, Node s2)
	{
		if (s1.getOverallDist() < s2.getOverallDist()) 
			return 1;
		if (s1.getOverallDist() > s2.getOverallDist()) 
			return -1;
		return 0;
	}
}	