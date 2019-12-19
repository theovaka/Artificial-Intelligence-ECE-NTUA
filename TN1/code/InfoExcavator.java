import java.io.*;
import java.util.*;

public class InfoExcavator {

	public int getTaxisNum(String filename)
	{

		int taxis_num = 0;

		try { 
			BufferedReader reader = new BufferedReader(new FileReader(filename));

			//Read the first X,Y,info line
			String line = reader.readLine();
			line = reader.readLine();

			while (line != null) {
				line = reader.readLine();
				taxis_num++;
			}

		} 
		catch(IOException e) {
			e.printStackTrace();
		}
		return taxis_num;
	}



	public Point getPointFromLine(String filename, int line_num) 
	{

		try { 
			BufferedReader reader = new BufferedReader(new FileReader(filename));

			//Read the first X,Y,info line
			String line = reader.readLine();

			while (line_num != 0) {
				line = reader.readLine();
				line_num--;
			}

			// Put the first point in the adj map
			double X = Double.parseDouble(line.split(",")[0]);
			double Y = Double.parseDouble(line.split(",")[1]);
			int id;

			try { 
				id = Integer.parseInt(line.split(",")[2]);
			} 
			catch (ArrayIndexOutOfBoundsException e) { id = 0; }

			return new Point(X,Y,id);
		} 
		catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//public void main (String[] args) {
	public Map<Point, List<Point>> makeAdjMap (String file) 
	{

		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			//Read the first X,Y,info line
			String line = reader.readLine();

			// Map with adjacent nodes for each node
			Map<Point, List<Point>> adjacency = new HashMap<>();

			// Info for a Point
			double X,Y;
			int id;
			Point p, prev_point;

			line = reader.readLine();

			// Put the first point in the adj map
			X = Double.parseDouble(line.split(",")[0]);
			Y = Double.parseDouble(line.split(",")[1]);
			id = Integer.parseInt(line.split(",")[2]);
			prev_point = new Point(X,Y,id);

			adjacency.put(prev_point, new ArrayList<Point>());

			//Read the new Point
			line = reader.readLine();
			while (line != null) {

				//Convert the values of the new point
				X = Double.parseDouble(line.split(",")[0]);
				Y = Double.parseDouble(line.split(",")[1]);
				id = Integer.parseInt(line.split(",")[2]);
				p = new Point(X,Y,id);

				//If the new point is from a different street
				if ( id != prev_point.id ) {
					adjacency.put(p, adjacency.getOrDefault(p, new ArrayList<Point>()));
				} 
				// Else add this point as adjacent to the previous one
				else {
					// Add current node to adj nodes of prev
					List<Point> next_nodes = adjacency.get(prev_point);
					next_nodes.add(p);
					adjacency.put(prev_point, next_nodes);

					// Add prev to adj nodes of current
					next_nodes = adjacency.getOrDefault(p, new ArrayList<Point>());
					next_nodes.add(prev_point);
					adjacency.put(p, next_nodes);
				}

				//Make current point -> previous 
				prev_point = p;
				line = reader.readLine();
			}

			return adjacency;

		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	//
	public Map<Point, List<Point>> addPointToMap(Map<Point, List<Point>> PrevMap, Point point)
	{
		// Insert given point in the map. Find the road where it belongs and change the nodes
		if (PrevMap.containsKey(point)) { return PrevMap; }

		Set<Point> seen = new HashSet<>();
		List<Point> next_nodes = new ArrayList<>();
		Point curr = (new ArrayList<Point>(PrevMap.keySet())).get(0);
		double min_dist = 100000000, curr_dist;
		Point p1 = new Point(0,0,1), p2 = new Point(0,0,1);

		next_nodes.add(curr);
		
		// For each node check if wanted node is between current and its neighbours
		while(!next_nodes.isEmpty()){
			curr = next_nodes.remove(0);

			// Skip node if already checked
			if(seen.contains(curr)) { continue; }

			// Check the neighbouts
			for(Point p : PrevMap.get(curr)){

				if(seen.contains(p)) { continue; }

				//Check if wanted point is between the vector
				boolean b1, b2, b3;
				//b1 = (point.Y - curr.Y)*(p.X - curr.X) == (p.Y - curr.Y)*(point.X - curr.X);
				b2 = (point.X >= Math.min(curr.X, p.X)) && (point.X <= Math.max(curr.X, p.X));
				b1 = (point.Y >= Math.min(curr.Y, p.Y)) && (point.Y <= Math.max(curr.Y, p.Y));

				// Find the distance of given point from line between the two points
				double lambda = (curr.Y - p.Y) / (curr.X - p.X);
				double x = ( Math.pow(lambda,2)*curr.X + point.X + point.Y - curr.Y ) / ( 1 + Math.pow(lambda,2));
				double y = curr.Y + lambda * (x - curr.X);
				curr_dist = Math.sqrt( Math.pow(point.X - x, 2) + Math.pow(point.Y - y, 2) );

				b3 = curr_dist < min_dist;

				if(b1 && b2 && b3){
					p1 = curr;
					p2 = p;
					min_dist = curr_dist;
				}

			}
			seen.add(curr);
			next_nodes.addAll(PrevMap.get(curr));
		}
	
		if (!p1.equals(new Point(0,0,1))) {
			// Add the wanted node and the two current nodes as its neighbours
			List<Point> Nodes = new ArrayList<>();
			Nodes.add(p1);
			Nodes.add(p2);
			PrevMap.put(point, Nodes);

			// fix the adj nodes for the two others
			Nodes = PrevMap.get(p1);
			Nodes.remove(p2);
			Nodes.add(point);
			PrevMap.put(p1, Nodes);

			Nodes = PrevMap.get(p2);
			Nodes.remove(p1);
			Nodes.add(point);
			PrevMap.put(p2, Nodes);
		}
		else {
			// Else just put the point to the closest one from the other points
			min_dist = 100000000;
			Point closest_point = new Point(0,0,1);

			List<Point> points = new ArrayList<Point>(PrevMap.keySet());
			
			for ( Point p : points ) {
				if (p.dist(point) < min_dist) { 
					closest_point = p;
					min_dist = p.dist(point);
				}
			}

			// Put the wanted point to the map with one point as neighbour
			List<Point> Nodes = new ArrayList<>();
			Nodes.add(closest_point);
			PrevMap.put(point, Nodes);

			// Add the wanted point as neighbour to the closest point
			Nodes = PrevMap.get(closest_point);
			Nodes.add(point);
			PrevMap.put(closest_point, Nodes);
		}

		return PrevMap;
	}
	 	
}


