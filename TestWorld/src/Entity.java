import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;



public class Entity {
	
	float Rotation = 0;
	float RotationSpeed = 0;
	Vector3f Position = new Vector3f(0,0.5f,0); 
	Vector3f OriginalPosition = new Vector3f(0,0.5f,0); 
	boolean OriginalSet = false;
	int ModelShow = 0;
	float EntityHeight = 0.5f;
	boolean Gravity = false;
	boolean Collision = false;
	boolean forward = true;
	boolean PickedUp = false;
	boolean AbleToPickUp = false;
	float PickUpDistance = 0.4f;
	int Lives = 0;
	int Gems = 0;
	
	boolean ColBox = false;
	
	boolean MoveBetween = false;
	Vector3f MoveBetweenA;
	Vector3f MoveBetweenB;
	float MoveBetweenTime;
	float Transparency = 1;
	
	boolean gravCheck = false;
	boolean GravFirstPass = false;
	
	boolean Collapse = false;
	Vector3f CollapseData = new Vector3f();
	int CollapseTimer;
	int CollapseState = 0;
	
	boolean Light = false;
	Vector3f LightPosition = new Vector3f();
	Vector3f LightColour = new Vector3f();
	int LightRange;
	
	//event trigger whether there is an event or not
	//EventInfo 1st (0 - sphere)(1 - cube) 2nd (range) 3rd (0 - repeatable, 1 - non-repeatable, 2 cannot activate again)
	//EventTrigInfo depends on event type
	//MyEventType - which type of event, check enum list.
	
	Boolean EventTrigger = false;
	Boolean EventTriggered = false;
	Vector3f EventInfo = new Vector3f(0,0,0);
	Vector3f EventTrigInfo = new Vector3f(0,0,0);
	EntityEventType MyEventType;
	
	Vector3f Movement = new Vector3f(0,0,0);
	
	int timer = 0;
	
	Scanner s = null;
	
	Model CurrentModel;
	Model CollisionModel;
	
	float YMinus;
	
	boolean DamageCylinder = false;
	float DCHeight;
	float DCWidth;
	
	boolean OscUp = false;
	float OscUpDistance;
	float OscUpDownTime;
	float OscUpUpTime;
	int OscTimer = 0;
	int OscState = 1;
	
	String CurrentModelName;
	String CurrentTextureName;
	
	String CollisionModelName;
	String CollisionTextureName;
	
	String EventType;
	
	public Entity(){
		
	}
	
	public Entity(String FileName){
		
		String FileOpen = "src/res/EntityData/" + FileName + ".Ent";
		String TempMod = null;
		String TempText = null;
		int ModelCount = 0;
		
	    try {
			s = new Scanner(new BufferedReader(new FileReader(FileOpen)));
			
			while (s.hasNext()){
				String Temp = s.next();
				
				if (Temp.equals("object:")){	
					TempMod = s.next();
				}
				
				if (Temp.equals("collision:")){	
					Collision = s.nextBoolean();
				}
				
				if (Temp.equals("colbox:")){	
					ColBox = s.nextBoolean();
				}
				
				if (Temp.equals("collapse:")){	
					Collapse = s.nextBoolean();
				}
				
				if (Temp.equals("light:")){	
					Light = s.nextBoolean();
				}
				
				if (Temp.equals("collapserange:")){	
					CollapseData.x = s.nextFloat();
				}
				
				if (Temp.equals("collapsetime:")){	
					CollapseData.y = s.nextInt();
				}
				
				if (Temp.equals("collapsedowntime:")){	
					CollapseData.z = s.nextInt();
				}
				
				if (Temp.equals("lightposition:")){	
					LightPosition.x = s.nextFloat();
					LightPosition.y = s.nextFloat();
					LightPosition.z = s.nextFloat();
				}
				
				if (Temp.equals("transparency:")){	
					Transparency = s.nextFloat();
				}
				
				
				//EVENT STUFF
				if (Temp.equals("event:")){	
					EventTrigger = s.nextBoolean();
				}
				
				if (Temp.equals("eventinfo:")){	
					EventInfo.x = s.nextFloat();
					EventInfo.y = s.nextFloat();
					EventInfo.z = s.nextFloat();
				}
				
				if (Temp.equals("eventtriginfo:")){	
					EventTrigInfo.x = s.nextFloat();
					EventTrigInfo.y = s.nextFloat();
					EventTrigInfo.z = s.nextFloat();
				}
				
				if (Temp.equals("eventtype:")){	
					EventType = s.next();
					
					if(EventType.equals("teleport")){
						MyEventType = EntityEventType.Teleport;
						System.out.println("TeleportEventDetected");
					}
					
				}
				
				//EVENT STUFF END
				
				if (Temp.equals("lightcolour:")){	
					LightColour.x = s.nextFloat();
					LightColour.y = s.nextFloat();
					LightColour.z = s.nextFloat();
				}
				
				if (Temp.equals("lightrange:")){	
					LightRange = (int)(s.nextFloat());

				}
				
				if (Temp.equals("damagecylinder:")){	
					DamageCylinder = true;
					DCHeight = s.nextFloat();
					DCWidth = s.nextFloat();
				}
				
				if (Temp.equals("oscillateup:")){	
					OscUp = true;
					OscUpDistance = s.nextFloat();
					OscUpUpTime = s.nextFloat();
					OscUpDownTime = s.nextFloat();
				}
				
				if (Temp.equals("texture:")){	
					TempText = s.next();
					
					//Model TempModel = new Model(TempMod, TempText, Collision);
					//TempModel.SetReference(ModelCount);
					//ModelList.add(TempModel);
					CurrentModelName = TempMod;
					CurrentTextureName = TempText;
					
					EntityModelContainer.AddModel(TempMod, TempText, Collision);
					
					if (ColBox == true){
					//Model TempModelB = new Model(TempMod + "col", TempText, true);
					//TempModelB.SetReference(ModelCount);
					//TempModelB.ShowModel(false);
					//ModelList.add(TempModelB);
					

					EntityModelContainer.AddModel(TempMod + "col", TempText, true);
					CollisionModelName = TempMod + "col";
					CollisionTextureName = TempText;
					
					}
					
					ModelCount++;
					
				}
				
				if (Temp.equals("rotation_speed:")){	
					RotationSpeed = s.nextFloat();

				}
				
				if (Temp.equals("height:")){	
					EntityHeight = s.nextFloat();

				}
				
				if (Temp.equals("pickupdistance:")){	
					PickUpDistance = s.nextFloat();
				}
				
				if (Temp.equals("lives:")){	
					Lives = s.nextInt();
				}
				
				if (Temp.equals("gems:")){	
					Gems = s.nextInt();
				}
				
				if (Temp.equals("gravity:")){	
					Gravity = s.nextBoolean();
				}
				
				if (Temp.equals("pickup:")){	
					AbleToPickUp = s.nextBoolean();
				}
				
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
	    	s.close();
	    	
	    	CurrentModel = EntityModelContainer.FindModel(CurrentModelName, CurrentTextureName);
	    	
	    	if(ColBox == true){
	    		CollisionModel = EntityModelContainer.FindModel(CollisionModelName, CollisionTextureName);
	    	}
	    	
	    }
	    
	}
	
	public void SetMoveBetween(Vector3f PointA, Vector3f PointB, float time){
		
		MoveBetween = true;
		MoveBetweenA = PointA;
		MoveBetweenB = PointB;
		MoveBetweenTime = time;
		
	}
	
	void MoveBetweenPoint(Vector3f PointA, Vector3f PointB, float time){
		
		Vector3f Movement = new Vector3f(0,0,0);
		timer++;
		
		if((MyMath.Length(Position, PointA) < 0.01f || timer > time) && forward == false){
			forward = true;
			timer = 0;
		}
		if((MyMath.Length(Position, PointB) < 0.01f || timer > time)  && forward == true){
			forward = false;
			timer = 0;
		}
		if (forward == true){
			Movement.x = (PointB.x - PointA.x)/time;
			Movement.y = (PointB.y - PointA.y)/time;
			Movement.z = (PointB.z - PointA.z)/time;
		}
		if (forward == false){
			Movement.x = -((PointB.x - PointA.x)/time);
			Movement.y = -((PointB.y - PointA.y)/time);
			Movement.z = -((PointB.z - PointA.z)/time);
		}
		ApplyMovement(Movement);
		
	}
	
	public void ApplyMovement(Vector3f movement){
		Position.x += movement.x;
		Position.y += movement.y;
		Position.z += movement.z;
		
		/*
		for(Model Temp : ModelList){
			
			Temp.SetMovement(movement);
			
		}
		*/
		Movement = movement;
		//CurrentModel.SetMovement(movement);
		
	}
	
	public void SetPosition(Vector3f Pos){
		
		Position = Pos;
		if(OriginalSet == false){
			OriginalPosition.x = Position.x;
			OriginalPosition.y = Position.y;
			OriginalPosition.z = Position.z;
			OriginalSet = true;
		}
		/*
		for(Model Temp : ModelList){
			
			Temp.SetPosition(Position);
			
		}
		*/
		//CurrentModel.SetPosition(Position);
	}
	
	public void SetEntityEvent(){
		EventTrigger = true;
	}
	
	public void SetEventTrigInfo(Vector3f MyEventTrigInfo){
		EventTrigInfo = MyEventTrigInfo;
	}
	
	public void EventUpdate(){
		
		//has event been triggered
		if(EventInfo.x == 0){
			if(MyMath.Length(Position, GameData.PlayerPosition)<=EventInfo.y){
				
				EventTriggered = true;
				
			}
		}
		
		
		
		//what to do on trigger
		if(EventTriggered == true){
			if(MyEventType == EntityEventType.Teleport){
				GameData.ForcePlayerPosition = true;
				System.out.println(EventTrigInfo);
				GameData.PlayerForcedPosition.x = EventTrigInfo.x;
				GameData.PlayerForcedPosition.y = EventTrigInfo.y;
				GameData.PlayerForcedPosition.z = EventTrigInfo.z;
				System.out.println("PlayerForcePositionUpdated");
			}
		
		
		
		//after event done
			if(EventInfo.z == 0){
				EventTriggered = false;
			}
			if(EventInfo.z == 1){
				EventTriggered = false;
				EventInfo.z = 2;
			}
		}
		
	}
	
	public void Update(){
		
		//event
		if(EventTrigger == true && EventInfo.z != 2){
			EventUpdate();
		}
		
		
		Rotation += RotationSpeed;
		
		if(OscUp == true){
			if(OscState == 1){
				OscTimer++;
				if (OscTimer >= OscUpUpTime){
					OscTimer = 0;
					OscState = 2;
				}
			}
			if(OscState == 2){
				OscTimer++;
				Position.y -= OscUpDistance / 50;
				if(OscTimer >= 50){
					OscTimer = 0;
					OscState = 3;
				}
			}
			if(OscState == 3){
				OscTimer++;
				if (OscTimer >= OscUpDownTime){
					OscTimer = 0;
					OscState = 4;
				}
			}
			if(OscState == 4){
				OscTimer++;
				Position.y += OscUpDistance / 50;
				if(OscTimer >= 50){
					OscTimer = 0;
					OscState = 1;
				}
			}
			
		}
		
		if (DamageCylinder == true){
			
			if(MyMath.PointInCylinder(GameData.PlayerPosition, Position, DCHeight, DCWidth)){
				
				GameData.PlayerHit = true;
				
			}
			
		}
		
		if (MoveBetween == true){
			MoveBetweenPoint(MoveBetweenA, MoveBetweenB, MoveBetweenTime);
		}
		
		
		if(Light == true){
			AmbientLight.AddLight(LightPosition.x + Position.x, LightPosition.y + Position.y, LightPosition.z + Position.z, LightColour.x, LightColour.y, LightColour.z, LightRange);
			
		}
		
		if(Gravity == true && GravFirstPass == true){
			
			//if (gravCheck == false){
			DynamicCollision.VertCheck(Position);
			
			//gravCheck = true;
			YMinus = DynamicCollision.YMinus;
			//}
			
			if (Position.y - 0.02f > YMinus + EntityHeight){
				Position.y -= 0.02f;
			}
			
			//System.out.println(YMinus);
			//System.out.println(Position);
			
			
		}
		GravFirstPass = true;
		
		if (Collapse == true){
			if(MyMath.TwoDLength(new Vector2f(Position.x, Position.z), new Vector2f(GameData.PlayerPosition.x, GameData.PlayerPosition.z)) < CollapseData.x){
				if(GameData.PlayerPosition.y - Position.y <= 0.6f){
					if(CollapseState == 0){
						CollapseState = 1;
					}
				}
			}
			if(CollapseState == 1){
				CollapseTimer++;
				if(CollapseTimer> CollapseData.y){
					CollapseState = 2;
					CollapseTimer = 0;
				}
			}
			if(CollapseState == 2){
				ApplyMovement(new Vector3f(0, -1, 0));
				
				if(Position.y <= -100){
					CollapseState = 3;
				}
			}
			if(CollapseState == 3){
				CollapseTimer++;
				if(CollapseTimer> CollapseData.z){
					CollapseState = 0;
					CollapseTimer = 0;
					Position.x = OriginalPosition.x;
					Position.y = OriginalPosition.y;
					Position.z = OriginalPosition.z;
					ApplyMovement(new Vector3f(0, 0, 0));
				}
			}
		}
		
		if (AbleToPickUp == true && PickedUp == false){
			
			if(MyMath.Length(Position, GameData.PlayerPosition) < PickUpDistance){
				
				PickedUp = true;
				Light = false;
				GameData.CurrentGems += Gems;
				GameData.CurrentLives += Lives;
				
			}
			
		}
		
		/*
		for(Model Temp : ModelList){
			
			Temp.SetRotation(Rotation);
			
		}
		*/
		//CurrentModel.SetRotation(Rotation);
		
	}
	
	public void SetLightColour(Vector3f lightcolour){
		LightColour = lightcolour;
	}
	
	public void SetLightRange(int range){
		LightRange = range;
	}

	public void Draw(){
		
		if (PickedUp == false){
			
			/*
		for(Model Temp : ModelList){
			
			if(Temp.GetReference() == ModelShow){
				Temp.Draw();
			}
			
			
			CurrentModel.Draw();
			
		}
		*/

		CurrentModel.SetRotation(Rotation);
		CurrentModel.SetPosition(Position);
		CurrentModel.SetMovement(Movement);
		CurrentModel.SetTransparency(Transparency);
		CurrentModel.Draw();
		
		if(ColBox == true){
			
			//CollisionModel = EntityModelContainer.FindModel(CollisionModelName, CollisionTextureName);
			CollisionModel.ShowModel(false);
			CollisionModel.SetRotation(Rotation);
			CollisionModel.SetPosition(Position);
			CollisionModel.SetMovement(Movement);
			CollisionModel.Draw();
			
		}
		
		}
	}
	
	
}
