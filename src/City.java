import java.awt.Dimension;
import java.awt.Toolkit;

public class City {
	private String name;
	private int xo, y;
	private int xt;
	private int xth;
	private int[] list;
	private long population;
	
	public City(String na, double xc, double yc, int pop) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth() / 1366;
		double height = screenSize.getHeight() / 768;
		xc = (int)(width * xc);
		yc = (int)(height * yc);
		name = na;
		xo = (int)xc;
		y = (int)yc;
		xt = (int)(xc - screenSize.getWidth());
		xth = (int)(xc + screenSize.getWidth());
		list = new int[]{xo, xt, xth};
		population = pop;
	} 
	public long getPopulation() {
		return population;
	}
	public int getX() { 
		return xo;
	}
	public int getNearestX(int compare) {
		int[] differences = new int[]{Math.abs(compare - list[0]), Math.abs(compare - list[1]), Math.abs(compare - list[2])};
		int min = 0;
		for (int i = 1; i < differences.length; i++)
			if (differences[i] < differences[min])
				min = i;
		return list[min];
	}
	public int getFX() {
		return xo;
	}
	public int getSX() {
		return xt;
	}
	public int getTX() {
		return xth;
	}
	public int getY() {
		return y;
	}
	public String getName() {
		return name;
	}
}
