package guis;

import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import engineTester.MainGameLoop;
import entities.Count;
import entities.Entity;
import entities.EntityDetect;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import particles.ParticleSystem;
import particles.ParticleTexture;
import renderEngine.Loader;
import toolbox.MousePicker;

public class GUIControl {
	private Count count;
	private Loader loader;
	private List<GuiTexture> guis;
	private List<GUIText> packText;
	private FontType font;
	
	float gui_dis = 0.0825f;
	float z_base = 0.054f;
	float x_base = 0.029f;
	float gui_scale = 1f;
	
	public static int heartNfood = 1;
	
	public static int foodNrabbit = 1;
	
	public static int faxNtree = 1;
	public static int faxNmineral = 1;
	
	public static int fireNstone = 1;
	public static int fireNtree = 1;
	
	
	public static int firecampNstone = 5;
	public static int firecampNtree = 6;
	
	public static int torchNtree = 2;
	public static int torchNstone = 2;
	
	public static int houseNtree = 1;
	public static int houseNstone = 1;
	public static int houseNmineral = 30;
	
	public static int boatNtree = 1;
	public static int boatNstone = 1;
	public static int boatNmineral = 1;
	public static int boatNfood = 1;
	public static int boatNfax = 1;
	
	public static int bootNfood = 3;
	
	
	
	
	
	public GUIControl(Count count, List<GuiTexture> guis, Loader loader,List<GUIText> packText, FontType font) {
		this.count = count;
		this.guis = guis;
		this.loader = loader;
		this.packText = packText;
		this.font = font;
	}
	

	public void checkUIClick() {
		if (Mouse.isButtonDown(0)) {
			//System.out.println(Mouse.getX() +" " + Mouse.getY());
		}
	}
	
	public Entity checkKeyWithObject(List<Entity> entities, MousePicker picker ) {
		Vector3f terrainPoint = picker.getCurrentTerrainPoint();
		Entity temp = null;
			if (terrainPoint == null) return null;
			if (Keyboard.isKeyDown(Keyboard.KEY_F8) && Keyboard.isKeyDown(Keyboard.KEY_U) && count.getCount(EntityDetect.tFirecamp) >=1) {
				temp = new Entity(MainGameLoop.texturedModels.get(EntityDetect.tFirecamp), terrainPoint, 0, 0, 0, 1, EntityDetect.tFirecamp);
				entities.add(temp);
				count.minusCount(EntityDetect.tFirecamp, 1);
			}

			else if (Keyboard.isKeyDown(Keyboard.KEY_F10) && Keyboard.isKeyDown(Keyboard.KEY_U) && count.getCount(EntityDetect.tHouse) >=1) {
				temp = new Entity(MainGameLoop.texturedModels.get(EntityDetect.tHouse), terrainPoint, 0, 0, 0, 1, EntityDetect.tHouse);
				entities.add(temp);
				count.minusCount(EntityDetect.tHouse, 1);
			}
			else if(Keyboard.isKeyDown(Keyboard.KEY_F11) && Keyboard.isKeyDown(Keyboard.KEY_U) && count.getCount(EntityDetect.tBoat) >=1){
				temp = new Entity(MainGameLoop.texturedModels.get(EntityDetect.tBoat), terrainPoint, 0, 0, 0, 1, EntityDetect.tBoat);
				entities.add(temp);
				count.minusCount(EntityDetect.tBoat, 1);
				packText.get(EntityDetect.tBoat).remove();
				Vector2f pos6 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tBoat));
				GUIText pack6 = new GUIText(count.getCount(EntityDetect.tBoat) + "" , gui_scale, font, pos6 , 1.5f, false);
				packText.set(EntityDetect.tBoat, pack6);
			}
			
		return temp;
	}
	
	public ParticleSystem checkKeyWithParticle(MousePicker picker, ParticleTexture particleTexture){
		ParticleSystem system = null;
		if (Keyboard.isKeyDown(Keyboard.KEY_F7) && Keyboard.isKeyDown(Keyboard.KEY_U) && count.getCount(EntityDetect.tFire) >=1) {
			system = new ParticleSystem(particleTexture, 40, 8, 0.1f, 4, 2);
			system.randomizeRotation();
			system.setDirection(new Vector3f(0, 1, 0), 0.05f);
			system.setLifeError(0.05f);
			system.setSpeedError(0.4f);
			system.setScaleError(3.0f);
			system.generateParticles(picker.getCurrentTerrainPoint());
			count.minusCount(EntityDetect.tFire, 1);
			packText.get(EntityDetect.tFire).remove();
			Vector2f pos3 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tFire));
			GUIText pack3 = new GUIText(count.getCount(EntityDetect.tFire) + "" , gui_scale, font, pos3 , 1.5f, false);
			packText.set(EntityDetect.tFire, pack3);
		}
		return system;
	}
	
	public void GoodTrans(){
		if (Keyboard.isKeyDown(Keyboard.KEY_F5)) {
			if (count.getCount(EntityDetect.tFood) >= heartNfood) {
				count.minusCount(EntityDetect.tFood, heartNfood);
				count.setCount(EntityDetect.tHeart);
				
				packText.get(EntityDetect.tFood).remove();
				Vector2f pos1 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tFood));
				GUIText pack1 = new GUIText(count.getCount(EntityDetect.tFood) + "" , gui_scale, font, pos1 , 1.5f, false);
				packText.set(EntityDetect.tFood, pack1);
				packText.get(EntityDetect.tHeart).remove();
				Vector2f pos2 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tHeart));
				GUIText pack2 = new GUIText(count.getCount(EntityDetect.tHeart) + "" , gui_scale, font, pos2 , 1.5f, false);
				packText.set(EntityDetect.tFood, pack2);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F6)) {
			if (count.getCount(EntityDetect.tTree) >= faxNtree && count.getCount(EntityDetect.tMineral) >= faxNmineral) {
				count.minusCount(EntityDetect.tTree, 10);
				count.minusCount(EntityDetect.tMineral, faxNmineral);
				count.setCount(EntityDetect.tFax);
				
				packText.get(EntityDetect.tTree).remove();
				Vector2f pos1 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tTree));
				GUIText pack1 = new GUIText(count.getCount(EntityDetect.tTree) + "" , gui_scale, font, pos1 , 1.5f, false);
				packText.set(EntityDetect.tTree, pack1);
				packText.get(EntityDetect.tMineral).remove();
				Vector2f pos2 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tMineral));
				GUIText pack2 = new GUIText(count.getCount(EntityDetect.tMineral) + "" , gui_scale, font, pos2 , 1.5f, false);
				packText.set(EntityDetect.tMineral, pack2);
				packText.get(EntityDetect.tFax).remove();
				Vector2f pos3 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tFax));
				GUIText pack3 = new GUIText(count.getCount(EntityDetect.tFax) + "" , gui_scale, font, pos3 , 1.5f, false);
				packText.set(EntityDetect.tFax, pack3);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F7)) {
			if (count.getCount(EntityDetect.tTree) >= fireNtree && count.getCount(EntityDetect.tStone) >= fireNstone) {
				count.minusCount(EntityDetect.tTree, fireNtree);
				count.minusCount(EntityDetect.tStone, fireNstone);
				count.setCount(EntityDetect.tFire);
				
				packText.get(EntityDetect.tTree).remove();
				Vector2f pos1 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tTree));
				GUIText pack1 = new GUIText(count.getCount(EntityDetect.tTree) + "" , gui_scale, font, pos1 , 1.5f, false);
				packText.set(EntityDetect.tTree, pack1);
				packText.get(EntityDetect.tStone).remove();
				Vector2f pos2 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tStone));
				GUIText pack2 = new GUIText(count.getCount(EntityDetect.tStone) + "" , gui_scale, font, pos2 , 1.5f, false);
				packText.set(EntityDetect.tStone, pack2);
				packText.get(EntityDetect.tFire).remove();
				Vector2f pos3 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tFire));
				GUIText pack3 = new GUIText(count.getCount(EntityDetect.tFire) + "" , gui_scale, font, pos3 , 1.5f, false);
				packText.set(EntityDetect.tFire, pack3);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F8)) {
			if (count.getCount(EntityDetect.tTree) >= firecampNtree && count.getCount(EntityDetect.tStone) >= firecampNstone) {
				count.minusCount(EntityDetect.tTree, firecampNtree);
				count.minusCount(EntityDetect.tStone, firecampNstone);
				count.setCount(EntityDetect.tFirecamp);
				
				packText.get(EntityDetect.tTree).remove();
				Vector2f pos1 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tTree));
				GUIText pack1 = new GUIText(count.getCount(EntityDetect.tTree) + "" , gui_scale, font, pos1 , 1.5f, false);
				packText.set(EntityDetect.tTree, pack1);
				packText.get(EntityDetect.tStone).remove();
				Vector2f pos2 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tStone));
				GUIText pack2 = new GUIText(count.getCount(EntityDetect.tStone) + "" , gui_scale, font, pos2 , 1.5f, false);
				packText.set(EntityDetect.tStone, pack2);
				packText.get(EntityDetect.tFirecamp).remove();
				Vector2f pos3 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tFirecamp));
				GUIText pack3 = new GUIText(count.getCount(EntityDetect.tFirecamp) + "" , gui_scale, font, pos3 , 1.5f, false);
				packText.set(EntityDetect.tFirecamp, pack3);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F9)) {
			if (count.getCount(EntityDetect.tTree) >= torchNtree && count.getCount(EntityDetect.tStone) >= torchNstone) {
				count.minusCount(EntityDetect.tTree, torchNtree);
				count.minusCount(EntityDetect.tStone, torchNstone);
				count.setCount(EntityDetect.tTorch);
				
				packText.get(EntityDetect.tTree).remove();
				Vector2f pos1 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tTree));
				GUIText pack1 = new GUIText(count.getCount(EntityDetect.tTree) + "" , gui_scale, font, pos1 , 1.5f, false);
				packText.set(EntityDetect.tTree, pack1);
				packText.get(EntityDetect.tStone).remove();
				Vector2f pos2 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tStone));
				GUIText pack2 = new GUIText(count.getCount(EntityDetect.tStone) + "" , gui_scale, font, pos2 , 1.5f, false);
				packText.set(EntityDetect.tStone, pack2);
				packText.get(EntityDetect.tTorch).remove();
				Vector2f pos3 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tTorch));
				GUIText pack3 = new GUIText(count.getCount(EntityDetect.tTorch) + "" , gui_scale, font, pos3 , 1.5f, false);
				packText.set(EntityDetect.tTorch, pack3);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F10)) {
			if (count.getCount(EntityDetect.tTree) >= houseNtree && count.getCount(EntityDetect.tStone) >= houseNstone &&
					count.getCount(EntityDetect.tMineral) >= houseNmineral) {
				count.minusCount(EntityDetect.tTree, houseNtree);
				count.minusCount(EntityDetect.tStone, houseNstone);
				count.minusCount(EntityDetect.tMineral, houseNmineral);
				count.setCount(EntityDetect.tHouse);
				
				packText.get(EntityDetect.tTree).remove();
				Vector2f pos1 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tTree));
				GUIText pack1 = new GUIText(count.getCount(EntityDetect.tTree) + "" , gui_scale, font, pos1 , 1.5f, false);
				packText.set(EntityDetect.tTree, pack1);
				packText.get(EntityDetect.tStone).remove();
				Vector2f pos2 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tStone));
				GUIText pack2 = new GUIText(count.getCount(EntityDetect.tStone) + "" , gui_scale, font, pos2 , 1.5f, false);
				packText.set(EntityDetect.tStone, pack2);
				packText.get(EntityDetect.tMineral).remove();
				Vector2f pos3 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tMineral));
				GUIText pack3 = new GUIText(count.getCount(EntityDetect.tMineral) + "" , gui_scale, font, pos3 , 1.5f, false);
				packText.set(EntityDetect.tMineral, pack3);
				packText.get(EntityDetect.tHouse).remove();
				Vector2f pos4 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tHouse));
				GUIText pack4 = new GUIText(count.getCount(EntityDetect.tHouse) + "" , gui_scale, font, pos4 , 1.5f, false);
				packText.set(EntityDetect.tHouse, pack4);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F11)) {
			if ( count.getCount(EntityDetect.tFood) >= bootNfood) {
				count.minusCount(EntityDetect.tFood, bootNfood);
				count.setCount(EntityDetect.tBoot);
				
				packText.get(EntityDetect.tFood).remove();
				Vector2f pos4 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tFood));
				GUIText pack4 = new GUIText(count.getCount(EntityDetect.tFood) + "" , gui_scale, font, pos4 , 1.5f, false);
				packText.set(EntityDetect.tFood, pack4);
				packText.get(EntityDetect.tBoot).remove();
				Vector2f pos5 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tBoot));
				GUIText pack5 = new GUIText(count.getCount(EntityDetect.tBoot) + "" , gui_scale, font, pos5 , 1.5f, false);
				packText.set(EntityDetect.tBoot, pack5);
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F11)) {
			if (count.getCount(EntityDetect.tTree) >= 40 && count.getCount(EntityDetect.tStone) >= 40 &&
					count.getCount(EntityDetect.tMineral) >= 40 && count.getCount(EntityDetect.tFood) >= 20
					&& count.getCount(EntityDetect.tFax)>= 3) {
				count.minusCount(EntityDetect.tTree, 40);
				count.minusCount(EntityDetect.tStone, 40);
				count.minusCount(EntityDetect.tMineral, 40);
				count.minusCount(EntityDetect.tFood, 20);
				count.minusCount(EntityDetect.tFax, 3);
				count.setCount(EntityDetect.tBoat);
				
				packText.get(EntityDetect.tTree).remove();
				Vector2f pos1 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tTree));
				GUIText pack1 = new GUIText(count.getCount(EntityDetect.tTree) + "" , gui_scale, font, pos1 , 1.5f, false);
				packText.set(EntityDetect.tTree, pack1);
				packText.get(EntityDetect.tStone).remove();
				Vector2f pos2 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tStone));
				GUIText pack2 = new GUIText(count.getCount(EntityDetect.tStone) + "" , gui_scale, font, pos2 , 1.5f, false);
				packText.set(EntityDetect.tStone, pack2);
				packText.get(EntityDetect.tMineral).remove();
				Vector2f pos3 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tMineral));
				GUIText pack3 = new GUIText(count.getCount(EntityDetect.tMineral) + "" , gui_scale, font, pos3 , 1.5f, false);
				packText.set(EntityDetect.tMineral, pack3);
				packText.get(EntityDetect.tFood).remove();
				Vector2f pos4 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tFood));
				GUIText pack4 = new GUIText(count.getCount(EntityDetect.tFood) + "" , gui_scale, font, pos4 , 1.5f, false);
				packText.set(EntityDetect.tFood, pack4);
				packText.get(EntityDetect.tFax).remove();
				Vector2f pos5 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tFax));
				GUIText pack5 = new GUIText(count.getCount(EntityDetect.tFax) + "" , gui_scale, font, pos5 , 1.5f, false);
				packText.set(EntityDetect.tFax, pack5);
				packText.get(EntityDetect.tBoat).remove();
				Vector2f pos6 = new Vector2f(x_base  ,z_base + (gui_dis*(float)EntityDetect.tBoat));
				GUIText pack6 = new GUIText(count.getCount(EntityDetect.tBoat) + "" , gui_scale, font, pos5 , 1.5f, false);
				packText.set(EntityDetect.tBoat, pack6);
			}
		}
		
		
	}
	
	
	
}
