package entities;
import java.util.List;

import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;



public class EntityDetect {
	
	private float x,y;
    public static final int treeType=1;
	public static final int flowerType = 2;
	public static final int fernType = 3;
	public static final int lowtreeType = 4;
	public static final int lampType =  5;
	public static final int playerType = 6;
	
	
	public void isEntity(Vector3f position, List<Entity> entities, Vector3f positionPlayer, Count count) {
		if (Mouse.isButtonDown(0)) {
			for (Entity entity : entities) {
				if (playerIsNearEntity(positionPlayer, entity.getPosition()) && 
						mouseIsNearEntity(position, entity.getPosition() )) {
					entity.setValid(false);
					
					if(entity.isClicked() == false){
						entity.setClicked(true);
						int type = entity.getEntityType();
						count.setCount(type);
						System.out.println(getTypeName(type) + "  :  " + count.getCount(type));
					}
				}
			}
		}
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

}
