package com.silvercarnival;

import java.util.Stack;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.silvercarnival.states.State;

public final class GameStateManager {

	
	
	private static SpriteBatch batch;
	
	private static Stack<State> states;
	
	
	public static void resize(float f, float g) {
		
		if(states.isEmpty()) return;
		states.peek().resize(f, g); 
	}

	public static void init() {
		states = new Stack<State>();
		
		
		batch = new SpriteBatch();
	}

	public static void update(float deltaTime) {
		if(states.isEmpty()) return;
		states.peek().update(deltaTime);
		
	}

	public static void render() {
		if(states.isEmpty()) return;
		states.peek().render(batch);
		
	}

	public static void dispose() {
		if(states.isEmpty()) return;
		states.peek().dispose();
		
	}
	
	public static void pushState(State state)
	{
		if(!states.isEmpty()) states.peek().pause();
		states.push(state);
		state.create();
		
	}
	
	public static void popState()
	{
		if(states.isEmpty()) return;
		dispose();
		states.pop();
		if(states.isEmpty()) return;
		states.peek().resume();
		
	}
	
	public static void swapStates(State state)
	{
		popState();
		pushState(state);
	}
	
	public static SpriteBatch getBatch()
	{
		return batch;
	}
	
	public static void deconstruct()
	{
		while(!states.isEmpty())
		{
			popState();
		}
		batch.dispose();
	}
	
}
