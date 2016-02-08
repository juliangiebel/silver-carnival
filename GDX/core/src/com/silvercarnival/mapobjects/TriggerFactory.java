/**
 * 
 */
package com.silvercarnival.mapobjects;


import java.util.Iterator;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.silvercarnival.entitys.components.PositionComponent;
import com.silvercarnival.entitys.components.SensorComponent;
import com.silvercarnival.entitys.components.TriggerComponent;

/**
 * @author Julian Giebel
 *
 */
public class TriggerFactory implements ObjectFactory {

	private World world;
	
	public TriggerFactory(World _world)
	{
		this.world = _world;
	}
	
	/* (non-Javadoc)
	 * @see com.silvercarnival.mapobjects.ObjectFactory#register()
	 */
	@Override
	public String register() {
		return "trigger";
	}

	/* (non-Javadoc)
	 * @see com.silvercarnival.mapobjects.ObjectFactory#generate(com.badlogic.gdx.utils.XmlReader.Element)
	 */
	@Override
	public Entity generate(Element e) {
		Entity entity = new Entity();
		
		
		int type = 0, delay = 0;
		
		Array<Element> propertys = e.getChildrenByNameRecursively("property");
		Iterator<Element> ip = propertys.iterator();
		
		while(ip.hasNext())
		{
			Element property = ip.next();
			switch(property.get("name"))
			{
			case "type":
				type = property.getInt("type");
				break;
			case "delay":
				delay = property.getInt("delay");
				break;
			//FIXME implement message data in a reasonable way.
			}
		}
		
		entity.add(new PositionComponent(new Vector2(e.getInt("x"),e.getInt("y"))));
		entity.add(new SensorComponent(e.getInt("width"), e.getFloat("height"), world));
		entity.add(new TriggerComponent(type, delay, null));
		
		propertys.clear();
		
		return entity;
	}

}
