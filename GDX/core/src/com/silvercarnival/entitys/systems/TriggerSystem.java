/**
 * 
 */
package com.silvercarnival.entitys.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.QueryCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.silvercarnival.entitys.components.PositionComponent;
import com.silvercarnival.entitys.components.SensorComponent;
import com.silvercarnival.entitys.components.TriggerComponent;

/**
 * @author Julian Giebel
 *
 */
public class TriggerSystem extends IteratingSystem implements QueryCallback {
	  @SuppressWarnings("unused")
		private ImmutableArray<Entity> entities;

	    private ComponentMapper<PositionComponent> pm = ComponentMapper.getFor(PositionComponent.class);
	    private ComponentMapper<SensorComponent>   sm = ComponentMapper.getFor(SensorComponent.class);
	    private ComponentMapper<TriggerComponent>  tm = ComponentMapper.getFor(TriggerComponent.class);
	    
	    
	    private boolean trig;
	    
	    private Entity current;

		/**
		 * 
		 */
		public TriggerSystem() {
			super(Family.all(PositionComponent.class, SensorComponent.class, TriggerComponent.class).get());
		}


		/* (non-Javadoc)
		 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
		 */
		@Override
		protected void processEntity(Entity entity, float deltaTime) {
			
				current = entity;
				
				World world = sm.get(entity).world;
				
				float x = pm.get(entity).position.x, y = pm.get(entity).position.y;
				
				float lx, ly , ux, uy;
				//Calculating the area for an AABB Query
				lx = x;
				ly = y;
				ux = x + sm.get(entity).width;
				uy = y + sm.get(entity).height;
				
				trig = false;
				world.QueryAABB(this, lx,ly,ux,uy);
				
				if(!trig) sm.get(current).isActive = false;
				
				
		}

		//Callback for AABB query
		@Override
		public boolean reportFixture(Fixture fixture) {
			
			//Entity detected;
			
			TriggerComponent msg;
			
			if(Entity.class.isInstance(fixture.getUserData()) ) 
			{
				//detected = (Entity)fixture.getUserData();
				trig = true;
				
			}else return true;
			
			if(sm.get(current).isActive) return false;
			
			msg = tm.get(current);
				
			//tell the world that trigger 'type' was triggered by 'entity' in 'delay' seconds.
			MessageManager.getInstance().dispatchMessage(msg.delay,msg.type,fixture.getUserData()); 
			System.out.println(msg.delay+"|"+msg.type+"|"+fixture.getUserData());
			sm.get(current).isActive = true;
				
			
			return false;
		}
}
