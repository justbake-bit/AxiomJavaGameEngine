package com.axiom.gameengine.Renderer.Window;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.*;

import org.joml.*;
import org.lwjgl.*;
import org.lwjgl.glfw.*;

public class GLFWWindow extends Window {

	public GLFWWindow(Vector2i size) {
		super();
		this.resolution = size;
	}
	
	public GLFWWindow() {
		super();
	}

	@Override
	public void show() {
		glfwShowWindow(id);
	}

	@Override
	public void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();
		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Could not initialize GLFW");

		id = create();

		// Make the OpenGL context current
		glfwMakeContextCurrent(id);
		// disable v sync
		glfwSwapInterval(1);
	}

	@Override
	protected long create() {
		long _id;
		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation

		// Create the window
		_id = glfwCreateWindow(resolution.x, resolution.y, "Test", NULL, NULL);

		if (_id == NULL) {
			glfwTerminate();
			throw new RuntimeException("Coudl not create GLFW window");
		}
		// Setup a key callback. It will be called every time a key is pressed, repeated
		// or released.
		// used to close the window if the escape key is pressed
		glfwSetKeyCallback(_id, (window, key, scancode, action, mods) -> {
			if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// center the window
		IntBuffer pWidth = BufferUtils.createIntBuffer(1); // int*
		IntBuffer pHeight = BufferUtils.createIntBuffer(1); // int*

		// Get the window size passed to glfwCreateWindow
		glfwGetWindowSize(_id, pWidth, pHeight);

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

		// Center the window
		glfwSetWindowPos(_id, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);

		pWidth.clear();
		pHeight.clear();

		monitor = glfwGetPrimaryMonitor();

		return _id;
	}

	@Override
	public boolean shouldClose() {
		return glfwWindowShouldClose(id);
	}

	@Override
	public boolean isFullscreen() {
		return glfwGetWindowMonitor(id) != NULL;
	}

	@Override
	public void setFullscreen(boolean fullscreen) {
		if (isFullscreen() == fullscreen)
			return;

		if (fullscreen) {
			IntBuffer x = BufferUtils.createIntBuffer(1); // int*
			IntBuffer y = BufferUtils.createIntBuffer(1); // int*

			// get position of window
			glfwGetWindowPos(id, x, y);
			position.x = x.get(0);
			position.y = y.get(0);

			x.clear();
			y.clear();

			// get size of window
			glfwGetWindowSize(id, x, y);
			resolution.x = x.get();
			resolution.y = y.get();

			x.clear();
			y.clear();

			// switch to fullscreen
			GLFWVidMode vidmode = glfwGetVideoMode(monitor);
			glfwSetWindowMonitor(id, monitor, 0, 0, vidmode.width(), vidmode.height(), 0);
		} else {
			glfwSetWindowMonitor(id, NULL, position.x, position.y, resolution.x, resolution.y, 0);
		}
	}

	@Override
	public void loop() {
		glfwSwapBuffers(id); // swap the color buffers

		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
	}

	@Override
	public void destroy() {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(id);
		glfwDestroyWindow(id);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

}
