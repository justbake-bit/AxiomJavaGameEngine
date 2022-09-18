package com.axiom.gameengine.Renderer;

import java.lang.Math;

import org.joml.*;

public class Transform {

	public static Vector3f UP = new Vector3f(0.0f, 1.0f, 0.0f);
	public static Vector3f Down = new Vector3f(0.0f, -1.0f, 0.0f);
	public static Vector3f Left = new Vector3f(-1.0f, 0.0f, 0.0f);
	public static Vector3f Right = new Vector3f(1.0f, 1.0f, 0.0f);
	public static Vector3f Forwards = new Vector3f(0.0f, 0.0f, -1.0f);
	public static Vector3f Backwards = new Vector3f(0.0f, 1.0f, 1.0f);


	private Vector3f front;
	private Vector3f up;
	private Vector3f right;
	
	public Vector3f position;
	
	public Vector3f rotation;
	public Vector3f scale;

	public Transform() {
		position = new Vector3f();
		rotation = new Vector3f();
		scale = new Vector3f(1.0f, 1.0f, 1.0f);
	}
	
	public Vector3f front() {
		front = Forwards;
		Quaternionf rot = new Quaternionf();
		rot.rotateXYZ((float)Math.toRadians(rotation.x), (float)Math.toRadians(rotation.y), (float)Math.toRadians(rotation.z));
		front.rotate(rot);
		return front.normalize();
	}
	
	public Vector3f right() {
		right = front.cross(UP).normalize();
		return right;
	}
	
	public Vector3f up() {
		up = right().cross(front());
		return up;
	}

}
