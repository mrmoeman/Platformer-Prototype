import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class World {
	
	int Temp;
	int TempB;
	int TempC;
	static Texture texture;
	
	Model World;
	Model Sun;
	Entity Lolipop;
	Entity Tree;
	
	Entity Platform;
	
	Entity RedGem;
	Entity BlueGem;
	Entity YellowGemA;
	Entity YellowGemB;
	Entity YellowGemC;
	
	Vector3f VA = new Vector3f();
	Vector3f VB= new Vector3f();
	Vector3f VC= new Vector3f();
	Vector3f VD= new Vector3f();
	
	public World(){
		World = new Model("testy", true);
		Sun = new Model("sun", false);
		Lolipop = new Entity("lolipop");
		Tree = new Entity("tree");
		
		Platform = new Entity("platform");
		
		Sun.SetPosition(new Vector3f (0,40,15));
		Sun.SetShiniess(10);
		
		RedGem = new Entity("redgem");
		RedGem.SetPosition(new Vector3f(15.03f, 1f, 0.37f));
		BlueGem = new Entity("bluegem");
		BlueGem.SetPosition(new Vector3f(4.81f, 3f, -5.57f));
		
		YellowGemA = new Entity("yellowgem");
		YellowGemA.SetPosition(new Vector3f(6.56f, 1f, -0.56f));
		YellowGemB = new Entity("yellowgem");
		YellowGemB.SetPosition(new Vector3f(3.3f, 1f, -0.56f));
		YellowGemC = new Entity("yellowgem");
		YellowGemC.SetPosition(new Vector3f(5.06f, 1f, -0.56f));
		
		Lolipop.SetPosition(new Vector3f(6.6f, 1f, 4.3f));
		Tree.SetPosition(new Vector3f(-0.05f, -0.32f, 1.60f));
		
		Platform.SetPosition(new Vector3f(0.18f, -0.32f, -3.77f));
		
		Platform.SetMoveBetween(new Vector3f(0.18f, -0.32f, -3.77f), new Vector3f(0.18f, -0.32f, -6.97f), 100);
	}
	
	public void Update(){
		
		Lolipop.Update();
		YellowGemA.Update();
		YellowGemB.Update();
		YellowGemC.Update();
		RedGem.Update();
		BlueGem.Update();
		Platform.Update();
		
		
		
	}
	
	public void Text(){
		
		
	}
	
	public void Draw(){
		
		//World.ShowModel(false);
		World.Draw();
		Sun.Draw();
		Lolipop.Draw();
		
		YellowGemA.Draw();
		YellowGemB.Draw();
		YellowGemC.Draw();
		RedGem.Draw();
		BlueGem.Draw();
		Tree.Draw();
		
		Platform.Draw();
		
		/*
		 GL11.glEnable( GL11.GL_TEXTURE_2D); 
		
		 GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
		 
		 CameraControl.WorldOffset();
		 
		 GL13.glActiveTexture(GL13.GL_TEXTURE0);
		 
		 int loc = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "texture1");
		 
		 GL20.glUniform1i(loc, 0);

		 GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
		 
		
		 for(int i = 0; i < testworld.FaceArray.length; i++ ){
	        	
	            GL11.glBegin(GL11.GL_TRIANGLES);                    // Drawing Using Triangles
	            GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 50);
	            
	            Temp = testworld.FaceArray[i][0]-1;
	            TempB = testworld.FaceArray[i][1]-1;
	            TempC = testworld.FaceArray[i][2]-1;
	            
	            GL11.glTexCoord2f(testworld.TextArray[TempC][0],testworld.TextArray[TempC][1]);
	            GL11.glNormal3f( testworld.NormArray[TempB][0], testworld.NormArray[TempB][1], testworld.NormArray[TempB][2]);         // Bottom Right
	            GL11.glVertex3f( testworld.VertArray[Temp][0], testworld.VertArray[Temp][1], testworld.VertArray[Temp][2]);         // Bottom Right
	           
	            
	            
	            
	            Temp = testworld.FaceArray[i][3]-1;
	            TempB = testworld.FaceArray[i][4]-1;
	            TempC = testworld.FaceArray[i][5]-1;
	            
	            GL11.glTexCoord2f(testworld.TextArray[TempC][0],testworld.TextArray[TempC][1]);
	            GL11.glNormal3f( testworld.NormArray[TempB][0], testworld.NormArray[TempB][1], testworld.NormArray[TempB][2]);  
	            GL11.glVertex3f( testworld.VertArray[Temp][0], testworld.VertArray[Temp][1], testworld.VertArray[Temp][2]);        // Bottom Right
	            
	            Temp = testworld.FaceArray[i][6]-1;
	            TempB = testworld.FaceArray[i][7]-1;
	            TempC = testworld.FaceArray[i][8]-1;
	            
	            GL11.glTexCoord2f(testworld.TextArray[TempC][0],testworld.TextArray[TempC][1]);
	            GL11.glNormal3f( testworld.NormArray[TempB][0], testworld.NormArray[TempB][1], testworld.NormArray[TempB][2]);  
	            GL11.glVertex3f( testworld.VertArray[Temp][0], testworld.VertArray[Temp][1], testworld.VertArray[Temp][2]);        // Bottom Right
	            GL11.glEnd();   
	            
	            
	            VA.x = testworld.VertArray[testworld.FaceArray[i][0]-1][0];
	            VA.y = testworld.VertArray[testworld.FaceArray[i][0]-1][1];
	            VA.z = testworld.VertArray[testworld.FaceArray[i][0]-1][2];
	            
	            VB.x = testworld.VertArray[testworld.FaceArray[i][3]-1][0];
	            VB.y = testworld.VertArray[testworld.FaceArray[i][3]-1][1];
	            VB.z = testworld.VertArray[testworld.FaceArray[i][3]-1][2];
	            
	            VC.x = testworld.VertArray[testworld.FaceArray[i][6]-1][0];
	            VC.y = testworld.VertArray[testworld.FaceArray[i][6]-1][1];
	            VC.z = testworld.VertArray[testworld.FaceArray[i][6]-1][2];
	            
	            VD.x = (testworld.NormArray[testworld.FaceArray[i][1]-1][0] + testworld.NormArray[testworld.FaceArray[i][4]-1][0] + testworld.NormArray[testworld.FaceArray[i][7]-1][0])/3;
	            VD.x = (testworld.NormArray[testworld.FaceArray[i][1]-1][1] + testworld.NormArray[testworld.FaceArray[i][4]-1][1] + testworld.NormArray[testworld.FaceArray[i][7]-1][1])/3;
	            VD.x = (testworld.NormArray[testworld.FaceArray[i][1]-1][2] + testworld.NormArray[testworld.FaceArray[i][4]-1][2] + testworld.NormArray[testworld.FaceArray[i][7]-1][2])/3;
	            
	            DynamicCollision.AddTriangleFace(VA, VB, VC, VD);
	            
	            }*/
		
		
		
	}
	
	

}
