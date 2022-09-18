package com.axiom.gameengine.main;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.joml.*;
import org.lwjgl.*;

import com.axiom.gameengine.Renderer.Window.*;

public class Renderer implements Runnable {


	private Thread thread;
	private static Window window;

	public Renderer() {
		window = new GLFWWindow();
	}
	
	public void init() {
		System.out.println("LWJGL " + Version.getVersion());

		window.init();

		window.show();

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
	}

	public void start() {
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		init();
		loop();
	}

	private void loop() {

		// the last time of the glfw timer
		double lastTime = glfwGetTime();
		int nbFrames = 0;

		Game.scene.init();
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (!window.shouldClose()) {
			// measure speed
			double currentTime = glfwGetTime();
			nbFrames++;
			// if the last print was more then 1 second ago
			if (currentTime - lastTime >= 1.0) {
				//System.out.println("FPS:" + nbFrames);
				nbFrames = 0;
				lastTime += currentTime - lastTime;
			}

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the frame buffer

			Game.scene.draw();
			
			window.loop();
		}
		window.destroy();
		System.exit(0);
	}

	public void stop() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Window getWindow() {
		return window;
	}
}
