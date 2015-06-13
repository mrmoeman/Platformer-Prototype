import java.io.*;
import java.util.Scanner;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Model {
	
	int Temp;
	int TempB;
	int TempC;
	
	float VertArray[][] = new float [2000000][3];
	float TextArray[][] = new float [2000000][2];
	float NormArray[][] = new float [2000000][3];
	int FaceArray[][] = new int [200000][10];
	
	float FinVertArray[][];
	float FinTextArray[][];
	float FinNormArray[][];
	int FinfaceArray[][];
	
	Vector3f VA = new Vector3f();
	Vector3f VB= new Vector3f();
	Vector3f VC= new Vector3f();
	Vector3f VD= new Vector3f();
	
	Vector3f Movement = new Vector3f(0,0,0);
	
	
	Vector3f Position= new Vector3f(0,0,0);
	
	
	
	float Shininess = 70;
	
	float Transparency = 0;
	
	float Rotation;
	
	boolean Collis;
	boolean ShowModel = true;
	boolean AllowPlayerShadow = true;
	
	int Faces = 0;
	
	Texture texture;
	
	String ModelName;
	String TextureName;
	
	int Reference = 0;
	
	public Model(){
	
	}
			
    public Model(String ObjName, boolean Collision){
    	
    	ModelName = ObjName;
    	TextureName = ObjName;
    	
    	Collis = Collision;
    	Scanner s = null;
    	int Vertice = 0;
    	int Normals = 0;
    	int Texts = 0;
    	String teststring;
    	String FileOpen;

    	FileOpen = "src/res/" + ObjName + ".obj";
    	
    	try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + ObjName +".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
            s = new Scanner(new BufferedReader(new FileReader(FileOpen)));
            s.useDelimiter("[\\/\\s+]");
            while (s.hasNext()) {
            	teststring = s.next();
            	if (teststring.equals("v")){
            		Vertice++;
            		VertArray[Vertice - 1][0] = s.nextFloat();
            		VertArray[Vertice - 1][1] = s.nextFloat();
            		VertArray[Vertice - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("vt")){
            		Texts++;
            		if (Texts == 1){
            		}
            		TextArray[Texts - 1][0] = s.nextFloat();
            		TextArray[Texts - 1][1] = s.nextFloat();
            	}
            	if (teststring.equals("vn")){
            		Normals++;
            		if (Normals == 1){
            		}
            		NormArray[Normals - 1][0] = s.nextFloat();
            		NormArray[Normals - 1][1] = s.nextFloat();
            		NormArray[Normals - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("f")){
            		Faces++;
            		FaceArray[Faces - 1][0] = s.nextInt();
            		FaceArray[Faces - 1][1] = s.nextInt();
            		FaceArray[Faces - 1][2] = s.nextInt();
            		FaceArray[Faces - 1][3] = s.nextInt();
            		FaceArray[Faces - 1][4] = s.nextInt();
            		FaceArray[Faces - 1][5] = s.nextInt();
            		FaceArray[Faces - 1][6] = s.nextInt();
            		FaceArray[Faces - 1][7] = s.nextInt();
            		FaceArray[Faces - 1][8] = s.nextInt();
            	}
            	
            }
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	s.close();
        	
        	
        	FinVertArray = new float[Vertice][3];
        	for(int i = 0; i < Vertice; i++){
        		FinVertArray[i] = VertArray[i];
        	}
        	
        	FinTextArray = new float[Texts][2];
        	for(int i = 0; i < Texts; i++){
        		FinTextArray[i] = TextArray[i];
        	}
        	
        	FinNormArray = new float[Normals][3];
        	for(int i = 0; i < Normals; i++){
        		FinNormArray[i] = NormArray[i];
        	}
        	
        	FinfaceArray = new int[Faces][10];
        	for(int i = 0; i < Faces; i++){
        		FinfaceArray[i] = FaceArray[i];
        	}
        	
        	
        	VertArray = null;
        	NormArray = null;
        	TextArray = null;
        	FaceArray = null;
        	
        }
    }
    
    
    
    public Model(String ObjName, String Texture, boolean Collision){
    	
    	ModelName = ObjName;
    	TextureName = Texture;
    	
    	Collis = Collision;
    	Scanner s = null;
    	int Vertice = 0;
    	int Normals = 0;
    	int Texts = 0;
    	String teststring;
    	String FileOpen;

    	FileOpen = "src/res/" + ObjName + ".obj";
    	
    	try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + Texture +".png"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try {
            s = new Scanner(new BufferedReader(new FileReader(FileOpen)));
            s.useDelimiter("[\\/\\s+]");
            while (s.hasNext()) {
            	teststring = s.next();
            	if (teststring.equals("v")){
            		Vertice++;
            		VertArray[Vertice - 1][0] = s.nextFloat();
            		VertArray[Vertice - 1][1] = s.nextFloat();
            		VertArray[Vertice - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("vt")){
            		Texts++;
            		if (Texts == 1){
            		}
            		TextArray[Texts - 1][0] = s.nextFloat();
            		TextArray[Texts - 1][1] = s.nextFloat();
            	}
            	if (teststring.equals("vn")){
            		Normals++;
            		if (Normals == 1){
            		}
            		NormArray[Normals - 1][0] = s.nextFloat();
            		NormArray[Normals - 1][1] = s.nextFloat();
            		NormArray[Normals - 1][2] = s.nextFloat();
            	}
            	if (teststring.equals("f")){
            		Faces++;
            		FaceArray[Faces - 1][0] = s.nextInt();
            		FaceArray[Faces - 1][1] = s.nextInt();
            		FaceArray[Faces - 1][2] = s.nextInt();
            		FaceArray[Faces - 1][3] = s.nextInt();
            		FaceArray[Faces - 1][4] = s.nextInt();
            		FaceArray[Faces - 1][5] = s.nextInt();
            		FaceArray[Faces - 1][6] = s.nextInt();
            		FaceArray[Faces - 1][7] = s.nextInt();
            		FaceArray[Faces - 1][8] = s.nextInt();
            	}
            	
            	
            	//LoadScreen.ShowLoad();
            	
            }
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
        	s.close();
        	
        	FinVertArray = new float[Vertice][3];
        	for(int i = 0; i < Vertice; i++){
        		FinVertArray[i] = VertArray[i];
        	}
        	
        	FinTextArray = new float[Texts][2];
        	for(int i = 0; i < Texts; i++){
        		FinTextArray[i] = TextArray[i];
        	}
        	
        	FinNormArray = new float[Normals][3];
        	for(int i = 0; i < Normals; i++){
        		FinNormArray[i] = NormArray[i];
        	}
        	
        	FinfaceArray = new int[Faces][10];
        	for(int i = 0; i < Faces; i++){
        		FinfaceArray[i] = FaceArray[i];
        	}
        	
        	VertArray = null;
        	NormArray = null;
        	TextArray = null;
        	FaceArray = null;
        	
        }
    }
    
    
    
    public void SetPosition(Vector3f Pos){
    	Position = Pos;
    }
    
    public void SetRotation(float rotation){
    	Rotation = rotation;
    }
    
    public void SetShiniess(float shiny){
    	Shininess = shiny;
    }
    
    public void Draw(){
    	
    	GL11.glEnable( GL11.GL_TEXTURE_2D); 
		
    	GL11.glLoadIdentity();                          // Reset The Current Modelview Matrix
    	
    	
    	
    	ModelOffset.WorldOffset();
    	
    	GL11.glTranslatef(Position.x, Position.y, Position.z);
    	
    	GL11.glRotatef(Rotation, 0, 1, 0);
    	
    	GL11.glTranslatef(-Position.x,-Position.y, -Position.z);
    	 
    	 GL13.glActiveTexture(GL13.GL_TEXTURE0);
		 
		 int loc = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "texture1");
		 
		 GL20.glUniform1i(loc, 0);
		 
		
		 
		 int locc = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "PlayerShadow");
		 
		 int loct = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "Transparency");
		 
		 
		 GL20.glUniform1f(loct, Transparency);
		 
		 
		 if (AllowPlayerShadow == true && GameData.ScreenShootMode == false){
			 GL20.glUniform1i(locc,1);
		 }
		 else{
			 GL20.glUniform1i(locc,0);
		 }
		 

		 GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getTextureID());
    	
    	for(int i = 0; i < Faces; i++ ){
    	
    	if (ShowModel == true){
    		
    	 GL11.glBegin(GL11.GL_TRIANGLES);                    // Drawing Using Triangles
         GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, Shininess);
         
         Temp = FinfaceArray[i][0]-1;
         TempB = FinfaceArray[i][1]-1;
         TempC = FinfaceArray[i][2]-1;
         
         GL11.glTexCoord2f(FinTextArray[TempC][0] ,FinTextArray[TempC][1]*-1);
         GL11.glNormal3f( FinNormArray[TempB][0], FinNormArray[TempB][1],FinNormArray[TempB][2]);         // Bottom Right
         GL11.glVertex3f( FinVertArray[Temp][0] + Position.x, FinVertArray[Temp][1] + Position.y, FinVertArray[Temp][2] + Position.z);         // Bottom Right
        
         Temp = FinfaceArray[i][3]-1;
         TempB = FinfaceArray[i][4]-1;
         TempC = FinfaceArray[i][5]-1;
         
         GL11.glTexCoord2f(FinTextArray[TempC][0],FinTextArray[TempC][1]*-1);
         GL11.glNormal3f( FinNormArray[TempB][0], FinNormArray[TempB][1], FinNormArray[TempB][2]);  
         GL11.glVertex3f( FinVertArray[Temp][0] + Position.x, FinVertArray[Temp][1] + Position.y, FinVertArray[Temp][2] + Position.z);        // Bottom Right
         
         Temp = FinfaceArray[i][6]-1;
         TempB = FinfaceArray[i][7]-1;
         TempC = FinfaceArray[i][8]-1;
         
         GL11.glTexCoord2f(FinTextArray[TempC][0],FinTextArray[TempC][1]*-1);
         GL11.glNormal3f( FinNormArray[TempB][0], FinNormArray[TempB][1], FinNormArray[TempB][2]);  
         GL11.glVertex3f( FinVertArray[Temp][0] + Position.x, FinVertArray[Temp][1] + Position.y, FinVertArray[Temp][2] + Position.z);        // Bottom Right
         GL11.glEnd();   
         
    	}
    	
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
         
         if (Collis == true){
         VA = new Vector3f();
         VB = new Vector3f();
         VC = new Vector3f();
         VD = new Vector3f();
         
         VA.x = FinVertArray[FinfaceArray[i][0]-1][0] + Position.x;
         VA.y = FinVertArray[FinfaceArray[i][0]-1][1] + Position.y;
         VA.z = FinVertArray[FinfaceArray[i][0]-1][2] + Position.z;
         
         VB.x = FinVertArray[FinfaceArray[i][3]-1][0] + Position.x;
         VB.y = FinVertArray[FinfaceArray[i][3]-1][1] + Position.y;
         VB.z = FinVertArray[FinfaceArray[i][3]-1][2] + Position.z;
         
         VC.x = FinVertArray[FinfaceArray[i][6]-1][0] + Position.x;
         VC.y = FinVertArray[FinfaceArray[i][6]-1][1] + Position.y;
         VC.z = FinVertArray[FinfaceArray[i][6]-1][2] + Position.z;
         
        // if(MyMath.Length(VA, ThirdPersonCamera.PointAt) < 50 || MyMath.Length(VB, ThirdPersonCamera.PointAt) < 50 || MyMath.Length(VC, ThirdPersonCamera.PointAt) < 50){
         
         TriangleFace Temp = new TriangleFace();
         Temp.VertA = VA;
         Temp.VertB = VB;
         Temp.VertC = VC;
         Temp.Normal = MyMath.CalculateTrianlgeNormal(VA, VB, VC);
         Temp.SetMovement(Movement);
         
         
         
         DynamicCollision.AddTriangleFace(Temp);
         
        // }
         }
         
        // DynamicCollision.FaceList.add(new TriangleFace(VA, VB, VC, VD));
         
         //GL11.glDisable(GL11.GL_ALPHA_TEST);
         
    	}
    }
    
    public void SetReference(int Ref){
    	Reference = Ref;
    }
    
    public void SetTransparency(float transparency){
    	Transparency = 1 - transparency;
    }
    
    public int GetReference(){
    	return Reference;
    }
    
    public void AlphaClear(){
    	
    	GL11.glEnable(GL11.GL_ALPHA_TEST);
    	GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
    	
    }
    
    public void ShowModel(Boolean State){
    	ShowModel = State;
    }
    
    public void SetMovement (Vector3f movement){
    	Movement = movement;
    }
    
    public void SetPlayerShadowAllowance(boolean allowance){
    	AllowPlayerShadow = allowance;
    }
    
    public String GetModelName(){
    	return ModelName;
    }
    
    public String GetTextureName(){
    	return TextureName;
    }
    
 }


