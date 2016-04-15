package main.java.VolatiliaOGL.entities;

import org.lwjgl.util.vector.Vector2f;

import main.java.VolatiliaOGL.game.Terrain;
import main.java.VolatiliaOGL.models.TexturedModel;

public class Player extends Entity
{
	public Player(TexturedModel model, Vector2f positon, float rot, Vector2f scale)
	{
		super(model, positon, rot, scale);
	}

	public void move(Terrain terrain)
	{
		
	}
}