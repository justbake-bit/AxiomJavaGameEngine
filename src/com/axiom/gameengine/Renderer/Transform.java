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
		//front.rotate(rotation);
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
	
	public void setPositon(float x, float y, float z) {
		this.position = new Vector3f(x, y, z);
	}
	
	public Matrix4f getTransformationMatrix() {
		Matrix4f matrix = new Matrix4f().identity();
		matrix.translate(position);
		matrix.rotate(Math.toRadians(rotation.x), Math.toRadians(rotation.y));
		return matrix;
	}

}
