package com.silvercarnival.entitys.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;

public final class EntityTelegraph implements Telegraph{

	private Entity entity;
	
	public EntityTelegraph(Entity _entity)
	{
		this.entity = _entity;
	}
	
	public Entity get()
	{
		return entity;
	}

	@Override
	public boolean handleMessage(Telegram msg) {
		return false;
	}
}