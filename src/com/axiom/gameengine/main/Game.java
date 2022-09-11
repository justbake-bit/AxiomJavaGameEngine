package com.axiom.gameengine.main;

public class Game implements Runnable{
	
	private Thread thread;
	private boolean running = false;

	public Game() {
		// TODO Auto-generated constructor stub
	}

	public void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (running) {
			update();
			render();
		}
	}

	private void update() {

	}

	private void render() {

	}
	
	public void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
