package com.ray3k.stripe.test;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class StripeExamples extends ApplicationAdapter {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1200;
		config.height = 800;
		config.resizable  = false;
		config.samples = 4;
		new LwjglApplication(new StripeExamples(), config);
	}
}