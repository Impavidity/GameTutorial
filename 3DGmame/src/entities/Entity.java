package entities;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import com.sun.jmx.snmp.tasks.ThreadService;

import engineTester.MainGameLoop;
import models.TexturedModel;
import terrains.Terrain;

public class Entity {
	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private boolean valid = true;
	private boolean clicked = false;
	private int entityType = 0;
	
	private int textureIndex = 0; 
	
	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, int entityType) {
		
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.entityType = entityType;
	}
	public Entity(TexturedModel model, int index,  Vector3f position, float rotX, float rotY, float rotZ, float scale, int entityType) {
		this.textureIndex = index; 
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.entityType = entityType;
	}
	
	public boolean noEntity(Vector3f newPosition) {
		int x = (int)newPosition.x;
		int z = (int)newPosition.z;
		if ( x < 0 || x >=Terrain.getSize() || z < 0 || z >= Terrain.getSize()) return false;
		if (MainGameLoop.detectMap[x][z])
			return false;
		return true;
	}
	
	
	public void increasePosition(float dx, float dy, float dz) {
		Vector3f newPosition = new Vector3f(this.position.x+dx, this.position.y+dy, this.position.z+dz);
		if (noEntity(newPosition)) {
			this.position.x += dx;
			this.position.y += dy;
			this.position.z += dz;
		}
	}
	
	public void increaseRotation(float dx,float dy, float dz) {
		this.rotX += dx;
		this.rotY += dy;
		this.rotZ += dz;
	}

	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float)column / (float)model.getTexture().getNumberOfRows();
	}
	public float getTextureYOffset() {
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float)row / (float)model.getTexture().getNumberOfRows();
	}
	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public void setRotY(float rotY) {
		this.rotY = rotY;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public TexturedModel getModel() {
		return model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotX() {
		return rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public float getScale() {
		return scale;
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public boolean isClicked() {
		return clicked;
	}
	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	public int getEntityType() {
		return entityType;
	}
	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}
	

	
	
}
