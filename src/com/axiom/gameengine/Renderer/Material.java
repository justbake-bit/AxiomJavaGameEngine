package com.axiom.gameengine.Renderer;

public class Material {

	public Shader shader;
	
	public Material() {
		shader = new GLShader();
	}

	public void Use() {
		// TODO Auto-generated method stub
		shader.use();
	}
	
}
