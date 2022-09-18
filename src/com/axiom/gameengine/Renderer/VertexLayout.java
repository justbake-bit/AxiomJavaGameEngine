package com.axiom.gameengine.Renderer;

import static org.lwjgl.opengl.GL20.*;

public class VertexLayout {
	
	private final Layout[] layouts;
	private final float[] data;

	public VertexLayout(Layout[] layouts, float[] data) {
		this.layouts = layouts;
		this.data = data;
	}

	public int getLayoutOffset(int layout) {
		int offset = 0;
		for (Layout prevLayout : layouts) {
			offset += prevLayout.getSize();
		}
		return offset;
	}

	public void setAttributeArrays() {
		int offset = 0;
		for (int i = 0; i < layouts.length; i++) {
			glEnableVertexAttribArray(i);
			glVertexAttribPointer(i, layouts[i].getSize(), layouts[i].type(), false, layouts[i].getSizeInBytes(), offset);
			offset += layouts[i].getSize();
		}
		disableAttributeArrays();
	}

	public void enableAttributeArrays() {
		for (int i = 0; i < layouts.length; i++) {
			glEnableVertexAttribArray(i);
		}
	}

	public void disableAttributeArrays() {
		for (int i = 0; i < layouts.length; i++) {
			glDisableVertexAttribArray(i);
		}
	}
	
	public int getSize() {
		int size = 0;
		for(int i = 0; i < layouts.length; i++) {
			size += layouts[i].getSize();
		}
		return size;
	}

	public Layout[] getLayouts() {
		return layouts;
	}

	public float[] getData() {
		return data;
	}
	
	

}
