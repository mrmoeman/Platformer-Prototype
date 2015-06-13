import org.lwjgl.util.vector.Vector3f;


class GameData {
	
	static int CurrentGems = 0;
	static int CurrentLevelGems = 500;
	static int CurrentLives = 3;
	
	static boolean PlayerHit = false;
	
	static boolean ScreenShootMode = false;
	
	static Vector3f PlayerPosition = new Vector3f(1000,1000,1000);
	static float PlayerRotation = 0;
	static Vector3f PlayerYMinus = new Vector3f(0,0,0); 
	
	static boolean ThirdPersonRegular = true;
	static boolean ThirdPersonFree = false;
	
	static boolean FirstPersonRegular = false;
	
	static String LoadWhich = "Egypt";
	
	static boolean ForcePlayerPosition = false;
	static Vector3f PlayerForcedPosition = new Vector3f(0,0,0);

}
