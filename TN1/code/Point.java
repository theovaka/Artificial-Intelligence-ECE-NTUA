
public class Point {
	double X;
	double Y;
	int id;

	public Point(double Xx, double Yy, int id) {
		this.X = Xx;
		this.Y = Yy;
		this.id = id;
	}
	
	public void printSelf() {
		System.out.println("Point(" + X + "," + Y + "," + id + ")");
	}

	public double dist(Point p1) 
	{
		return Math.sqrt( Math.pow(p1.X - X, 2) + Math.pow(p1.Y - Y, 2));
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Point that = (Point) o;

		return (this.X == that.X) && (this.Y == that.Y);
	}

	public int hashCode() {
		return Double.valueOf(X).hashCode() * 51 + Double.valueOf(Y).hashCode();
	}
}
