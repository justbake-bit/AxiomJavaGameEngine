package com.axiom.gameengine.main;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.*;

import com.axiom.gameengine.main.Scene.*;

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;

public class Renderer implements Runnable {

	private Thread thread;
	private long WindowID;

	public Renderer() {
		// TODO Auto-generated constructor stub
	}

	public void init() {
		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if (!glfwInit())
			throw new IllegalStateException("Could not initialize GLFW");

		// Create the window
		WindowID = glfwCreateWindow(1920, 1080, "Test", NULL, NULL);

		if (WindowID == NULL)
			throw new RuntimeException("Coudl not create GLFW window");
		
		glfwSwapInterval(0);
		// Make the OpenGL context current
		glfwMakeContextCurrent(WindowID);
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
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		createCapabilities();
		
		//the last time of the glfw timer
		double lastTime = glfwGetTime();
		int nbFrames = 0;
		

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (!glfwWindowShouldClose(WindowID)) {
			//measure speed
			double currentTime = glfwGetTime();
			nbFrames ++;
			// if the last print was more then 1 second ago
			if(currentTime - lastTime >= 1.0) {
				System.out.println("FPS:" + nbFrames);
				nbFrames = 0;
				lastTime += currentTime - lastTime;
			}
			
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the frame buffer

			Game.scene.draw();

			glfwSwapBuffers(WindowID); // swap the color buffers
			
			
			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
		glfwTerminate();
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

}
