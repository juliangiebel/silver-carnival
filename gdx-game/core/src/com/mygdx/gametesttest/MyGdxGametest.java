package com.mygdx.gametesttest;

import states.GameStateManager;
import states.PlayState;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

 public class MyGdxGametest extends ApplicationAdapter {
	
	@SuppressWarnings("unused")
	private boolean DEBUG = false;
	private final float SCALE = 2.0f;
	
	private OrthographicCamera camera; 
	private SpriteBatch batch;
	

	
	
	@Override
	public void create () {
		
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w/SCALE, h/SCALE);
		
		
		GameStateManager.init(this);
		GameStateManager.setNextMap("desert.tmx");
		GameStateManager.pushState(new PlayState(camera));
		batch = new SpriteBatch();
	}



	public void resize(int width, int height)
	{
		
		GameStateManager.resize(width/SCALE, height/SCALE);
	
	}
	
	@Override
	public void render () {
		GameStateManager.update(Gdx.graphics.getDeltaTime());
		GameStateManager.render();
	}
	
	public void dispose () 
	{ 
		GameStateManager.dispose();
		batch.dispose();

	}
	
	public SpriteBatch getSpriteBatch()
	{
		return batch;
	}
	
	public OrthographicCamera getCamera()
	{
		return camera;
	}
	

	

}
