package com.axiom.gameengine.main.Scene;

import java.util.*;

public class Scene {

	private List<GameObject> objects;

	public Scene() {
		objects = new ArrayList<>();
	}

	public void init() {
		for (GameObject gameObject : objects) {
			if (gameObject.isActive())
				gameObject.init();
		}
	}

	public void update() {
		for (GameObject gameObject : objects) {
			if (gameObject.isActive())
				gameObject.update();
		}
		
		for(int i = 0; i < objects.size(); i++) {
			if(objects.get(i).shouldRemove()) objects.remove(i);
		}
	}

	public void fixedUpdate() {
		for (GameObject gameObject : objects) {
			if (gameObject.isActive())
				gameObject.fixedUpdate();
		}
	}

	public void draw() {
		for (GameObject gameObject : objects) {
			if (gameObject.isActive())
				gameObject.draw();
		}
	}

	public GameObject getGameObjectByName(String name) {
		for (GameObject gameObject : objects) {
			if (gameObject.name.equals(name))
				return gameObject;
		}
		return null;
	}

	public List<GameObject> getGameObjectsByName(String name) {
		List<GameObject> gameObjectsMatchingName = new LinkedList<>();
		for (GameObject gameObject : objects) {
			if (gameObject.name.equals(name))
				gameObjectsMatchingName.add(gameObject);
		}
		return gameObjectsMatchingName;
	}

	public void addObject(GameObject object) {
		object.scene = this;
		objects.add(object);
	}

	public void removeObject(GameObject gameObject) {
		objects.remove(gameObject);
	}

}
