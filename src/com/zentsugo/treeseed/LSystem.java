package com.zentsugo.treeseed;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Random;
import java.util.Stack;

import org.eclipse.jdt.annotation.NonNull;

public class LSystem {
	
	private StringBuffer generation;
	private String variables, axiom;
	private Rule[] rules;
	
	/**
	 * Initialize an L-System with the given parameters.
	 * Example of constructor : LSystem("BC", "B", myrules)
	 * @param variables a string representing the variables sticked to each other
	 * @param axiom a string representing the axiom so the starter of the l-system
	 * @param r variable number of rules for the l-system
	 */
	public LSystem(String variables, String axiom, Rule... r) {
		this.axiom = axiom;
		this.variables = variables;
		this.rules = r;
		generation = new StringBuffer(axiom);
	}
	
	private float length = -20;
	private Stack<AffineTransform> path = new Stack<AffineTransform>();
	public void draw(Graphics2D g) {
		g.translate(TreeSeed.ORIGIN.x / 2, TreeSeed.ORIGIN.y);
		for (int i = 0; i < generation.length(); i++) {
			char c = generation.charAt(i);
			switch (c) {
				case 'F' :
					g.drawLine(0, 0, 0, (int) length);
					g.translate(0, length);
					g.setColor(new Color(0.8f, 0.8f, 0.1f, 0.3f));
					g.fillOval(0, 1, 2, 3);
					g.setColor(new Color(0.7f, 0.3f, 0, 0.5f));
					break;
				case '+' :
					g.rotate(Math.toRadians(25));
					break;
				case '-' :
					g.rotate(-Math.toRadians(25));
					break;
				case '[' :
					path.push(g.getTransform());
					break;
				case ']' :
					g.setTransform(path.pop());
					break;
			}
		}
		path.clear();
	}
	
	public static Rule generateRule(int length) {
		StringBuffer sub = new StringBuffer(length);
		String[] variables = {"F", "+", "-", "[", "]"};
		sub.append("F"); //forward at least once
		Random r = new Random();
		int v_length = length;
		int rdm;
		for (int i = 0; i < v_length; i++) {
			rdm = r.nextInt(variables.length);
			if (variables[rdm] == "]") {
				int l = sub.lastIndexOf("[");
				int l2 = sub.lastIndexOf("]");
				if (l2 != -1) {
					if (l < l2) {
						++v_length;
						continue;
					}
				} else if (l == -1) {
					++v_length;
					continue;
				}
			}
			sub.append(variables[rdm]);
		}
		return new Rule('F', sub.toString());
	}
	
	public String getGeneration() {
		return generation.toString();
	}
	
	public void run() {
		length *= 0.7;
		StringBuffer generated = new StringBuffer();
		for (int i = 0; i < generation.length(); i++) {
			char c = generation.charAt(i);
			boolean apply = false;
			for (Rule r : rules) {
				if (c == r.id) {
					generated.append(r.sub);
					apply = true;
					//The L-System says that not more than one rule can be true/be applied on
					break;
				}
			}
			if (!apply)
				generated.append(c);
		}
		
		this.generation = generated;
		
		System.out.println("Generation : " + generation.toString());
	}
	
	static class Rule {
		public final char id;
		public final String sub;
		/**
		 * Creates a rule where the id gets replaced by the sub.
		 * This goes for : id -> substitute
		 * @param id identifier, i.e "B"
		 * @param sub substitute, i.e "BCC"
		 */
		@NonNull
		public Rule(char id, String sub) {
			this.id = id;
			this.sub = sub;
		}
	}
}

