/**
 * 
 */
package com.silvercarnival.entitys.components;

import com.badlogic.ashley.core.Component;

/**
 * @author Julian Giebel
 *
 */
public class TriggerComponent implements Component {
	
	public int delay, type;
	public Object data;//TODO create a template message data class.
	
	public TriggerComponent(int _delay, int _type,Object _data)
	{
		this.data  = _data;
		this.delay = _delay;
		this.type  = _type;
	}

}
