import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;


class ModelOffset {
	
	static Vector3f Position = new Vector3f(0,0,0);
	static float Rotation = 0;
	static float Yaw = 0;
	

	public static void SetOffset(Vector3f position, float rotation, float yaw){
		Position = position;
		Rotation = rotation;
		Yaw = yaw;
		
	}
	
	public static void WorldOffset(){
		
		GL11.glRotatef(Yaw, 1, 0, 0);
		GL11.glRotatef(Rotation, 0, 1, 0);
		GL11.glTranslatef(Position.x, Position.y, Position.z);  
		
		//System.out.println(Position);
		//System.out.println(Rotation);
		//System.out.println(Yaw);
		
		
	}
	
	
}
