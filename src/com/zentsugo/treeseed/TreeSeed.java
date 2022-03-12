package com.zentsugo.treeseed;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.zentsugo.treeseed.LSystem.Rule;

public class TreeSeed {
	
	private JFrame frame;
	private MODE mode;
	char[][] map;
	
	public static final int WIDTH, HEIGHT;
	public static final Point ORIGIN;
	
	static {
		WIDTH = 500;
		HEIGHT = 500;
		ORIGIN = new Point(WIDTH / 2, HEIGHT);
	}
	
	enum MODE {
		CONSOLE, FRAME
	}
	
	private LSystem lsystem;
	
	public TreeSeed(MODE mode) {
		if (mode == MODE.FRAME) {
			frame = new JFrame("TreeSeed Generator");
			frame.setPreferredSize(new Dimension(600, 600));
			frame.getContentPane().setLayout(null);
			
			JPanel panel = new JTreePanel();
			panel.setSize(new Dimension(500, 500));
			panel.setLayout(null);
			
			panel.setLocation(10, 10);
			
			JButton button = new JButton("Generate");
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					lsystem.run();
					panel.repaint();
				}
			});
			button.setSize(new Dimension(100, 30));
			button.setLocation(10, 530);
			
			panel.setBackground(Color.BLACK);
			
			frame.add(button);
			frame.add(panel);
			frame.setResizable(false);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		} else {
			map = new char[40][40];
		}
		
		String variables = "F+-[]";
		String axiom = "X";
		//F+[FF-F]--[F-F+FF]+FF
		Rule r = new Rule('X', "F+[[X]-X]-F[-FX]+X), (F → FF");
		Rule r2 = new Rule('F', "FF");
		//to fix LSystem.generateRule(8)
		lsystem = new LSystem(variables, axiom, r, r2);
	}
	
	public void generateFromSeed(int seed) {
		if (seed < 0) {
			System.err.println("Seed must be >= 0.");
			return;
		}
		
		if (mode == MODE.FRAME) {
			turtle(seed);
			System.out.println("Generated : " + lsystem.getGeneration());
		} else {
			
		}
	}
	
	private final int generations = 8;
	
	private void turtle(int seed) {
		for (int i = generations; i > 0; i--) {
			lsystem.run();
		}
	}
	
	public void setMode(MODE mode) {
		this.mode = mode;
	}
	
	public static void main(String[] args) {
		TreeSeed tree = new TreeSeed(MODE.FRAME);
//		tree.generateFromSeed(20);
	}
	
	private class JTreePanel extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (lsystem == null) return;
			
			Graphics2D g2 = (Graphics2D) g;
			
			g2.setColor(new Color(0.7f, 0.3f, 0, 0.5f));
			g2.setStroke(new BasicStroke(1));
			lsystem.draw(g2);
		}
	}
}
