/**
 * 
 */
package com.silvercircus.utils;

import static com.silvercircus.utils.Constants.PPM;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * @author Julian Giebel
 *
 */
public class BodyBuilder {
	
	private BodyBuilder()
	{
		
	}
	
	public static Body createBox(final World world,int x, int y,int width,int height, boolean isStatic, boolean canRotate) 
	{
		Body pBody;
		BodyDef def = new BodyDef();
		
		if(isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;
		
		def.position.set(x/PPM, y/PPM);
		def.fixedRotation = !canRotate;
		pBody = world.createBody(def);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width/2)/PPM, (height/2)/PPM);
		
		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		
		return pBody;
	}
	public static Body createBox(final World world,int x, int y,int width,int height, boolean isStatic, boolean canRotate,
			short cBits,short mBits) 
	{
		//Body pBody;
		BodyDef def = new BodyDef();
		def.position.set(x/PPM, y/PPM);
		def.fixedRotation = !canRotate;
		
		if(isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width/2)/PPM, (height/2)/PPM);
		
		//pBody = world.createBody(def);
		
		FixtureDef fdef = new FixtureDef();
	    fdef.shape = shape;
	    fdef.density = 1.0f;
	    fdef.filter.categoryBits = cBits;
	    fdef.filter.maskBits     = mBits;
		
		
		shape.dispose();
		
		return world.createBody(def).createFixture(fdef).getBody();
	}
	public static Body createBox(final World world,int x, int y,int width,int height, boolean isStatic, boolean canRotate,
			short cBits,short mBits,short gIndex) 
	{
		//Body pBody;
		BodyDef def = new BodyDef();
		def.position.set(x/PPM, y/PPM);
		def.fixedRotation = !canRotate;
		
		if(isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox((width/2)/PPM, (height/2)/PPM);
		
		//pBody = world.createBody(def);
		
		FixtureDef fdef = new FixtureDef();
	    fdef.shape = shape;
	    fdef.density = 1.0f;
	    fdef.filter.categoryBits = cBits;
	    fdef.filter.maskBits     = mBits;
	    fdef.filter.groupIndex   = gIndex;
		
		
		shape.dispose();
		
		return world.createBody(def).createFixture(fdef).getBody();
	}
}
