package dev.flash.imagetesting;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Flash on 09/04/2018.
 */

public class PixelManager {

	public ArrayList<Pixel> all;
	public ArrayList<Pixel> toRemove;

	public Handler handler;

	public PixelManager(Handler handler) {
		this.handler = handler;
		all = new ArrayList<>();
		toRemove = new ArrayList<>();
	}

	public boolean add() {
		int x = new Random().nextInt(handler.getWorldWidth() / 32);
		int y = new Random().nextInt(handler.getWorldHeight() / 32);
		for(Pixel p : all) {
			if(p.getX() == x && p.getY() == y) {
				toRemove.add(p);
			}
		}
		all.add(new Pixel(x, y, new Color(new Random().nextInt(50) + 50, new Random().nextInt(50) + 50, new Random().nextInt(50) + 150)));
		all.removeAll(toRemove);
		toRemove.clear();
		return true;
	}

	public boolean add(int x, int y) {
		for(Pixel p : all) {
			if(p.getX() == x && p.getY() == y) {
				toRemove.add(p);
			}
		}
		all.add(new Pixel(x, y, new Color(new Random().nextInt(50) + 50, new Random().nextInt(50) + 150, new Random().nextInt(50) + 150)));

		all.removeAll(toRemove);
		toRemove.clear();
		return true;
	}


	public boolean add(int x, int y, int color) {
		for(Pixel p : all) {
			if(p.getX() == x && p.getY() == y) {
				toRemove.add(p);
				System.out.println("medical");
			}
		}
		switch(color) {
			case (0):
				all.add(new Pixel(x, y, new Color(new Random().nextInt(50) + 50, new Random().nextInt(50) + 150, new Random().nextInt(50) + 150)));
				break;
			case (1):
				all.add(new Pixel(x, y, new Color(new Random().nextInt(50) + 150, new Random().nextInt(50) + 50, new Random().nextInt(50) + 150)));
				break;
			case (2):
				all.add(new Pixel(x, y, new Color(new Random().nextInt(50) + 150, new Random().nextInt(50) + 150, new Random().nextInt(50) + 50)));
				break;
			case (3):
				all.add(new Pixel(x, y, new Color(new Random().nextInt(50) + 150, new Random().nextInt(50) + 50, new Random().nextInt(50) + 50)));
				break;
			case (4):
				all.add(new Pixel(x, y, new Color(new Random().nextInt(50) + 50, new Random().nextInt(50) + 150, new Random().nextInt(50) + 50)));
				break;
			case (5):
				all.add(new Pixel(x, y, new Color(new Random().nextInt(50) + 50, new Random().nextInt(50) + 50, new Random().nextInt(50) + 150)));
				break;
			case (6):
				all.add(new Pixel(x, y, new Color(new Random().nextInt(10) + 150, new Random().nextInt(10) + 150, new Random().nextInt(10) + 150)));
				break;

			default:
				break;
		}
		all.removeAll(toRemove);
		toRemove.clear();
		return true;
	}


	public void tick(double delta) {
		for(Pixel pixel : all) {
			pixel.fade();
			if(pixel.isDead()) {
				toRemove.add(pixel);
			}
		}
	}

	public void render(Graphics g) {
		for(Pixel p : all) {
			p.render(g);
		}
	}
}
