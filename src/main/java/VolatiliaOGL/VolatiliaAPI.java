package main.java.VolatiliaOGL;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

import main.java.VolatiliaOGL.screen.ScreenManager;
import main.java.VolatiliaOGL.settings.VideoSettings;
import main.java.VolatiliaOGL.util.Loader;

public class VolatiliaAPI
{
	public static final String VERSION = "Indev 1.2.2";
	public static VolatiliaAPI instance;

	private static long lastFrameTime;
	private static float delta;

	public static void createDisplay()
	{
		ContextAttribs attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);
		try
		{
			Display.setDisplayMode(new DisplayMode(VideoSettings.WIDTH, VideoSettings.HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle("TEST API 2D - Version: " + VolatiliaAPI.VERSION);
			Display.setResizable(true);
		} catch(LWJGLException e)
		{
			e.printStackTrace();
		}

		GL11.glClearColor(1f, 1f, 1f, 0f);
		GL11.glViewport(0, 0, VideoSettings.WIDTH, VideoSettings.HEIGHT);
		lastFrameTime = getCurrentTime();
	}

	public static void updateDisplay()
	{
		Display.sync(VideoSettings.fpsCap);
		Display.update();

		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}

	public static void startAPIRun()
	{
		while(!Display.isCloseRequested())
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			ScreenManager.getInstance().getCurrentScreen().render();

			VolatiliaAPI.updateDisplay();
		}

		cleanUpGame();
	}

	public static void cleanUpGame()
	{
		ScreenManager.getInstance().finalCleanUpAllScreens();

		// MasterRenderer.INSTANCE.cleanUp();

		Loader.INSTANCE.CleanUp();

		VolatiliaAPI.closeDisplay();
	}

	public static void closeDisplay()
	{
		Display.destroy();
	}

	private static long getCurrentTime()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	public static float getFrameTimeSeconds()
	{
		return delta;
	}
}