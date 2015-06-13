import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;


class GameLoop {
	
	static Level CurrentLevel;
	
	static GameState MyState = GameState.SplashScreen;
	
	static void Update(){
		if(MyState == GameState.SplashScreen){
			
			
			if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
				//ResetLevelData();
				//LoadLevel("Egypt");
				MyState = GameState.Level_Select;
				
			}
		}
		
		if(MyState == GameState.Loading){
			ResetLevelData();
			LoadLevel(GameData.LoadWhich);
			MyState = GameState.Level_Running;
		}
		
		if(MyState == GameState.Level_Select){
			
			LevelSelect.Update();
			
		}
		
		
		
		if(MyState == GameState.Level_Running){
			
			if(GameData.CurrentLives < 0){
				MyState = GameState.SplashScreen;
			}
			
			CurrentLevel.Update();
		}
    	
		
	}
	
	
	static void Draw(){
		
		
		if(MyState == GameState.Level_Select){
			LevelSelect.draw();
		}
		
		
		
		
		if(MyState == GameState.Level_Running || MyState == GameState.Level_Paused){
			CurrentLevel.Draw();
		}
		
	}
	
	
	static void LoadLevel(String Name){
		LoadScreen.LoadLoadTexture();
		CurrentLevel = new Level(Name);
		
	}
	
	static void ResetLevelData(){
		
		ThirdPersonCamera.PointAt = new Vector3f(0, 0.5f, 0);
		CameraControl.X = 0;
		CameraControl.Y = -0.5f;
		CameraControl.Z = 0;
		
		GameData.CurrentLives = 3;
		GameData.PlayerHit = false;
		GameData.CurrentLevelGems = 0;
		
	}

}
