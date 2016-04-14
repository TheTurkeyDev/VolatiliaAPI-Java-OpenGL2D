package main.java.VolatiliaOGL.game;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import main.java.VolatiliaOGL.entities.Camera;
import main.java.VolatiliaOGL.entities.Entity;
import main.java.VolatiliaOGL.entities.Light;
import main.java.VolatiliaOGL.renderEngine.MasterRenderer;

public class World
{
	public static float fogDensity = 0.0035f;
	public static float fogGradient = 5;

	private int worldID;

	private Light sun = null;
	private List<Light> lights = new ArrayList<Light>();
	private List<Terrain> terrains = new ArrayList<Terrain>();
	private List<Entity> entities = new ArrayList<Entity>();

	public World(int id)
	{
		this.worldID = id;
	}
	
	public int getWorldD()
	{
		return this.worldID;
	}
	
	public void renderWorld(Camera camera)
	{
		MasterRenderer.INSTANCE.renderScene(entities, terrains, this.getNearestNLights(camera.getPosition(), 4, true), camera, new Vector4f(0, -1, 0, 100000));
	}
	
	public void addEntityToWorld(Entity ent)
	{
		this.entities.add(ent);
	}
	
	public void removeEntityFromWorld(Entity ent)
	{
		this.entities.remove(ent);
	}

	public void addLightSource(Light light)
	{
		lights.add(light);
	}

	public void removeLightSource(Light light)
	{
		lights.remove(light);
	}

	public List<Light> getNearestNLights(Vector2f position, int numberOfLights, boolean sun)
	{
		return this.getNearestNLights(position.x, position.y, numberOfLights, sun);
	}
	
	public List<Light> getNearestNLights(float x, float y, int numberOfLights, boolean sun)
	{
		Vector2f source = new Vector2f(x, y);
		List<Light> lightsToReturn = new ArrayList<Light>();
		Light furthest = null;
		for(Light l : lights)
		{
			if(lightsToReturn.size() < numberOfLights)
			{
				lightsToReturn.add(l);

				if(furthest == null)
					furthest = l;
				else if(this.distanceBetween(furthest.getPosition(), source) < this.distanceBetween(l.getPosition(), source))
					furthest = l;
			}
			else
			{
				if(this.distanceBetween(furthest.getPosition(), source) > this.distanceBetween(l.getPosition(), source))
				{
					lightsToReturn.remove(furthest);
					lightsToReturn.add(l);
					furthest = l;
					for(Light light : lightsToReturn)
						if(this.distanceBetween(furthest.getPosition(), source) < this.distanceBetween(light.getPosition(), source))
							furthest = light;
				}
			}
		}

		if(sun)
		{
			lightsToReturn.remove(furthest);
			lightsToReturn.add(this.sun);
		}

		return lightsToReturn;
	}

	private float distanceBetween(Vector2f p1, Vector2f p2)
	{
		return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}

	public Light getSun()
	{
		return this.sun;
	}

	public void setSun(Light sun)
	{
		this.sun = sun;
	}

	public boolean addTerrain(Terrain t)
	{
		for(Terrain terrain : terrains)
			if(terrain.getGridX() == t.getGridX() && terrain.getGridZ() == t.getGridZ())
				return false;
		terrains.add(t);
		return true;
	}

	public void removeTerrain(Terrain t)
	{
		terrains.remove(t);
	}

	public List<Terrain> getTerrains()
	{
		return this.terrains;
	}

	public Terrain getTerrainAt(float x, float z)
	{
		int gridX = (int) (x / Terrain.SIZE);
		int gridZ = (int) (z / Terrain.SIZE);

		if(x < 0)
			gridX--;
		if(z < 0)
			gridZ--;

		for(Terrain terrain : terrains)
			if(terrain.getGridX() == gridX && terrain.getGridZ() == gridZ)
				return terrain;
		return null;
	}

	public void cleanUp()
	{
		
	}
}