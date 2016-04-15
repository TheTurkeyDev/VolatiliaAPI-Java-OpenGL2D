package main.java.VolatiliaOGL.renderEngine;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import main.java.VolatiliaOGL.gui.ShapeRectangle;
import main.java.VolatiliaOGL.models.RawModel;
import main.java.VolatiliaOGL.shaders.shapes.ShapeShader;
import main.java.VolatiliaOGL.util.Loader;
import main.java.VolatiliaOGL.util.MathUtil;

public class ShapeRenderer
{
	private final RawModel quad;
	private ShapeShader shader;

	public ShapeRenderer()
	{
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = Loader.INSTANCE.loadToVAO(positions, 2);
		shader = new ShapeShader();
	}

	public void render(List<ShapeRectangle> shapes)
	{
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		for(ShapeRectangle rect : shapes)
		{
			shader.loadShapeColor(rect.getColor());
			Matrix4f matrix = MathUtil.createTransformationMatrix(rect.getPosition(), rect.getScale());
			shader.loadTransformationMatrix(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp()
	{
		shader.cleanUp();
	}
}
