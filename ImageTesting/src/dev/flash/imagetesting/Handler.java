package dev.flash.imagetesting;

/**
 * Created by Flash on 10/03/2018.
 */

public class Handler {
	Instance instance;

	public Handler(Instance instance) {
		this.instance = instance;
	}

	public int getWorldWidth() {
		return instance.getWorld().getWidth();
	}

	public int getWorldHeight() {
		return instance.getWorld().getHeight();
	}

}
