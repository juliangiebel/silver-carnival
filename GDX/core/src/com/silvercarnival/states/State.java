package com.silvercarnival.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public abstract class State {
	
	
	protected OrthographicCamera camera;
	
	public State(OrthographicCamera camera)
	{
		this.camera = camera;
	}
	
	public abstract void create();
	
	public void resize(float w, float h)
	{
		camera.setToOrtho(false, w, h);
	}

	public abstract void update(float deltaTime);

	public abstract void render(SpriteBatch batch);
	
	public abstract void pause();
	
	public abstract void resume();

	public abstract void dispose();
}
