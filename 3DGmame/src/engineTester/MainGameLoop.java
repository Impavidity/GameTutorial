package engineTester;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class MainGameLoop {

	public static void main(String[] args) {


		DisplayManager.createDisplay();
		
		Loader loader = new Loader();
		
		// ********Terrain Texture Stuff *********
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
		
		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		// ***************************************
		
		ModelData treeData = OBJFileLoader.loadOBJ("tree");
		
		RawModel treeModel = loader.loadToVAO(treeData.getVertices(), 
				treeData.getTextureCoords(), 
				treeData.getNormals(), 
				treeData.getIndices());
		
		//TexturedModel staticModel = new TexturedModel(OBJLoader.loadObjModel("tree", loader), 
		//		new ModelTexture(loader.loadTexture("tree")));
		TexturedModel tree = new TexturedModel(treeModel, 
						new ModelTexture(loader.loadTexture("tree")));
		
		ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
		RawModel grassModel = loader.loadToVAO(grassData.getVertices(), 
				grassData.getTextureCoords(), 
				grassData.getNormals(),
				grassData.getIndices());
		TexturedModel grass = new TexturedModel(grassModel,
				new ModelTexture(loader.loadTexture("grassTexture")));
		
		//TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
		//		new ModelTexture(loader.loadTexture("grassTexture")));
		
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		
		//TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader),
		//		new ModelTexture(loader.loadTexture("fern")));
		
		
		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		RawModel fernModel = loader.loadToVAO(fernData.getVertices(), 
				fernData.getTextureCoords(), 
				fernData.getNormals(),
				fernData.getIndices());
		TexturedModel fern = new TexturedModel(fernModel,
				new ModelTexture(loader.loadTexture("fernTexture")));
		fern.getTexture().setHasTransparency(true);
		
		//texture.setShineDamper(10);
		//texture.setRelectivity(1);
		
		
		ModelData lowPolyTreeData = OBJFileLoader.loadOBJ("lowPolyTree");
		RawModel lowPolyTreeModel = loader.loadToVAO(lowPolyTreeData.getVertices(), 
				lowPolyTreeData.getTextureCoords(), 
				lowPolyTreeData.getNormals(),
				lowPolyTreeData.getIndices());
		TexturedModel lowPolyTree = new TexturedModel(lowPolyTreeModel,
				new ModelTexture(loader.loadTexture("lowPolyTree")));
		
		
		ModelData flowerData = OBJFileLoader.loadOBJ("grassModel");
		RawModel flowerModel = loader.loadToVAO(flowerData.getVertices(), 
				flowerData.getTextureCoords(), 
				flowerData.getNormals(),
				flowerData.getIndices());
		TexturedModel flower = new TexturedModel(flowerModel,
				new ModelTexture(loader.loadTexture("flower")));
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		
		ModelData bunnyData = OBJFileLoader.loadOBJ("bunny");
		RawModel bunnyModel = loader.loadToVAO(bunnyData.getVertices(), 
				bunnyData.getTextureCoords(), 
				bunnyData.getNormals(),
				bunnyData.getIndices());
		TexturedModel bunny = new TexturedModel(bunnyModel,
				new ModelTexture(loader.loadTexture("white")));
		
		Player player = new Player(bunny, new Vector3f(100, 0 , -50), 0 ,0, 0, 1);
		
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i< 500; i++) {
			entities.add(new Entity(tree,
					new Vector3f(random.nextFloat() * 800 - 400, 0 , random.nextFloat() *- 600),
					0, 0, 0, 3));
			entities.add(new Entity(grass,
					new Vector3f(random.nextFloat()* 800 - 400, 0, random.nextFloat() *-600),
					0, 0, 0, 1));
			entities.add(new Entity(fern, 
					new Vector3f(random.nextFloat()* 800 - 400, 0, random.nextFloat() *- 600),
					0, 0, 0, 0.6f));
			entities.add(new Entity(lowPolyTree,
					new Vector3f(random.nextFloat() * 800 - 400, 0 , random.nextFloat() *- 600),
					0, 0, 0, 0.5f));
			entities.add(new Entity(flower,
					new Vector3f(random.nextFloat() * 800 - 400, 0 , random.nextFloat() *- 600),
					0, 0, 0, 1f));
		}
		
		
		
		
		Light light = new Light(new Vector3f(3000, 2000, 2000), new Vector3f(1,1,1));
		Camera camera =new Camera(player);
		
		
		Terrain terrain = new Terrain(-1,-1,loader, texturePack, blendMap);
		Terrain terrain2 = new Terrain(0, -1,loader, texturePack, blendMap);
		
		
		
		MasterRenderer renderer = new MasterRenderer();
		while(!Display.isCloseRequested()){
			
			camera.move();
			player.move();
			renderer.processEntity(player);
			
			for (Entity entity: entities) {
				renderer.processEntity(entity);
			}
			
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