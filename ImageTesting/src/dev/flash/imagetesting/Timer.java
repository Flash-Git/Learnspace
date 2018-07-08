package dev.flash.imagetesting;

/**
 * Created by Flash on 09/04/2018.
 */

public class Timer {

	private double delay;
	private double elapsedTime;
	private boolean active;


	public Timer(double delay) {
		this.delay = delay;
		elapsedTime = 0;
		active = true;
	}

	public void tick(double delta) {
		if(active) {
			elapsedTime = delta + elapsedTime;
		}
	}

	public void reset() {
		elapsedTime = 0;
	}

	public boolean isDone() {
		if(active == false) {
			return false;
		}
		if(elapsedTime > delay) {
			elapsedTime = 0;
			return true;
		} else {
			return false;
		}
	}
}
