package entities;

import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import terrains.Terrain;

public class RunAnimal {
	Random random = new Random();
	Terrain terrain;
	float angleAroundPlayer = 0;
	float horizDistance = 5;
	
	public RunAnimal(Terrain terrain) {
		this.terrain = terrain;
	}
	
	public void randomRun(Entity entity, Player player) {
		if (entity.getPet()) {
			Vector3f position = new Vector3f();
			float theta = player.getRotY() + angleAroundPlayer;
			float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
			float offsetZ  = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
			position.x = player.getPosition().x - offsetX;
			position.z = player.getPosition().z - offsetZ;
			position.y = terrain.getHeightOfTerrain(position.x, position.z);
			entity.setPosition(position);
		} else {
			Vector3f pos = entity.getPosition();
			float dx = (random.nextFloat() * 2 -1) * 7;
			float dz = (random.nextFloat() * 2 - 1)* 7;
			float dy =  terrain.getHeightOfTerrain(pos.x+dx, pos.z+dz) - pos.y  ;
			entity.increasePosition(dx, dy , dz);
		}
		
	}
	
	

}
