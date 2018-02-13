package com.moss.gameengine.renderingexample;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class RenderGL {
	
	public static void render(Renderable r, int pId) {
		GL20.glUseProgram(pId);
		GL30.glBindVertexArray(r.getVaoId());
		GL20.glEnableVertexAttribArray(0);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, r.getIboId());
				GL11.glDrawElements
					(GL11.GL_TRIANGLES, 
					r.getIndexLength(), 
					GL11.GL_UNSIGNED_BYTE, 
					0);
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		GL20.glUseProgram(0);
	}
	
	public static Renderable createRenderable(float[] vertData, byte[] indexData) {
		int vaoId = createVaoId();
		int vboId = createBufferId();
		int iboId = createBufferId();
		FloatBuffer vboData = createFlippedFloatBuffer(vertData);
		ByteBuffer iboData = createFlippedByteBuffer(indexData);
		
		GL30.glBindVertexArray(vaoId);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
				GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vboData, GL15.GL_STATIC_DRAW);
				GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL30.glBindVertexArray(0);
		
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, iboId);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, iboData, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
		
		return new Renderable(vaoId, vboId, iboId, indexData.length);
	}
	
	public static void deleteBuffers(Renderable r) {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(r.getVboId());
        
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL15.glDeleteBuffers(r.getIboId());
        
        GL30.glBindVertexArray(0);
        GL30.glDeleteVertexArrays(r.getVaoId());
	}
	
	public static int createVaoId() {
		return GL30.glGenVertexArrays();
	}
	
	public static int createBufferId() {
		return GL15.glGenBuffers();
	}

	public static FloatBuffer createFlippedFloatBuffer(float[] dataArray) {
		if(dataArray.length > 0) {
			FloatBuffer buffer = BufferUtils.createFloatBuffer(dataArray.length);
			buffer.put(dataArray);
			buffer.flip();
			return buffer;
		} else {
			System.err.println("Flipped Float Buffer creation failed.");
			System.err.println("-empty float array");
			return null;
		}
	}
	
	public static ByteBuffer createFlippedByteBuffer(byte[] dataArray) {
		if(dataArray.length > 0) {
			ByteBuffer buffer = BufferUtils.createByteBuffer(dataArray.length);
			buffer.put(dataArray);
			buffer.flip();
			return buffer;
		} else {
			System.err.println("Flipped Byte Buffer creation failed.");
			System.err.println("-empty byte array");
			return null;
		}
	}
	
	public static int createShaderProgram() {
		int pId = GL20.glCreateProgram();
	    int vsId = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
	    
	    GL20.glShaderSource(vsId, toByteBuffer
	    		("#version 150 core\n" +
	        "in vec2 vVertex;\n" +
	        "void main() {\n" +
	        "  gl_Position = vec4(vVertex.x, vVertex.y, 0.0, 1.0);\n" +
	        "}"));
	    GL20.glCompileShader(vsId);
	    
	    int fsId = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	    
	    GL20.glShaderSource(fsId, toByteBuffer
	    		("#version 150 core\n" +
	        "out vec4 color;\n" +
	        "void main() {\n" +
	        "  color = vec4(1.0);\n" +
	        "}"));
	    GL20.glCompileShader(fsId);
	    GL20.glAttachShader(pId, vsId);
	    GL20.glAttachShader(pId, fsId);
	    GL20.glLinkProgram(pId);
	    
	    return pId;
	}
	
	public static void deleteShaderProgram(int pId) {
		GL20.glUseProgram(0);
        GL20.glDeleteProgram(pId);
	}
	
	public static ByteBuffer toByteBuffer(final String data) {
	    byte[] vertexShaderData = data.getBytes(Charset.forName("ISO-8859-1"));
	    ByteBuffer vertexShader = BufferUtils.createByteBuffer(vertexShaderData.length);
	    vertexShader.put(vertexShaderData);
	    vertexShader.flip();
	    return vertexShader;
	}
	
}
