package com.silvercarnival.graphics;

import java.util.Iterator;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.silvercarnival.entitys.components.AnimationComponent;
import com.silvercarnival.entitys.components.DrawableTag;
import com.silvercarnival.entitys.components.PositionComponent;
import com.silvercarnival.entitys.components.SpriteComponent;

public class EntityRenderer implements EntityListener {

	Engine engine;
	Array<Entity> entities;
	private ComponentMapper<PositionComponent> 	pm = ComponentMapper.getFor(PositionComponent.class);
	private ComponentMapper<SpriteComponent> 	sm = ComponentMapper.getFor(SpriteComponent.class);
	private ComponentMapper<AnimationComponent> am = ComponentMapper.getFor(AnimationComponent.class);
	
	
	public EntityRenderer(Engine _engine)
	{
		this.engine = _engine;
		
		engine.addEntityListener(100, this);
		
		entities.addAll(engine.getEntitiesFor(Family.all(PositionComponent.class, DrawableTag.class)
				.one(SpriteComponent.class,AnimationComponent.class).get()).toArray());
	}
	
	public void render(Batch batch, float stateTime)
	{
		Iterator<Entity> iter = entities.iterator();
		
		Entity entity = null;
		
		batch.begin();
		
		Vector2 pos = null;
		
		while(iter.hasNext())
		{
			entity = iter.next();
			pos = pm.get(entity).position;
			if(sm.has(entity))
			{
				sm.get(entity).sprite.setPosition(pos.x, pos.y);
				sm.get(entity).sprite.draw(batch);
			}
			else{
				
				batch.draw(am.get(entity).anims.get(am.get(entity).curAnim).getKeyFrame(stateTime), pos.x, pos.y);
				
			}
		}
		
		batch.end();
	}

	@Override
	public void entityAdded(Entity entity) {
		entities.clear();
		entities.addAll(engine.getEntitiesFor(Family.all(PositionComponent.class, DrawableTag.class)
				.one(SpriteComponent.class,AnimationComponent.class).get()).toArray());
	}

	@Override
	public void entityRemoved(Entity entity) {
		if(entities.contains(entity, false)){
			entities.removeValue(entity, false);
		}
	}
	
}
