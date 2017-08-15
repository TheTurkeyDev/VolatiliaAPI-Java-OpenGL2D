package main.java.VolatiliaOGL.shaders.terrain;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import main.java.VolatiliaOGL.entities.Light;
import main.java.VolatiliaOGL.shaders.BaseShader;

public class TerrainShader extends BaseShader
{
	private static final int MAX_LIGHTS = 4;

	private static final String VERTEX_FILE = "/main/java/VolatiliaOGL/shaders/terrain/terrainVertexShader.txt";
	private static final String FRAGMENT_FILE = "/main/java/VolatiliaOGL/shaders/terrain/terrainFragmentShader.txt";

	private int locationTransformationMatrix;
	private int[] locationLightPosition;
	private int[] locationLightColor;
	private int[] locationLightAttenuation;
	private int locationShineDampen;
	private int locationReflectivity;
	private int locationBackgroundTexture;
	private int locationRTexture;
	private int locationGTexture;
	private int locationBTexture;
	private int locationBlendMap;
	private int locationDensity;
	private int locationGradient;

	public TerrainShader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
	}

	@Override
	protected void getAllUniformLocations()
	{
		this.locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		this.locationShineDampen = super.getUniformLocation("shineDamper");
		this.locationReflectivity = super.getUniformLocation("reflectivity");
		this.locationBackgroundTexture = super.getUniformLocation("backgroundTexture");
		this.locationRTexture = super.getUniformLocation("rTexture");
		this.locationGTexture = super.getUniformLocation("gTexture");
		this.locationBTexture = super.getUniformLocation("bTexture");
		this.locationBlendMap = super.getUniformLocation("blendMap");
		this.locationDensity = super.getUniformLocation("density");
		this.locationGradient = super.getUniformLocation("gradient");

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
		for(int i = 0; i < MAX_LIGHTS; i++)
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

	public void loadShineVariables(float dampen, float reflectivity)
	{
		super.loadFloat(this.locationShineDampen, dampen);
		super.loadFloat(this.locationReflectivity, reflectivity);
	}
	public void connectTextureUnits()
	{
		super.loadInt(this.locationBackgroundTexture, 0);
		super.loadInt(this.locationRTexture, 1);
		super.loadInt(this.locationGTexture, 2);
		super.loadInt(this.locationBTexture, 3);
		super.loadInt(this.locationBlendMap, 4);
	}

	public void loadFogData(float density, float gradient)
	{
		super.loadFloat(this.locationDensity, density);
		super.loadFloat(this.locationGradient, gradient);
	}
}