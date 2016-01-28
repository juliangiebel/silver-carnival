/**
 * 
 */
package jUnitTests;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.hamcrest.CoreMatchers;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;

import entitys.components.InputComponent;
import entitys.components.VelocityComponent;
import entitys.systems.InputSystem;
import graphics.ScreenSize;

/**
 * @author Julian Giebel
 *
 */
@RunWith(Parameterized.class)
public class InputSystemTest {

	
	

	@Parameters(name = "{index}: <{0}|{1}>")
	public static Iterable<Object[]> data() {
        return Arrays.asList(new Object[][] { 
                 { Keys.W, false, 120,340 }, {Keys.W, true, 430,550}, { Keys.A, false, 113,450}, {Keys.A, true, 34,2},
                 { Keys.S, false, 123,540 }, {Keys.S, true, 120,230}, { Keys.D, false, 0,0}, {Keys.D, true, 0,0}
           });
    }
	
	int pKey;
	boolean pRunning;
	int x,y;
	
	static Engine engine;
	
	static Entity entity;
	
	static InputSystem test;
	
	static final int walkSpeed = 10, runSpeed = 20;
	
	/**
	 * @param pKey
	 * @param pRunning
	 * @param x
	 * @param y
	 */
	public InputSystemTest(int pKey, boolean pRunning, int x, int y) {
		this.pKey = pKey;
		this.pRunning = pRunning;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		engine = new Engine();
		
		test = new InputSystem();
		
		engine.addSystem(test);
		
		entity = new Entity();
		
		entity.add(new InputComponent());
		entity.add(new VelocityComponent(walkSpeed, runSpeed));
		
		engine.addEntity(entity);
		engine.update(1);
		
		
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
		engine.removeAllEntities();
		engine.removeSystem(test);
		engine = null;
		entity = null;
		test   = null;
		
	}

	/**
	 * Test method for {@link entitys.systems.InputSystem#keyDown(int)}.
	 */
	@Test
	public void testKeyDown() {
		
		VelocityComponent velCom = entity.getComponent(VelocityComponent.class);
		
		velCom.velocity.x = 0f;
		velCom.velocity.y = 0f;
		if(pRunning) test.keyDown(Keys.SHIFT_LEFT);
		
		test.keyDown(pKey);
		engine.update(1);
		
		Assert.assertThat("Invalid value for VelocityComponent.running.",velCom.running, CoreMatchers.is(pRunning));
		
		Assert.assertThat("Invalid value for VelocityComponent.velocity.x",velCom.velocity.y, 
				CoreMatchers.is((float)((pKey == Keys.W?1:0)+(pKey == Keys.S?-1:0))*(pRunning?runSpeed:walkSpeed)));
		
		Assert.assertThat("Invalid value for VelocityComponent.velocity.y",velCom.velocity.x, 
				CoreMatchers.is((float)((pKey == Keys.D?1:0)+(pKey == Keys.A?-1:0))*(pRunning?runSpeed:walkSpeed)));
	}

	/**
	 * Test method for {@link entitys.systems.InputSystem#keyUp(int)}.
	 */
	@Test
	public void testKeyUp() {
		if(pRunning) test.keyUp(Keys.SHIFT_LEFT);
		test.keyUp(pKey);
		engine.update(1);
	}

	/**
	 * Test method for {@link entitys.systems.InputSystem#mouseMoved(int, int)}.
	 */
	@Test
	public void testMouseMoved() {
		
		test.mouseMoved(x, y);
		VelocityComponent velCom = entity.getComponent(VelocityComponent.class);
		Vector2 vec = new Vector2(x-(ScreenSize.getWidth()/2),(y-(ScreenSize.getHeight()/2))*(-1));
		
		engine.update(1);
		
		Assert.assertThat(velCom.heading, CoreMatchers.is(vec.angle()));
		
		
	}

	/**
	 * Test method for {@link entitys.systems.InputSystem#scrolled(int)}.
	 */
	@Ignore("Scrolled is not yet implemented.")
	@Test
	public void testScrolled() {
		fail("Not yet implemented");
	}

}
