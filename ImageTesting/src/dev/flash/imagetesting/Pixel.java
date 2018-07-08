package dev.flash.imagetesting;

import java.awt.*;

/**
 * Created by Flash on 09/04/2018.
 */

public class Pixel {

	private int x, y;

	private Color color;

	private boolean dead;

	public Pixel(int x, int y, Color color) {
		this.x = x;
		this.y = y;
		this.color = color;
		dead = false;
	}

	public void render(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, 1 * World.PIXEL_SIZE, 1 * World.PIXEL_SIZE);
	}

	public void fade() {
		if(dead) {
			return;
		}
		Color tempColor = new Color(color.getRed() - 1, color.getGreen() - 1, color.getBlue() - 1);
		color = tempColor;
		if(color.getRed() == 15 || color.getGreen() == 15 || color.getBlue() == 15) {
			dead = true;
		}
	}


	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Color getColor() {
		return color;
	}

	public boolean isDead() {
		return dead;
	}
}
