import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;


class DynamicCollision {
	
	public static ArrayList<TriangleFace> FaceList = new ArrayList<>();
	public static TriangleFace temp = new TriangleFace();
	
	public static OctreeRoot MyOctree = new OctreeRoot();
	
	public static float YPlus = 1000;
	public static float YMinus = -1000;
	
	public static float XPlus = 1000;
	public static float XMinus = -1000;
	
	public static float ZPlus = 1000;
	public static float ZMinus = -1000;
	
	public static Vector3f YMinusVec = new Vector3f(0,0,0);
	
	public static void AddTriangleFace(Vector3f VA, Vector3f VB, Vector3f VC, Vector3f VD){
		
		temp = new TriangleFace();
		temp.VertA = VA;
		temp.VertB = VB;
		temp.VertC = VC;
		temp.Normal = VD;
		//FaceList.add(temp);
		
		MyOctree.AddTri(temp);

	}
	
public static void AddTriangleFace(TriangleFace MyTri){
		
		if(MyTri.Movement.x == 0 && MyTri.Movement.y == 0 && MyTri.Movement.z == 0){
			MyOctree.AddTri(MyTri);
		}
		else{ 
			FaceList.add(MyTri);
		}
	}
	
	
	public static void ClearList(){	
		
		FaceList.clear();
		
		MyOctree.clearlist();

	}
	
	
	public static void CheckOctree(){
		
		MyOctree.CheckLimit();
		
	}
	
	public static void VertCheck(Vector3f CheckPoint){
		
		YMinusVec = new Vector3f(0,0,0);
		
		YPlus = 1000;
		YMinus = -1000;
		
		XPlus = 1000;
		XMinus = -1000;
		
		ZPlus = 1000;
		ZMinus = -1000;

		
		//Vector3f CheckPoint = new Vector3f(-CameraControl.X, -CameraControl.Y, -CameraControl.Z);
		/*
		Shaders.NewShader.Activate();
		GL11.glLoadIdentity();
		ModelOffset.WorldOffset();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glColor3f(0f, 0f, 1f); //Y
		GL11.glVertex3f( CheckPoint.x, 20,CheckPoint.z); 
		GL11.glVertex3f( CheckPoint.x , - 20, CheckPoint.z); 
		GL11.glVertex3f( CheckPoint.x + 0.02f, - 20,CheckPoint.z); 
		GL11.glVertex3f( CheckPoint.x + 0.02f, 20,CheckPoint.z); 
		
		GL11.glColor3f(0f, 1f, 0f); //z
		GL11.glVertex3f( CheckPoint.x, CheckPoint.y,20); 
		GL11.glVertex3f( CheckPoint.x , CheckPoint.y + 0.02f, 20); 
		GL11.glVertex3f( CheckPoint.x, CheckPoint.y  + 0.02f,-20); 
		GL11.glVertex3f( CheckPoint.x , CheckPoint.y ,-20); 
		
		GL11.glColor3f(1f, 0f, 0f); //x
		GL11.glVertex3f( 20, CheckPoint.y,CheckPoint.z); 
		GL11.glVertex3f( 20 , CheckPoint.y + 0.02f, CheckPoint.z); 
		GL11.glVertex3f( -20, CheckPoint.y + 0.02f ,CheckPoint.z); 
		GL11.glVertex3f( -20 , CheckPoint.y ,CheckPoint.z); 
		GL11.glEnd();
		
		GL11.glColor3f(1f, 1f, 1f);*/
		
		//System.out.println(MyOctree.GetFaces(CheckPoint, 2000).size());
		
		ArrayList<TriangleFace> TempFaces = MyOctree.GetFaces(CheckPoint);
		TempFaces.addAll(FaceList);
		//System.out.println(TempFaces.size());
		
		for(TriangleFace Tri : TempFaces) {
			
			//System.out.println(Tri.VertA);
			//GL11.glColor3f(1f, 1f, 1f);
			/*if(DistanceofPointFromCamera(Tri.VertA) < 0.2f){
				count++;
				GL11.glColor3f(1f, 0f, 0f);
			}
			
			if(DistanceofPointFromCamera(Tri.VertB) < 0.2f){
				count++;
				GL11.glColor3f(1f, 0f, 0f);
			}
			
			if(DistanceofPointFromCamera(Tri.VertC) < 0.2f){
				count++;
				GL11.glColor3f(1f, 0f, 0f);
			}*/
			
			//Y
			if(MyMath.Length(Tri.VertA, CheckPoint) < 3 || MyMath.Length(Tri.VertB, CheckPoint) < 3 || MyMath.Length(Tri.VertC, CheckPoint) < 3){
			if (AreParralel(Tri.Normal, new Vector3f(0,1,0)) == false){
				
				if (PointInTwoDTriangle(Tri.VertA, Tri.VertB, Tri.VertC, LineIntersectWithPlanePoint(new Vector3f(CheckPoint.x, CheckPoint.y, CheckPoint.z ), new Vector3f(0,1,0), Tri.VertA, Tri.Normal), new Vector3f(0,1,0)) == true){
					
					Vector3f Temp = LineIntersectWithPlanePoint(new Vector3f(CheckPoint.x, CheckPoint.y, CheckPoint.z ), new Vector3f(0,1,0), Tri.VertA, Tri.Normal);
					
					if (Temp.y > CheckPoint.y && Temp.y < YPlus){
						YPlus = Temp.y;
						//GL11.glColor3f(0f, 0f, 1f);
					}
					
					if (Temp.y < CheckPoint.y && Temp.y > YMinus){
						if (Tri.VertA.y >= Temp.y - 0.02f || Tri.VertB.y >= Temp.y- 0.02f || Tri.VertC.y >= Temp.y- 0.02f)
						YMinus = Temp.y;
						
						//System.out.println("Intersect " + Temp);
						//System.out.println("Normal " + Tri.Normal);	
						//System.out.println("A " + Tri.VertA);	
						//System.out.println("B " + Tri.VertB);	
						//System.out.println("C " + Tri.VertC);	
						//System.out.println("Check " + CheckPoint);	
						//GL11.glColor3f(0f, 0f, 1f);
						
						if (Tri.HasMovement() == true){
							YMinusVec = Tri.GetMovement();
						}
						else{
							YMinusVec = new Vector3f(0,0,0);
						}
						
					}
					
					
					
				}
				
			}
			
			//Z
			if (AreParralel(Tri.Normal, new Vector3f(0,0,1)) == false){
				
				if (PointInTwoDTriangle(Tri.VertA, Tri.VertB, Tri.VertC, LineIntersectWithPlanePoint(new Vector3f(CheckPoint.x, CheckPoint.y, CheckPoint.z ), new Vector3f(0,0,1), Tri.VertA, Tri.Normal), new Vector3f(0,0,1)) == true){
					
					Vector3f Temp = LineIntersectWithPlanePoint(new Vector3f(CheckPoint.x, CheckPoint.y, CheckPoint.z ), new Vector3f(0,0,1), Tri.VertA, Tri.Normal);
					
					if (Temp.z > CheckPoint.z && Temp.z < ZPlus){
						ZPlus = Temp.z;
						
						//System.out.println("Intersect " + Temp);
						//System.out.println("Normal " + Tri.Normal);	
						//System.out.println("A " + Tri.VertA);	
						//System.out.println("B " + Tri.VertB);	
						//System.out.println("C " + Tri.VertC);	
						//System.out.println("Check " + CheckPoint);	
						
					}
					
					if (Temp.z < CheckPoint.z && Temp.z > ZMinus){
						ZMinus = Temp.z;
					}
					
					//GL11.glColor3f(0f, 1f, 0f);
					
				}
				
			}
			
			//X
			if (AreParralel(Tri.Normal, new Vector3f(1,0,0)) == false){
				
				if (PointInTwoDTriangle(Tri.VertA, Tri.VertB, Tri.VertC, LineIntersectWithPlanePoint(new Vector3f(CheckPoint.x, CheckPoint.y, CheckPoint.z ), new Vector3f(1,0,0), Tri.VertA, Tri.Normal), new Vector3f(1,0,0)) == true){
					
					Vector3f Temp = LineIntersectWithPlanePoint(new Vector3f(CheckPoint.x, CheckPoint.y, CheckPoint.z ), new Vector3f(1,0,0), Tri.VertA, Tri.Normal);
					
					if (Temp.x > CheckPoint.x && Temp.x < XPlus){
						XPlus = Temp.x;
					}
					
					if (Temp.x < CheckPoint.x && Temp.x > XMinus){
						XMinus = Temp.x;
					}
					
					//GL11.glColor3f(1f, 0f, 0f);
					
				}
				
			}
			}
			/*GL11.glLoadIdentity();  

			Shaders.NewShader.Activate();
	    	ModelOffset.WorldOffset();
	    		
	    	 GL11.glDisable(GL11.GL_TEXTURE_2D);
	    	 GL11.glEnable(GL11.GL_COLOR_MATERIAL);
	    	 
	    	 GL11.glBegin(GL11.GL_TRIANGLES);                    // Drawing Using Triangles
	         GL11.glMaterialf(GL11.GL_FRONT, GL11.GL_SHININESS, 50);
	    	 
	    	 GL11.glNormal3f( Tri.Normal.x, Tri.Normal.y,Tri.Normal.z); 
	    	 GL11.glVertex3f( Tri.VertA.x, Tri.VertA.y, Tri.VertA.z);         // Bottom Right
	    	 GL11.glNormal3f( Tri.Normal.x, Tri.Normal.y,Tri.Normal.z); 
	    	 GL11.glVertex3f( Tri.VertB.x, Tri.VertB.y, Tri.VertB.z);         // Bottom Right
	    	 GL11.glNormal3f( Tri.Normal.x, Tri.Normal.y,Tri.Normal.z); 
	    	 GL11.glVertex3f( Tri.VertC.x, Tri.VertC.y, Tri.VertC.z);         // Bottom Right
	    		
	    	 
	         
	    	 GL11.glColor3f(1f, 1f, 1f);
	         GL11.glEnd();
	         Shaders.NewShader.DeActivate();*/
			
		}
		
		//MyFont.Print("Count: " + GameData.CurrentGems, -1.5f, 1.0f, 1.0f, 1.0f, 1.0f);
		
		//MyFont.Print("XPlus: " + XPlus + " YPlus: " + YPlus + " ZPlus: " + ZPlus, -1.5f, 1.0f, 1.0f, 1.0f, 1.0f);
		
		//MyFont.Print("XMinus: " + XMinus + " YMinus: " + YMinus + " ZMinus: " + ZMinus, -1.5f, 0.8f, 1.0f, 1.0f, 1.0f);
		
		//MyFont.Print("CheckX: " + CheckPoint.x + " CheckY: " + CheckPoint.y + " CheckZ: " + CheckPoint.z, -1.5f, 0.6f, 1.0f, 1.0f, 1.0f);
		//System.out.println("YMinusVec " + YMinusVec);	

	}
	
	public static double DistanceofPointFromCamera(Vector3f point){
		
		double XY = Math.sqrt(((point.x + CameraControl.X) * (point.x + CameraControl.X)  +  (point.y + CameraControl.Y) * (point.y + CameraControl.Y)));
		
		double XYZ = Math.sqrt((XY * XY  +  (point.z + CameraControl.Z) * (point.z + CameraControl.Z)));
		
		return XYZ;
	}
	
	public static double Area(Vector3f VA, Vector3f VB, Vector3f VC){
		
		double AB = Length(VA, VB);
		double AC = Length(VA, VC);
		double BC = Length(VB, VC);
		
		double P = (AB + AC + BC)/2;
		
		double area =  (Math.sqrt(P*(P-AB)*(P-BC)*(P-AC))); 
		
		if (AB == Double.NaN || AC == Double.NaN || BC == Double.NaN){
			System.out.println("Nan");
			return Double.NaN;
		}
		else{
			return area;
		}
	}
	
	public static double TwoDArea(Vector2f VA, Vector2f VB, Vector2f VC){
		
		double AB = TwoDLength(VA, VB);
		double AC = TwoDLength(VA, VC);
		double BC = TwoDLength(VB, VC);
		
		double P = (AB + AC + BC)/2;
		
		double area =  (Math.sqrt(P*(P-AB)*(P-BC)*(P-AC))); 
		
		if (AB == Double.NaN || AC == Double.NaN || BC == Double.NaN){
			System.out.println("Nan");
			return Double.NaN;
		}
		else{
			return area;
		}
	}
	
	public static double Length(Vector3f VA, Vector3f VB){
		
		double XY = ((VA.x - VB.x) * (VA.x - VB.x)  +  (VA.y - VB.y) * (VA.y -  VB.y));
		
		
		double XYZ = (XY  +  (VA.z - VB.z) * (VA.z - VB.z));
		
		double finxyz = Math.sqrt(XYZ);
		
		return finxyz;
	}
	
	public static double TwoDLength(Vector2f VA, Vector2f VB){
		
		double XY = ((VA.x - VB.x) * (VA.x - VB.x)  +  (VA.y - VB.y) * (VA.y -  VB.y));

		double finxyz = Math.sqrt(XY);
		
		return finxyz;
	}
	
	public static Vector3f LineIntersectWithPlanePoint(Vector3f LineOrigin, Vector3f LineVector, Vector3f PlanePoint, Vector3f PlaneNormal){
		
		double PlaneNP = PlaneNormal.x * PlanePoint.x + PlaneNormal.y * PlanePoint.y + PlaneNormal.z * PlanePoint.z;
		double PlaneNLineP = PlaneNormal.x * LineOrigin.x + PlaneNormal.y * LineOrigin.y + PlaneNormal.z * LineOrigin.z;
		double PlaneNLineV = PlaneNormal.x * LineVector.x + PlaneNormal.y * LineVector.y + PlaneNormal.z * LineVector.z;
		double LineMagnitude = (PlaneNP - PlaneNLineP)/PlaneNLineV;
		
		Vector3f IntersectPoint = new Vector3f();
		IntersectPoint.x = (float) (LineOrigin.x + LineVector.x * LineMagnitude);
		IntersectPoint.y = (float) (LineOrigin.y + LineVector.y * LineMagnitude);
		IntersectPoint.z = (float) (LineOrigin.z + LineVector.z * LineMagnitude);
		
		//System.out.println("PlaneNP" + PlaneNP);
		
		return IntersectPoint;
	}
	
	public static boolean AreParralel(Vector3f VecA, Vector3f VecB){
		
		if (VecA.x / VecB.x == VecA.y/VecB.y && VecA.z/VecB.z == VecA.y/VecB.y){
			return true;
		}
		else return false;
		
	}
	
	public static boolean PointInTriangle(Vector3f VertA, Vector3f VertB, Vector3f VertC, Vector3f VertPoint){
		
		double ABC = Area(VertA, VertB, VertC);
		double ABP = Area(VertA, VertB, VertPoint);
		double APC = Area(VertA, VertPoint, VertC);
		double PBC = Area(VertPoint, VertB, VertC);
		Vector3f UVW = new Vector3f((float)(APC/ABC),(float) (ABP/ABC), (float)(PBC/ABC));
		
		/*if (UVW.x >= 0 && UVW.x <=1 && UVW.y >= 0 && UVW.y <=1 && UVW.z >= 0 && UVW.z <=1){
			
			return true;
		}
		else{
			return false;
		}*/
		
		if ((ABP + APC + PBC) < ABC){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	public static boolean PointInTwoDTriangle(Vector3f VertA, Vector3f VertB, Vector3f VertC, Vector3f VertPoint, Vector3f Ignore){
		
		
		double ABC = 0;
		double ABP = 0;
		double APC = 0;
		double PBC = 0;
		
		
		if (Ignore.x > 0){
			ABC = TwoDArea(new Vector2f(VertA.y, VertA.z), new Vector2f(VertB.y, VertB.z), new Vector2f(VertC.y, VertC.z));
			ABP = TwoDArea(new Vector2f(VertA.y, VertA.z), new Vector2f(VertB.y, VertB.z), new Vector2f(VertPoint.y, VertPoint.z));
			APC = TwoDArea(new Vector2f(VertA.y, VertA.z), new Vector2f(VertPoint.y, VertPoint.z), new Vector2f(VertC.y, VertC.z));
			PBC = TwoDArea(new Vector2f(VertPoint.y, VertPoint.z), new Vector2f(VertB.y, VertB.z), new Vector2f(VertC.y, VertC.z));
		}
		if (Ignore.y > 0){
			ABC = TwoDArea(new Vector2f(VertA.x, VertA.z), new Vector2f(VertB.x, VertB.z), new Vector2f(VertC.x, VertC.z));
			ABP = TwoDArea(new Vector2f(VertA.x, VertA.z), new Vector2f(VertB.x, VertB.z), new Vector2f(VertPoint.x, VertPoint.z));
			APC = TwoDArea(new Vector2f(VertA.x, VertA.z), new Vector2f(VertPoint.x, VertPoint.z), new Vector2f(VertC.x, VertC.z));
			PBC = TwoDArea(new Vector2f(VertPoint.x, VertPoint.z), new Vector2f(VertB.x, VertB.z), new Vector2f(VertC.x, VertC.z));
		}
		if (Ignore.z > 0){
			ABC = TwoDArea(new Vector2f(VertA.y, VertA.x), new Vector2f(VertB.y, VertB.x), new Vector2f(VertC.y, VertC.x));
			ABP = TwoDArea(new Vector2f(VertA.y, VertA.x), new Vector2f(VertB.y, VertB.x), new Vector2f(VertPoint.y, VertPoint.x));
			APC = TwoDArea(new Vector2f(VertA.y, VertA.x), new Vector2f(VertPoint.y, VertPoint.x), new Vector2f(VertC.y, VertC.x));
			PBC = TwoDArea(new Vector2f(VertPoint.y, VertPoint.x), new Vector2f(VertB.y, VertB.x), new Vector2f(VertC.y, VertC.x));
		}
		
		Vector3f UVW = new Vector3f((float)(APC/ABC),(float) (ABP/ABC), (float)(PBC/ABC));
		/*
		if (UVW.x >= 0 && UVW.x <=1 && UVW.y >= 0 && UVW.y <=1 && UVW.z >= 0 && UVW.z <=1){
			
			return true;
		}
		else{
			return false;
		}*/
		
		if ((ABP + APC + PBC) <= ABC+(ABC/10)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
}
	
	
