package guis;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import engineTester.MainGameLoop;
import entities.Count;
import entities.EntityDetect;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import renderEngine.Loader;

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
		if (count.getCount(EntityDetect.flowerType) >= 5) {
			GuiTexture gui = new GuiTexture(loader.loadTexture("wood"),new Vector2f(-0.7f, 0.8f), new Vector2f(0.08f, 0.08f* MainGameLoop.UIratio));
			guis.add(gui);
			count.clearCount(EntityDetect.flowerType);
			
			packText.get(EntityDetect.flowerType - 1).remove();
			Vector2f pos = new Vector2f(0.1f  ,0.05f + (0.1f*(float)(EntityDetect.flowerType - 1)));
			System.out.println(pos.x + " " + pos.y);
			GUIText pack = new GUIText("0" ,3, font, pos , 1.5f, false);
			pack.setColour(1, 1, 1);
			packText.set(EntityDetect.flowerType- 1, pack);
			
		}
	}
	
	public void checkUIClick() {
		if (Mouse.isButtonDown(0)) {
			System.out.println(Mouse.getX() +" " + Mouse.getY());
		}
	}
}
