package pack_1_Collective;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;





//only 1 can be selected
public class GoLGUI_Launcher extends JFrame {
	
	private JTextField tf_numberblocks;
	private JTextField tf_blocksize;
	private JLabel L_numberblocks;
	private JLabel L_blocksize;
	private JRadioButton largebutton;
	private JRadioButton mediumbutton;
	private JRadioButton smallbutton;
	private JRadioButton custombutton;
	private ButtonGroup group; //"groups buttons, so only 1 can be active"
	
	private JButton butt;
	
	private JCheckBox borderbox;
	private JTextField tf_border;
	private JLabel L_border;
	
	private JLabel speedlabel;
	private JRadioButton fastbutton;
	private JRadioButton slowbutton;
	private ButtonGroup group2;
	private int speed = 10;

	private JTextField tf_live;
	
	public static void main(String[] args) {
		GoLGUI_Launcher GUI = new GoLGUI_Launcher();
		GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GUI.setSize(300,220);
		GUI.setVisible(true);
		GUI.setLocationRelativeTo(null);
	}
	
	
	
	//Konstructor
	public GoLGUI_Launcher() {
		super("titles are for faggots");
		setLayout(new FlowLayout());
		
		
//1 create radiobuttons
		tf_numberblocks = new JTextField("10", 3);
		tf_numberblocks.setEditable(false);
		tf_blocksize = new JTextField("30", 3);
		tf_blocksize.setEditable(false);
		L_numberblocks = new JLabel("Number of Blocks");
		L_blocksize = new JLabel("Size per Block");
		add(L_numberblocks);
		add(tf_numberblocks);
		add(L_blocksize);
		add(tf_blocksize);
		
		largebutton = new JRadioButton("Large", true);	//bo means if checked or unchecked
		mediumbutton = new JRadioButton("Medium", false);
		smallbutton = new JRadioButton("Small", false);
		custombutton = new JRadioButton("Custom", false);

		add(largebutton);
		add(mediumbutton);
		add(smallbutton);
		add(custombutton);
	
		//group buttons	
		group = new ButtonGroup();	//by grouping they know, that if one gets checked the others uncheck
		group.add(largebutton);
		group.add(mediumbutton);
		group.add(smallbutton);
		group.add(custombutton);
		
		//add functionality
		largebutton.addItemListener(new RadioHandler("large"));
		mediumbutton.addItemListener(new RadioHandler("medium"));
		smallbutton.addItemListener(new RadioHandler("small"));
		custombutton.addItemListener(new RadioHandler("unlock"));
	
//2. Create Checkbox for Border
		borderbox = new JCheckBox("set Border");
		tf_border = new JTextField("0", 3);
		L_border = new JLabel("enter space size");
		add(borderbox);
		add(L_border);
		add(tf_border);		
		tf_border.setEnabled(false);
		L_border.setEnabled(false);
		
		//add fucntionality
		borderbox.addItemListener(new CheckboxHandler());
		
		
//3. RAdiobutton speeds:
		
		slowbutton = new JRadioButton("Slow", false);	//bo means if checked or unchecked
		fastbutton = new JRadioButton("Fast", true);
		speedlabel = new JLabel("Evolution Speed");
		add(speedlabel);
		add(fastbutton);
		add(slowbutton);
	
		//group buttons	
		group2 = new ButtonGroup();	//by grouping they know, that if one gets checked the others uncheck
		group2.add(fastbutton);
		group2.add(slowbutton);
		
		//add functionality
		slowbutton.addItemListener(new RadioHandlerSpeed());
		fastbutton.addItemListener(new RadioHandlerSpeed());
		
//4. Livepercentage
		JLabel livepercentage = new JLabel("Chance that a cell starts alive: ");
		tf_live = new JTextField("30",3);
		add(livepercentage);
		add(tf_live);
		
		
		
		
		
//5. Create Button
		butt = new JButton("Let's Go");
		add(butt);
		
		//add functionality
		butt.addActionListener(new ButtonHandler());
	}
	//END OF CONSTRUCTOR
	
	
	
	
	
	private class RadioHandler implements ItemListener{
		private String input;
		//constructor
		public RadioHandler(String input) {
			this.input = input;
		}
		@Override
		public void itemStateChanged(ItemEvent event) {
			if(input=="unlock") {	
				tf_numberblocks.setEditable(true);
				tf_blocksize.setEditable(true);
			}
			if(input=="large") {
				tf_numberblocks.setEditable(false);
				tf_blocksize.setEditable(false);
				tf_numberblocks.setText("10");
				tf_blocksize.setText("30");
			}
			if(input=="medium") {
				tf_numberblocks.setEditable(false);
				tf_blocksize.setEditable(false);
				tf_numberblocks.setText("60");
				tf_blocksize.setText("13");
			}
			if(input=="small") {
				tf_numberblocks.setEditable(false);
				tf_blocksize.setEditable(false);
				tf_numberblocks.setText("130");
				tf_blocksize.setText("6");
			}
		}
	}
	
	private class RadioHandlerSpeed implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e2) {
			if(slowbutton.isSelected()){
				speed = 300;
			}
			else {
				speed = 12;
			}
		}
	}
	
	private class ButtonHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e1) {
			
			int blocknumber = Integer.parseInt(tf_numberblocks.getText());
			int blocksize = Integer.parseInt(tf_blocksize.getText());
			int bordersize = Integer.parseInt(tf_border.getText());
			int livepercentage = Integer.parseInt(tf_live.getText());
			
			
			GoLGUI_Ressource window = new GoLGUI_Ressource(blocknumber, bordersize, blocksize, speed, livepercentage);
			
		}
	}
	
	private class CheckboxHandler implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(borderbox.isSelected()) {
				tf_border.setEnabled(true);
				L_border.setEnabled(true);
				tf_border.setText("10");
				validate();
			}
			else {
				tf_border.setEnabled(false);
				L_border.setEnabled(false);
				tf_border.setText("0");
				validate();

			}
		}
		
	}
	
}
