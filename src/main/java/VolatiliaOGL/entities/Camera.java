package main.java.VolatiliaOGL.entities;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class Camera
{
	private float zoom = 50;

	private Vector2f position = new Vector2f(0, 0);
	private float rotation = 15;

	private Player player;

	public Camera(Player player)
	{
		this.player = player;
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

	private void calculateZoom()
	{
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		this.zoom -= zoomLevel;
	}
}
