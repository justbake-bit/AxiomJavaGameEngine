package com.axiom.gameengine.main;

public class Application {
	
	
	//Protected variables
	protected Renderer renderer;
	protected Game game;
	
	public Application() {
		renderer = new Renderer();
		game = new Game();
	}
	
	// starts the main thread and sets running to true
	public void start() {
		renderer.start();
		game.start();
	}
	
	public void stop() {
		renderer.stop();
		game.stop();
	}
	
	
}
