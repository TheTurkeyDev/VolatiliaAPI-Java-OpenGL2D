package main.java.VolatiliaOGL.entities;

import org.lwjgl.util.vector.Vector2f;

public class Camera
{
	private Vector2f position = new Vector2f(0, 0);
	private float rotation = 15;

	public Camera(Player player)
	{
		
	}

	public void move()
	{
		this.rotation = 0;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public float getRotation()
	{
		return rotation;
	}
}
