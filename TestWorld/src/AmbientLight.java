import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL44;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;


class AmbientLight {
	
	static float Light[] = new float[200];
	static float LightColour[] = new float[150];
	static int LightCount;
	
	static Vector3f AmbientColour = new Vector3f(0.71f, 0.71f, 0.71f);
	static Vector3f SkyColour = new Vector3f(0.529f, 0.807f, 0.921f);
	static Vector3f DiffuseColour = new Vector3f(0.71f, 0.71f, 0.71f);
	
	public static Vector3f Position = new Vector3f(0, 50, -25);
	
	static void Update(){
		
		GL11.glClearColor(SkyColour.x, SkyColour.y, SkyColour.z, 0.0f); 
		
		//lighting
        float lightAmbient[] = { AmbientColour.x, AmbientColour.y , AmbientColour.z, 1.0f };
    	float lightDiffuse[] = { 0.01f, 0.01f, 0.01f, 1.0f };
        float lightPosition[] = { 0.0f, 50.0f, -25.0f, 0.0f };
        float lightSpecular[] = { 1.0f, 1.0f, 1.0f, 1.0f };
        
        
        ByteBuffer temp = ByteBuffer.allocateDirect(16);
        temp.order(ByteOrder.nativeOrder());
        GL11.glLoadIdentity();
        ModelOffset.WorldOffset();
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_AMBIENT, (FloatBuffer)temp.asFloatBuffer().put(lightAmbient).flip());              // Setup The Ambient Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_DIFFUSE, (FloatBuffer)temp.asFloatBuffer().put(lightDiffuse).flip());              // Setup The Diffuse Light
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_POSITION,(FloatBuffer)temp.asFloatBuffer().put(lightPosition).flip());  
        GL11.glLight(GL11.GL_LIGHT1, GL11.GL_SPECULAR,(FloatBuffer)temp.asFloatBuffer().put(lightSpecular).flip());  
		
	}
	
	public static void LightReset(){
		
		Light = new float[200];
		LightColour = new float[150];
		LightCount = 0;
		
	}
	
	public static void AddLight(float x, float y, float z, float r, float g, float b, float distance){
		
		Light[LightCount*4] = x;
		Light[LightCount*4+1] = y;
		Light[LightCount*4+2] = z;
		LightColour[LightCount*3] = r;
		LightColour[LightCount*3+1] = g;
		LightColour[LightCount*3+2] = b;
		Light[LightCount*4+3] = distance;
		LightCount++;
		
	}
	
	public static void CalculateMatrixPosition(){
		
		

			 Shaders.NewShader.Activate();
			 
			 int locarraya = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "lightarray");
			 
			 FloatBuffer LightData = BufferUtils.createFloatBuffer(200);
			 LightData.put(Light);
			 LightData.rewind();
			 GL20.glUniform4(locarraya, LightData);
			 
			 
			 int colloc = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "lightcolor");
			 
			FloatBuffer LightColourBuffer = BufferUtils.createFloatBuffer(150);
			LightColourBuffer.put(LightColour);
			LightColourBuffer.rewind();
			GL20.glUniform3(colloc, LightColourBuffer);
			 
			 //System.out.println(LightCount);
			 
			 Shaders.NewShader.DeActivate();

	}
	
	public static void SetAmbient(Vector3f Colour){
		AmbientColour = Colour;
	}
	
	public static void SetSky(Vector3f Colour){
		SkyColour = Colour;
	}
	
	public static void SetDiffuse(Vector3f Colour){
		DiffuseColour = Colour;
	}
	

}
