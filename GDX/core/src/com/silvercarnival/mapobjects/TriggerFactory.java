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
import com.silvercarnival.entitys.components.DebugRenderTag;
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
		
		@SuppressWarnings("rawtypes")
		MessageData data = null;
		
		Iterator<Element> ip = propertys.iterator();
		
		while(ip.hasNext())
		{
			Element property = ip.next();
			switch(property.get("name"))
			{
			
			case "type":
				type = property.getInt("value");
				System.out.println("trigger type:"+ type);
				break;
			case "delay":
				delay = property.getInt("value");
				break;
			case "intdata":
				data = new MessageData<Integer>(property.getInt("value"));
			break;
			case "strdata":
				data = new MessageData<String>(property.get("value"));
				break;
			case "booldata":
				data = new MessageData<Boolean>(property.getBoolean("value"));
				break;
			}
		}
		
		entity.add(new PositionComponent(new Vector2(e.getInt("x"),e.getInt("y"))));
		entity.add(new SensorComponent(e.getInt("width"), e.getFloat("height"), world));
		entity.add(new TriggerComponent(type, delay, data));
		entity.add(new DebugRenderTag());//for debugging
		
		propertys.clear();
		
		return entity;
	}

}
