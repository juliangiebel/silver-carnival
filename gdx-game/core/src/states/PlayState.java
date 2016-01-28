package states;

import utils.TiledUtil;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class PlayState extends State {

	private TiledMap map;
	private World world;
	private PlayRender render;
	private Box2DDebugRenderer b2dr; 
	
	public PlayState(OrthographicCamera camera) {
		super(camera);
		world = new World(new Vector2(0,0), true);
		map = new TmxMapLoader().load("assets/"+GameStateManager.getNextMap());
		render = new PlayRender(map);
		TiledUtil.parseTiledObjectLayer(world, map.getLayers().get("Kachelebene 1").getObjects());	
		b2dr = new Box2DDebugRenderer();
		TiledUtil.parseTiledTileCollision(world, (TiledMapTileLayer) map.getLayers().get("Kachelebene 1"), GameStateManager.getNextMap());
		
	}
	Array<Body> bodies;
	@Override
	public void update(float deltaTime) {
		
		world.step(1 / 60f, 6, 2);
		
		
		
		cameraUpdate();
	}
	
	private void cameraUpdate()
	{
		camera.update();
	}

	@Override
	public void render(SpriteBatch batch) {
		
		render.render(batch,camera,b2dr,world);
		

	}

	@Override
	public void dispose() {
		
		world.dispose();
		map.dispose();
	}

}
