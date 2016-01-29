/**
 * 
 */
package com.silvercircus.entitys.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.utils.Bag;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Julian Giebel
 *
 */
public class SensorComponent implements Component {

	public Boolean isActive;
	public Entity firstIF;
	public Bag<Entity> inFront;
	
	public float width, height;
	public World world;
	
	/**
	 * 
	 */
	public SensorComponent(float _width, float _height,  World _world) {
		isActive = false;
		firstIF = null;
		
		inFront = new Bag<Entity>();
		
		this.width = _width;
		this.height = _height;
		
		this.world = _world;
		
	}

}
