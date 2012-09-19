import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JApplet;

public class Punktgenau extends JApplet implements MouseListener, ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	  Image bgImage = null;
	  MediaTracker tracker = null;
	  Image grenzen = null;
	  Image karte = null;
	  Label l1 = new Label();
	  Label l2 = new Label();
	  Label l3 = new Label();
	  ArrayList<Frage> fragen = null;
	  boolean newFrage = true;
	  boolean point = false;
	  boolean results = false;
	  boolean player1 = false;
	  boolean player2 = false;
	  Frage currentf = null;
	  Point p1 = null;
	  Point p2 = null;
	  Point temp = null;
	  int radius = 15;
	  Button weiter = new Button("Punktgenau");
	  
	public void init(){
		System.out.println();
		this.setSize(1000, 527);
		fragen = parseCSV();
		Font font = new Font("Arial", Font.PLAIN, 15);
		tracker = new MediaTracker (this);
		grenzen = getImage(getCodeBase(), "grenzen.jpg");
		karte = getImage(getCodeBase(), "karte.jpg");
			//grenzen = getToolkit().getImage("grenzen.jpg");
			//karte = getToolkit().getImage("karte.jpg");
		bgImage = grenzen;
	   tracker.addImage (bgImage, 0);
	addMouseListener(this);
	setLayout(null);
	add(weiter);
	weiter.addActionListener(this);
	weiter.setLocation(20, 20);
	weiter.setSize(200, 50);
	weiter.setFont(new Font("Arial", Font.PLAIN, 20));
	l1.setLocation(20, 75);
	l1.setSize(200, 25);
	l1.setFont(font);
	add(l1);
	l2.setLocation(20, 105);
	l2.setSize(200, 25);
	l2.setForeground(Color.red);
	l2.setFont(font);
	add(l2);
	l3.setLocation(20, 135);
	l3.setSize(200, 25);
	l3.setForeground(Color.blue);
	l3.setFont(font);
	add(l3);
	l2.setVisible(false);
	l3.setVisible(false);
	l1.setBackground(Color.lightGray);
	l1.setVisible(true);
	try {
		tracker.waitForAll();
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//repaint();
	l1.setText("Test");
	}
	
	private ArrayList<Frage> parseCSV() {
		ArrayList<Frage> parse = new ArrayList<Frage> ();
		String trennzeichen = ";";
		try {
			URL source = new URL(getCodeBase(), "frage.csv");
		      BufferedReader br = 
		        new BufferedReader
		          (new InputStreamReader(source.openStream()));
			String readString;
			while ((readString = br.readLine()) != null) {
				String[] line = readString.split(trennzeichen);
				Frage temp = new Frage(line[0], line[1], Integer.parseInt(line[2]), Integer.parseInt(line[3]));
				parse.add(temp);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parse;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (!results) {
			System.out.println(temp = e.getPoint());
			point = true;
			repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (player1 && (temp!=null)){
			p1 = temp;
			player2 = true;
			player1 = false;
			point = false;
			l1.setText("Spieler 2 setzen!");
			temp = null;
		}else if (player2 && (temp!=null)){
			p2 = temp;
			player2 = false;
			results = true;
			point = false;
			l1.setText("Lösung: "+currentf.antwort);
			l2.setVisible(true);
			double xdist = currentf.p.getX() - p1.getX();
			double ydist = currentf.p.getY() - p1.getY();
			double dist = Math.sqrt(xdist*xdist + ydist*ydist);
			System.out.println(dist);
			l2.setText("Entfernung Spieler 1: " + (int) dist);
			l3.setVisible(true);
			xdist = currentf.p.getX() - p2.getX();
			ydist = currentf.p.getY() - p2.getY();
			dist = Math.sqrt(xdist*xdist + ydist*ydist);
			System.out.println(dist);
			l3.setText("Entfernung Spieler 2: " + (int) dist);
			bgImage = karte;
			repaint();
			weiter.setLabel("Weiter");
		}else if (results){
			newFrage = true;
			player2 = false;
			point = false;
			results = false;
			temp = null;
			p1 = null;
			p2 = null;
			l2.setVisible(false);
			l3.setVisible(false);
			bgImage = grenzen;
			weiter.setLabel("Punktgenau");
		}
		System.out.println("----------------");
		System.out.println(player1);
		System.out.println(player2);
		System.out.println(point);
		System.out.println(results);
		System.out.println(newFrage);
		System.out.println("----------------");
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void update( Graphics g) { 
		paint(g); 
	} 

	public void paint(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		if (newFrage){
			if (!fragen.isEmpty()){
				currentf = fragen.get(0);
				fragen.remove(0);
				newFrage = false;
				currentf.print();
				l1.setText("Spieler 1 setzen!");
			}else{
				System.exit(0);
			}
			results = false;
			player1 = true;
		}
		if(bgImage != null) {
			g.drawImage(bgImage, 0, 0, this);
			/*int x = 0, y = 0; 
			while(y < this.getHeight()) { 
				x = 0; 
				while(x< this.getWidth()) { 
					g.drawImage(bgImage, x, y, this); 
					x=x+bgImage.getWidth(null); 
				} 
				y=y+bgImage.getHeight(null); 
			}*/
		} 
		//else {
			//g.clearRect(0, 0, this.getWidth(), this.getHeight()); 
		//}
		if (point){
			//setForeground(Color.red);
			int x = (int) temp.getX();
			int y = (int) temp.getY();
			g.fillOval(x - (radius/2), y - (radius/2), radius, radius);
		}
		if (results){
			g.setColor(Color.red);
			g.fillOval((int) p1.getX() - (radius/2), (int) p1.getY() - (radius/2), radius, radius);
			g.setColor(Color.blue);
			g.fillOval((int) p2.getX() - (radius/2), (int) p2.getY() - (radius/2), radius, radius);
			g.setColor(Color.black);
			g.fillOval((int) currentf.p.getX() - (radius/2), (int) currentf.p.getY() - (radius/2), radius, radius);
		}
	}
} 

