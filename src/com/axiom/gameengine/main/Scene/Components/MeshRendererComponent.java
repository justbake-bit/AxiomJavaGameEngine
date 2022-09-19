package com.axiom.gameengine.main.Scene.Components;

import com.axiom.gameengine.Renderer.*;
import com.axiom.gameengine.main.Scene.*;

@RequireComponent(classes = { MeshFilterComponent.class })
public class MeshRendererComponent extends Component {

	MeshFilterComponent meshFilterComponent;
	public Material material;

	public void init() {
		material = new Material();
		meshFilterComponent = this.gameObject.getComponent(MeshFilterComponent.class);
	}

	@Override
	public void draw() {
		material.shader.transform = this.gameObject.transform;
		material.Use();
		if (meshFilterComponent.mesh != null)
			meshFilterComponent.mesh.draw();
	}

}
