import java.io.*;
import java.util.*;

public class Taxi {

	// java Taxi nodes.csv taxis.csv client.csv
	public static void main (String[] args) {
		
		// Arg[1] = Search_width
		String files_directory = args[0];
		if ( !files_directory.substring(files_directory.length() - 1).equals("/") ) { files_directory += "/"; }

		String taxis_file = files_directory + "taxis.csv";
		String client_file = files_directory + "client.csv";
		String nodes_file = files_directory + "nodes.csv";

		InfoExcavator info = new InfoExcavator();
		Solver solver = new AstarSolver();
		Point client = info.getPointFromLine(client_file,1);
		Map<Point, List<Point>> adjMap = info.makeAdjMap(nodes_file);
		int search_width  = Integer.parseInt(args[1]);

		for (int i=1; i<info.getTaxisNum(taxis_file) + 1; i++) {
			//Redirect the output to fhe files
			try {
				PrintStream out = new PrintStream(new FileOutputStream(files_directory + "taxi" + i + ".out"));
				System.setOut(out);		
			} catch (FileNotFoundException e) {
				System.out.println("Cant create the output file");
				return;
			}

			Point taxi = info.getPointFromLine(taxis_file,i);

			adjMap = info.addPointToMap(adjMap, taxi);
			adjMap = info.addPointToMap(adjMap, client);
		
			Node result = solver.solve(new StreetNode(taxi, null, 0, adjMap, client), search_width);
			writePath(result);	
			writeStepsNum(files_directory + "taxi" + i + "_", solver.getStepsNum());
		}

		// Write Max Width file
		writeMaxWidth(files_directory, solver.getMaxWidth());
	}

	private static void writePath (Node node)
	{
		if (node == null) { return; }

		while (node.getPrevious() != null) {
			node.printSelf();
			node = node.getPrevious();
		}
		node.printSelf();
	}

	public static void writeMaxWidth(String files_directory, int max_width)
	{
		try {
			PrintStream out = new PrintStream(new FileOutputStream(files_directory + "max_width"));
			System.setOut(out);		
			System.out.println(max_width);
		} catch (FileNotFoundException e) {
			System.out.println("Cant create the output file");
			return;
		}
	}

	public static void writeStepsNum(String files_directory, int steps_num)
	{
		try {
			PrintStream out = new PrintStream(new FileOutputStream(files_directory + "steps_num"));
			System.setOut(out);		
			System.out.println(steps_num);
		} catch (FileNotFoundException e) {
			System.out.println("Cant create the output file");
			return;
		}
	}

}


