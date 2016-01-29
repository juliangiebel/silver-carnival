/**
 * 
 */
package com.silvercircus.entitys.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Vector2;

import com.silvercircus.entitys.components.BodyComponent;
import com.silvercircus.entitys.components.PositionComponent;
import com.silvercircus.entitys.components.VelocityComponent;

/**
 * @author Julian Giebel
 *
 */
public class MovementSystem extends IteratingSystem {

    @SuppressWarnings("unused")
	private ImmutableArray<Entity> entities;

    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    private ComponentMapper<BodyComponent> bm = ComponentMapper.getFor(BodyComponent.class);

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public MovementSystem() {
		super(Family.all(PositionComponent.class, VelocityComponent.class).get());
	}


	/* (non-Javadoc)
	 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		
		//TODO Add lag compensation.
		Vector2 vel = vm.get(entity).velocity;
		
		bm.get(entity).body.applyForceToCenter(vel, true);
		pm.get(entity).position = bm.get(entity).body.getWorldCenter();
		
		vm.get(entity).velocity.set(0f, 0f);

	}

}
