/**
 * 
 */
package com.silvercarnival.entitys.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

/**
 * @author Julian Giebel
 *
 */
public class TriggerSystem extends IteratingSystem {
//TODO Implement the trigger system class
	public TriggerSystem(Family family) {
		super(family);
	}

	/* (non-Javadoc)
	 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {

	}

}
