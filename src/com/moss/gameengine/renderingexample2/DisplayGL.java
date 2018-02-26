package com.moss.gameengine.renderingexample2;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class DisplayGL {
	
	public static void createDisplay(int width, int height, String title) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setTitle("LWJGL Rendering System Example 2");
			Display.create(new PixelFormat(), new ContextAttribs(3, 2).withProfileCore(true).withForwardCompatible(true));
		} catch(LWJGLException e) {
			System.err.println("DisplayGL: Display creation failed.");
			e.printStackTrace();
		}
	}
	
	public static void deleteDisplay() {
		Display.destroy();
	}
	
	public static void updateDisplay() {
		Display.update();
	}
	
	public static void syncDisplay(int fps) {
		Display.sync(60);
	}
	
	public static boolean isCloseReq() {
		return Display.isCloseRequested();
	}

}
