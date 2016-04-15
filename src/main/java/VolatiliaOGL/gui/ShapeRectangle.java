package main.java.VolatiliaOGL.gui;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

public class ShapeRectangle
{	
	private Vector4f color;
	
	private Vector2f position;
	private Vector2f scale;

	public ShapeRectangle(Vector4f color, Vector2f position, Vector2f scale)
	{
		this.color = color;
		this.position = position;
		this.scale = scale;
	}
	
	public Vector4f getColor()
	{
		return color;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public Vector2f getScale()
	{
		return scale;
	}

	public void render()
	{
		
	}
}
