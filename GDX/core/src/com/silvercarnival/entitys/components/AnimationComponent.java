package com.silvercarnival.entitys.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Array;

public class AnimationComponent implements Component {

	
	public int curAnim;
	public int idle;
	public Array<Animation> anims;
	
	public AnimationComponent(int _idle,Array<Animation> _anims)
	{
		this.idle = _idle;
		this.curAnim = _idle;
		this.anims = _anims;
	}
	
}
