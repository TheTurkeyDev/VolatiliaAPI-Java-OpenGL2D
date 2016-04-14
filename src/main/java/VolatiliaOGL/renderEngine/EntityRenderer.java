package main.java.VolatiliaOGL.renderEngine;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import main.java.VolatiliaOGL.entities.Entity;
import main.java.VolatiliaOGL.models.RawModel;
import main.java.VolatiliaOGL.models.TexturedModel;
import main.java.VolatiliaOGL.shaders.basic.StaticShader;
import main.java.VolatiliaOGL.textures.ModelTexture;
import main.java.VolatiliaOGL.util.Loader;
import main.java.VolatiliaOGL.util.MathUtil;

public class EntityRenderer
{
	private final RawModel quad;
	private StaticShader shader;

	public EntityRenderer(StaticShader shader)
	{
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = Loader.INSTANCE.loadToVAO(positions, 2);
		this.shader = shader;
	}

	public void render(Map<TexturedModel, List<Entity>> entities)
	{
		shader.start();
		for(TexturedModel model : entities.keySet())
		{
			GL30.glBindVertexArray(quad.getVaoID());
			GL20.glEnableVertexAttribArray(0);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
			for(Entity ent : entities.get(model))
			{
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getTextureID());
				Matrix4f matrix = MathUtil.createTransformationMatrix(ent.getPosition(), ent.getScale());
				shader.loadTransformationMatrix(matrix);
				GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
			}
			GL11.glEnable(GL11.GL_DEPTH_TEST);
			GL11.glDisable(GL11.GL_BLEND);
			GL20.glDisableVertexAttribArray(0);
			GL30.glBindVertexArray(0);
		}
		shader.stop();
	}
	


	private void prepareTexturedModel(TexturedModel model)
	{
		RawModel rmodel = model.getRawModel();
		GL30.glBindVertexArray(rmodel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);

		ModelTexture texture = model.getTexture();
		shader.loadNumberOfTextureAtlasRows(texture.getNumberOfRows());

	}

	private void unbindTexturedModel()
	{
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(Entity entity)
	{
		Matrix4f transformationMatrix = MathUtil.createTransformationMatrix(entity.getPosition(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		shader.loadTextureOffset(entity.getTextureXOffset(), entity.getTextureYOffset());
	}
}