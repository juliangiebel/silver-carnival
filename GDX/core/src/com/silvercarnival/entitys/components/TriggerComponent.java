/**
 * 
 */
package com.silvercarnival.entitys.components;

import com.badlogic.ashley.core.Component;
import com.silvercarnival.mapobjects.MessageData;

/**
 * @author Julian Giebel
 *
 */
public class TriggerComponent implements Component {
	
	public int delay, type; // type serves as a non unique identifier to link triggers to certain actions.
	@SuppressWarnings("rawtypes")
	public MessageData  data;//TODO remove MessageData from the codebase.
	
	public TriggerComponent(int _delay, int _type,@SuppressWarnings("rawtypes") MessageData _data)
	{
		this.data  = _data;
		this.delay = _delay;
		this.type  = _type;
	}
	
	

}
