package main.java.VolatiliaOGL.renderEngine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import main.java.VolatiliaOGL.entities.Camera;
import main.java.VolatiliaOGL.entities.Entity;
import main.java.VolatiliaOGL.entities.Light;
import main.java.VolatiliaOGL.game.Terrain;
import main.java.VolatiliaOGL.shaders.basic.StaticShader;
import main.java.VolatiliaOGL.shaders.terrain.TerrainShader;

public class MasterRenderer
{
	public static final MasterRenderer INSTANCE = new MasterRenderer();

	public static final float SKYRED = 0.5444f;
	public static final float SKYGREEN = 0.62f;
	public static final float SKYBLUE = 0.69f;

	private StaticShader shader = new StaticShader();	
	private TerrainShader terrainShader = new TerrainShader();

	private EntityRenderer renderer;
	private TerrainRenderer terainRenderer;

	private List<Entity> entities = new ArrayList<Entity>();
	private List<Terrain> terrains = new ArrayList<Terrain>();

	public MasterRenderer()
	{
		renderer = new EntityRenderer(shader);
		terainRenderer = new TerrainRenderer(terrainShader);
	}

	public void render(List<Light> lights, Camera camera)
	{
		prepare();

		shader.start();
		shader.loadLights(lights);
		renderer.render(entities);
		shader.stop();

		terrainShader.start();
		terrainShader.loadLights(lights);
		terainRenderer.render(terrains);
		terrainShader.stop();

		entities.clear();
		terrains.clear();
	}

	public void renderScene(List<Entity> entities, List<Terrain> terrains, List<Light> lights, Camera camera)
	{
		for(Terrain terrain : terrains)
			this.processTerrain(terrain);
		for(Entity entity : entities)
			this.processEntity(entity);
		this.render(lights, camera);
	}

	public void processTerrain(Terrain terrain)
	{
		terrains.add(terrain);
	}

	public void processEntity(Entity entity)
	{
		entities.add(entity);
	}

	public void prepare()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(SKYRED, SKYGREEN, SKYBLUE, 1);
	}

	public static void enableCulling()
	{
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling()
	{
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public void cleanUp()
	{
		this.shader.cleanUp();
		this.terrainShader.cleanUp();
	}
}
