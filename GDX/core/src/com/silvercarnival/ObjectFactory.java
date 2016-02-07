/**
 * 
 */
package com.silvercarnival;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.utils.XmlReader.Element;

/**
 * @author Julian Giebel
 * Interface for generating objects with the MapObjectLoader
 */
public interface ObjectFactory {

	/**
	 * Used to register the factory instance to a certain type of object
	 * @return String The type of object
	 */
	public String register();
	
	/**
	 * Constructs the registered type of entity and returns the instants to the MapObjectLoader class
	 * @param e The Element used to construct the entity
	 * @return Entity The constructed entity
	 */
	public Entity generate(Element e);
}
