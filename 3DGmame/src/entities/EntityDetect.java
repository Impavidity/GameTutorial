package entities;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import engineTester.MainGameLoop;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import terrains.Terrain;



public class EntityDetect {
	
	private float x,y;
    public static final int treeType=1;
	public static final int flowerType = 2;
	public static final int fernType = 3;
	public static final int lowtreeType = 4;
	public static final int lampType =  5;
	public static final int playerType = 6;
	
	public static final int totType = 6;
	private FontType font;
	
	private GUIText text;
	
	private boolean textShow =false;
	
	private Count count;
	private List<GUIText> packText;
	
	
	public EntityDetect(FontType font, Count count, List<GUIText> packText) {
		this.font = font;
		this.count = count;
		this.packText = packText;
		for (int i = 0; i< totType; i++) {
			Vector2f pos = new Vector2f(0.1f  ,0.05f + (0.1f*(float)i));
			System.out.println(pos.x + " " + pos.y);
			GUIText pack = new GUIText("0" ,3, font, pos , 1.5f, false);
			pack.setColour(1, 1, 1);
			this.packText.add(pack);
		}
	}
	
	public void isEntity(Vector3f position, List<Entity> entities, Vector3f positionPlayer) {
		if (Mouse.isButtonDown(0)) {
			for (Entity entity : entities) {
				if (playerIsNearEntity(positionPlayer, entity.getPosition()) && 
						mouseIsNearEntity(position, entity.getPosition() )) {
					int type = entity.getEntityType();
					if (type == playerType){
						entity.setValid(true);
					}else {
						entity.setValid(false);
						int x = (int)entity.getPosition().x;
						int z = (int)entity.getPosition().z;
						for (int p = x-5; p<=x+5; p++)
							for ( int q = z-5; q<=z+5; q++) {
								if (p<0 || p>Terrain.getSize() || q<0 || q>Terrain.getSize()) continue;
								MainGameLoop.detectMap[p][q] = false;
							}
						if(entity.isClicked() == false){
							entity.setClicked(true);
							if (textShow) textShutDown();
							textPopUp();
							
							increase(type);
							System.out.println(getTypeName(type) + "  :  " + count.getCount(type));
						}
						
					}
					
				}
			}
		}
	}
	
	
	private void increase(int type){
		packText.get(type-1).remove();
		count.setCount(type);
		Vector2f pos = new Vector2f(0.1f  ,0.05f + (0.1f*(float)(type-1)));
		String num = count.getCount(type)+"";
		System.out.println(num);
		GUIText inc = new GUIText(num ,3, font, pos , 1.5f, false);
		inc.setColour(1, 1, 1);
		packText.set(type-1, inc);
	}
	
	private boolean mouseIsNearEntity(Vector3f positionMouse,  Vector3f positionEntity) {
		if (positionMouse.x < (positionEntity.x + 10) && positionMouse.x > (positionEntity.x - 10)  
				&& positionMouse.z < (positionEntity.z + 10) && positionMouse.z > (positionEntity.z -10) ) {
			return true;
		}
		return false;
	}
	
	private boolean playerIsNearEntity(Vector3f positionPlayer, Vector3f positionEntity){
		if (positionPlayer.x < (positionEntity.x + 30) && positionPlayer.x > (positionEntity.x - 30)  
				&& positionPlayer.z < (positionEntity.z + 30) && positionPlayer.z > (positionEntity.z -30) ) {
			return true;
		}
		return false;
	}
	
	private String getTypeName(int type){
		switch (type) {
		case treeType:
			return "treeType";
		case fernType:
			return "fernType";
		case flowerType:
			return "flowerType";
		case lowtreeType:
			return "lowtreeType";
		default:
			return "UndefinedType";
		}
	}
	
	public void textPopUp() {
		this.text  = new GUIText("This is a test text!", 1, font, new Vector2f(0f,0.5f), 1f, true);
		this.text.setColour(1, 0, 0);
		System.out.println("Pop up");
		textShow = true;
	}
	
	public void textShutDown() {
		this.text.remove();
		System.out.println("Remove");
		textShow = false;
		MainGameLoop.textTime = 0;
	}
	
	public GUIText getGUIText() {
		return text;
	}
	
	public boolean getTextStatus() {
		return textShow;
	}
	
	


}
