import java.util.*;

public class DijkstraSolver implements Solver {

	@Override
	public Node solve(Node initial, int openSet_size) {
		Set<Node> seen = new HashSet<>();
		Comparator<Node> comparator = new NodeDistComparator();
		PriorityQueue<Node> remaining = new PriorityQueue<Node>(openSet_size, comparator);

		// Add the initial state to the queue
		remaining.add(initial);

		while (!remaining.isEmpty()) {
			// Remove the first remaining state from the queue
			Node s = remaining.poll();

			if (!seen.contains(s)) {

				seen.add(s);
				// If s is final, then return it
				if (s.isFinal())
					return s;
				// Find the states that can be reached from this one
				for (Node n : s.next())
					// If n has not been visited before
					remaining.add(n);
			}
		}
		//if the queue is empty and no sulution was found, return null
		return null;
	}
}
