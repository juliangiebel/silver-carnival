package states;

import utils.Constants;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

class PlayRender {
	
	private OrthogonalTiledMapRenderer mapRenderer;
	
	PlayRender(TiledMap map)
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
	
	
}
