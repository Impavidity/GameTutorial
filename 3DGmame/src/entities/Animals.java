package entities;

import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Animals extends Entity{
	
	private Random random = new Random();
	
	private static final float RUN_SPEED = 50;
	private static final float TURN_SPEED = 160;
	public static final float GRAVITY = -50;
	
	private float currentSpeed = 0;
	private float currentTurnSpeed = 0;
	private float upwardsSpeed = 0;
	private boolean isInAir = false;

	public Animals(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale,
			int entityType) {
		super(model, position, rotX, rotY, rotZ, scale, entityType);
		// TODO Auto-generated constructor stub
	}
	
	public void move(Terrain terrain, List<Entity> entities) {
		
		super.increaseRotation(0, currentTurnSpeed * DisplayManager.getFrameTimeSeconds(), 0);
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds() , 0);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x,super.getPosition().z);
		if (super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			isInAir = false;
			super.getPosition().y = terrainHeight;
		}
	}
	
}
