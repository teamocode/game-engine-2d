package com.joss.asteroiddodger2d.glwrappers;

// Rendering code is written using LWJGL's immediate mode rendering (legacy)

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

public class RenderGL {
	
	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT);
	}
	
	public static void changeColor(float[] colorData) {
		glColor4f(colorData[0], colorData[1], colorData[2], colorData[3]);
	}
	
	public static void renderIMQuad(float[] vertexData) {
		// If the data is the right size the quad is rendered
		if(vertexData.length == 8) {
			glBegin(GL_QUADS);
			glVertex2f(vertexData[0], vertexData[1]);
			glVertex2f(vertexData[2], vertexData[3]);
			glVertex2f(vertexData[4], vertexData[5]);
			glVertex2f(vertexData[6], vertexData[7]);
			glEnd();
		} else {
			System.err.println("RenderGL | renderIMQuad | VertexData length was out of bounds");
		}	
	}
	
	public static void renderIMTri(float[] vertexData) {
		if(vertexData.length == 6) {
			glBegin(GL_TRIANGLES);
			glVertex2f(vertexData[0], vertexData[1]);
			glVertex2f(vertexData[2], vertexData[3]);
			glVertex2f(vertexData[4], vertexData[5]);
			glEnd();
		} else {
			System.err.println("RenderGL | renderIMTri | VertexData length was out of bounds");
		}
	}

}
