package main.java.VolatiliaOGL.shaders.shapes;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import main.java.VolatiliaOGL.shaders.BaseShader;

public class ShapeShader extends BaseShader
{
	private static final String VERTEX_FILE = "/main/java/VolatiliaOGL/shaders/shapes/shapeVertexShader.txt";
	private static final String FRAGMENT_FILE = "/main/java/VolatiliaOGL/shaders/shapes/shapeFragmentShader.txt";

	private int locationTransformationMatrix;
	private int locationColor;

	public ShapeShader()
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
		this.locationColor = super.getUniformLocation("color");
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(this.locationTransformationMatrix, matrix);
	}
	
	public void loadShapeColor(Vector4f color)
	{
		super.loadVector(this.locationColor, color);
	}
}
