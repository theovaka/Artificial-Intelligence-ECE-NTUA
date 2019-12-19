import java.util.*;

public class AstarSolver implements Solver {

	// Array in order to pass it by reference
	private int max_search_width[];
	private int num_steps;

	public AstarSolver() {
		this.max_search_width = new int[1];
		this.max_search_width[0] = 0;
		this.num_steps = 0;
	}

	@Override
	public Node solve(Node initial, int search_width) {
		// Reset the num of steps
		num_steps=0;

		Set<Node> closedSet = new HashSet<>();
		OpenSetList openSet = new OpenSetList(search_width, max_search_width);
		Map<Node, Double> gScore = new HashMap<>();

		// Add the initial state to the queue
		openSet.add(initial);
		gScore.put(initial, 0.0);

		while (!openSet.isEmpty()) {
			// Increase the steps
			num_steps++;

			// Remove the first closedSet state from the queue
			Node current = openSet.poll();

			// If s is final, then return it
			if (current.isFinal()) { return current; }

			closedSet.add(current);

			// Find the states that can be reached from this one
			for (Node n : current.next()) {

				if (closedSet.contains(n)) { continue; }
				
				if (!openSet.contains(n)) { openSet.add(n); }

				// If current cost to get to that node is bigger than one 
				// already calculated, then don't even bother
				double tentative_gScore = gScore.get(current) + current.dist(n.getCoord());
				if (tentative_gScore >= gScore.getOrDefault(n, Double.MAX_VALUE)) { continue; }

				gScore.put(n, tentative_gScore);
				
				//Fix priority queue if node is in the open set
				if (openSet.contains(n)) { 
					openSet.remove(n);
					openSet.add(n);
				}

			}
		}
		//if the queue is empty and no sulution was found, return null
		return null;
	}

	public int getMaxWidth()
	{
		return max_search_width[0];
	}

	public int getStepsNum()
	{
		return num_steps;
	}
}
