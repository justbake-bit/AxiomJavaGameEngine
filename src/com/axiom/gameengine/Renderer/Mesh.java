package com.axiom.gameengine.Renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.*;

import org.lwjgl.*;

public class Mesh {
	
	private VertexLayout vertexLayout;
	public int[] indices;
	
	private int VAO, VBO, EBO;
	
	public Mesh(VertexLayout vertexLayout, int[] indices) {
		this.vertexLayout = vertexLayout;
		this.indices = indices;
	}

	public void setupMesh() {
		VAO = glGenVertexArrays();
		glBindVertexArray(VAO);
		
		//vbo
		VBO = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertexLayout.data, GL_STATIC_DRAW);
		
		//create int buffer of indices
		IntBuffer indiceBuffer = BufferUtils.createIntBuffer(indices.length);
		indiceBuffer.put(indices);
		indiceBuffer.flip();
		
		//ebo
		EBO = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indiceBuffer, GL_STATIC_DRAW);
		//glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		vertexLayout.setAttributeArrays();
		
		glBindVertexArray(0);
	}
	
	public void draw() {
		glActiveTexture(GL_TEXTURE0);
		glBindVertexArray(VAO);
		
		vertexLayout.enableAttributeArrays();
		
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
		
		vertexLayout.disableAttributeArrays();
		
		glBindVertexArray(0);
	}
}
