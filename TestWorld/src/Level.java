import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;


public class Level {
	
	
	ArrayList<Model> LevelModelList = new ArrayList<>();
	
	ArrayList<Entity> EntityList = new ArrayList<>();
	
	ArrayList<LevelVBO> VBOList = new ArrayList<>();
	
	Model PlayerModel;
	
	int GemCount = 0;
	
	boolean FogOn = false;
	float FogMinDistance = 0;
	float FogMaxDistance = 0;
	Vector3f FogColour = new Vector3f(0,0,0);

	public Level(String FileName){
		
		String FileOpen = "src/res/LevelData/" + FileName + ".Dat";
		String TempMod = null;
		String TempText = null;
		Vector3f TempPos = new Vector3f(0,0,0);
		float TempShiny = 70;
		
		Scanner s = null;
		
		boolean Level = false;
		boolean Player = false;
		boolean Entity = false;
		Entity TempEnt = new Entity();
		
		boolean VBO = false;
		
		boolean Show = true;
		boolean Collision = true;
		
		float x;
		float y;
		float z;
		
	    try {
			s = new Scanner(new BufferedReader(new FileReader(FileOpen)));
			
			while (s.hasNext()){
				String Temp = s.next();
				
				if (Temp.equals("#LoadScreen")){	
					
					LoadScreen.LoadLoadTexture(s.next());
					
				}
				
				if (Temp.equals("#Ambient")){	
					AmbientLight.SetAmbient(new Vector3f(s.nextFloat(), s.nextFloat(), s.nextFloat()));
				}
				
				if (Temp.equals("#Diffuse")){	
					AmbientLight.SetDiffuse(new Vector3f(s.nextFloat(), s.nextFloat(), s.nextFloat()));
				}
				
				if (Temp.equals("#ClearColour")){	
					AmbientLight.SetSky(new Vector3f(s.nextFloat(), s.nextFloat(), s.nextFloat()));
					FogColour = AmbientLight.AmbientColour;
				}
				
				if (Temp.equals("#SetFog")){
					FogOn = true;
					FogMinDistance = s.nextFloat();
					FogMaxDistance = s.nextFloat();
				}
				
				if (Temp.equals("#Level")){	
					Level = true;
					Player = false;
					Entity = false;
				}
				
				if (Temp.equals("#Player")){	
					Level = false;
					Player = true;
					Entity = false;
				}
				
				if (Temp.equals("#Entities")){	
					Level = false;
					Player = false;
					Entity = true;
				}
				
				if (Temp.equals("object:")){	
					TempMod = s.next();
				}
				
				if (Temp.equals("lightcolour:")){	
					TempEnt.SetLightColour(new Vector3f(s.nextFloat(), s.nextFloat(), s.nextFloat()));
				}
				
				if (Temp.equals("lightrange:")){	
					TempEnt.SetLightRange((int)(s.nextFloat()));
				}
				
				if (Temp.equals("texture:")){	
					TempText = s.next();
				}
				
				if (Temp.equals("show:")){	
					Show = s.nextBoolean();
				}
				
				if (Temp.equals("collision:")){	
					Collision = s.nextBoolean();
				}
				
				if (Temp.equals("vbo:")){	
					VBO = s.nextBoolean();
				}
				
				if (Temp.equals("position:")){	
					
					TempPos.x = s.nextFloat();
					TempPos.y = s.nextFloat();
					TempPos.z = s.nextFloat();
					if (Entity == true){
						TempEnt.SetPosition(TempPos);
					}
				}
				
				//EVENT STUFF FOR ENTITY
				if (Temp.equals("#EntEventInfo")){
					if (Entity == true){
						TempEnt.SetEventTrigInfo(new Vector3f(s.nextFloat(), s.nextFloat(), s.nextFloat()));
						TempEnt.SetEntityEvent();
						System.out.println("EventTrigInfoAdded For " + TempEnt.CurrentModelName);
					}
				}
				
				if (Temp.equals("shininess:")){	
					TempShiny = s.nextFloat();
				}
				
				if (Temp.equals("entity:")){
					TempMod = s.next();
					if (TempMod .equals("bluegem")){
						GemCount +=3;
					}
					if (TempMod .equals("redgem")){
						GemCount +=5;
					}
					if (TempMod .equals("yellowgem")){
						GemCount +=1;
					}
					TempEnt = new Entity(TempMod);
				}
				
				if (Temp.equals("movebetween:")){	
					TempEnt.SetMoveBetween(new Vector3f(s.nextFloat(), s.nextFloat(), s.nextFloat()), new Vector3f(s.nextFloat(), s.nextFloat(), s.nextFloat()), s.nextFloat());
				}
				
				if (Temp.equals("#AddEntity")){	
					EntityList.add(TempEnt);
					System.out.println("EntityDone");
					TempMod = null;
					TempText = null;
					TempPos = new Vector3f(0,0,0);
					TempShiny = 70;
					
				}
				
				if (Temp.equals("#AddObject")){	
					if (Level == true){
						if(VBO == false){
						Model TempModel = new Model(TempMod, TempText, Collision);
						TempModel.ShowModel(Show);
						TempModel.SetPosition(TempPos);
						TempModel.SetShiniess(TempShiny);
						LevelModelList.add(TempModel);
						System.out.println("ModelDone");
						}
						
						if(VBO == true){
							LevelVBO TempVBO = new LevelVBO(TempMod, TempText);
							VBOList.add(TempVBO);
							System.out.println("VBODone");
						}
						
						TempMod = null;
						TempText = null;
						TempPos = new Vector3f(0,0,0);
						TempShiny = 70;
						Show = true;
						Collision = true;
						VBO = false;
					}
					if (Player == true){
						PlayerModel = new Model(TempMod, TempText, false);
						PlayerModel.SetPosition(TempPos);
						PlayerModel.SetShiniess(TempShiny);
						PlayerModel.SetPlayerShadowAllowance(true);
						
						TempMod = null;
						TempText = null;
						TempPos = new Vector3f(0,0,0);
						TempShiny = 70;
						
						
					}
					
				}
				LoadScreen.ShowLoad();
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
	    	s.close();
	    }
	    
	    GameData.CurrentLevelGems = GemCount;

	}
	
	
	public void Update(){
		
		AmbientLight.LightReset();
    	DynamicCollision.CheckOctree();
    	
		
		AmbientLight.Update();
		
		if(GameData.ThirdPersonRegular == true){
			ThirdPersonCamera.Update();
		}
		if(GameData.FirstPersonRegular == true){
			CameraControl.Update();
		}
    	
    	
    	
		
		for(Entity MyEnt: EntityList){
			
			MyEnt.Update();
			
		}
		
		
		PlayerModel.SetPosition(new Vector3f(GameData.PlayerPosition.x, GameData.PlayerPosition.y - 0.52f, GameData.PlayerPosition.z));
		PlayerModel.SetRotation(-(GameData.PlayerRotation)-90);
		PlayerModel.SetPlayerShadowAllowance(false);
		
		DevKeys();
		
		
		DynamicCollision.ClearList();
		
		AmbientLight.CalculateMatrixPosition();
	}
	
	public void Draw(){
		

		Shaders.NewShader.Activate();
		
		
		int PlayerLoc = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "PlayerPos");
		GL20.glUniform3f(PlayerLoc, GameData.PlayerPosition.x, GameData.PlayerPosition.y, GameData.PlayerPosition.z);
		
		int fogcolourloc = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "MyFog");
		GL20.glUniform3f(fogcolourloc, FogColour.x, FogColour.y, FogColour.z);
		
		int fograngeloc = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "FogRange");
		GL20.glUniform2f(fograngeloc, FogMinDistance, FogMaxDistance);
		
		int fogonloc = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "FogOn");
		if(FogOn == true){
			GL20.glUniform1i(fogonloc, 1);
		}
		else{
			GL20.glUniform1i(fogonloc, 0);
		}
		
		for(LevelVBO MyVBO : VBOList){
			
			MyVBO.DrawVBO();
			
		}
		
		for(Model MyModel: LevelModelList){
			
			MyModel.Draw();
			
		}
		
		for(Entity MyEnt: EntityList){
			
			MyEnt.Draw();
			
		}
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		Shaders.NewShader.Activate();
		PlayerModel.AlphaClear();
		if(GameData.ThirdPersonRegular == true || GameData.ThirdPersonFree == true){
			if(GameData.ScreenShootMode == false){
				PlayerModel.Draw();
			}
		}
		Shaders.NewShader.DeActivate();
		GL11.glEnable(GL11.GL_CULL_FACE);
		
		
		
	    Shaders.NewShader.DeActivate();
	    GL11.glDisable(GL11.GL_CULL_FACE);
	    if(GameData.ScreenShootMode == false){
	    	//MyFont.Scale(1f, 1f);
	    	MyFont.Print(GameData.CurrentLives +" ", -0.95f, 0.95f, 1.0f, 1.0f, 0.0f);
	    	//MyFont.Scale(0.2f, 0.2f);
	    	MyFont.Print(GameData.CurrentGems+"/" +GameData.CurrentLevelGems, 0.78f, 0.95f, 0.0f, 0.0f, 1.0f);
	    }
	        
	    GL11.glEnable(GL11.GL_CULL_FACE);
	    GL11.glColor3f(1.0f, 1.0f, 1.0f);
		
	}
	
	void DevKeys(){
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F3)){
			GameData.ScreenShootMode = true;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_F4)){
			GameData.ScreenShootMode = false;
		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F5) && GameData.ThirdPersonRegular == false){
			GameData.FirstPersonRegular = false;
			GameData.ThirdPersonRegular = true;
			GameData.ThirdPersonFree = false;
			ThirdPersonCamera.SetPointAt(GameData.PlayerPosition);
			ThirdPersonCamera.Rotation = GameData.PlayerRotation + 90;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F6) && GameData.FirstPersonRegular == false){
			GameData.FirstPersonRegular = true;
			GameData.ThirdPersonRegular = false;
			GameData.ThirdPersonFree = false;
			CameraControl.X = -GameData.PlayerPosition.x;
			CameraControl.Y = -GameData.PlayerPosition.y;
			CameraControl.Z = -GameData.PlayerPosition.z;
			CameraControl.Rotation = GameData.PlayerRotation-90;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F7)){
			GameData.FirstPersonRegular = false;
			GameData.ThirdPersonRegular = false;
			GameData.ThirdPersonFree = true;
		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_F2)){
			System.out.println("entity: spike");
			System.out.println("position: " + GameData.PlayerYMinus.x + " " + GameData.PlayerYMinus.y + " " + GameData.PlayerYMinus.z);
			System.out.println("#AddEntity");
		}
		
	}
	
}
