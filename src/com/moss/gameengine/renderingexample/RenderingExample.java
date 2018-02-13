package com.moss.gameengine.renderingexample;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class RenderingExample {

	public static void main(String[] args) {
		new RenderingExample();
	}
	
	private Renderable triangleA, triangleB;
	private Renderable squareA, squareB, squareC;
	private Renderable[] border;
	
	private int shadProgId;
	
	public RenderingExample() {
		setup();
		loop();
		cleanUp();
	}
	
	private void setup() {
		createWindow();
		shadProgId = RenderGL.createShaderProgram();
		//									 X POS: Y POS: WIDTH: HEIGHT:
		triangleA = createTriangle(triangleA, -0.5f, -0.25f, 0.25f, 0.5f);
		triangleB = createTriangle(triangleB, 0.5f, 0.25f, 0.25f, -0.5f);
		squareA = createSquare(squareA, -0.5f, 0.5f, 0.25f, 0.25f);
		squareB = createSquare(squareB, 0f, 0f, 0.5f, 0.5f);
		squareC = createSquare(squareC, 0.5f, -0.5f, 0.25f, 0.25f);
		border = createDefaultBorder(border);
	}
	
	private void loop() {
		while(!Display.isCloseRequested()) {
			RenderGL.render(triangleA, shadProgId);
			RenderGL.render(triangleB, shadProgId);
			RenderGL.render(squareA, shadProgId);
			RenderGL.render(squareB, shadProgId);
			RenderGL.render(squareC, shadProgId);
			
			for(Renderable r : border) {
				RenderGL.render(r, shadProgId);
			}
			
			Display.update();
			Display.sync(60);
		}
	}
	
	private void cleanUp() {
		RenderGL.deleteBuffers(triangleA);
		RenderGL.deleteBuffers(triangleB);
		RenderGL.deleteBuffers(squareA);
		RenderGL.deleteBuffers(squareB);
		RenderGL.deleteBuffers(squareC);
		
		for(Renderable r : border) {
			RenderGL.deleteBuffers(r);
		}
		
		RenderGL.deleteShaderProgram(shadProgId);  
		Display.destroy();
	}
	
	private void createWindow() {
		try {
			Display.setDisplayMode(new DisplayMode(800, 800));
			//test
			Display.setTitle("LWJGL Rendering Using Vertex Array Objects");
			Display.create(new PixelFormat(), new ContextAttribs(3, 2).withProfileCore(true));
		} catch(LWJGLException e) {
			System.err.println("Display creation failed.");
			e.printStackTrace();
		}
	}
	
	private Renderable createTriangle(Renderable triangle, float x, float y, float base, float height) {
		float[] vertexFloatArray =
			{x - base / 2f, y - height / 2f,
			x, y + height / 2f,
			x + base / 2f, y - height / 2f};
		byte[] indexArray = null;
		
		if(height > y) indexArray = new byte[]{0, 2, 1};
		else indexArray = new byte[] {0, 1, 2};
		
		return RenderGL.createRenderable(vertexFloatArray, indexArray);
	}

	private Renderable createSquare(Renderable square, float x, float y, float width, float height) {
		float[] vertexFloatArray = 
				{x - width / 2f, y + height / 2f,
				x + width / 2f, y + height / 2f,
				x + width / 2f, y - height / 2f,
				x - width / 2f, y - height / 2f,};
		byte[] indexArray = 
				{0, 3, 1,
				1, 3, 2};
		
		return RenderGL.createRenderable(vertexFloatArray, indexArray);
	}
	
	private Renderable[] createDefaultBorder(Renderable[] border) {
		Renderable[] b = new Renderable[28];
		boolean makeSquare = true;
		float iX = -0.875f;
		float iY = 0.875f;
		for(int i = 0; i < 28; i++) {
			if(makeSquare == true) {
				b[i] = createSquare(b[i], iX, iY, 0.15f, 0.15f);
				makeSquare = false;
			} else {
				b[i] = createTriangle(b[i], iX, iY, 0.15f, 0.15f);
				makeSquare = true;
			}
			if(iX <= -0.875 && iY < 0.875) {
				iY += 0.25f;
			}
			if(iY <= -0.875) {
				iX -= 0.25f;
			}
			if(iX >= 0.875) {
				iY -= 0.25f;
			}
			if(iY >= 0.875f) {
				if(iX < 0.875) 
					iX += 0.25f;
			}
		}
		return b;
	}
	
}
