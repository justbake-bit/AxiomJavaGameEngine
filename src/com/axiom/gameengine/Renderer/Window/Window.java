package com.axiom.gameengine.Renderer.Window;

import org.joml.*;

public abstract class Window {
	
	Vector2i position;
	Vector2i resolution;
	Vector2i viewportSize;
	boolean updateViewport;
	
	long id;
	long monitor;
	
	public Window() {
		position = new Vector2i();
		resolution = new Vector2i(1920, 1080);
	}
	
	public abstract void init();
	public abstract void loop();
	public abstract void show();
	public abstract void destroy();
	protected abstract long create();
	public abstract boolean shouldClose();
	public abstract boolean isFullscreen();
	public abstract void setFullscreen(boolean fullscreen);
	
	public float getAspectRatio() {
		return resolution.x / resolution.y;
	}

	public float getLeft() {
		return -resolution.x / 2.0f;
	}

	public float getRight() {
		return resolution.x / 2.0f;
	}

	public float getTop() {
		return -resolution.y / 2.0f;
	}

	public float getBottom() {
		return resolution.y / 2.0f;
	}

}
