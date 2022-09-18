package com.axiom.gameengine.main;

public class PCApplication extends Application {
	
	//Protected variables
	protected Renderer renderer;
	protected Game game;

	// starts the main thread and sets running to true
	public PCApplication() {
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
