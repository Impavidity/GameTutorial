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
	
	public GUIControl(Count count, List<GuiTexture> guis, Loader loader,List<GUIText> packText, FontType font) {
		this.count = count;
		this.guis = guis;
		this.loader = loader;
		this.packText = packText;
		this.font = font;
	}
	
	public void check() {
		if (count.getCount(Entity.flowerType) >= 5) {
			GuiTexture gui = new GuiTexture(loader.loadTexture("wood"),new Vector2f(-0.7f, 0.8f), new Vector2f(0.08f, 0.08f* MainGameLoop.UIratio));
			guis.add(gui);
			count.clearCount(Entity.flowerType);
			
			packText.get(Entity.flowerType - 1).remove();
			Vector2f pos = new Vector2f(0.1f  ,0.05f + (0.1f*(float)(Entity.flowerType - 1)));
			System.out.println(pos.x + " " + pos.y);
			GUIText pack = new GUIText("0" ,3, font, pos , 1.5f, false);
			pack.setColour(1, 1, 1);
			packText.set(Entity.flowerType- 1, pack);
			
		}
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
			if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
				temp = new Entity(MainGameLoop.texturedModels.get(Entity.fireplaceType), terrainPoint, 0, 0, 0, 1, Entity.fireplaceType);
				entities.add(temp);
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
				temp = new Entity(MainGameLoop.texturedModels.get(Entity.palletType), terrainPoint, 0, 0, 0, 1, Entity.palletType);
				entities.add(temp);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
				temp = new Entity(MainGameLoop.texturedModels.get(Entity.houseType), terrainPoint, 0, 0, 0, 1, Entity.houseType);
				entities.add(temp);
			}
			
		return temp;
	}
	
	public ParticleSystem checkKeyWithParticle(MousePicker picker, ParticleTexture particleTexture){
		ParticleSystem system = null;
		if (Keyboard.isKeyDown(Keyboard.KEY_F2)) {
			system = new ParticleSystem(particleTexture, 40, 8, 0.1f, 4, 2);
			system.randomizeRotation();
			system.setDirection(new Vector3f(0, 1, 0), 0.05f);
			system.setLifeError(0.05f);
			system.setSpeedError(0.4f);
			system.setScaleError(3.0f);
			system.generateParticles(picker.getCurrentTerrainPoint());
		}
		return system;
	}
	
}
