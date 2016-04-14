package main.java.VolatiliaOGL.shaders.basic;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import main.java.VolatiliaOGL.entities.Light;
import main.java.VolatiliaOGL.shaders.BaseShader;

public class StaticShader extends BaseShader
{
	private static final int MAX_LIGHTS = 4;

	private static final String VERTEX_FILE = "/main/java/VolatiliaOGL/shaders/basic/vertexShader.txt";
	private static final String FRAGMENT_FILE = "/main/java/VolatiliaOGL/shaders/basic/fragmentShader.txt";

	private int locationTransformationMatrix;
	private int[] locationLightPosition;
	private int[] locationLightColor;
	private int[] locationLightAttenuation;
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
		
		locationLightPosition = new int[MAX_LIGHTS];
		locationLightColor = new int[MAX_LIGHTS];
		locationLightAttenuation = new int[MAX_LIGHTS];
		for(int i = 0; i < MAX_LIGHTS; i++)
		{
			locationLightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			locationLightColor[i] = super.getUniformLocation("lightColor[" + i + "]");
			locationLightAttenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(this.locationTransformationMatrix, matrix);
	}

	public void loadLights(List<Light> lights)
	{
		int end = lights.size() > MAX_LIGHTS ? MAX_LIGHTS : lights.size();
		for(int i = 0; i < end; i++)
		{
			if(i < lights.size())
			{
				super.loadVector(this.locationLightPosition[i], lights.get(i).getPosition());
				super.loadVector(this.locationLightColor[i], lights.get(i).getColor());
				super.loadVector(this.locationLightAttenuation[i], lights.get(i).getAttenuation());
			}
			else
			{
				super.loadVector(this.locationLightPosition[i], new Vector3f(0, 0, 0));
				super.loadVector(this.locationLightColor[i], new Vector3f(0, 0, 0));
				super.loadVector(this.locationLightAttenuation[i], new Vector3f(1, 0, 0));
			}
		}
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
