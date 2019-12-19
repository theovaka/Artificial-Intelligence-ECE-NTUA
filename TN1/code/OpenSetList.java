import java.util.*;

// PriorityQueue's method contains has O(n) complexity
// In order to speed this up to O(1) we also use a Set to keep track of 
// objects inside the PriorityQueue
public class OpenSetList 
{
	private PriorityQueue<Node> nodes_list;
	private Set<Node> nodes_in_openSet;
	private PriorityQueue<Node> inverse_list;
	private int search_width;
	public int max_width_size[];
	private int current_search_width;

	public OpenSetList(int search_width, int max_width_size[]) 
	{
		this.nodes_list = new PriorityQueue<Node>(new NodeDistComparator());
		this.inverse_list = new PriorityQueue<Node>(new NodeDistInverseComparator());
		this.nodes_in_openSet = new HashSet<>();
		this.search_width = search_width;
		this.max_width_size = max_width_size;
		this.current_search_width = 0;
	}

	public void add(Node n) 
	{
		int add = 1;

		nodes_list.add(n);
		nodes_in_openSet.add(n);
		inverse_list.add(n);
		if (nodes_list.size() > search_width){
			Node max = inverse_list.poll();
			nodes_list.remove(max);
			nodes_in_openSet.remove(max);
			add = 0;
		}

		current_search_width += add;
		if (current_search_width > max_width_size[0]) {
			max_width_size[0] = current_search_width;
		}
	}

	public boolean isEmpty() 
	{
		return nodes_list.isEmpty();
	}

	public Node poll()
	{
		Node n = nodes_list.poll();
		inverse_list.remove(n);
		nodes_in_openSet.remove(n);
		current_search_width -= 1;
		return n;
	}

	public boolean contains(Node n)
	{
		return nodes_in_openSet.contains(n);
	}

	public void remove(Node n)
	{
		nodes_list.remove(n);
		nodes_in_openSet.remove(n);
		inverse_list.remove(n);
		current_search_width -= 1;
	}
}
