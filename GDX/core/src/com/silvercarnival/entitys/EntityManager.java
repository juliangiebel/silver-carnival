/**
 * 
 */
package com.silvercarnival.entitys;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.signals.*;
import com.badlogic.ashley.systems.*;
import com.silvercarnival.entitys.systems.InputSystem;
import com.silvercarnival.entitys.systems.MovementSystem;
/**
 * @author Julian Giebel
 *
 */
public final class EntityManager {

	private static Engine engine;
	
	/**
	 * 
	 */
	public EntityManager() {
		
		if(engine == null){engine = new Engine(); build();}
		
		
	}
	
	private void build()
	{
		//add all systems here
		engine.addSystem(new InputSystem());
		engine.addSystem(new MovementSystem());
		
	}

}
