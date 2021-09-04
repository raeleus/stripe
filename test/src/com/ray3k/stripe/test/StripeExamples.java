package com.ray3k.stripe.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class StripeExamples extends ApplicationAdapter {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setWindowedMode(1200, 800);
		config.setResizable(false);
		config.setBackBufferConfig(8, 8, 8, 8, 16, 0, 4);
		new Lwjgl3Application(new StripeExamples(), config);
	}
}