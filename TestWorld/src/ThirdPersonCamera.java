import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


class ThirdPersonCamera {
	
	static float BaseDistance = 4f;
	static float BaseHeight = 2f;
	static float Distance = 4f;
	static float Height = 2f;
	static float Rotation = 180;
	static float Yaw = 0;
	static Vector3f Position = new Vector3f(0,0,0);
	static float Zoom = 1;
	static Vector3f PointAt = new Vector3f(0,0.8f,0);
	static float Speed = 0.05f;
	static boolean JumpOn = false;
	static float JumpTimer = 0;
	static int JumpHeight = 1;
	static float CharHeight = 0.5f;
	static boolean AutoRun = false;
	static int switchtimer = 0;
	static boolean ControlsOn = true;
	static float DropOff = -2f;
	static float PointAtRotation;
	static float BaseSpeed = 0.05f;
	static float RunSpeed = 0.1f;
	static float JumpSpeed = 0.08f;	
	static boolean AllowJumpGiveway = false;
	static int JumpGiveMaxTime = 10;
	static int JumpGiveTime = 0;
	
	static float OldYMinus;	
	
	static Vector3f ColMinusA = new Vector3f(0,5,0);
	
	static Vector3f ColPlusA = new Vector3f(0,5,0);
	
	static Model Joanna = new Model("Joanna", false);
	
	public static void SetPointAt(Vector3f Point){
		PointAt = Point;
	}
	
	public static void SetBaseDistance(Float distance){
		BaseDistance = distance;
	}
	
	public static void SetBaseHeight(Float height){
		BaseHeight = height;
	}
	
	public static void SetLevelDropOff(Float dropoff){
		DropOff = dropoff;
	}
	
	public static void Update(){
		
		//PlayerForceFullyMoved
		if(GameData.ForcePlayerPosition == true){
			//Position = GameData.PlayerForcedPosition;
			PointAt = GameData.PlayerForcedPosition;
			GameData.ForcePlayerPosition = false;
			System.out.println("Player Moved");
		}
		
		switchtimer++;
		
		OldYMinus = ColMinusA.y;
		
		DynamicCollision.VertCheck(PointAt);
		
		ColMinusA = new Vector3f(DynamicCollision.XMinus, DynamicCollision.YMinus, DynamicCollision.ZMinus);
		ColMinusA.y = Math.round(ColMinusA.y * 1000);
		ColMinusA.y = ColMinusA.y / 1000;
		
		ColMinusA.x = Math.round(ColMinusA.x * 1000);
		ColMinusA.x = ColMinusA.x / 1000;
		
		ColMinusA.z = Math.round(ColMinusA.z * 1000);
		ColMinusA.z = ColMinusA.z / 1000;
		
		ColPlusA = new Vector3f(DynamicCollision.XPlus, DynamicCollision.YPlus, DynamicCollision.ZPlus);
		ColPlusA.y = Math.round(ColPlusA.y * 1000);
		ColPlusA.y = ColPlusA.y / 1000;
		
		ColPlusA.x = Math.round(ColPlusA.x * 1000);
		ColPlusA.x = ColPlusA.x / 1000;
		
		ColPlusA.z = Math.round(ColPlusA.z * 1000);
		ColPlusA.z = ColPlusA.z / 1000;
		
		if(OldYMinus - ColMinusA.y > 0.5 && PointAt.y - OldYMinus  > 0 && PointAt.y - OldYMinus  < 0.8 && JumpOn == false){
			AllowJumpGiveway = true;
			JumpGiveTime = 0;
		}
		
		if(AllowJumpGiveway == true){
			JumpGiveTime++;
			if (JumpGiveTime > JumpGiveMaxTime){
				AllowJumpGiveway = false;
			}
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && ControlsOn == true) {
			Speed = RunSpeed;
		}
		else if (ControlsOn == true){
			Speed = BaseSpeed;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q) && ControlsOn == true) {     
			if(((PointAt.x- Math.sin(Math.toRadians(Rotation)) * Speed)  >= ColPlusA.x + Speed || (PointAt.x- Math.sin(Math.toRadians(Rotation)) * Speed) <= ColPlusA.x - 0.2f) && ((PointAt.x- Math.sin(Math.toRadians(Rotation)) * Speed)  >= ColMinusA.x + Speed || (PointAt.x- Math.sin(Math.toRadians(Rotation)) * Speed) <= ColMinusA.x - 0.2f)){
            	PointAt.x-= Math.sin(Math.toRadians(Rotation)) * Speed;
			}
            
			if(((PointAt.z+ Math.cos(Math.toRadians(Rotation)) * Speed) >= ColPlusA.z + Speed || (PointAt.z+ Math.cos(Math.toRadians(Rotation)) * Speed) <= ColPlusA.z - 0.2f) && ((PointAt.z+ Math.cos(Math.toRadians(Rotation)) * Speed) >= ColMinusA.z + Speed || (PointAt.z+ Math.cos(Math.toRadians(Rotation)) * Speed) <= ColMinusA.z - 0.2f)){
				PointAt.z+= Math.cos(Math.toRadians(Rotation)) * Speed;
			}
			PointAtRotation = Rotation;
        }
		if(Keyboard.isKeyDown(Keyboard.KEY_E) && ControlsOn == true) {      
			if(((PointAt.x+ Math.sin(Math.toRadians(Rotation)) * Speed)  >= ColPlusA.x + Speed || (PointAt.x+ Math.sin(Math.toRadians(Rotation)) * Speed) <= ColPlusA.x - 0.2f) && ((PointAt.x+ Math.sin(Math.toRadians(Rotation)) * Speed)  >= ColMinusA.x + Speed || (PointAt.x+ Math.sin(Math.toRadians(Rotation)) * Speed) <= ColMinusA.x - 0.2f)){
				PointAt.x+= Math.sin(Math.toRadians(Rotation)) * Speed;
			}
			if(((PointAt.z- Math.cos(Math.toRadians(Rotation)) * Speed) >= ColPlusA.z + Speed || (PointAt.z- Math.cos(Math.toRadians(Rotation)) * Speed) <= ColPlusA.z - 0.2f) && ((PointAt.z- Math.cos(Math.toRadians(Rotation)) * Speed) >= ColMinusA.z + Speed || (PointAt.z- Math.cos(Math.toRadians(Rotation)) * Speed) <= ColMinusA.z - 0.2f)){
				PointAt.z-= Math.cos(Math.toRadians(Rotation)) * Speed;
			}
			PointAtRotation = Rotation;
        }
		
		if(Keyboard.isKeyDown(Keyboard.KEY_S) && ControlsOn == true) {   
			if(((PointAt.x- Math.sin(Math.toRadians(Rotation-90)) * Speed)  >= ColPlusA.x + Speed || (PointAt.x- Math.sin(Math.toRadians(Rotation-90)) * Speed) <= ColPlusA.x - 0.2f) && ((PointAt.x- Math.sin(Math.toRadians(Rotation-90)) * Speed)  >= ColMinusA.x + Speed || (PointAt.x- Math.sin(Math.toRadians(Rotation-90)) * Speed) <= ColMinusA.x - 0.2f)){
				PointAt.x-= Math.sin(Math.toRadians(Rotation-90)) * Speed;
			}
			if(((PointAt.z+ Math.cos(Math.toRadians(Rotation-90)) * Speed) >= ColPlusA.z + Speed || (PointAt.z+ Math.cos(Math.toRadians(Rotation-90)) * Speed) <= ColPlusA.z - 0.2f) && ((PointAt.z+ Math.cos(Math.toRadians(Rotation-90)) * Speed) >= ColMinusA.z + Speed || (PointAt.z+ Math.cos(Math.toRadians(Rotation-90)) * Speed) <= ColMinusA.z - 0.2f)){
				PointAt.z+= Math.cos(Math.toRadians(Rotation-90)) * Speed;
			}
			AutoRun = false;
			PointAtRotation = Rotation;
        }
		if((Keyboard.isKeyDown(Keyboard.KEY_W)|| AutoRun == true) && ControlsOn == true) {   
			if(((PointAt.x- Math.sin(Math.toRadians(Rotation+90)) * Speed)  >= ColPlusA.x + Speed || (PointAt.x- Math.sin(Math.toRadians(Rotation+90)) * Speed) <= ColPlusA.x - 0.2f) && ((PointAt.x- Math.sin(Math.toRadians(Rotation+90)) * Speed)  >= ColMinusA.x + Speed || (PointAt.x- Math.sin(Math.toRadians(Rotation+90)) * Speed) <= ColMinusA.x - 0.2f)){
				PointAt.x-= Math.sin(Math.toRadians(Rotation+90)) * Speed;
			}
			if(((PointAt.z+ Math.cos(Math.toRadians(Rotation+90)) * Speed) >= ColPlusA.z + Speed || (PointAt.z+ Math.cos(Math.toRadians(Rotation+90)) * Speed) <= ColPlusA.z - 0.2f) && ((PointAt.z+ Math.cos(Math.toRadians(Rotation+90)) * Speed) >= ColMinusA.z + Speed || (PointAt.z+ Math.cos(Math.toRadians(Rotation+90)) * Speed) <= ColMinusA.z - 0.2f)){
				PointAt.z+= Math.cos(Math.toRadians(Rotation+90)) * Speed;
			}
			PointAtRotation = Rotation;
        }
		if(Keyboard.isKeyDown(Keyboard.KEY_W) && ControlsOn == true){
			AutoRun = false;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_R) && ControlsOn == true) {  
			if (AutoRun == true && switchtimer > 10){
				AutoRun = false;
			}
			else if (switchtimer > 10){
				AutoRun = true;
			}
			switchtimer = 0;
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && (PointAt.y - Speed <= ColMinusA.y + CharHeight || AllowJumpGiveway == true) && ControlsOn == true) {  
			
			if (JumpOn == false){
				JumpOn = true;
				JumpTimer = 0;
				AllowJumpGiveway = false;
			}
		
		}
		if(JumpOn == true){
			if (PointAt.y + Speed < ColPlusA.y - CharHeight){
				JumpTimer += JumpSpeed;
				PointAt.y += JumpSpeed;
			}
			else{
				JumpOn = false;
			}
			if (JumpTimer >= JumpHeight){
				JumpOn = false;
			}
		}
		
		if(JumpOn == false){
			if (PointAt.y - Speed > ColMinusA.y + CharHeight){
				if(ControlsOn == true){
					PointAt.y -= BaseSpeed;
				}
				else{
					PointAt.y -= Speed;
				}
			}
			if (PointAt.y - 0.003f > ColMinusA.y + CharHeight){
				PointAt.y -= 0.003f;
			}
			if (PointAt.y - 0.006f > ColMinusA.y + CharHeight){
				PointAt.y -= 0.006f;
			}
			if (PointAt.y - 0.012f > ColMinusA.y + CharHeight){
				PointAt.y -= 0.012f;
			}
		}
		
		if (PointAt.y < ColMinusA.y + CharHeight){
			PointAt.y = ColMinusA.y + CharHeight;
		}
		//System.out.println(DynamicCollision.YMinusVec);
		if (PointAt.y <= ColMinusA.y + CharHeight +0.01f){
			PointAt.x += DynamicCollision.YMinusVec.x;
			PointAt.y += DynamicCollision.YMinusVec.y;
			PointAt.z += DynamicCollision.YMinusVec.z;
		}
		
		if (PointAt.y < -80 || GameData.PlayerHit == true){
			PointAt.x = 0;
			PointAt.y = 0.4f;
			PointAt.z = 0;
			GameData.CurrentLives -= 1;
			ControlsOn = true;
			GameData.PlayerHit = false;
		}
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_O)){
			
			PointAt.y++;
			
		}
		
		
		/*
		GL11.glDisable(GL11.GL_CULL_FACE);
		Shaders.NewShader.Activate();
		Joanna.AlphaClear();
		Joanna.SetPosition(new Vector3f(PointAt.x, PointAt.y - 0.52f, PointAt.z));
		Joanna.SetRotation(-PointAtRotation-90);
		Joanna.SetPlayerShadowAllowance(false);
		Joanna.Draw();
		Shaders.NewShader.DeActivate();
		GL11.glEnable(GL11.GL_CULL_FACE);
		*/
		
		if (Rotation > 360){
			Rotation = Rotation - 360;
		}
		if (Rotation < 0){
			Rotation = 360 + Rotation;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_A) && ControlsOn == true) {   
			Rotation -= 2;
        }
		if(Keyboard.isKeyDown(Keyboard.KEY_D) && ControlsOn == true) {   
			Rotation += 2;
        }
		if (Mouse.hasWheel() == true){
			int Scroll = Mouse.getDWheel();
			if (Scroll < 0){
				Zoom += 0.05;
			}
			if (Scroll > 0){
				Zoom -= 0.05;
			}
		}
		if (Zoom < 0.5)
		{
			Zoom = 0.5f;
		}
		if (Zoom > 1.5)
		{
			Zoom = 1.5f;
		}
		
		Distance = BaseDistance * Zoom;
		//Height = BaseHeight * Zoom;
		Height = Distance * Distance /10;
		
		Position.x = MyMath.RotateAboutPoint(new Vector2f(PointAt.x, PointAt.z), Distance, Rotation).x;
		Position.z = MyMath.RotateAboutPoint(new Vector2f(PointAt.x, PointAt.z), Distance, Rotation).y;
		Position.y = PointAt.y + Height;
		
		if (Position.y <= DropOff){
			Position.y = DropOff;
			ControlsOn = false;
			Speed = 1;
		}
		
		Yaw = MyMath.CalculateYaw(PointAt, Position) - 90;
		
		//System.out.println(ColMinusA.y);
		
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_M)){
			Position.x += 1;
		}
		
		ModelOffset.SetOffset(new Vector3f (-Position.x, -Position.y, -Position.z),Rotation-90, Yaw);
		
		
		
		GameData.PlayerPosition = PointAt;
		GameData.PlayerYMinus = new Vector3f(PointAt.x, ColMinusA.y, PointAt.z);
		GameData.PlayerRotation = Rotation;
		
		
		
		
		Shaders.NewShader.Activate();
		int locb = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "CharPosition");
		 
		 float TempAngle = MyMath.AngleInDegrees(ThirdPersonCamera.Position, ThirdPersonCamera.PointAt, new Vector3f(ThirdPersonCamera.PointAt.x, ThirdPersonCamera.ColMinusA.y, ThirdPersonCamera.PointAt.z));
		 
		 double TempHyp = MyMath.Length(ThirdPersonCamera.Position,new Vector3f(ThirdPersonCamera.PointAt.x, ThirdPersonCamera.ColMinusA.y, ThirdPersonCamera.PointAt.z));
		 
		 float TempY = (float) (Math.sin(TempAngle * Math.PI/180) * TempHyp);
		 
		 float TempZ = (float) (Math.cos(TempAngle * Math.PI/180) * TempHyp);
		 
		 GL20.glUniform4f(locb,0,-TempY,-TempZ,0);
		 Shaders.NewShader.DeActivate();
		 
		 Mouse.setGrabbed(false);
		 
		
	}
	
	public static float GetRotation(){
		return PointAtRotation;
	}
	
	public static Vector3f GetPointAt(){
		return PointAt;
	}

}
