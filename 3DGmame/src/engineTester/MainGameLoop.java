package engineTester;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.jndi.url.corbaname.corbanameURLContextFactory;

import entities.Camera;
import entities.Count;
import entities.Entity;
import entities.EntityDetect;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

public class MainGameLoop {

    public static final int treeType=1;
	public static final int flowerType = 2;
	public static final int fernType = 3;
	public static final int lowtreeType = 4;
	public static final int lampType =  5;
	public static final int playerType = 6;
	
	public static boolean [][] detectMap = new boolean[(int)Terrain.getSize()][(int)Terrain.getSize()];
	
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
		
		ModelData palletData = OBJFileLoader.loadOBJ("house");
		RawModel palletModel = loader.loadToVAO(palletData.getVertices(),
				palletData.getTextureCoords(),
				palletData.getNormals(),
				palletData.getIndices());
		TexturedModel pallet = new TexturedModel(palletModel,
				new ModelTexture(loader.loadTexture("house")));
		
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
		
		ModelData lampData = OBJFileLoader.loadOBJ("lamp");
		RawModel lampModel = loader.loadToVAO(lampData.getVertices(), 
				lampData.getTextureCoords(), 
				lampData.getNormals(),
				lampData.getIndices());
		TexturedModel lamp = new TexturedModel(lampModel,
				new ModelTexture(loader.loadTexture("lamp")));
		lamp.getTexture().setHasTransparency(true);
		
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
		
		ModelData bunnyData = OBJFileLoader.loadOBJ("person");
		RawModel bunnyModel = loader.loadToVAO(bunnyData.getVertices(), 
				bunnyData.getTextureCoords(), 
				bunnyData.getNormals(),
				bunnyData.getIndices());
		TexturedModel bunny = new TexturedModel(bunnyModel,
				new ModelTexture(loader.loadTexture("playerTexture")));
		
		Player player = new Player(bunny, new Vector3f(100, 250 , 50), 0 ,0, 0, 1);
		List<Terrain>terrains = new ArrayList<Terrain>();
		Terrain terrain = new Terrain(0,0,loader, texturePack, blendMap, "heightmap");
		terrains.add(terrain);
		
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i< 500; i++) {
			if(i % 10 == 0){
				float x = random.nextFloat() *800;
				float z = random.nextFloat() *800;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(fern, 
						new Vector3f(x, y, z),
						0, random.nextFloat() * 360, 0, 0.9f,fernType));
			}
			if (i%6 == 0){
				float x = random.nextFloat() *800;
				float z = random.nextFloat() *800;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(flower,
						new Vector3f(x,y,z),
						0, 0, 0, 1f, flowerType));
				
			}
			if (i%7 == 0) {
				float x = random.nextFloat() * 800;
				float z = random.nextFloat() * 800;
				float y = terrain.getHeightOfTerrain(x,	z);
				entities.add(new Entity(pallet,
						new Vector3f(x,y,z),
						0, 0, 0, 1f, flowerType));
			}
			if (i%10 == 0){
				float x = random.nextFloat() *800;
				float z = random.nextFloat() *800;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(tree,
						new Vector3f(x,y,z),
						0, 0, 0, 1f, treeType));
				for (int p=(int)x -5 ; p<=(int)x +5; p++) 
					for (int q=(int)z -5; q<=(int)z+5; q++) {
						if (p<0 || p>=Terrain.getSize() || q<0 || q>=Terrain.getSize())
							continue;
						detectMap[p][q] = true;
					}
						
				
			}
			if (i%18 == 0){
				float x = random.nextFloat() *800;
				float z = random.nextFloat() *800;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(lowPolyTree,
						new Vector3f(x, y, z),
						0, 0, 0, 1f, lowtreeType) );
				
				for (int p=(int)x -5 ; p<=(int)x +5; p++) 
					for (int q=(int)z -5; q<=(int)z+5; q++) {
						if (p<0 || p>=Terrain.getSize() || q<0 || q>=Terrain.getSize())
							continue;
						detectMap[p][q] = true;
					}
				
			}
			entities.add(player);
/*			entities.add(new Entity(tree,
					new Vector3f(random.nextFloat() * 800 - 400, 0 , random.nextFloat() *- 600),
					0, 0, 0, 3));
			entities.add(new Entity(grass,
					new Vector3f(random.nextFloat()* 800 - 400, 0, random.nextFloat() *800),
					0, 0, 0, 1));

			entities.add(new Entity(lowPolyTree,
					new Vector3f(random.nextFloat() * 800 - 400, 0 , random.nextFloat() *- 600),
					0, 0, 0, 0.5f));*/
			
		}
		
		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light( new Vector3f(400, 1000, -400), new Vector3f(0.2f, 0.2f, 0.2f));
		lights.add(new Light(new Vector3f(165, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1f, 0.01f, 0.02f)));
		lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1f, 0.01f, 0.02f)));
		lights.add(sun);
		
		entities.add(new Entity(lamp, new Vector3f(165,terrain.getHeightOfTerrain(165, -293),-293),	0, 0, 0, 1f, lampType));
		
		
		Camera camera =new Camera(player);
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"),new Vector2f(0.5f, 0.5f), new Vector2f(0.25f, 0.25f));
		guis.add(gui);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		
		
		//Terrain terrain2 = new Terrain(0, 2,loader, texturePack, blendMap, "heightmap");
		
		MasterRenderer renderer = new MasterRenderer(loader);
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		Entity lampEntity = new Entity(lamp, new Vector3f(293, -6.8f, -305)
				, 0, 0, 0, 1, lampType);
		entities.add(lampEntity);
		Light light = new Light(new Vector3f(200, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1f, 0.01f, 0.02f));
		lights.add(light);
			
		EntityDetect entityDetect = new EntityDetect();
		Count count = new Count();
		
		//TrueTypeFont font;
		//Font awtFont = new Font("Times New Roman", Font.BOLD, 24);
		//font = new TrueTypeFont(awtFont, false);
		
		//font.drawString(100, 50, "Hello world", Color.yellow);
		
		/* *********************** water render set up*********************   */
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(-500, 250, 0);
		waters.add(water);
		
		//******************************Gameloop  Begin*********************
		while(!Display.isCloseRequested()){
			
			camera.move();
			player.move(terrain, entities);
			picker.update();
			Vector3f terrainPoint = picker.getCurrentTerrainPoint();
			
			//click the tree 
			if(terrainPoint != null){
				entityDetect.isEntity(terrainPoint, entities, player.getPosition(), count);
			}
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			// render reflection
			buffers.bindReflectionFrameBuffer();
			float distance = 2* (camera.getPosition().y - water.getHeight());
			camera.getPosition().y  -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			//render refraction
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
			
			buffers.unbindCurrentFrameBuffer();			
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			renderer.renderScene(entities, terrains, lights, camera, new Vector4f(0, -1, 0, 10000));
			waterRenderer.render(waters, camera,sun);
			guiRenderer.render(guis); 
			DisplayManager.updateDisplay();
			
		}
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanup();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}