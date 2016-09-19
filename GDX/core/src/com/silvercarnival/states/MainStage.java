/**
 * 
 */
package com.silvercarnival.states;
import java.util.Iterator;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.silvercarnival.GameStateManager;
import com.silvercarnival.entitys.components.FollowerTag;
import com.silvercarnival.entitys.components.PlayerTag;
import com.silvercarnival.mapobjects.MapObjectLoader;
import com.silvercarnival.mapobjects.TriggerFactory;
import com.silvercarnival.utils.TiledUtil;

/**
 * @author Julian Giebel
 *The Main game Stage. Sub-stages will be initialized and loaded through this stage.
 */
public final class MainStage extends State implements Telegraph {

	public  static String currentMap = null;
	public  static final int nextMapMsg = 32;
	private static final int[] backgroundLayers = {0};
	//private static final int[] foregroudLayers = {0};
	
	private boolean isActive = true;
	
	private boolean chMap = false;
	private String nextMap = null;
	
	private Engine engine;
	
	private OrthogonalTiledMapRenderer mapRender;
	private TiledMap map;
	
	
	private World world;
	
	public MainStage(OrthographicCamera camera, Engine _engine, TiledMap _map,World _world) {
		super(camera);
		this.engine 	= _engine;
		this.map 		= _map;
		this.world		= _world;
		this.mapRender  = new OrthogonalTiledMapRenderer(map , GameStateManager.getBatch());
	}

	/* (non-Javadoc)
	 * @see com.silvercircus.states.State#update(float)
	 */
	@Override
	public void update(float deltaTime) {
		engine.update(deltaTime);
		
		if(chMap)
		{
			if(nextMap != null)
			{
				changeMap(nextMap);
			}else{
				chMap = false;
				System.out.println("can't change to empty space!");
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.silvercircus.states.State#render(com.badlogic.gdx.graphics.g2d.SpriteBatch)
	 */
	@Override
	public void render(SpriteBatch batch) {
		
		mapRender.setView(camera);
		batch.setProjectionMatrix(this.camera.combined);
		Gdx.gl20.glClearColor(0,0,0,0);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT|GL20.GL_DEPTH_BUFFER_BIT);
		
		
		mapRender.render(backgroundLayers);
		
		
	}
	
	@Override
	public void pause(){
		isActive = false;
	}
	
	@Override
	public void resume() {
		isActive = true;
	}
	
	/* (non-Javadoc)
	 * @see com.silvercircus.states.State#dispose()
	 */
	@Override
	public void dispose() {
		mapRender.dispose();
		world.dispose();

	}

	@Override
	public void create() {
		MessageManager.getInstance().addListener(this, nextMapMsg);
		
	}
	

	@Override
	public boolean handleMessage(Telegram msg) {
		//--Added a local flag for changing the map and a return true instead of break
		//----Wish i could find a better solution.
		
		if(!isActive) return false;
		
		switch(msg.message) 
		{
		
		case nextMapMsg:
			if(msg.extraInfo.getClass() != String.class) return false;
			nextMap = (String)msg.extraInfo;
			chMap = true;
			return true;
		
		}
		
		return false;
	}
	
	private void changeMap(String mapname)
	{
		
		System.out.println("changed Map to:" + mapname);
		ImmutableArray<Entity> group = engine.getEntitiesFor(Family.all(PlayerTag.class,FollowerTag.class).get());
		engine.removeAllEntities();
		Iterator<Entity> iter = group.iterator();
		while(iter.hasNext())
		{
			engine.addEntity(iter.next());
		}
		World tworld = new World(new Vector2(0,0), true);
		TiledMap tmap = new TmxMapLoader().load(mapname);
		TiledUtil.parseTiledObjectLayer(tworld, tmap.getLayers().get(0).getObjects());
		TiledUtil.parseTiledTileCollision(tworld, (TiledMapTileLayer) tmap.getLayers().get(0), mapname);
		
		
		
		MapObjectLoader loader = new MapObjectLoader(mapname, engine);
		loader.addFactory(new TriggerFactory(tworld));
		loader.buildObjects();
		
		GameStateManager.swapStates(new MainStage(camera,engine,tmap,tworld));
		
	}

	

}
