package pack_1_Collective;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import java.awt.Graphics;
import java.awt.Component;

//todo:
// frame. setsize fix

public class GoLGUI_Ressource extends Component implements MouseListener {	
	GameOfLife game;	
	int start_position = 10; // setting the start position in the windowframe to draw

	int spacelength;
	int square_size; // setting the size for the cube size for drawing
	int move_position; // setting how much to move to draw the next cube to x-axis or y-axis-->
	int speed;
	int livepercentage;	//percentage that a cell begins in the living state. 
	
	JFrame frame;
	boolean mouseDown;	//for continous printing
	
//public static void main(String[] args) throws InterruptedException {
//		GoLGUI_Ressource window = new GoLGUI_Ressource(15, 10, 30, 12, 30); 
//	}
	

	//Konstruktor
	GoLGUI_Ressource (int blocknumber, int spacelength, int squaresize, int speed, int livepercentage){
		this.spacelength = spacelength;
		this.square_size = squaresize;
		this.move_position = this.square_size+this.spacelength;
		this.speed = speed;
		this.livepercentage = livepercentage;
		
		GameOfLife game = new GameOfLife(blocknumber, livepercentage);
		
		this.game = game;
		
		frame = new JFrame("Game of Life Game of Death");
		frame.setVisible(true); // setting the window visible
		frame.setSize(game.size*(spacelength+square_size)+2*start_position+25, 75+ game.size*(spacelength+square_size)+2*start_position); // setting the the windowsize ###		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes program on pressing (X) button
		this.addMouseListener(this); // adding the mouseListener method to this ---> this means the jframe object, as
		frame.add(this);
	}
	
	//Overrides paint to clear the window
	public void paint(Graphics first) {		
		first.clearRect(0,0, game.size*(spacelength+square_size)+2*start_position, game.size*(spacelength+square_size)+2*start_position);
	
		for(int x=0; x<this.game.size; x++)
		for(int y=0; y<this.game.size; y++) {
			
			if (this.game.area[y][x] == true) {
				first.fillRect(x*this.move_position + this.start_position, y * this.move_position + this.start_position, this.square_size, this.square_size);
			}
			else {
				first.drawRect(x * this.move_position + this.start_position, y * this.move_position + this.start_position, this.square_size, this.square_size);
			}
		}
	}		
	
	//toggels cell from dead to alive or reverse
	private void toggleCell (MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
	
		if (x > this.start_position && y > this.start_position) {	
			x = (x - this.start_position) / (this.move_position);		//arrayposition
			y = (y - this.start_position) / (this.move_position);
			
			System.out.println("x: " + x+ "y: " + y);			
			this.game.area[y][x] = !(this.game.area[y][x]);
		}
		this.repaint();		
	}
	
	//evolves 1 time, repaints. 
	private void getNextGen() {		
			game.evolve(1);
			this.repaint();
	}
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton()==1) {
			toggleCell(e);			
		}
		
		if (e.getButton() == 3) {
			mouseDown = true;
			if (mouseDown)
				click_Thread();
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {	//equals 
			mouseDown = false;
		}
	}
	
	private void click_Thread() {
		Thread fast = new Thread() {
			public void run() {
				while (mouseDown) {
					getNextGen();
					try {
						Thread.sleep(speed);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		fast.start();
	}

	
	
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
	
	public void mouseClicked(MouseEvent e) {
	}
}



//###############################################################################
class GameOfLife {

	boolean area[][];
	int size;
	int step = 0;
	int livepercentageGOL;
	
//	public static void main(String[] args)  {
//			
//	//	GameOfLife y = new GameOfLife();
//		GameOfLife x = new GameOfLife(20);
//	//x.evolve()
//		x.evolve(10);
//	}
	
		
	GameOfLife(int size, int livepercentage) {
		this.livepercentageGOL = livepercentage;
		this.size = size;
		this.area = new boolean[size][size];
		Random random = new Random();
		
		for(int x=0; x<size ;x++)
			for(int y=0; y<size; y++) {
				
				this.area[x][y] = false;
				if(random.nextInt(100)<livepercentageGOL) {
					this.area[x][y] = true;
				}
			}
	}

	GameOfLife() {
		this.size = 6;
		this.area = new boolean[size][size]; // this empty is going to be filled up
		
		for(int x=0; x<size ;x++)			//fill with false
			for(int y=0; y<size; y++) {
				this.area[x][y] = false;}
	
		this.area[1][1] = true; // setting the certain squares with true, based on the question
		this.area[1][3] = true;
		this.area[2][2] = true;
		this.area[2][3] = true;
		this.area[3][2] = true;
	}
	
	//returns if a cell is alive or dead
	boolean alive(int x, int y) {
		return this.area[x][y];
	}
	
	//counts how many cells are alive overally
	private int lifecount() {
		int lifecount = 0;
		
		for(int x=0; x<size ;x++)			
		for(int y=0; y<size; y++) {
			if(this.area[x][y]==true) {
				lifecount++;
			}
		}
		return lifecount;
	}
	
	
	//counts how many neighbours it has
	private int neighbours(int x, int y) {		//counter returns int 
		int counter = 0;
		
		for (int i = x -1; i <= 1+x; i++)
		for (int j = y -1; j <= 1+y; j++) {		// i & j are -1, 0, 1
				
			int truex = (i+this.size) % this.size;		//to prevent over/underflow
			int truey = (j+this.size) % this.size; 
			
			if(this.area[truex][truey]== true) {
				counter++;
			}
		}
		if(this.alive(x,y)) {
			counter--;					//counts own cell too, so if it is alive we need to substract one
		}
		return counter;		
	}
	
	//modifies and prints the table to the next generation
	public void nextGen() {
		boolean nextGen[][] = new boolean[this.size][this.size];
		
		for(int x=0; x<size ;x++)			
		for(int y=0; y<size; y++) {
			
			if (this.neighbours(x, y) == 3) {	// 3 neighbours always alive
				nextGen[x][y] = true;
			}
			
			else if (this.neighbours(x, y) == 2  && this.alive(x, y)) {
				nextGen[x][y] = true;			//2 neighbours and being alive -> surviving
			}
			
			else {
				nextGen[x][y] = false;
			}
		}
		this.area = nextGen;
		this.step++;
		System.out.println(this);
		System.out.println("Evolution steps: " + this.step);
	}
	
	//calculates the next gen for # times
	public void evolve(int times) {				
		for(int i = 0 ; i< times; i ++) {
			this.nextGen();
			
			if(this.lifecount()==0) {		//abort if all died
				System.out.println("OH GOD EVERYONE IS DEAD");
				//JOptionPane.showMessageDialog(null, String.format("OH GOD EVERYONE IS DEAD"));
				break;
			}
		}
	}
	
	public void evolve(){			//calculates the next gen forever and ever (until everybody dies)
		this.evolve(1);
		if(this.lifecount()!=0) {
			this.evolve();			
		}
	}
	
	public String toString() {
		StringBuffer game = new StringBuffer();
		for (int x = 0; x < this.size; x++) {
			game.append("\n");
			for (int y = 0; y < this.size; y++) {
				if (this.area[x][y] == true) {
					game.append("@");
				} else {
					game.append(".");
				}
			}
		}
		return game.toString();
	}
}

