import java.awt.Point;
import java.io.FileWriter;


public class Frage {
	public String frage;
	public String antwort;
	Point p;
	static FileWriter f;

	
	public Frage (String frage, String antwort, int x, int y){
		this.frage = frage;
		this.antwort = antwort;
		this.p = new Point(x, y);
		this.print();
	}

	public void print(){
		System.out.println("Frage: " + this.frage);
		System.out.println("Antwort: " + this.antwort);
		System.out.println("Punkt: " + this.p);
	}
}
