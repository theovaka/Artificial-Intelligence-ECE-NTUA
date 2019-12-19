public interface Solver {
	Node solve(Node initial, int search_widthS);
	int getMaxWidth();
	int getStepsNum();
	//Node solve(Node initial);
}
