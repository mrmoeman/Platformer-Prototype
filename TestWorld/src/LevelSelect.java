import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;


 class LevelSelect {
	 
	 static ArrayList<String> LevelList = new ArrayList<>();
	 static Scanner s;
	 static int CurrentHighlight = 0;
	 static boolean ListGot = false;
	 static boolean UpDown = false;
	 static boolean DownDown = false;
	 
	 public static void Update(){
		 if(ListGot == false){
			 ReadLevelList();
			 ListGot= true;
		 }
		 
		 
		 if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			 UpDown = true;
		 }
		 
		 if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			 DownDown = true;
		 }
		 
		 if(UpDown == true && Keyboard.isKeyDown(Keyboard.KEY_UP) == false){
			 UpDown = false;
			 CurrentHighlight--;
		 }
		 if(DownDown == true && Keyboard.isKeyDown(Keyboard.KEY_DOWN) == false){
			 DownDown = false;
			 CurrentHighlight++;
		 }
		 
		 
		 if(CurrentHighlight < 0){
			 CurrentHighlight = LevelList.size() - 1;
		 }
		 
		 if(CurrentHighlight > LevelList.size() - 1){
			 CurrentHighlight = 0;
		 }
		 
		 //System.out.println(LevelList.size());
		 if(Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			 GameData.LoadWhich = LevelList.get(CurrentHighlight);
			 GameLoop.MyState = GameState.Loading;
		 }
		 
	 }
	 
	 public static void draw(){
		 Shaders.NewShader.DeActivate();
		 GL11.glDisable(GL11.GL_CULL_FACE);
		 
		 MyFont.Scale(2, 2);
		 MyFont.Print(LevelList.get(CurrentHighlight), -0.8f, 0f, 1f, 1f, 1f);
		 
		 MyFont.Scale(1, 1);
		 MyFont.Print(LevelList.get(MyMath.FindPosition(CurrentHighlight, LevelList.size() - 1, -1)), -0.8f, 0.1f, 1f, 1f, 1f);
		 
		 MyFont.Scale(0.6f, 0.6f);
		 MyFont.Print(LevelList.get(MyMath.FindPosition(CurrentHighlight, LevelList.size() - 1, -2)), -0.8f, 0.2f, 1f, 1f, 1f);
		 
		 MyFont.Scale(1, 1);
		 MyFont.Print(LevelList.get(MyMath.FindPosition(CurrentHighlight, LevelList.size() - 1, 1)), -0.8f, -0.1f, 1f, 1f, 1f);
		 
		 MyFont.Scale(0.6f, 0.6f);
		 MyFont.Print(LevelList.get(MyMath.FindPosition(CurrentHighlight, LevelList.size() - 1, 2)), -0.8f, -0.2f, 1f, 1f, 1f);
		 
		 GL11.glEnable(GL11.GL_CULL_FACE);
		 GL11.glColor3f(1.0f, 1.0f, 1.0f);
		 
	 }
	 
	 
	 static void ReadLevelList(){
		 
		 try {
			 String FileName = "src/res/MiscData/LevelList.Dat";
			s = new Scanner(new BufferedReader(new FileReader(FileName)));
			
			while (s.hasNext()){
				
				String levelNameString = new String();
				levelNameString = s.next();
				LevelList.add(levelNameString);
				
			}
			
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally{
			 s.close();
		 }
		 
		 
	 }

}
