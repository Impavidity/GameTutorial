package engineTester;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
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
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GUIControl;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import particles.Particle;
import particles.ParticleMaster;
import particles.ParticleSystem;
import particles.ParticleTexture;
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
	
	public static int textTime = 0;
	
	public static boolean [][] detectMap = new boolean[(int)Terrain.getSize()][(int)Terrain.getSize()];
	public static float UIratio;
	
	public static void main(String[] args) {
		
		

		DisplayManager.createDisplay();
		UIratio = (float)DisplayManager.WIDTH / (float)DisplayManager.HEIGHT;
		Loader loader = new Loader();
		

		// Text
		TextMaster.init(loader);
		FontType font = new FontType(loader.loadTexture("arial"), new File("res/arial.fnt"));
		
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
		
		
		ModelData houseData = OBJFileLoader.loadOBJ("whouse");
		RawModel houseModel = loader.loadToVAO(houseData.getVertices(), 
				houseData.getTextureCoords(), 
				houseData.getNormals(),
				houseData.getIndices());
		TexturedModel house = new TexturedModel(houseModel,
				new ModelTexture(loader.loadTexture("whouse")));
		house.getTexture().setHasTransparency(true);
		
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
		
		ModelData princeData = OBJFileLoader.loadOBJ("small_prince");
		RawModel princeModel = loader.loadToVAO(princeData.getVertices(), 
				princeData.getTextureCoords(), 
				princeData.getNormals(),
				princeData.getIndices());
		TexturedModel prince = new TexturedModel(princeModel,
				new ModelTexture(loader.loadTexture("prince")));
		

		ModelData palmData = OBJFileLoader.loadOBJ("fire");
		RawModel palmModel = loader.loadToVAO(palmData.getVertices(), 
				palmData.getTextureCoords(), 
				palmData.getNormals(),
				palmData.getIndices());
		TexturedModel palm = new TexturedModel(palmModel,
				new ModelTexture(loader.loadTexture("fire")));
		
		Player player;
		Player player1 = new Player(prince, new Vector3f(800, 0, 800), 0f, 0f, 0f ,0.2f);
		Player player2 = new Player(bunny, new Vector3f(800, 0 , 800), 0 ,0, 0, 1);
		player = player1;
		List<Terrain>terrains = new ArrayList<Terrain>();
		Terrain terrain = new Terrain(0,0,loader, texturePack, blendMap, "heightmap");
		terrains.add(terrain);
		
		
		List<Entity> entities = new ArrayList<Entity>();
		Random random = new Random();
		for (int i = 0; i< 5; i++) {

				float x = random.nextFloat() *800;
				float z = random.nextFloat() *800;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(house,
						new Vector3f(x,y,z),
						0, 0, 0, 7f, treeType));
				for (int p=(int)x -5 ; p<=(int)x +5; p++) 
					for (int q=(int)z -5; q<=(int)z+5; q++) {
						if (p<0 || p>=Terrain.getSize() || q<0 || q>=Terrain.getSize())
							continue;
						detectMap[p][q] = true;
					}
		}
		entities.add(player);
			
		
		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light( new Vector3f(800, 1000, 800), new Vector3f(1f, 1f, 1f));
		lights.add(new Light(new Vector3f(165, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1f, 0.01f, 0.02f)));
		lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1f, 0.01f, 0.02f)));
		lights.add(sun);
		
		entities.add(new Entity(lamp, new Vector3f(165,terrain.getHeightOfTerrain(165, -293),-293),	0, 0, 0, 1f, lampType));
		
		
		Camera camera =new Camera(player);
		
		// ************************* GUI ********************************************
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		
		float gui_dis = 0.165f;
		float z_base = 0.91f;
		float x_base = -0.95f;
		float gui_scale = 0.04f;
		
		GuiTexture guitorch = new GuiTexture(loader.loadTexture("torch"),new Vector2f(x_base, z_base), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guitorch);
		GuiTexture guiwood = new GuiTexture(loader.loadTexture("pine-tree"),new Vector2f(x_base, z_base - gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guiwood);
		GuiTexture guiheartbeat = new GuiTexture(loader.loadTexture("heart-beats"),new Vector2f(x_base, z_base - 2*gui_dis), new Vector2f(gui_scale,gui_scale*UIratio));
		guis.add(guiheartbeat);
		GuiTexture guimeat = new GuiTexture(loader.loadTexture("meat"),new Vector2f(x_base, z_base - 3*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guimeat);
		GuiTexture guistone = new GuiTexture(loader.loadTexture("stone-block"),new Vector2f(x_base, z_base - 4*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guistone);
		GuiTexture mineral = new GuiTexture(loader.loadTexture("minerals"),new Vector2f(x_base, z_base - 5*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(mineral);
		GuiTexture guispade = new GuiTexture(loader.loadTexture("sword-spade"),new Vector2f(x_base, z_base - 6*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guispade);
		GuiTexture guiflame = new GuiTexture(loader.loadTexture("flame"),new Vector2f(x_base, z_base - 7*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guiflame);
		GuiTexture guicampfire = new GuiTexture(loader.loadTexture("campfire"),new Vector2f(x_base, z_base - 8*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guicampfire);
		GuiTexture guihouse = new GuiTexture(loader.loadTexture("church"),new Vector2f(x_base, z_base - 9*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guihouse);
		GuiTexture guiboot = new GuiTexture(loader.loadTexture("leather-boot"),new Vector2f(x_base, z_base - 10*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guiboot);
		GuiTexture guiboat = new GuiTexture(loader.loadTexture("sailboat"),new Vector2f(x_base, z_base - 11*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guiboat);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		
		// ************************** GUI ***********************************************
		//Terrain terrain2 = new Terrain(0, 2,loader, texturePack, blendMap, "heightmap");
		
		MasterRenderer renderer = new MasterRenderer(loader);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		Entity lampEntity = new Entity(lamp, new Vector3f(293, -6.8f, -305)
				, 0, 0, 0, 1, lampType);
		entities.add(lampEntity);
		Light light = new Light(new Vector3f(200, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1f, 0.01f, 0.02f));
		lights.add(light);
		

		entities.add(new Entity(palm,
				new Vector3f(100, 250 , -50),
				0, 0, 0, 1f, flowerType));
			

		List<GUIText> packText = new ArrayList<GUIText>();

		Count count = new Count();
		EntityDetect entityDetect = new EntityDetect(font, count, packText);
		
		GUIControl uiManager = new GUIControl(count, guis, loader, packText, font);
		
		
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
		
		ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("fire2"), 1);

		ParticleSystem system  = new ParticleSystem(particleTexture, 40, 8, 0.1f, 4, 2);
		system.randomizeRotation();
		system.setDirection(new Vector3f(0, 1, 0), 0.05f);
		system.setLifeError(0.05f);
		system.setSpeedError(0.4f);
		system.setScaleError(3.0f);

		
		//******************************Gameloop  Begin*********************
		
		while(!Display.isCloseRequested()){
			
			camera.move();
			player.move(terrain, entities);
			picker.update();
			
			//system.generateParticles(player.getPosition());
			
			ParticleMaster.update(camera);
			Vector3f terrainPoint = picker.getCurrentTerrainPoint();
			
			//click the tree 
			if(terrainPoint != null){
				entityDetect.isEntity(terrainPoint, entities, player.getPosition());
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
			
			ParticleMaster.renderParticles(camera);
			
			guiRenderer.render(guis); 
			
			if (entityDetect.getTextStatus()) {
				textTime += 1;
				if (textTime == 100) {
					entityDetect.textShutDown();
					player.setTexturedModel(bunny);
				}
				System.out.println(textTime);
			}
			
			
			uiManager.check();
			uiManager.checkUIClick();
			TextMaster.render();
			
			DisplayManager.updateDisplay();
			
		}
		ParticleMaster.cleanUp();
		TextMaster.cleanUp(); 
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanup();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

}