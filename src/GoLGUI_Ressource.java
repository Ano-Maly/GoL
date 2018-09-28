

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

