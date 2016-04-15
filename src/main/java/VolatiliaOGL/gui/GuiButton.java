package main.java.VolatiliaOGL.gui;

import org.lwjgl.util.vector.Vector2f;

public class GuiButton extends GuiComponent
{
	public String displayText;
	
	public GuiButton(String name, int textID, Vector2f position, Vector2f scale, String text)
	{
		super(name, textID, position, scale);
		this.displayText = text;
	}

	public void render()
	{
		super.render();
	}
}