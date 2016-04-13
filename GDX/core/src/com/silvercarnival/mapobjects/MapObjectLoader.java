/**
 * 
 */
package com.silvercarnival.mapobjects;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * @author Julian Giebel
 *
 */
public final class MapObjectLoader {

	private HashMap<String, Vector<Element>> objects;
	private HashMap<String, ObjectFactory>   factorys;
	private Array<Element> unSortedObjects;
	private Engine engine;
	
	public MapObjectLoader(final String filename, Engine _engine)
	{
		String type;
		objects  = new HashMap<String, Vector<Element>>();
		factorys = new HashMap<String, ObjectFactory>();
		
		
		this.engine = _engine;
		
		
		try {
			unSortedObjects = new XmlReader().parse(Gdx.files.internal("assets/"+filename)).getChildrenByNameRecursively("object");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(unSortedObjects != null&&unSortedObjects.size > 0)
		{
			
			for(Element object : unSortedObjects)
			{
				type = object.getAttribute("type", null);
				System.out.println("object <"+ type+">");
				if(type != null)
				{
					System.out.println("adding new object.");
					if(objects.containsKey(type))
					{
						objects.get(type).add(object);
					}else{
						objects.put(type, new Vector<Element>());
						objects.get(type).add(object);
					}
				}
				
			}
		}
	
			
		
	}
	
	public void buildObjects()
	{
		Iterator<String> ikeys = objects.keySet().iterator();
		String currentType;
		Entity bEntity;
		
		while(ikeys.hasNext())
		{
			currentType = ikeys.next();
			Iterator<Element> iobj = objects.get(currentType).iterator();
			while(iobj.hasNext())
			{
				ObjectFactory f = factorys.get(currentType);
				if(f == null) {System.out.println("null"); break; }
				System.out.println("Factory found:");
				bEntity = f.generate(iobj.next());
				engine.addEntity(bEntity);
			}
		}
	}
	
	public void addFactory(ObjectFactory factory)
	{
		if(factorys.containsKey(factory.register()))
		{
			//TODO log error message
			return;
		}
		factorys.put(factory.register(), factory);
	}
	
	public void removeFactory(String type)
	{
		if(!factorys.containsKey(type))
		{
			//TODO log error message
			return;
		}
		factorys.remove(type);
	}
	
}
