package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.RawModel;
import models.TexturedModel;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

public class MainGameLoop {

	public static void main(String[] args) {


		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		RawModel model = OBJLoader.loadObjModel("tree", loader);
		ModelTexture texture = new ModelTexture(loader.loadTexture("tree"));
		TexturedModel staticModel = new TexturedModel(model, texture);
		
		//texture.setShineDamper(10);
		//texture.setRelectivity(1);
		Entity entity = new Entity(staticModel, new Vector3f(100,0,0), 0,0,0,1);
		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1,1,1));
		Camera camera =new Camera();
		
		/*
		List<Entity> allDragons = new ArrayList<Entity>();
		Random random = new Random();
		
		for (int i = 0; i< 10; i++) {
			float x= random.nextFloat() * 100 - 50;
			float y = random.nextFloat() * 100 - 50;
			float z = random.nextFloat() * -300;
			allDragons.add(new Entity(staticModel, new Vector3f(x,y,z), random.nextFloat()*180f, random.nextFloat()*180f, 0f, 01f));
		}
		*/
		
		Terrain terrain = new Terrain(0,0,loader, new ModelTexture(loader.loadTexture("grass")));
		Terrain terrain2 = new Terrain(1,0,loader, new ModelTexture(loader.loadTexture("grass")));
		
		
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()){
			entity.increaseRotation(0, 1, 0);
			camera.move();
			/*
			for (Entity dragon: allDragons) {
				renderer.processEntity(dragon);
			}
			*/
			renderer.processEntity(entity);
			renderer.processTerrain(terrain);
			renderer.processTerrain(terrain2);
				
			renderer.render(light, camera);
			DisplayManager.updateDisplay();
			
		}
		renderer.clearUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}