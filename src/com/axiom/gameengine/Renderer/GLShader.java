package com.axiom.gameengine.Renderer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;
import java.util.*;

import org.joml.*;
import org.lwjgl.*;

import com.axiom.gameengine.main.Scene.*;

public class GLShader extends Shader {

	private int id;
	private int[] ids;

	public GLShader() {
		ids = new int[6];
		shaders = new HashMap<>();
		loadShaderFromFile("assets/shaders/default.glsl");
	}

	@Override
	public void use() {
		// TODO Auto-generated method stub
		if (id == NULL)
			compile();
		glUseProgram(id);
		setMatrix4f("view", Scene.camera.getViewMatrix());
		setMatrix4f("projection", Scene.camera.getProjectionMatrix());
	}

	@Override
	protected void compile() {
		//compile shader and check for erros
		for (int i = 0; i < ids.length; i++) {
			if(ids[i] != NULL) {
				glCompileShader(ids[i]);
				checkShaderError(ids[i]);
			}
		}

		id = glCreateProgram();
		//attach shaders to program
		for (int i = 0; i < ids.length; i++) {
			if(ids[i] != NULL) {
				glAttachShader(id, ids[i]); 
			}
		}
		
		//link program and check for errors
		glLinkProgram(id);
		checkProgramError();
	}

	private boolean checkShaderError(int sid) {
		int success = glGetShaderi(sid, GL_COMPILE_STATUS);
		if (success == GL_FALSE) {
			System.out.print(" ... failed\n");
			int log_length = glGetShaderi(sid, GL_INFO_LOG_LENGTH);
			System.err.println("ERROR: " + getTypeFromId(sid).name() + " failed to compile for shader id: " + this.id);
			System.err.println(glGetShaderInfoLog(sid, log_length));
			return false;
		}
		return true;
	}

	private boolean checkProgramError() {
		int success = glGetProgrami(id, GL_LINK_STATUS);
		if (success == GL_FALSE) {
			System.out.print(" ... failed\n");
			int log_length = glGetProgrami(id, GL_INFO_LOG_LENGTH);
			System.err.println("ERROR: failed to link shader id: " + id);
			System.err.println(glGetProgramInfoLog(id, log_length));
			return false;
		}
		System.out.print(" ... Success\n");
		return true;
	}

	@Override
	public void addShader(String shaderString, ShaderType type) {
		super.addShader(shaderString, type);

		// generate id for the shader type
		int id = glCreateShader(getGLType(type));
		// pass the source to the GPU
		glShaderSource(id, shaderString);

		ids[getIDIndex(type)] = id;
	}

	private int getGLType(ShaderType type) {
		switch (type) {
		case Vertex:
			return GL_VERTEX_SHADER;
		case Fragment:
			return GL_FRAGMENT_SHADER;
		default:
			return (int) NULL;
		}
	}
	
	private int getIDIndex(ShaderType type) {
		switch (type) {
		case Vertex:
			return 0;
		case Fragment:
			return 1;
		default:
			return (int) NULL;
		}
	}
	
	private ShaderType getTypeFromIndex(int index) {
		switch (index) {
		case 0:
			return ShaderType.Vertex;
		case 1:
			return ShaderType.Fragment;
		default:
			return null;
		}
	}
	
	private ShaderType getTypeFromId(int id) {
		for(int i = 0; i < ids.length; i++) {
			if(ids[i] == id) {
				return getTypeFromIndex(i);
			}
		}
		return null;
	}

	@Override
	public void setMatrix4f(String location, Matrix4f value) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		glUniformMatrix4fv(getUniformLocation(location), false, buffer);
	}
	
	private int getUniformLocation(String location) {
		return glGetUniformLocation(id, location);
	}
}
