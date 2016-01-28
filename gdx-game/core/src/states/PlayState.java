package states;

import inCommand.InputManager;
import graphics.PlayRender;
import utils.CameraStyles;
import utils.TiledUtil;
import physics.Physics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.ashley.core.*;

public class PlayState extends State {

	private TiledMap map;
	private Physics phys;
	private PlayRender render;
	private Box2DDebugRenderer b2dr;
	private Engine engine; //Ashley engine
	
	private InputManager testin;
	
	private Preferences pref;
	
	public PlayState(OrthographicCamera camera) {
		super(camera);
		
		engine = new Engine();
		
		phys = new Physics();
		map = new TmxMapLoader().load("assets/"+GameStateManager.getNextMap());
		render = new PlayRender(map);
		TiledUtil.parseTiledObjectLayer(phys.getWorld(), map.getLayers().get("Kachelebene 1").getObjects());	
		
		b2dr = new Box2DDebugRenderer();
		
		TiledUtil.parseTiledTileCollision(phys.getWorld(), (TiledMapTileLayer) map.getLayers().get("Kachelebene 1"), GameStateManager.getNextMap());
		pref = Gdx.app.getPreferences("test");
		
		testin = new InputManager(pref);
		
		Gdx.input.setInputProcessor(testin);
		
	}
	Array<Body> bodies;
	@Override
	public void update(float deltaTime) {
		
		phys.update(1 / 60f, 6, 2);
		
		
		cameraUpdate();
	}
	
	
	private void cameraUpdate()
	{
		camera.update();
	}

	@Override
	public void render(SpriteBatch batch) {
		
		render.render(batch,camera,b2dr,phys.getWorld());
		

	}

	@Override
	public void dispose() {
		
		phys.dispose();
		map.dispose();
	}

}
