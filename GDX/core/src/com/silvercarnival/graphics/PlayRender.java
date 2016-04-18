package com.silvercarnival.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.silvercarnival.utils.Constants;

public class PlayRender {
	
	private OrthogonalTiledMapRenderer mapRenderer;
	
	public PlayRender(TiledMap map)
	{
		
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
	}
	
	
	public void render(SpriteBatch batch,OrthographicCamera camera,Box2DDebugRenderer b2dr,World world)
	{
		
		mapRenderer.setView(camera);
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl20.glClearColor(0,0,0,0);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		mapRenderer.render();
		b2dr.render(world, camera.combined.scl(Constants.PPM));
	}
	public void render(SpriteBatch batch,OrthographicCamera camera)
	{
		
		mapRenderer.setView(camera);
		batch.setProjectionMatrix(camera.combined);
		Gdx.gl20.glClearColor(0,0,0,0);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		mapRenderer.render();
	}
	
	
	public static int getHeight()
	{
		return 0;
	}
	
	public static int getWidth()
	{
		return 0;
	}
	
	
	
}
