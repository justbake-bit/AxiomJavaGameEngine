package com.axiom.gameengine.main.Scene;

import java.util.*;

import com.axiom.gameengine.Renderer.*;
import com.axiom.gameengine.main.Scene.Components.*;

public class GameObject {

	public String name;
	
	public Scene scene;
	public GameObject parent = null;

	public Transform transform;
	
	private boolean _isActive = true;
	private boolean _shouldRemove = false;

	private List<Component> components;
	private List<GameObject> children;

	public GameObject(String name) {
		this.name = name;
		transform = new Transform();
		components = new ArrayList<>();
		children = new ArrayList<>();
	}

	public void init() {
		for (Component component : components) {
			if (component.isActive())
				component.init();
		}

		for (GameObject gameObject : children) {
			if (gameObject.isActive())
				gameObject.init();
		}
	}

	public void update() {
		for (Component component : components) {
			if (component.isActive())
				component.update();
		}

		for (GameObject gameObject : children) {
			if (gameObject.isActive())
				gameObject.update();
		}
		

		for(int i = 0; i < components.size(); i++) {
			if(components.get(i).shouldRemove()) components.remove(i);
		}
		for(int i = 0; i < children.size(); i++) {
			if(children.get(i).shouldRemove()) children.remove(i);
		}
	}

	public void fixedUpdate() {
		for (Component component : components) {
			if (component.isActive())
				component.fixedUpdate();
		}

		for (GameObject gameObject : children) {
			if (gameObject.isActive())
				gameObject.fixedUpdate();
		}
	}

	public void draw() {
		for (Component component : components) {
			if (component.isActive())
				component.draw();
		}

		for (GameObject gameObject : children) {
			if (gameObject.isActive())
				gameObject.draw();
		}
	}
	
	public void onEnable() {
		for (Component component : components) {
			if (component.isActive())
				component.onEnable();
		}
		
		for (GameObject gameObject : children) {
			if (gameObject.isActive())
				gameObject.onEnable();
		}
	}
	
	public void onDisable() {
		for (Component component : components) {
			if (component.isActive())
				component.onDisable();
		}
		
		for (GameObject gameObject : children) {
			if (gameObject.isActive())
				gameObject.onDisable();
		}
	}
	
	public void Destroy() {
		_shouldRemove = true;
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> T getComponent(Class<T> cls) {
		for (Component c : components) {
			if(c.getClass() == cls) {
				return (T)c; 
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> List<T> getComponents(T component) {
		List<T> componenetsOfTypeT = new LinkedList<>();
		for (Component c : components) {
			if (c.getClass().equals(component.getClass()))
				componenetsOfTypeT.add((T) c);
		}
		return componenetsOfTypeT;
	}

	public <T extends Component> Component getComponentInChildren(T component) {
		for (GameObject gameObject : children) {
			for (Component c : gameObject.components) {
				if (c.getClass().equals(component.getClass()))
					return c;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends Component> List<T> getComponentsInChildren(T component) {
		List<T> componenetsOfTypeT = new LinkedList<>();
		for (GameObject gameObject : children) {
			for (Component c : gameObject.components) {
				if (c.getClass().equals(component.getClass()))
					componenetsOfTypeT.add((T) c);
			}
		}
		return componenetsOfTypeT;
	}
	
	public int getChildindex(GameObject child) {
		int i = 0;
		for (GameObject gameObject : children) {
			if (gameObject.equals(child))
				return i;
			i++;
		}
		return -1;
	}
	
	public GameObject getChild(int i) {
		return children.get(i);
	}
	
	public void removeChild(int i) {
		children.remove(i);
	}

	public void addComponent(Component component) {
		if(component.getClass().isAnnotationPresent(RequireComponent.class)) {
			RequireComponent rc = component.getClass().getAnnotation(RequireComponent.class);
			for(Class<? extends Component> cls : rc.classes()) {
				if(getComponent(cls) == null) {
					throw new IllegalStateException("Game object requires component of type: " + cls);
				}
			}
		}
		component.gameObject = this;
		components.add(component);
	}

	public void removeComponent(Component component) {
		components.remove(component);
	}

	public void addChild(GameObject object) {
		object.parent = this;
		children.add(object);
	}

	public void removeChild(GameObject object) {
		children.remove(object);
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
