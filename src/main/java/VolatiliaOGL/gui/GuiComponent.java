package main.java.VolatiliaOGL.gui;

import org.lwjgl.util.vector.Vector2f;

public class GuiComponent
{
	private String name;
	
	private int texureID;
	
	private Vector2f position;
	private Vector2f scale;

	public int getTexureID()
	{
		return texureID;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public Vector2f getScale()
	{
		return scale;
	}

	public GuiComponent(String name, int texureID, Vector2f position, Vector2f scale)
	{
		this.name = name;
		this.texureID = texureID;
		this.position = position;
		this.scale = scale;
	}

	public void render()
	{
		
	}

	public String getName()
	{
		return this.name;
	}
}
