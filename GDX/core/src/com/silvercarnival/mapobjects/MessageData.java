/**
 * 
 */
package com.silvercarnival.mapobjects;

/**
 * @author Julian Giebel
 *
 */
public class MessageData<T> {

	private T data;
	
	public MessageData(T _data)
	{
		this.data = _data;
	}
	
	public T get()
	{
		return data;
	}
	
}
