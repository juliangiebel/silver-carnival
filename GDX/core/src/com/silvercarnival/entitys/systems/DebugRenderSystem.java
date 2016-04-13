/**
 * 
 */
package com.silvercarnival.entitys.systems;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.silvercarnival.entitys.components.DebugRenderTag;
import com.silvercarnival.entitys.components.PositionComponent;

/**
 * @author Julian Giebel
 *
 */
public final class DebugRenderSystem extends IteratingSystem {

	@SuppressWarnings("unused")
	private ImmutableArray<Entity> entities;

	private Vector<Vector2> marks;
	private final int marksize = 3;
	private int n = -1;
	
	
	private ShapeRenderer renderer;
	
    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	
    
    
	public DebugRenderSystem()
	{
		super(Family.all(PositionComponent.class, DebugRenderTag.class).get());
		marks = new Vector<Vector2>();
		renderer = new ShapeRenderer();
	}
	
	/* (non-Javadoc)
	 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		PositionComponent pc = pm.get(entity);
		marks.addElement(pc.position);
		n++;
		
		

	}
	
	public void render(Camera camera)
	{
		Enumeration<Vector2> iter = marks.elements();
		
		Vector2 pos = new Vector2(0,0);
		
		
		while(n >= 0)
		{
			
			pos = iter.nextElement();
			
			camera.update();
		    renderer.setProjectionMatrix(camera.combined);
			 
		    renderer.begin(ShapeType.Line);
		
			
			renderer.setColor(1, 0.5f, 0, 1);
			renderer.rect(pos.x - marksize/2, pos.y - marksize/2, marksize, marksize);
			renderer.end();
			
			n--;
			
		}
		
		n = -1;
		
			
	}

}
