package engineTester;

import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.PrimitiveIterator.OfDouble;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.sun.javafx.webkit.theme.Renderer;
import com.sun.jndi.url.corbaname.corbanameURLContextFactory;
import com.sun.xml.internal.ws.client.sei.ResponseBuilder.RpcLit;

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


	
	public static List<TexturedModel> texturedModels = new ArrayList<TexturedModel>();
	public static int [] index = new int[20];
	
	public static int textTime = 0;
	
	public static boolean [][] detectMap = new boolean[(int)Terrain.getSize()][(int)Terrain.getSize()];
	public static float UIratio;
	
	public static void main(String[] args) {
		
		
		int cc =0;
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
		
/*		ModelData palletData = OBJFileLoader.loadOBJ("house");
		RawModel palletModel = loader.loadToVAO(palletData.getVertices(),
				palletData.getTextureCoords(),
				palletData.getNormals(),
				palletData.getIndices());
		TexturedModel pallet = new TexturedModel(palletModel,
				new ModelTexture(loader.loadTexture("house")));*/

		
		ModelData treeData = OBJFileLoader.loadOBJ("tree");		
		RawModel treeModel = loader.loadToVAO(treeData.getVertices(), 
				treeData.getTextureCoords(), 
				treeData.getNormals(), 
				treeData.getIndices());
		TexturedModel tree = new TexturedModel(treeModel, 
						new ModelTexture(loader.loadTexture("tree")));
		texturedModels.add(tree);
		index[EntityDetect.tTree] = cc++;
		
		
/*		ModelData flowerData = OBJFileLoader.loadOBJ("grassModel");
		RawModel flowerModel = loader.loadToVAO(flowerData.getVertices(), 
				flowerData.getTextureCoords(), 
				flowerData.getNormals(),
				flowerData.getIndices());
		TexturedModel flower = new TexturedModel(flowerModel,
				new ModelTexture(loader.loadTexture("flower")));
		flower.getTexture().setHasTransparency(true);
		flower.getTexture().setUseFakeLighting(true);
		texturedModels.add(flower);
		index[EntityDetect.tf] = cc++;*/
		
		
/*		ModelData fernData = OBJFileLoader.loadOBJ("fern");
		RawModel fernModel = loader.loadToVAO(fernData.getVertices(), 
				fernData.getTextureCoords(), 
				fernData.getNormals(),
				fernData.getIndices());
		TexturedModel fern = new TexturedModel(fernModel,
				new ModelTexture(loader.loadTexture("fernTexture")));
		fern.getTexture().setHasTransparency(true);
		texturedModels.add(fern);
		index[Entity.fernType] = cc++;
	*/	
		ModelData lowPolyTreeData = OBJFileLoader.loadOBJ("lowPolyTree");
		RawModel lowPolyTreeModel = loader.loadToVAO(lowPolyTreeData.getVertices(), 
				lowPolyTreeData.getTextureCoords(), 
				lowPolyTreeData.getNormals(),
				lowPolyTreeData.getIndices());
		TexturedModel lowPolyTree = new TexturedModel(lowPolyTreeModel,
				new ModelTexture(loader.loadTexture("lowPolyTree")));
		
		ModelData lampData = OBJFileLoader.loadOBJ("lamp");
		RawModel lampModel = loader.loadToVAO(lampData.getVertices(), 
				lampData.getTextureCoords(), 
				lampData.getNormals(),
				lampData.getIndices());
		TexturedModel lamp = new TexturedModel(lampModel,
				new ModelTexture(loader.loadTexture("lamp")));
		lamp.getTexture().setHasTransparency(true);
		texturedModels.add(lamp);
		index[EntityDetect.tTorch] = cc++;
		
/*		ModelData bunnyData = OBJFileLoader.loadOBJ("person");
		RawModel bunnyModel = loader.loadToVAO(bunnyData.getVertices(), 
				bunnyData.getTextureCoords(), 
				bunnyData.getNormals(),
				bunnyData.getIndices());
		TexturedModel bunny = new TexturedModel(bunnyModel,
				new ModelTexture(loader.loadTexture("playerTexture")));
		texturedModels.add(bunny);*/

		
		ModelData fireplaceData = OBJFileLoader.loadOBJ("fire");
		RawModel fireplaceModel = loader.loadToVAO(fireplaceData.getVertices(), 
				fireplaceData.getTextureCoords(), 
				fireplaceData.getNormals(),
				fireplaceData.getIndices());
		TexturedModel fireplace = new TexturedModel(fireplaceModel,
				new ModelTexture(loader.loadTexture("fireplace")));
		texturedModels.add(fireplace);
		index[EntityDetect.tFirecamp] = cc++;
		
/*		ModelData grassData = OBJFileLoader.loadOBJ("grassModel");
		RawModel grassModel = loader.loadToVAO(grassData.getVertices(), 
				grassData.getTextureCoords(), 
				grassData.getNormals(),
				grassData.getIndices());
		TexturedModel grass = new TexturedModel(grassModel,
				new ModelTexture(loader.loadTexture("grassTexture")));
		grass.getTexture().setHasTransparency(true);
		grass.getTexture().setUseFakeLighting(true);
		texturedModels.add(grass);*/

/*		ModelData houseData = OBJFileLoader.loadOBJ("whouse");
		RawModel houseModel = loader.loadToVAO(houseData.getVertices(), 
				houseData.getTextureCoords(), 
				houseData.getNormals(),
				houseData.getIndices());
		TexturedModel house = new TexturedModel(houseModel,
				new ModelTexture(loader.loadTexture("whouse")));
		house.getTexture().setHasTransparency(true);
		texturedModels.add(house);*/
		
		ModelData princeData = OBJFileLoader.loadOBJ("small_prince");
		RawModel princeModel = loader.loadToVAO(princeData.getVertices(), 
				princeData.getTextureCoords(), 
				princeData.getNormals(),
				princeData.getIndices());
		TexturedModel prince = new TexturedModel(princeModel,
				new ModelTexture(loader.loadTexture("prince")));
		texturedModels.add(prince);
		index[EntityDetect.tPlayer] = cc++;
		
		ModelData rockData = OBJFileLoader.loadOBJ("boulder");
		RawModel rockModel = loader.loadToVAO(rockData.getVertices(), 
				rockData.getTextureCoords(), 
				rockData.getNormals(),
				rockData.getIndices());
		TexturedModel rock = new TexturedModel(rockModel,
				new ModelTexture(loader.loadTexture("white")));
		texturedModels.add(rock);
		index[EntityDetect.tStone] = cc++;		
		
		ModelData oldhouseData = OBJFileLoader.loadOBJ("oldhouse");
		RawModel oldhouseModel = loader.loadToVAO(oldhouseData.getVertices(), 
				oldhouseData.getTextureCoords(), 
				oldhouseData.getNormals(),
				oldhouseData.getIndices());
		TexturedModel oldhouse = new TexturedModel(oldhouseModel,
				new ModelTexture(loader.loadTexture("oldhouse")));
		index[EntityDetect.tHouse] = cc++;
		texturedModels.add(oldhouse);
		
		List<Entity> entities = new ArrayList<Entity>();
		/*
		for(int i=1;i<=24;i++){
			ModelData basictempleData = OBJFileLoader.loadOBJ("t" + i);
			System.out.println("Here" + i);
			RawModel basictempleModel = loader.loadToVAO(basictempleData.getVertices(), 
					basictempleData.getTextureCoords(), 
					basictempleData.getNormals(),
					basictempleData.getIndices());
			TexturedModel basictemple = new TexturedModel(basictempleModel,
					new ModelTexture(loader.loadTexture("box")));
			entities.add(new Entity(basictemple, new Vector3f(800, 10, 800), 0, 0, 0, 1, 14));
		}
		*/
		ModelData basictempleData = OBJFileLoader.loadOBJ("temple");
		RawModel basictempleModel = loader.loadToVAO(basictempleData.getVertices(), 
				basictempleData.getTextureCoords(), 
				basictempleData.getNormals(),
				basictempleData.getIndices());
		TexturedModel basictemple = new TexturedModel(basictempleModel,
				new ModelTexture(loader.loadTexture("box")));

		texturedModels.add(basictemple);
		index[EntityDetect.tTemple] = cc++;
		
		ModelData ballData = OBJFileLoader.loadOBJ("ball");
		RawModel ballModel = loader.loadToVAO(ballData.getVertices(), 
				ballData.getTextureCoords(), 
				ballData.getNormals(),
				ballData.getIndices());
		TexturedModel ball = new TexturedModel(ballModel,
				new ModelTexture(loader.loadTexture("ball")));
		texturedModels.add(ball);
		index[EntityDetect.tTemple] = cc++;
		
		ModelData mineralData = OBJFileLoader.loadOBJ("boulder");
		RawModel mineralModel = loader.loadToVAO(mineralData.getVertices(), 
				mineralData.getTextureCoords(), 
				mineralData.getNormals(),
				mineralData.getIndices());
		TexturedModel mine = new TexturedModel(mineralModel,
				new ModelTexture(loader.loadTexture("mineral")));
		texturedModels.add(mine);
		index[EntityDetect.tMineral] = cc++;		
		//entities.add(new Entity(mine, new Vector3f(1000, 10, 800), 0, 0, 0, 6.0f, 14));
		
		ModelData boatData = OBJFileLoader.loadOBJ("boat");
		RawModel boatModel = loader.loadToVAO(boatData.getVertices(), 
				boatData.getTextureCoords(), 
				boatData.getNormals(),
				boatData.getIndices());
		TexturedModel boat = new TexturedModel(boatModel,
				new ModelTexture(loader.loadTexture("boat")));
		texturedModels.add(boat);
		index[EntityDetect.tBoat] = cc++;		
		//entities.add(new Entity(boat, new Vector3f(700, 30, 700), 0, 0, 0, 10.0f, EntityDetect.tBoat));
		
		ModelData rabbitData = OBJFileLoader.loadOBJ("Rabbit");
		RawModel rabbitModel = loader.loadToVAO(rabbitData.getVertices(), 
				rabbitData.getTextureCoords(), 
				rabbitData.getNormals(),
				rabbitData.getIndices());
		TexturedModel rabbit = new TexturedModel(rabbitModel,
				new ModelTexture(loader.loadTexture("rabbit")));
		texturedModels.add(rabbit);
		index[EntityDetect.tFood] = cc++;		
		//entities.add(new Entity(rabbit, new Vector3f(600, 30, 600), 0, 0, 0, 10.0f, EntityDetect.tRabbit));
		
		Player player = new Player(prince, new Vector3f(700, 0, 700), 0f, 0f, 0f ,0.2f);

		List<Terrain>terrains = new ArrayList<Terrain>();
		Terrain terrain = new Terrain(0,0,loader, texturePack, blendMap, "heightmap");
		terrains.add(terrain);
		
		
		
		Random random = new Random();
		entities.add(new Entity(basictemple, new Vector3f(500, 30, 800), 0, 0, 0, 1.5f, 14));
		for (int i = 0; i< 400; i++) {
				float x = random.nextFloat() *1600;
				float z = random.nextFloat() *1600;
				float y = terrain.getHeightOfTerrain(x, z);
				entities.add(new Entity(tree,
						new Vector3f(x,y,z),0, 0, 0,  2.0f+random.nextFloat()*2.5f, EntityDetect.tTree));
				for (int p=(int)x -5 ; p<=(int)x +5; p++) 
					for (int q=(int)z -5; q<=(int)z+5; q++) {
						if (p<0 || p>=Terrain.getSize() || q<0 || q>=Terrain.getSize())
							continue;
						detectMap[p][q] = true;
					}
		}
		for (int i = 0; i< 400; i++) {
			float x = random.nextFloat() *1600;
			float z = random.nextFloat() *1600;
			float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(lowPolyTree,
					new Vector3f(x,y,z),0, 0, 0, random.nextFloat()*2.5f, EntityDetect.tTree));
			for (int p=(int)x -5 ; p<=(int)x +5; p++) 
				for (int q=(int)z -5; q<=(int)z+5; q++) {
					if (p<0 || p>=Terrain.getSize() || q<0 || q>=Terrain.getSize())
						continue;
					detectMap[p][q] = true;
				}
	}
		
		for (int i = 0; i< 300; i++) {
			float x = random.nextFloat() *1600;
			float z = random.nextFloat() *1600;
			float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(rock,
					new Vector3f(x,y,z),0, 0, 0,  random.nextFloat()*2.0f, EntityDetect.tStone));
			for (int p=(int)x -5 ; p<=(int)x +5; p++) 
				for (int q=(int)z -5; q<=(int)z+5; q++) {
					if (p<0 || p>=Terrain.getSize() || q<0 || q>=Terrain.getSize())
						continue;
					detectMap[p][q] = true;
				}
	}
		
		for (int i = 0; i< 100; i++) {
			float x = random.nextFloat() *1600;
			float z = random.nextFloat() *1600;
			float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(mine,
					new Vector3f(x,y,z),0, 0, 0,  random.nextFloat()*1.0f, EntityDetect.tMineral));
			for (int p=(int)x -5 ; p<=(int)x +5; p++) 
				for (int q=(int)z -5; q<=(int)z+5; q++) {
					if (p<0 || p>=Terrain.getSize() || q<0 || q>=Terrain.getSize())
						continue;
					detectMap[p][q] = true;
				}
	}
		for (int i = 0; i< 30; i++) {
			float x = random.nextFloat() *1600;
			float z = random.nextFloat() *1600;
			float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(rabbit,
					new Vector3f(x,y,z),0, 0, 0,  random.nextFloat()* 1.5f, EntityDetect.tFood));
			for (int p=(int)x -5 ; p<=(int)x +5; p++) 
				for (int q=(int)z -5; q<=(int)z+5; q++) {
					if (p<0 || p>=Terrain.getSize() || q<0 || q>=Terrain.getSize())
						continue;
					detectMap[p][q] = true;
				}
	}
		for (int i = 0; i< 50; i++) {
			float x = random.nextFloat() *1600;
			float z = random.nextFloat() *1600;
			float y = terrain.getHeightOfTerrain(x, z);
			entities.add(new Entity(rabbit,
					new Vector3f(x,y,z),0, 0, 0, 8.0f, EntityDetect.tFood));
			for (int p=(int)x -5 ; p<=(int)x +5; p++) 
				for (int q=(int)z -5; q<=(int)z+5; q++) {
					if (p<0 || p>=Terrain.getSize() || q<0 || q>=Terrain.getSize())
						continue;
					detectMap[p][q] = true;
				}
	}
		
		
		
		entities.add(player);
		MasterRenderer renderer = new MasterRenderer(loader);
		
		List<Light> lights = new ArrayList<Light>();
		float lightness;		
		lights.add(new Light(new Vector3f(165, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1f, 0.01f, 0.02f)));
		lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1f, 0.01f, 0.02f)));		
		//entities.add(new Entity(lamp, new Vector3f(165,terrain.getHeightOfTerrain(165, -293),-293),	0, 0, 0, 1f, Entity.lampType));
		
		
		Camera camera =new Camera(player);
		
		// ************************* GUI ********************************************
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		
		float gui_dis = 0.165f;
		float z_base = 0.91f;
		float x_base = -0.95f;
		float gui_scale = 0.04f;
		float gui_dis_text = 0.0825f;
		float z_base_text = 0.054f;
		float x_base_text = 0.029f;
		float gui_scale_text = 1f;
		
		GuiTexture guiheartbeat = new GuiTexture(loader.loadTexture("heart-beats"),new Vector2f(x_base, z_base), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guiheartbeat);
		
		GuiTexture guiwood = new GuiTexture(loader.loadTexture("pine-tree"),new Vector2f(x_base, z_base - gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guiwood);
		
		GuiTexture guistone = new GuiTexture(loader.loadTexture("stone-block"),new Vector2f(x_base, z_base - 2*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guistone);
		
		GuiTexture mineral = new GuiTexture(loader.loadTexture("minerals"),new Vector2f(x_base, z_base - 3*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(mineral);
		
		GuiTexture guimeat = new GuiTexture(loader.loadTexture("meat"),new Vector2f(x_base, z_base - 4*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guimeat);
		
		GuiTexture guispade = new GuiTexture(loader.loadTexture("sword-spade"),new Vector2f(x_base, z_base - 5*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guispade);
		
		GuiTexture guiflame = new GuiTexture(loader.loadTexture("flame"),new Vector2f(x_base, z_base - 6*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guiflame);
		
		GuiTexture guicampfire = new GuiTexture(loader.loadTexture("campfire"),new Vector2f(x_base, z_base - 7*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guicampfire);
		
		GuiTexture guihouse = new GuiTexture(loader.loadTexture("church"),new Vector2f(x_base, z_base - 8*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guihouse);
		
		GuiTexture guitorch = new GuiTexture(loader.loadTexture("torch"),new Vector2f(x_base, z_base - 9*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guitorch);
		
		GuiTexture guiboot = new GuiTexture(loader.loadTexture("leather-boot"),new Vector2f(x_base, z_base - 10*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guiboot);
		
		GuiTexture guiboat = new GuiTexture(loader.loadTexture("sailboat"),new Vector2f(x_base, z_base - 11*gui_dis), new Vector2f(gui_scale, gui_scale*UIratio));
		guis.add(guiboat);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		
		// ************************** GUI ***********************************************
		//Terrain terrain2 = new Terrain(0, 2,loader, texturePack, blendMap, "heightmap");
		

		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		//Entity lampEntity = new Entity(lamp, new Vector3f(293, -6.8f, -305)
		//		, 0, 0, 0, 1, Entity.lampType);
		//entities.add(lampEntity);
		Light light = new Light(new Vector3f(200, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1f, 0.01f, 0.02f));
		lights.add(light);
			

		List<GUIText> packText = new ArrayList<GUIText>();

		Count count = new Count();
		EntityDetect entityDetect = new EntityDetect(font, count, packText);
		
		GUIControl uiManager = new GUIControl(count, guis, loader, packText, font);
		
		
		//T
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
		
		List<ParticleSystem> systems = new ArrayList<ParticleSystem>();
		List<Vector3f> sysPos = new ArrayList<Vector3f>();
		ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("fire2"), 1);



		
		//******************************Gameloop  Begin*********************
		
		//lightness = renderer.getLightness();
		lightness = 1.0f;
		Light sun = new Light( new Vector3f(800, 1000, 800), new Vector3f(lightness, lightness, lightness));
		Entity entityClick = null;
		ParticleSystem system = null;
		boolean entityClickFlag = false;
		lights.add(sun);
		
		int HeartCount = 0;
		
		//
		while(!Display.isCloseRequested()){
			lightness = renderer.getLightness();
			//sun.setColour(new Vector3f(lightness,lightness,lightness));
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
			for (Entity entity : entities) {
				if (entity == null) System.out.println("There is a null");
			}
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
					//player.setTexturedModel(bunny);
				}
				System.out.println(textTime);
			}
			/*
			if(Keyboard.isKeyDown(Keyboard.KEY_F1)){
				entities.add(new Entity(fireplace, terrainPoint,
						0, 0, 0, 1f, flowerType));
			}
			*/
			//system.generateParticles(player.getPosition());
			if (entityClickFlag == false) {
				entityClick = uiManager.checkKeyWithObject(entities, picker);
				system = uiManager.checkKeyWithParticle(picker, particleTexture);
			}
			
			if (entityClick != null) {
				entityClickFlag = true;
				if (terrainPoint!=null) {
					entityClick.setPosition(new Vector3f(terrainPoint.x, terrainPoint.y+30.0f, terrainPoint.z));
					if (Mouse.isButtonDown(0)) {
						if(entityClick.getEntityType() == entityDetect.tBoat && 
								terrainPoint.getX()<1600 && terrainPoint.getX() >0 
								&& terrainPoint.getZ() <1600 && terrainPoint.getZ()>0)
						{
							entityDetect.textPopUp("Boat should be put out of the terrain!");
						}
						else{
						entityClick = null;
						entityClickFlag = false;
						}
					} else {
						
					}
				}
			}
			if (system != null ) {
				entityClickFlag = true;
				systems.add(system);
				if (terrainPoint!=null) {
					system.generateParticles(terrainPoint);
					if (Mouse.isButtonDown(0)) {
							sysPos.add(terrainPoint);
							System.out.println("i am in");
							System.out.println(sysPos.get(0));
							system = null;				
							entityClickFlag = false;
							count.minusCount(EntityDetect.tFire, 1);
					}
				}
			}
			int i=0;
			if(sysPos.size() != 0){
				for (ParticleSystem system2 : systems){
					System.out.println(i);
					if (i>=sysPos.size())
						break;
					else{ 
						system2.generateParticles(sysPos.get(i));
						i++;
					}
				}
			}
			
			uiManager.checkUIClick();
			TextMaster.render();
			HeartCount ++;
			if (HeartCount == 2000) {
				entityDetect.heartDecrease();
				if (count.getCount(EntityDetect.tHeart) >= 0) {
					packText.get(EntityDetect.tHeart).remove();
					Vector2f pos = new Vector2f(x_base_text  ,z_base_text + (gui_dis_text*(float)(EntityDetect.tHeart)));
					String sHeart = count.getCount(EntityDetect.tHeart) + "";
					if (count.getCount(EntityDetect.tHeart)<10) {
						sHeart = "0" + sHeart;
					}
					System.out.println("Heart " + sHeart + " "+pos.x +" "  +pos.y);
					GUIText pack = new GUIText(sHeart , gui_scale_text, font, pos , 1.5f, false);
					pack.setColour(0, 0, 0);
					packText.set(EntityDetect.tHeart, pack);
				}
				HeartCount = 0;
			}
			if (count.getCount(EntityDetect.tHeart) <= 0) {
				entityDetect.textPopUp("Game Over");
			}
			if (count.getCount(EntityDetect.tHeart) <= -20) {
				break;
			}
			uiManager.GoodTrans();
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