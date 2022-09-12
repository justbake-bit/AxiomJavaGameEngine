package com.axiom.gameengine.main.Scene;

public abstract class Component {
	
	public GameObject gameObject;
	
	private boolean _isActive = true;
	private boolean _shouldRemove = false;
	
	public void init() {}
	
	public void update() {}

	public void fixedUpdate() {}

	public void draw() {}
	
	public void onEnable() {}
	
	public void onDisable() {}
	
	public void Destroy() {
		_shouldRemove = true;
	}
	
	public boolean isActive() {
		return _isActive;
	}
	
	public void enable() {
		if(_isActive) return;
		_isActive = true;
		onEnable();
	}
	
	public void disable() {
		if(!_isActive) return;
		_isActive = false;
		onDisable();
	}
	
	public boolean shouldRemove() {
		return _shouldRemove;
	}

}
