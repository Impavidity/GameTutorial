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

	
	public static final int totType = 12;
	private FontType font;
	
	private GUIText text;
	
	private boolean textShow =false;
	
	private Count count;
	private List<GUIText> packText;
	
	float gui_dis = 0.0825f;
	float z_base = 0.054f;
	float x_base = 0.029f;
	float gui_scale = 1f;
	
	float r = 0f; 
	float g = 0f;
	float b = 0f;
	
	public EntityDetect(FontType font, Count count, List<GUIText> packText) {
		this.font = font;
		this.count = count;
		this.packText = packText;
		
		for (int i = 0; i< totType; i++) {
			Vector2f pos = new Vector2f(x_base  ,z_base + (gui_dis*(float)i));
			//System.out.println(pos.x + " " + pos.y);
			GUIText pack = new GUIText("00" , gui_scale, font, pos , 1.5f, false);
			pack.setColour(r ,g, b);
			this.packText.add(pack);
		}
		
	}
	

	public void isEntity(Vector3f position, List<Entity> entities, Vector3f positionPlayer) {
		if (Mouse.isButtonDown(0)) {
			for (Entity entity : entities) {
				if (playerIsNearEntity(positionPlayer, entity.getPosition()) && 
						mouseIsNearEntity(position, entity.getPosition())) {
					int type = entity.getEntityType();
					if (type == Entity.playerType || type == Entity.palletType || type == Entity.houseType || type == Entity.fireplaceType){
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
							//System.out.println(getTypeName(type) + "  :  " + count.getCount(type));
						}
						
					}
					
				}
			}
		}
	}
	
	
	private void increase(int type){
		packText.get(type).remove();
		count.setCount(type);
		Vector2f pos = new Vector2f(x_base  ,z_base + (gui_dis*(float)(type))); 
		long num = count.getCount(type);
		String snum;
		if (num < 10) snum = "0" + num;
		else snum = num + "";
		
		GUIText inc = new GUIText(snum ,gui_scale, font, pos , 1.5f, false);
		inc.setColour(r, g, b);
		packText.set(type, inc);
	}
	
	private boolean mouseIsNearEntity(Vector3f positionMouse,  Vector3f positionEntity) {
		if (positionMouse.x < (positionEntity.x + 10) && positionMouse.x > (positionEntity.x - 10)  
				&& positionMouse.z < (positionEntity.z + 10) && positionMouse.z > (positionEntity.z -10) ) {
			return true;
		}
		return false;
	}
	
	private boolean playerIsNearEntity(Vector3f positionPlayer, Vector3f positionEntity){
		if (positionPlayer.x < (positionEntity.x + 15) && positionPlayer.x > (positionEntity.x - 15)  
				&& positionPlayer.z < (positionEntity.z + 15) && positionPlayer.z > (positionEntity.z -15) ) {
			return true;
		}
		return false;
	}
	
	private String getTypeName(int type){
		switch (type) {
		case Entity.treeType:
			return "treeType";
		case Entity.fernType:
			return "fernType";
		case Entity.flowerType:
			return "flowerType";
		case Entity.lowtreeType:
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
