package states;

import java.util.Stack;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.gametesttest.MyGdxGametest;

public final class GameStateManager {

	private static MyGdxGametest gameclass;
	
	
	
	private static String nextMap;
	
	
	
	private static Stack<State> states;
	
	
	public static void resize(float f, float g) {
		
		if(states.isEmpty()) return;
		states.peek().resize(f, g);
	}

	public static void init(MyGdxGametest game) {
		states = new Stack<State>();
		
		
		gameclass = game;
	}

	public static void update(float deltaTime) {
		if(states.isEmpty()) return;
		states.peek().update(deltaTime);
		
	}

	public static void render() {
		if(states.isEmpty()) return;
		states.peek().render(gameclass.getSpriteBatch());
		
	}

	public static void dispose() {
		if(states.isEmpty()) return;
		states.peek().dispose();
		
	}
	
	public static void pushState(State state)
	{
		
		states.push(state);
		
	}
	
	public static void popState()
	{
		if(states.isEmpty()) return;
		dispose();
		states.pop();
		
	}
	
	public static void swapStates(State state)
	{
		popState();
		pushState(state);
	}

	public static String getNextMap() {
		return nextMap;
	}

	public static void setNextMap(String nextMap) {
		GameStateManager.nextMap = nextMap;
	}
	
}
