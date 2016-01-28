/**
 * 
 */
package utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * @author Julian Giebel
 *
 */
public class TiledUtil {
	
	
	public static void parseTiledTileCollision(World world, TiledMapTileLayer tlayer,String mapName)
	{
		Array<Element> tiles;
		Map<Integer, Polyline[]> collisionTiles = new HashMap<Integer, Polyline[]>(); //map holding the polylines per tile with collision data
		ArrayList<Float> fpoints = new ArrayList<Float>();                            //this holds the points of one polyline and is reused
		try {
			Element element = new XmlReader().parse(Gdx.files.internal("assets/"+mapName));
			tiles = element.getChildrenByNameRecursively("tile");
			if(tiles.size > 0)
			{
				for(Element tile : tiles)
				{
					int tileid = tile.getInt("id");
					
					//into the object group holding the polylines per tile
					if(tile.getChildByName("objectgroup") != null)
					{
						
						Array<Element> objects = tile.getChildrenByNameRecursively("object");
						Polyline[] objectsPerTile = new Polyline[objects.size];
						int objcount = 0;
						for(Element object : objects)//iterate over each polyline
						{
							
							float x = object.getFloat("x"), y = object.getFloat("y");
							
							//getting the points as a string and parsing them into fpoints two at a time
							String pointsraw = object.getChildByName("polyline").get("points");
							String[] pointsxy =  pointsraw.split(" ");
							for(String pointxy : pointsxy)
							{
								String[] point = pointxy.split(",", 2);
								fpoints.add(Float.parseFloat(point[0])+x);
								fpoints.add(tlayer.getTileHeight() - (Float.parseFloat(point[1])+y));
							}
							
							float[] tmp = new float[fpoints.size()];
							//converting fpoints into a float array 
							int i = 0;
							for(Float f : fpoints)
							{
								tmp[i] = (f != null ? f : Float.NaN);
								i++;
							}
							Polyline line = new Polyline(tmp);
							
							objectsPerTile[objcount] = line;
							objcount++;
							
							fpoints.clear();
							
						}
						
						collisionTiles.put( tileid, objectsPerTile);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		int xmax = tlayer.getWidth();
		int ymax = tlayer.getHeight();
		int x = 0, y = 0;
		while(x != xmax)
		{
			while(y != ymax)
			{
				Cell cell = tlayer.getCell(x, y);
				if(collisionTiles.containsKey(cell.getTile().getId()-1))
				{
					//System.out.println("Hit at"+x+"|"+y+"|ID: "+(cell.getTile().getId()-1));
					Polyline[] objects = collisionTiles.get(cell.getTile().getId()-1);
					for(Polyline object : objects)
					{
						object.setPosition(x*tlayer.getTileWidth(), y*tlayer.getTileHeight());
						Shape shape = createPolyline(new PolylineMapObject(object));
						
					    Body body;
					    BodyDef bdef = new BodyDef();
					    bdef.type = BodyDef.BodyType.StaticBody;
					    body = world.createBody(bdef);
					    
					    body.createFixture(shape,1.0f);
					    shape.dispose();
					}
				}
				y++;
			}
			y = 0;
			x++;
		}
		
	}
	
	
	
	public static void parseTiledObjectLayer(World world,MapObjects objects)
	{
		for(MapObject object : objects)
		{
			Shape shape = null;
			if(object instanceof PolylineMapObject)
			{
				shape = createPolyline((PolylineMapObject) object);
				
			    Body body;
			    BodyDef bdef = new BodyDef();
			    bdef.type = BodyDef.BodyType.StaticBody;
			    body = world.createBody(bdef);
			    
			    body.createFixture(shape,1.0f);
			    shape.dispose();
			}
		}
	}
	
	private static ChainShape createPolyline(PolylineMapObject polyline)
	{
		float[] vertices = polyline.getPolyline().getTransformedVertices();
		Vector2[] worldVertices = new Vector2[vertices.length/2]; 
		
		
		for(int i = 0; i< worldVertices.length; i++)
		{
			worldVertices[i] = new Vector2(vertices[i*2]/Constants.PPM,vertices[i*2+1]/Constants.PPM);
		}
		
		ChainShape cs = new ChainShape();
		cs.createChain(worldVertices);
		
		return cs;
	}
}
