package main.java.VolatiliaOGL.shaders.basic;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import main.java.VolatiliaOGL.entities.Light;
import main.java.VolatiliaOGL.shaders.BaseShader;

public class StaticShader extends BaseShader
{
	private static final String VERTEX_FILE = "/main/java/VolatiliaOGL/shaders/basic/vertexShader.txt";
	private static final String FRAGMENT_FILE = "/main/java/VolatiliaOGL/shaders/basic/fragmentShader.txt";

	private int locationTransformationMatrix;
	private int locationTextureAtlasRows;
	private int locationTextureOffset;

	public StaticShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations()
	{
		this.locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		this.locationTextureAtlasRows = super.getUniformLocation("numberOfRows");
		this.locationTextureOffset = super.getUniformLocation("offset");
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(this.locationTransformationMatrix, matrix);
	}

	public void loadLights(List<Light> lights)
	{
		
	}
	
	public void loadNumberOfTextureAtlasRows(int num)
	{
		super.loadFloat(this.locationTextureAtlasRows, num);
	}

	public void loadTextureOffset(float x, float y)
	{
		super.loadVector(this.locationTextureOffset, new Vector2f(x, y));
	}
}
