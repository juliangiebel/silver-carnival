package com.silvercarnival;


import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Json;
import com.silvercarnival.entitys.components.BodyComponent;
import com.silvercarnival.entitys.components.DebugRenderTag;
import com.silvercarnival.entitys.components.PositionComponent;
import com.silvercarnival.entitys.systems.DebugRenderSystem;
import com.silvercarnival.entitys.systems.TriggerSystem;
import com.silvercarnival.mapobjects.MapObjectLoader;
import com.silvercarnival.mapobjects.TriggerFactory;
import com.silvercarnival.states.MainStage;
import com.silvercarnival.utils.BodyBuilder;
import com.silvercarnival.utils.TiledUtil;

public class Game extends ApplicationAdapter {

	private static OrthographicCamera camera;
	/*
	private MapObjectLoader loader;
	private Engine engine;
	private World world;
	
	private DebugRenderSystem dbs;
	private Box2DDebugRenderer dbg;
	private Body pb;
	*/
	
	private int tsti = 0;
	
	GameStateManager manager;
	
	@SuppressWarnings("static-access")
	@Override
	public void create () {
		
		manager = new GameStateManager();
		manager.init();
		
		camera = new OrthographicCamera(100f,100f);
		camera.position.set(new Vector2(38,118), 1);
		camera.update();
		manager.pushState(buildStage("run.tmx"));
		/*
		Json json = new Json();
		
		engine = new Engine();
		
		engine.addSystem(new TriggerSystem());
		
		world = new World(new Vector2(10,0), false);
		
		
		
		dbs = new DebugRenderSystem();
		dbg = new Box2DDebugRenderer();
		
		
		engine.addSystem(dbs);
		
		loader = new MapObjectLoader("desert.tmx", engine);
		loader.addFactory(new TriggerFactory(world));
		loader.buildObjects();
		
		Entity  te = new Entity();
		
		PositionComponent pc = new PositionComponent(new Vector2(38,140));
		
		pb = BodyBuilder.createBox(world, 36, 140, 200, 200, false, false);
		pb.setTransform(new Vector2(36,140), 0f);
		pb.getFixtureList().first().setUserData(te);
		System.out.println(te);
		BodyComponent bc = new BodyComponent(pb);
		te.add(pc);
		te.add(bc);
		
		engine.addEntity(te);
		
		
		
		System.out.println(json.prettyPrint(engine.getEntities().first().getComponents().first()));
		*/
	}

	@Override
	public void render () {
		
		/*Gdx.gl20.glClearColor(0,0,0,0);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		dbg.render(world, camera.combined);
		
		engine.update(0f);

		world.step((1/20), 8, 3);
		
		dbs.render(camera);*/
		
		GameStateManager.update(1f);
		GameStateManager.render();
		
		if(tsti <= 3) tsti++;
		if(tsti == 3)
		{
			MessageManager.getInstance().dispatchMessage(MainStage.nextMapMsg, "desert.tmx");
		}
		
		
	}
	
	private static MainStage buildStage(String mapname)
	{
		Engine _engine = new Engine();
		World tworld = new World(new Vector2(0,0), true);
		TiledMap tmap = new TmxMapLoader().load(mapname);
		TiledUtil.parseTiledObjectLayer(tworld, tmap.getLayers().get(0).getObjects());
		TiledUtil.parseTiledTileCollision(tworld, (TiledMapTileLayer) tmap.getLayers().get(0), mapname);
		
		
		
		MapObjectLoader loader = new MapObjectLoader(mapname,  _engine);
		loader.addFactory(new TriggerFactory(tworld));
		loader.buildObjects();
		return new MainStage(camera, _engine, tmap, tworld );
	}
}
