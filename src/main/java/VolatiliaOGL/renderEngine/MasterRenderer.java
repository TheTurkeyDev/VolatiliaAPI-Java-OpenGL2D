package main.java.VolatiliaOGL.renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import main.java.VolatiliaOGL.entities.Camera;
import main.java.VolatiliaOGL.entities.Entity;
import main.java.VolatiliaOGL.entities.Light;
import main.java.VolatiliaOGL.game.Terrain;
import main.java.VolatiliaOGL.models.TexturedModel;
import main.java.VolatiliaOGL.shaders.basic.StaticShader;
import main.java.VolatiliaOGL.shaders.terrain.TerrainShader;

public class MasterRenderer
{
	public static final MasterRenderer INSTANCE = new MasterRenderer();

	public static final float SKYRED = 0.5444f;
	public static final float SKYGREEN = 0.62f;
	public static final float SKYBLUE = 0.69f;

	private Matrix4f projectionMatrix;

	private StaticShader shader = new StaticShader();
	private TerrainShader terrainShader = new TerrainShader();

	private EntityRenderer renderer;
	private TerrainRenderer terainRenderer;

	private Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private Map<TexturedModel, List<Entity>> normalMapEntities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();

	public MasterRenderer()
	{
		renderer = new EntityRenderer(shader);
		terainRenderer = new TerrainRenderer(terrainShader);
	}

	public void render(List<Light> lights, Camera camera, Vector4f clipPlane)
	{
		prepare();

		shader.start();
		shader.loadClipedPlane(clipPlane);
		shader.loadSkyColor(SKYRED, SKYGREEN, SKYBLUE);
		shader.loadLights(lights);
		renderer.render(entities);
		shader.stop();

		terrainShader.start();
		terrainShader.loadSkyColor(SKYRED, SKYGREEN, SKYBLUE);
		terrainShader.loadLights(lights);
		terainRenderer.render(terrains);
		terrainShader.stop();

		entities.clear();
		normalMapEntities.clear();
		terrains.clear();
	}

	public void renderScene(List<Entity> entities, List<Terrain> terrains, List<Light> lights, Camera camera, Vector4f clipPlane)
	{
		for(Terrain terrain : terrains)
			this.processTerrain(terrain);
		for(Entity entity : entities)
			this.processEntity(entity);
		this.render(lights, camera, clipPlane);
	}

	public void processTerrain(Terrain terrain)
	{
		terrains.add(terrain);
	}

	public void processEntity(Entity entity)
	{
		TexturedModel model = entity.getModel();
		List<Entity> batch = entities.get(model);
		if(batch != null)
		{
			batch.add(entity);
		}
		else
		{
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(model, newBatch);
		}
	}

	public void prepare()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
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
	
	public Matrix4f getProjectionMatrix()
	{
		return this.projectionMatrix;
	}
}
