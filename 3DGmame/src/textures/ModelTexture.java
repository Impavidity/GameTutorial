package textures;

public class ModelTexture {
	
	private int textureID;
	
	private float shineDamper = 1;
	
	private float relectivity = 0;
	
	public ModelTexture(int id) {
		this.textureID = id;
		
	}
	
	public int getID() {
		return this.textureID;
	}

	public void setTextureID(int textureID) {
		this.textureID = textureID;
	}

	public void setShineDamper(float shineDamper) {
		this.shineDamper = shineDamper;
	}

	public void setRelectivity(float relectivity) {
		this.relectivity = relectivity;
	}

	public int getTextureID() {
		return textureID;
	}

	public float getShineDamper() {
		return shineDamper;
	}

	public float getRelectivity() {
		return relectivity;
	}


}
