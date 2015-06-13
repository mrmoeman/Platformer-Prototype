import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Vector3f;


class CameraControl {
	
	static float X = 0.0f;
	static float Y = -5f;
	static float Z = 0.0f;
	static float Rotation;
	static float Yaw;
	static float Speed = 0.05f;
	static Vector3f Cam = new Vector3f(0,0,0);
	
	static float OldX = 0.0f;
	static float OldY = -0.8f;
	static float OldZ = 0.0f;
	
	
	
	static boolean JumpOn = false;
	static float JumpTimer = 0;
	static int JumpHeight = 1;
	static float CharHeight = 0.5f;
	static float BaseSpeed = 0.05f;
	static float RunSpeed = 0.1f;
	static float JumpSpeed = 0.08f;	
	static boolean AllowJumpGiveway = false;
	static int JumpGiveMaxTime = 10;
	static int JumpGiveTime = 0;
	static boolean ControlsOn = true;
	static float OldYMinus;
	
	
	static Vector3f ColMinusA = new Vector3f(0,5,0);
	
	static Vector3f ColPlusA = new Vector3f(0,5,0);
	
	public static void Update(){
		
		OldYMinus = ColMinusA.y;
		
		DynamicCollision.VertCheck(new Vector3f(-X, -Y, -Z));
		
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
		
		//System.out.println("ColPlusA " + ColPlusA);
		
		//System.out.println("Z " + Z);
		
		
		
		if(OldYMinus - ColMinusA.y > 0.5 && -Y - OldYMinus  > 0 && -Y - OldYMinus  < 0.8 && JumpOn == false){
			AllowJumpGiveway = true;
			JumpGiveTime = 0;
		}
		
		if(AllowJumpGiveway == true){
			JumpGiveTime++;
			if (JumpGiveTime > JumpGiveMaxTime){
				AllowJumpGiveway = false;
			}
		}
		
		
		
		OldX = X;
		OldY = Y;
		OldZ = Z;
		
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) {     
			if((-(X - Math.sin(Math.toRadians(Rotation)) * Speed)  >= ColPlusA.x + 0.2f || -(X - Math.sin(Math.toRadians(Rotation)) * Speed) <= ColPlusA.x - 0.2f) && (-(X - Math.sin(Math.toRadians(Rotation)) * Speed)  >= ColMinusA.x + 0.2f || -(X - Math.sin(Math.toRadians(Rotation)) * Speed) <= ColMinusA.x - 0.2f)){
            	X-= Math.sin(Math.toRadians(Rotation)) * Speed;
			}
            
			if((-(Z + Math.cos(Math.toRadians(Rotation)) * Speed) >= ColPlusA.z + 0.2f || -(Z + Math.cos(Math.toRadians(Rotation)) * Speed) <= ColPlusA.z - 0.2f) && (-(Z + Math.cos(Math.toRadians(Rotation)) * Speed) >= ColMinusA.z + 0.2f || -(Z + Math.cos(Math.toRadians(Rotation)) * Speed) <= ColMinusA.z - 0.2f)){
				Z+= Math.cos(Math.toRadians(Rotation)) * Speed;
			}
        }
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) {      
			if((-(X + Math.sin(Math.toRadians(Rotation)) * Speed)  >= ColPlusA.x + 0.2f || -(X + Math.sin(Math.toRadians(Rotation)) * Speed) <= ColPlusA.x - 0.2f) && (-(X + Math.sin(Math.toRadians(Rotation)) * Speed)  >= ColMinusA.x + 0.2f || -(X + Math.sin(Math.toRadians(Rotation)) * Speed) <= ColMinusA.x - 0.2f)){
				X+= Math.sin(Math.toRadians(Rotation)) * Speed;
			}
			if((-(Z - Math.cos(Math.toRadians(Rotation)) * Speed) >= ColPlusA.z + 0.2f || -(Z - Math.cos(Math.toRadians(Rotation)) * Speed) <= ColPlusA.z - 0.2f) && (-(Z - Math.cos(Math.toRadians(Rotation)) * Speed) >= ColMinusA.z + 0.2f || -(Z - Math.cos(Math.toRadians(Rotation)) * Speed) <= ColMinusA.z - 0.2f)){
				Z-= Math.cos(Math.toRadians(Rotation)) * Speed;
			}
        }
		
		if(Keyboard.isKeyDown(Keyboard.KEY_Q)) {   
			if((-(X - Math.sin(Math.toRadians(Rotation-90)) * Speed)  >= ColPlusA.x + 0.2f || -(X - Math.sin(Math.toRadians(Rotation-90)) * Speed) <= ColPlusA.x - 0.2f) && (-(X - Math.sin(Math.toRadians(Rotation-90)) * Speed)  >= ColMinusA.x + 0.2f || -(X - Math.sin(Math.toRadians(Rotation-90)) * Speed) <= ColMinusA.x - 0.2f)){
				X-= Math.sin(Math.toRadians(Rotation-90)) * Speed;
			}
			if((-(Z + Math.cos(Math.toRadians(Rotation-90)) * Speed) >= ColPlusA.z + 0.2f || -(Z + Math.cos(Math.toRadians(Rotation-90)) * Speed) <= ColPlusA.z - 0.2f) && (-(Z + Math.cos(Math.toRadians(Rotation-90)) * Speed) >= ColMinusA.z + 0.2f || -(Z + Math.cos(Math.toRadians(Rotation-90)) * Speed) <= ColMinusA.z - 0.2f)){
				Z+= Math.cos(Math.toRadians(Rotation-90)) * Speed;
			}
        }
		if(Keyboard.isKeyDown(Keyboard.KEY_E)) {   
			if((-(X - Math.sin(Math.toRadians(Rotation+90)) * Speed)  >= ColPlusA.x + 0.2f || -(X - Math.sin(Math.toRadians(Rotation+90)) * Speed) <= ColPlusA.x - 0.2f) && (-(X - Math.sin(Math.toRadians(Rotation+90)) * Speed)  >= ColMinusA.x + 0.2f || -(X - Math.sin(Math.toRadians(Rotation+90)) * Speed) <= ColMinusA.x - 0.2f)){
				X-= Math.sin(Math.toRadians(Rotation+90)) * Speed;
			}
			if((-(Z + Math.cos(Math.toRadians(Rotation+90)) * Speed) >= ColPlusA.z + 0.2f || -(Z + Math.cos(Math.toRadians(Rotation+90)) * Speed) <= ColPlusA.z - 0.2f) && (-(Z + Math.cos(Math.toRadians(Rotation+90)) * Speed) >= ColMinusA.z + 0.2f || -(Z + Math.cos(Math.toRadians(Rotation+90)) * Speed) <= ColMinusA.z - 0.2f)){
				Z+= Math.cos(Math.toRadians(Rotation+90)) * Speed;
			}
        }
		
		
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && ControlsOn == true) {
			Speed = RunSpeed;
		}
		else if (ControlsOn == true){
			Speed = BaseSpeed;
		}
		
		
		
		
		if(Keyboard.isKeyDown(Keyboard.KEY_SPACE) && (-Y - Speed <= ColMinusA.y + CharHeight || AllowJumpGiveway == true) && ControlsOn == true) {  
			
			if (JumpOn == false){
				JumpOn = true;
				JumpTimer = 0;
				AllowJumpGiveway = false;
			}
		
		}
		if(JumpOn == true){
			if (-Y + Speed < ColPlusA.y - CharHeight){
				JumpTimer += JumpSpeed;
				Y -= JumpSpeed;
			}
			else{
				JumpOn = false;
			}
			if (JumpTimer >= JumpHeight){
				JumpOn = false;
			}
		}
		
		if(JumpOn == false){
			if (-Y - Speed > ColMinusA.y + CharHeight){
				if(ControlsOn == true){
					Y += BaseSpeed;
				}
				else{
					Y += Speed;
				}
			}
			if (-Y - 0.003f > ColMinusA.y + CharHeight){
				Y += 0.003f;
			}
			if (-Y - 0.006f > ColMinusA.y + CharHeight){
				Y += 0.006f;
			}
			if (-Y - 0.012f > ColMinusA.y + CharHeight){
				Y += 0.012f;
			}
		}
		
		if (-Y < ColMinusA.y + CharHeight){
			Y = ColMinusA.y + CharHeight;
		}
		
		
		
		//System.out.println(-Y + " " + ColMinusA.y + "  " + CharHeight);
		
		
		if (-Y <= ColMinusA.y + CharHeight +0.01f){
			X -= DynamicCollision.YMinusVec.x;
			Y -= DynamicCollision.YMinusVec.y;
			Z -= DynamicCollision.YMinusVec.z;
		}
		
		
		
		
		if (-Y < -5 || GameData.PlayerHit == true){
			X = 0;
			Y = -0.4f;
			Z = 0;
			GameData.CurrentLives -= 1;
			ControlsOn = true;
			GameData.PlayerHit = false;
		}
		
		
		
		
		//if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {   
		//	Y +=Speed;
       // }
		
		
		if (Mouse.getY() - 240 > 0){
			Yaw -= 4f;
		}
		if (Mouse.getY() - 240 < 0){
			Yaw += 4f;
		}
		
		if (Mouse.getX() - 320 > 0){
			Rotation += 4f;
		}
		if (Mouse.getX() - 320 < 0){
			Rotation -= 4f;
		}
		
		if (Yaw > 90){
			Yaw = 90;
		}
		if (Yaw < -90){
			Yaw = -90;
		}
		
		Cam.x = X;
		Cam.y = Y;
		Cam.z = Z;
		
		Mouse.setGrabbed(true);
		Mouse.setCursorPosition(320, 240);
		
		GameData.PlayerPosition = new Vector3f(-X, -Y, -Z);
		GameData.PlayerYMinus = new Vector3f(-X, ColMinusA.y, -Z);
		GameData.PlayerRotation = Rotation;
		
		Cam.y = Cam.y - CharHeight;
		
		
		Shaders.NewShader.Activate();
		int locb = GL20.glGetUniformLocation(Shaders.NewShader.shaderProgram, "CharPosition");
		 
		 GL20.glUniform4f(locb,1000,1000,1000,0);
		 Shaders.NewShader.DeActivate();
		
		
		ModelOffset.SetOffset(Cam, Rotation, Yaw);
		
	
	}
	
	

}
