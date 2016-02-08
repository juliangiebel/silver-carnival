/**
 * 
 */
package com.silvercarnival.entitys.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.silvercarnival.entitys.components.InputComponent;
import com.silvercarnival.entitys.components.InteractionComponent;
import com.silvercarnival.entitys.components.PositionComponent;
import com.silvercarnival.entitys.components.SensorComponent;
import com.silvercarnival.entitys.components.TriggerComponent;

/**
 * @author Julian Giebel
 *
 */
public class ActionSystem extends IteratingSystem implements QueryCallback {

	  @SuppressWarnings("unused")
		private ImmutableArray<Entity> entities;

	    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	    private ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);
	    private ComponentMapper<SensorComponent> sm = ComponentMapper.getFor(SensorComponent.class);
	    private ComponentMapper<InteractionComponent> inm = ComponentMapper.getFor(InteractionComponent.class);
	    
	    private Entity current;

		/**
		 * 
		 */
		@SuppressWarnings("unchecked")
		public ActionSystem() {
			super(Family.all(PositionComponent.class, InputComponent.class, SensorComponent.class).exclude(TriggerComponent.class).get());
		}


		/* (non-Javadoc)
		 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
		 */
		@Override
		protected void processEntity(Entity entity, float deltaTime) {
			
			if(im.get(entity).action)
			{
				current = entity;
				
				World world = sm.get(entity).world;
				
				float x = pm.get(entity).position.x, y = pm.get(entity).position.y;
				
				float lx, ly , ux, uy;
				//Calculating the area for an AABB Query
				lx = x - (sm.get(entity).width / 2);
				ly = y;
				ux = x + (sm.get(entity).width / 2);
				uy = y + sm.get(entity).height;
				
				world.QueryAABB(this, lx,ly,ux,uy);
				
				
			}else{
				sm.get(entity).isActive = false;
				//sm.get(entity).inFront.clear();
			}

		}

		//Callback for AABB query
		@Override
		public boolean reportFixture(Fixture fixture) {
			
			Entity detected;
			
			if(Entity.class.isInstance(fixture.getUserData()) ) 
			{
				detected = (Entity)fixture.getUserData();
				
			}else return true;
			
			
			if(!sm.get(current).isActive& inm.has(detected))
			{
				sm.get(current).firstIF = detected;
				sm.get(current).isActive = true;
				return false;
			}
			
			return true;
		}
}
