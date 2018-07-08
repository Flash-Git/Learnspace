package dev.flash.imagetesting;

/**
 * Created by Flash on 10/03/2018.
 */

public class Node {

	private int x, y;
	Node parent;
	static Node dest;
	private float weight;

	public Node(int x, int y, Node parent) {
		this.x = x;
		this.y = y;
		this.parent = parent;
	}


	public double calcF() {
		return calcH() + calcG();
	}

	public float calcG() {
		float p;
		int cost;
		if(parent == null) {//only reaches this if there's no path
			return 0;
		}

		p = parent.calcG();
		//p=0;

		if(parent.getX() != x && parent.getY() != y) {//is it diagonal
			cost = 14;
		} else {
			cost = 10;
		}
		return cost * weight + p;
	}

	public double calcH() {//probably not precise
		int dx = dest.getX() - x;
		int dy = dest.getY() - y;
		return Math.sqrt((dx * dx) + (dy * dy)) * 10;
	}

	//GETTERS AND SETTERS

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

}
