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
				temp = new Entity(MainGameLoop.texturedModels.get(EntityDetect.tFirecamp), terrainPoint, 0, 0, 0, 1, EntityDetect.tFirecamp);
				entities.add(temp);
			}
			else if (Keyboard.isKeyDown(Keyboard.KEY_F3)) {
				temp = new Entity(MainGameLoop.texturedModels.get(EntityDetect.tHouse), terrainPoint, 0, 0, 0, 1, EntityDetect.tHouse);
				entities.add(temp);
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_F4)) {
				temp = new Entity(MainGameLoop.texturedModels.get(EntityDetect.tBoat), terrainPoint, 0, 0, 0, 1, EntityDetect.tHouse);
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
