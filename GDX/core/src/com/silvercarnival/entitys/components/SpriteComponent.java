package com.silvercarnival.entitys.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteComponent implements Component {

	public Sprite sprite;
	
	public SpriteComponent(Sprite _sprite) {
		this.sprite = _sprite;
	}

}
