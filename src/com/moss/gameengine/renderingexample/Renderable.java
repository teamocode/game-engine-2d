package com.moss.gameengine.renderingexample;

public class Renderable {

	private int vaoId = -1;
	private int vboId = -1;
	private int iboId = -1;
	private int indexLength = -1;
	
	public Renderable(int vaoId, int vboId, int iboId, int indexLength) {
		this.vaoId = vaoId;
		this.vboId = vboId;
		this.iboId = iboId;
		this.indexLength = indexLength;
	}

	public int getVaoId() {
		return vaoId;
	}
	
	public int getVboId() {
		return vboId;
	}
	
	public int getIboId() {
		return iboId;
	}

	public int getIndexLength() {
		return indexLength;
	}
	
	public void setVaoId(int vaoId) {
		this.vaoId = vaoId;
	}
	
	public void setVboId(int vboId) {
		this.vboId = vboId;
	}
	
	public void setIboId(int iboId) {
		this.iboId = iboId;
	}
	
	public void setIndexLength(int indexLength) {
		this.indexLength = indexLength;
	}
	
}
