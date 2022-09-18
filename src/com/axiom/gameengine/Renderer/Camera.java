package com.axiom.gameengine.Renderer;

import java.lang.Math;

import org.joml.*;

import com.axiom.gameengine.main.*;

public class Camera {

	public enum CameraMode {
		Projection, Orthographic
	}

	float fov = (float) Math.PI/2f;
	float near = 0.1f, far = 1000.0f;
	Transform transform;
	Transform lastTransform;
	private Matrix4f viewMatrix;
	private Matrix4f projectionMatrix;

	CameraMode mode = CameraMode.Projection;
	
	public Camera() {
		transform = new Transform();
		transform.position.z = -10f;
		viewMatrix = new Matrix4f();
		projectionMatrix = new Matrix4f();
	}

	public Matrix4f getViewMatrix() {
		viewMatrix.identity();
		viewMatrix = viewMatrix.lookAt(transform.position, transform.position.add(transform.front()), transform.up());
		return viewMatrix;
	}

	public Matrix4f getProjectionMatrix() {
		projectionMatrix.identity();
		switch (mode) {
		case Orthographic:
			return projectionMatrix.ortho(Renderer.getWindow().getLeft(), Renderer.getWindow().getRight(), Renderer.getWindow().getBottom(),
					Renderer.getWindow().getTop(), near, far);
		default:
			return projectionMatrix.setPerspective(fov, Renderer.getWindow().getAspectRatio(), near, far);
		}
	}

	public void setCameraMode(CameraMode mode) {
		this.mode = mode;
	}
}
