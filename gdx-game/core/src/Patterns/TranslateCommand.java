package Patterns;

import com.badlogic.gdx.physics.box2d.Body;

public class TranslateCommand implements Command {

	
	private Body body;
	private float x, y, bodyX, bodyY;
	private boolean wake, linearVelocity = false;
	
	public TranslateCommand(Body body)
	{
		this.body = body;
	}
	
	@Override
	public void execute(Body body) {
		
		if(linearVelocity)
		{
			body.setLinearVelocity(x, y);
		}
		else{
			
			if(bodyX != 0 & bodyY != 0) body.applyForce(x, y, bodyX,bodyY,wake);
			else body.applyForceToCenter(x, y, wake);
		}
		
	}
	
	public void applyForceToCenter(float x, float y,boolean wake)
	{
		this.x = x;
		this.y = y;
		this.wake = wake;
		bodyX = body.getLocalCenter().x;
		bodyY = body.getLocalCenter().y;
		linearVelocity = false;
		execute(this.body);
	}
	
	public void setLinearVelocity(float x, float y)
	{
		this.x = x;
		this.y = y;
		this.wake = true;
		bodyX = body.getLocalCenter().x;
		bodyY = body.getLocalCenter().y;
		linearVelocity = true;
		execute(this.body);
	}
	
	public Body getBody()
	{
		return body;
	}
	
	public void setBody(Body body)
	{
		this.body = body;
	}
	
}
