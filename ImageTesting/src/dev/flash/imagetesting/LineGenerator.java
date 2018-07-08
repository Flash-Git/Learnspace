package dev.flash.imagetesting;

import java.awt.*;
import java.awt.geom.Line2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by Flash on 09/04/2018.
 */

public class LineGenerator {

	private Handler handler;

	private int x1, y1, x2, y2, genRate;

	private boolean done;

	private ArrayList<int[]> nodes;//better name?

	private Timer timer;

	private int currentColor;


	public LineGenerator(Handler handler, int x1, int y1, int x2, int y2, int genRate) {
		this.handler = handler;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		done = false;
		nodes = new ArrayList<>();//better name?
		timer = new Timer(genRate);
		generate();
	}

	private void sort() {
		int[] middle = nodes.get(nodes.size() / 2);
		ArrayList<int[]> half1 = new ArrayList<>();
		ArrayList<int[]> half2 = new ArrayList<>();
		boolean half = false;
		for(int[] node : nodes) {
			if(node == middle) {
				half = true;
				continue;
			}
			if(! half) {
				half1.add(node);
			} else {
				half2.add(node);
			}
		}
		Collections.reverse(half1);

		nodes.clear();
		for(int i = half2.size() - 1; i > - 1; i--) {
			nodes.add(half1.get(i));
			nodes.add(half2.get(i));
		}
		nodes.add(middle);

	}

	private boolean generate() {
		currentColor = new Random().nextInt(30);
		//get direction
		int facingX = x2 - x1;
		int facingY = y2 - y1;

		//on same tile
		if(facingX == 0 && facingY == 0) {
			return false;
		}

		//Normalising
		if(facingX != 0) {
			facingX = facingX * World.PIXEL_SIZE / Math.abs(facingX);
		}

		if(facingY != 0) {
			facingY = facingY * World.PIXEL_SIZE / Math.abs(facingY);
		}
		Line2D.Double line = new Line2D.Double(x1 + World.PIXEL_SIZE / 2, y1 + World.PIXEL_SIZE / 2, x2 + World.PIXEL_SIZE / 2, y2 + World.PIXEL_SIZE / 2);
		int x = x1;
		int y = y1;

		int[] start = {x, y};
		nodes.add(start);
		for(int i = 0; i < 100; i++) {
			if(x == x2 && y == y2) {
				sort();
				return true;
			}
			if(line.intersects(x + facingX, y, World.PIXEL_SIZE, World.PIXEL_SIZE)) {
				x = x + facingX;
				int[] point = {x, y};
				nodes.add(point);
				continue;
			} else if(line.intersects(x, y + facingY, World.PIXEL_SIZE, World.PIXEL_SIZE)) {
				y += facingY;
				int[] point = {x, y};
				nodes.add(point);
				continue;
			} else {
				sort();
				return true;
			}
		}
		done = true;
		return false;
	}

	private void next() {
		if(nodes.size() == 0) {
			done = true;
			return;
		}
		handler.instance.getWorld().pixelManager.add(nodes.get(0)[0], nodes.get(0)[1], currentColor);
		nodes.remove(0);
	}

	public void tick(double delta) {
		timer.tick(delta);
		if(timer.isDone()) {
			next();
		}
	}

	public void render(Graphics g) {

	}

	//Getters and Setters

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public boolean isDone() {
		return done;
	}
}
