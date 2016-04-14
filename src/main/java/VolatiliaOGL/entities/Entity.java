package main.java.VolatiliaOGL.entities;

import org.lwjgl.util.vector.Vector2f;

import main.java.VolatiliaOGL.models.TexturedModel;

public class Entity
{
	private TexturedModel model;
	private Vector2f position;
	private float rotation;
	private Vector2f scale;
	private float velX, velY;

	private int textureIndex = 0;

	public Entity(TexturedModel model, Vector2f position, float rotation, Vector2f scale)
	{
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	public Entity(TexturedModel model, Vector2f position, float rotation, Vector2f scale, int textureIndex)
	{
		this.model = model;
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
		this.textureIndex = textureIndex;
	}

	public float getTextureXOffset()
	{
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column / (float) model.getTexture().getNumberOfRows();
	}

	public float getTextureYOffset()
	{
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float) row / (float) model.getTexture().getNumberOfRows();
	}

	public void increasePosition(float dx, float dy)
	{
		this.position.x += dx;
		this.position.y += dy;
	}

	public void increaseRotation(float rotation)
	{
		this.rotation += rotation;
	}

	public TexturedModel getModel()
	{
		return model;
	}

	public void setModel(TexturedModel model)
	{
		this.model = model;
	}

	public Vector2f getPosition()
	{
		return position;
	}

	public void setPosition(Vector2f position)
	{
		this.position = position;
	}

	public float getRotation()
	{
		return rotation;
	}

	public void setRotation(float rotation)
	{
		this.rotation = rotation;
	}

	public Vector2f getScale()
	{
		return scale;
	}

	public void setScale(Vector2f scale)
	{
		this.scale = scale;
	}

	public float getVelocityX()
	{
		return velX;
	}

	public void setVelocityX(float velX)
	{
		this.velX = velX;
	}

	public float getVelocityY()
	{
		return velY;
	}

	public void setVelocityY(float velY)
	{
		this.velY = velY;
	}
}