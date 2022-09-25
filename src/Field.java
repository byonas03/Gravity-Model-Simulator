import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Field extends JFrame {
	static Field field = new Field();
	static ArrayList<City> cities;	
	static int stop = 0;
	static boolean clicked = false;
	static int redq = -1;
	static int queue = 0;
	static int xo = 0, yo = 0, xt = 0, yt = 0, first = 0, second = 0;
	static int ggs = 0;
	static double scalex;
	static double scaley;
	static double width, height;
	static int clearbefore = 0;
	static int gs = 0;
	static JTextField label;
	public Field() {
		label = new JTextField("");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		width = screenSize.getWidth() / 1366;
		height = screenSize.getHeight() / 768;
		setTitle("Gravity Model Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		label.setEditable(false);
		try {
			setContentPane(new JLabel(resizeImageIcon(new ImageIcon(ImageIO.read(new File("worldmap2.png"))), (int)(1433 * width), (int)(930 * height))));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pack();
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setVisible(true);
		cities = new ArrayList<City>();
		cities.add(new City("Tokyo", 1208 , 347, 37435000)); // 0
		cities.add(new City("Dehli", 971, 374, 29399000)); // 1
		cities.add(new City("Shanghai", 1136, 367, 26317000)); // 2
		cities.add(new City("Sao Paulo", 500, 553, 21317000)); // 3
		cities.add(new City("Mexico City", 295, 406, 21671000)); // 4
		cities.add(new City("Cairo", 803, 370, 20484965)); // 5
		cities.add(new City("Dhaka", 1026, 389, 20283552)); // 6 
		cities.add(new City("Mumbai", 958, 407, 20185064)); // 7
		cities.add(new City("Beijing", 1112, 325, 20035455)); // 8
		cities.add(new City("Osaka", 1196, 351, 19222665)); // 9
		cities.add(new City("Karachi", 936, 388, 15741406)); // 10
		cities.add(new City("Chongqing", 1082, 365, 15354067)); // 11
		cities.add(new City("Buenos Aires", 454, 599, 15057273)); // 12
		cities.add(new City("Istanbul", 786, 324, 14967667)); // 13
		cities.add(new City("Kolkata", 1009, 397, 14755186)); // 14
		cities.add(new City("Lagos", 699, 449, 13903620)); // 15
		cities.add(new City("Manila", 1138, 424, 13698889)); // 16
		cities.add(new City("Tianjin", 1127, 332, 13396402)); // 17
		cities.add(new City("Rio De Janeiro", 511, 550, 13374275)); // 18
		cities.add(new City("Guangzou", 1116, 388, 12967862)); // 19
		cities.add(new City("Moscow", 847, 247, 12476171)); // 20
		cities.add(new City("Lahore", 961, 359, 12188196)); // 21
		cities.add(new City("Bangladore", 973, 426, 11882666)); // 22
		cities.add(new City("Paris", 692, 287, 10958187)); // 23
		cities.add(new City("Bogota", 393, 455, 10779376)); // 24
		cities.add(new City("Chennai", 983, 427, 10771243)); // 25
		cities.add(new City("Jakarta", 1083, 493, 10638089)); // 26
		cities.add(new City("Lima", 383, 514, 10554712)); // 27
		cities.add(new City("Bangkok", 1061, 424, 10350204)); // 28
		cities.add(new City("New York City", 394, 327, 8634000)); // 29
		cities.add(new City("London", 678, 270, 8136000)); // 30
		cities.add(new City("Los Angeles", 230, 353, 4000000)); // 31
		cities.add(new City("Houston", 312, 372, 2313000)); // 32
		cities.add(new City("Sydney", 1248, 593, 4627000)); // 33
	}
	public static void main(String args[]) {
		field.repaint();
		Timer ti = new Timer();
		ti.schedule(new TimerTask() {
		    @Override
		    public void run() {
		  	    	field.addMouseListener(new MouseAdapter() { 
					@Override
					public void mouseClicked(java.awt.event.MouseEvent arg0) {}	
					@Override
					public void mouseEntered(java.awt.event.MouseEvent arg0) {}
					@Override
					public void mouseExited(java.awt.event.MouseEvent arg0) {}
					@Override
					public void mousePressed(java.awt.event.MouseEvent arg0) { clicked = true; }
					@Override
					public void mouseReleased(java.awt.event.MouseEvent arg0) { clicked = false; }
			    }); 
		    	if (clearbefore == 1) {
		    		xo = yo = xt = yt = 0;
	    			queue = 0;
	    			redq = -1;
	    			clearbefore = 0;
	    			field.add(label);
	    			label.setText(cities.get(first).getName() + "-" + cities.get(second).getName() + ": " + gs);
	    			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		    	}
		    	
		    	int ct = 0;
		    	for (int i = 0; i < cities.size(); i++) 
		    		if (clicked && Math.abs(MouseInfo.getPointerInfo().getLocation().getX() - cities.get(i).getX()) < 8 && Math.abs(MouseInfo.getPointerInfo().getLocation().getY() - cities.get(i).getY()) < 8) {
		    			if (redq == -1) {xo = cities.get(i).getX(); yo = cities.get(i).getY(); second = 0; first = i;} 
		    			if (redq != i) queue++; 
		    			if (queue > 1) {xt = cities.get(i).getNearestX(xo); yt = cities.get(i).getY(); second = i;}
		    			redq = i;
		    			ct = 1;
		    		} else if (Math.abs(MouseInfo.getPointerInfo().getLocation().getX() - cities.get(i).getX()) < 8 && Math.abs(MouseInfo.getPointerInfo().getLocation().getY() - cities.get(i).getY()) < 8) {
		    			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		    			label.setBackground(new Color(0, 0, 0, 0));
		    			field.add(label);
			    		label.setBounds((int)(screenSize.getWidth() / 2 - (150)), (int)(screenSize.getHeight()  * .8) ,300,30);
			    		label.setHorizontalAlignment(JLabel.CENTER);
			    		if (i != second)
			    		label.setText(cities.get(i).getName());
			    		label.setFont(new Font("Arial", Font.PLAIN, 20));      
			    		label.setOpaque(false);
			    		label.setBackground(null);
			    		label.setBorder(null);
			    	}
		    	if (clicked && ct == 0) {
		    		xo = yo = xt = yt = 0;
	    			queue = 0;
	    			redq = -1;
	    			field.remove(label);
	    			}
		    	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		    	label.setBackground(new Color(0, 0, 0, 0));
    			field.add(label);
	    		label.setBounds((int)(screenSize.getWidth() / 2 - (150)), (int)(screenSize.getHeight()  * .8) ,300,30);
	    		label.setHorizontalAlignment(JLabel.CENTER);
	    		label.setFont(new Font("Arial", Font.PLAIN, 20));      
	    		label.setOpaque(false);
	    		label.setBackground(null);
	    		label.setBorder(null);
		    	field.repaint();
		    	field.add(label);
		    	label.repaint();
		    }
		}, 0, 50);
	}
	public void paint(Graphics g) {
		if (stop == 0) {
		super.paintComponents(g);   
		stop++;
		g = (Graphics2D) g;
		for (int i = 0; i < 255 * (width * 4) + 2; i++) {	
			g.setColor(new Color((int)(i / (width * 4)),0,(int)(255 - (i / (width * 4)))));
			g.drawLine((int)((179 + (i * (1/width))) * width), (int)(701 * height),(int)((179 + (i * (1/width))) * width),(int)(729 * height));
			g.setColor(Color.BLACK);
			g.drawRect((int)(178 * width), (int)(700 * height), (int)(width * 1022), (int)(height * 30));
			}
		}
		g.setColor(Color.DARK_GRAY);
		for (int i = 0; i < cities.size(); i++) 
			if (i != redq)
			g.fillOval(cities.get(i).getX(), cities.get(i).getY(), 8, 8);
		g.setColor(Color.RED);
		if (redq != -1) {
			g.fillOval(cities.get(redq).getX(), cities.get(redq).getY(), 8, 8);
		}
		if (queue > 1) {
			gs = (int)(255 * ((cities.get(first).getPopulation() * cities.get(second).getPopulation()) / 1000000000 / 23472.62583603954) / Math.sqrt(Math.pow(Math.abs(yo - yt), 2) + Math.pow(Math.abs(xo - xt), 2)));
			gs *= 2.5;
			if (gs > 255) 
				gs = 255;
			ggs = gs;
			if (gs > 255)
			g.setColor(new Color(255,0,0));
			else 
			g.setColor(new Color(gs,0, 255 - gs));
			((Graphics2D) g).setStroke(new BasicStroke(5));
			g.drawLine(xo + 4, yo + 4, xt + 4, yt + 4);
			for (int i = 0; i < 255 * (width * 4) + 2; i++) {	
				((Graphics2D) g).setStroke(new BasicStroke(1));
				g.setColor(new Color((int)(i / (width * 4)),0,(int)(255 - (i / (width * 4)))));
				g.drawLine((int)((179 + (i * (1/width))) * width), (int)(701 * height),(int)((179 + (i * (1/width))) * width),(int)(729 * height));
				g.setColor(Color.BLACK);
				g.drawRect((int)(178 * width), (int)(700 * height), (int)(width * 1022), (int)(height * 30));
				}
			((Graphics2D) g).setStroke(new BasicStroke(5));
			g.setColor(Color.BLACK);
			g.fillRect((int)((179 + (ggs * 4)) * width),(int)(701 * height),(int)(3 * width),(int)(30 * height));
			if (xo > Toolkit.getDefaultToolkit().getScreenSize().getWidth() || xt > Toolkit.getDefaultToolkit().getScreenSize().getWidth() || xo < 0 || xt < 0) {
				g.setColor(new Color(gs,0, 255 - gs));
				g.drawLine((int)((xo + 4 + (1366 * width))), (int)(yo + 4), (int)((xt + 4 + (width * 1366))), (int)(yt + 4));
				g.drawLine((int)((xo + 4 - (1366 * width))), (int)(yo + 4), (int)((xt + 4 - (width * 1366))), (int)(yt + 4));
			}
			try {
				TimeUnit.MILLISECONDS.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			xo = yo = xt = yt = 0;
			queue = 0;
			redq = -1;
			clearbefore = 1;
			field.remove(label);
		}
	}
	public static ImageIcon resizeImageIcon( ImageIcon imageIcon , Integer width , Integer height )
	{
	    BufferedImage bufferedImage = new BufferedImage( width , height , BufferedImage.TRANSLUCENT );

	    Graphics2D graphics2D = bufferedImage.createGraphics();
	    graphics2D.drawImage( imageIcon.getImage() , 0 , 0 , width , height , null );
	    graphics2D.dispose();

	    return new ImageIcon( bufferedImage , imageIcon.getDescription() );
	}
}
