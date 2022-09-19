package com.axiom.gameengine.Renderer;

import java.util.*;

import org.joml.*;

import com.axiom.gameengine.Utils.*;

public abstract class Shader {

	public enum ShaderType {
		Vertex(new String[] { "vertex", "vert" }), Fragment(new String[] { "fragment", "frag" });

		private String[] names;

		private ShaderType(String[] names) {
			this.names = names;
		}

		ShaderType(String string) {
			names = new String[] { string };
		}

		public static ShaderType fromString(String text) {
			for (ShaderType b : ShaderType.values()) {
				for (String name : b.names) {
					if (text.equalsIgnoreCase(name))
						return b;
				}
			}
			return null;
		}
	}

	private static final String TYPE_STRING = "#type";
	private static final String TYPE_REGEX = "(#type)( )+([a-zA-Z]+)";
	
	public Transform transform;

	protected Map<ShaderType, String> shaders;

	protected abstract void compile();

	public abstract void use();

	public void loadShaderFromFile(String path) {
		System.out.print("Loading Shader: " + path);
		loadShader(FileLoader.loadFileAsString(path));
	}

	public void loadShader(String shaderString) {
		String[] splitString = shaderString.split(TYPE_REGEX);

		int index = shaderString.indexOf(TYPE_STRING);
		int eol = shaderString.indexOf("\r\n", index);

		int shaderIndex = 1;
		while (index != -1) {
			String pattern = shaderString.substring(index + TYPE_STRING.length() + 1, eol).trim();

			ShaderType type = ShaderType.fromString(pattern);
			String shader = splitString[shaderIndex];

			addShader(shader, type);

			index = shaderString.indexOf(TYPE_STRING, eol);
			eol = shaderString.indexOf("\r\n", index);
			shaderIndex++;
		}

	}

	public void addShader(String shaderString, ShaderType type) {
		if (shaders.replace(type, shaderString) == null) {
			shaders.put(type, shaderString);
		}
	}

	public void addShaderFromFile(String path, ShaderType type) {
		addShader(FileLoader.loadFileAsString(path), type);
	}

	public boolean isValidShader() {
		return this.hasShaderType(ShaderType.Fragment) && this.hasShaderType(ShaderType.Vertex);
	}
	
	protected boolean isValidShader(String shader) {
		return true;
	}
	
	protected boolean hasShaderType(ShaderType type) {
		return shaders.containsKey(type);
	}

	public abstract void setMatrix4f(String location, Matrix4f matrix);
}
