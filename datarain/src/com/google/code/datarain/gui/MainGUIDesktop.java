package com.google.code.datarain.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.google.code.datarain.components.Controller;
import com.google.code.datarain.dataobjets.Input;
import com.google.code.datarain.utils.Constants;

public class MainGUIDesktop implements ActionListener, PropertyChangeListener { 
	JPanel cards; 
	final static String BUTTONPANEL = "Buttons";
	
	String input;
	Input inputObj = new Input();

	JButton inputButton;
	JButton executeButton;
	JFileChooser fc = new JFileChooser();

	JLabel xl = new JLabel("X:");
	JTextField x = new JTextField();
	
	JLabel yl = new JLabel("Y:");
	JTextField y = new JTextField();
	
	JLabel yel = new JLabel("YE:");
	JTextField ye = new JTextField();
	
	JLabel wl = new JLabel("W:");
	JTextField w = new JTextField();
	
	JLabel ytl = new JLabel("YT:");
	JTextField yt = new JTextField();
	
	JLabel ul = new JLabel("U:");
	JTextField u = new JTextField();
	
	

	private Task task;
	private JProgressBar progressBar;
	boolean done = false;
	
	JTextField console;

	public void addComponentToPane(Container pane) {

		progressBar = new JProgressBar(0, 100);
		progressBar.setString("ready.");
		progressBar.setStringPainted(true);

		
		inputButton = new JButton("Input img");
		inputButton.addActionListener(this);
		

		executeButton  = new JButton("Process!");
		executeButton.addActionListener(this);

		JPanel card1 = new JPanel();
		
		card1.add(inputButton);
		
		x.setText("00000");
		y.setText("00000");
		ye.setText("00000");
		w.setText("00000");
		yt.setText("00000");
		u.setText("ml  ");
		
		card1.add(xl);
		card1.add(x);
		card1.add(yl);
		card1.add(y);
		card1.add(yel);
		card1.add(ye);

		card1.add(ytl);
		card1.add(yt);
		card1.add(ul);
		card1.add(u);
		
		
		card1.add(executeButton);
		card1.add(progressBar);
		
		cards = new JPanel(new CardLayout());
		cards.add(card1, BUTTONPANEL);
		
		console = new JTextField();
		console.setBackground(Color.BLACK);
		console.setForeground(Color.GREEN);
		
	    BufferedImage logoTitle = null;
		try {
			logoTitle = ImageIO.read(new File("./res/logo-desk.png"));
			JLabel logoTitleLabel = new JLabel(new ImageIcon( logoTitle ));
		    pane.add(logoTitleLabel, BorderLayout.PAGE_START);
		} catch (IOException e) {
			String msg = "Wrong installation. The executable file must be parallel to resources/ directory. The application will try to run anyway.";
			JOptionPane.showMessageDialog(new JFrame(),msg,"Error",JOptionPane.ERROR_MESSAGE);
		}
	    
		pane.add(cards, BorderLayout.CENTER);
		pane.add(console, BorderLayout.PAGE_END);
		
	}

	public void itemStateChanged(ItemEvent evt) {
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, (String)evt.getItem());
	}

	/**
	 * Create the GUI and show it.  For thread safety,
	 * this method should be invoked from the
	 * event dispatch thread.
	 */
	private static void createAndShowGUI() {
		
		JFrame frame = new JFrame(Constants.APP_NAME+" "+Constants.APP_VERSION);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MainGUIDesktop gui = new MainGUIDesktop();
		gui.addComponentToPane(frame.getContentPane());
		
	    ImageIcon icon = new ImageIcon("./res/icon.gif");
	    frame.setIconImage(icon.getImage());
		
		frame.pack();
		frame.setResizable(false);
		frame.setVisible(true);
		
	}

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		
		UIManager.put("swing.boldMetal", Boolean.FALSE);
		
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		console.setForeground(Color.GREEN);
		if(e.getSource() == inputButton){
			int returnVal = fc.showOpenDialog(cards);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				input = file.getAbsolutePath();
				inputObj.setInputFilePath(input);
				console.setText(input);
			}
		}else if(e.getSource() == executeButton){
			
			int h = Integer.parseInt(ye.getText().trim()) - Integer.parseInt(y.getText().trim());
			
			inputObj.setH(h);
			inputObj.setU(u.getText().trim());
			inputObj.setW(Constants.W);
			
			inputObj.setX(Integer.parseInt(x.getText().trim()));
			inputObj.setY(Integer.parseInt(y.getText().trim()));
			inputObj.setYE(Integer.parseInt(ye.getText().trim()));
			inputObj.setYT(Integer.parseInt(yt.getText().trim()));
			
			executeButton.setEnabled(done);
			
			task = new Task();
			task.addPropertyChangeListener(this);
			task.execute();
		}
	}

	class Task extends SwingWorker<Void, Void> {

		@Override
		public Void doInBackground() {
			
			
			while (!done) {

				
				double ret = Controller.getInstance().process(inputObj);
				console.setText("Result: "+ret+" "+inputObj.getU());
				inputButton.setEnabled(done);
				executeButton.setEnabled(done);
				progressBar.setString("done.");
				done = true;
				
			}
			return null;
		}

		/*
		 * Executed in event dispatching thread
		 */
		@Override
		public void done() {
			Toolkit.getDefaultToolkit().beep();
			done = true;

			inputButton.setEnabled(done);
			executeButton.setEnabled(done);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		if (!done){
			progressBar.setString("running...");
			progressBar.setIndeterminate(true);
		} else {
			progressBar.setIndeterminate(false);
			done = false;
		}
	}

}