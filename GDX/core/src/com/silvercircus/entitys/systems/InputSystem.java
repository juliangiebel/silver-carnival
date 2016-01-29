/**
 * 
 */
package com.silvercircus.entitys.systems;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.silvercircus.entitys.components.InputComponent;
import com.silvercircus.entitys.components.VelocityComponent;
import com.silvercircus.graphics.ScreenSize;
import com.silvercircus.inCommand.KeyInputCommand;
import com.silvercircus.inCommand.MouseInputCommand;

/**
 * @author Julian Giebel
 *
 */
public class InputSystem extends IteratingSystem implements KeyInputCommand, MouseInputCommand{

    @SuppressWarnings("unused")
	private ImmutableArray<Entity> entities;

    private ComponentMapper<InputComponent> im = ComponentMapper.getFor(InputComponent.class);
    private ComponentMapper<VelocityComponent> vm = ComponentMapper.getFor(VelocityComponent.class);
    
    Vector2 dir;
    //internal representations of VelocityComponent variables
    float intHeading;
    boolean intRunning;
    boolean intAction;
    
	@SuppressWarnings("unchecked")
	public InputSystem() {
		super(Family.all(InputComponent.class, VelocityComponent.class).get());
		intHeading = 0f;
	    dir = new Vector2();
	    intRunning = false;
	    intAction = false;
	}


	/* (non-Javadoc)
	 * @see com.badlogic.ashley.systems.IteratingSystem#processEntity(com.badlogic.ashley.core.Entity, float)
	 */
	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		InputComponent input = im.get(entity);
		VelocityComponent velCom = vm.get(entity);
		
		input.action = intAction;
		input.down = (dir.x == -1);
		input.up = (dir.x == 1);
		input.left = (dir.y == -1);
		input.right = (dir.y == 1);
		
		velCom.running = intRunning;
		velCom.heading = intHeading;
		
		if(intRunning)dir.scl(velCom.runSpeed); 
			else dir.scl(velCom.walkSpeed);
		velCom.velocity.add(dir);
		dir.x = 0; dir.y = 0;
		
		
		
	}


	@Override
	public void keyDown(int key) {
		
		switch(key)
		{
		case Keys.W:
			dir.y = 1;
			break;
		case Keys.A:
			dir.x = -1;
			break;
		case Keys.S:
			dir.y = -1;
			break;
		case Keys.D:
			dir.x = 1;
			break;
		case Keys.SHIFT_LEFT:
			intRunning = true;
			break;
		
		}
		
	}


	@Override
	public void keyUp(int key) {
		
		switch(key)
		{
		case Keys.W:
			dir.y = 0;
			break;
		case Keys.A:
			dir.x = 0;
			break;
		case Keys.S:
			dir.y = 0;
			break;
		case Keys.D:
			dir.x = 0;
			break;
		case Keys.SHIFT_LEFT:
			intRunning = false;
			break;
		
		}
		
	}


	@Override
	public void mouseMoved(int x, int y) {
		Vector2 vec = new Vector2(x-(ScreenSize.getWidth()/2),(y-(ScreenSize.getHeight()/2))*(-1));
		
		intHeading = vec.angle();
	}


	@Override
	public void scrolled(int ammount) {
		// Not used
		
	}

}
