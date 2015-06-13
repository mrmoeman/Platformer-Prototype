import org.lwjgl.util.vector.Vector3f;


public class TriangleFace {
	
	Vector3f VertA = new Vector3f();
	Vector3f VertB = new Vector3f();
	Vector3f VertC= new Vector3f();
	Vector3f Normal= new Vector3f();
	Vector3f Movement = new Vector3f(0,0,0);
	
	public TriangleFace(){
		
	}
	
	
	public TriangleFace(Vector3f VA, Vector3f VB, Vector3f VC, Vector3f VD){
		VertA = VA;
		VertB = VB;
		VertC = VC;
		Normal = VD;
	}
	
	
	public void SetMovement(Vector3f movement){
		Movement = movement;
	}
	
	public boolean HasMovement(){
		if (Movement != new Vector3f(0,0,0)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public Vector3f GetMovement(){
		return Movement;
	}

}
