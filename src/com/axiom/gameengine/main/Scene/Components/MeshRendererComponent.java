package com.axiom.gameengine.main.Scene.Components;

import com.axiom.gameengine.Renderer.*;
import com.axiom.gameengine.main.Scene.*;

public class MeshRendererComponent extends Component {
	
	Mesh mesh;
	
	public MeshRendererComponent(Mesh mesh) {
		this.mesh = mesh;
	}
	
	@Override
	public void draw() {
		mesh.draw();
	}

}
