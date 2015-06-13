import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;


public class OctreeRoot {
	
	ArrayList<TriangleFace> FaceList = new ArrayList<>();
	
	ArrayList<TriangleFace> UnSortableFaceList = new ArrayList<>();
	
	ArrayList<OctreeNode> NodeList = new ArrayList<>();
	
	float MaxFaces = 50;
	
	Vector3f centerPoint = new Vector3f(-1000,-900,-1000);
	
	float Size = 2000;
	
	public OctreeRoot(){
		
		
		
	}
	
	public void CheckLimit(){
		
		if (FaceList.size() > MaxFaces){
			if(NodeList.size() < 8){
				AddNodes();
			}
			ShuffleFaces();
		}
		
		for(OctreeNode MyNode: NodeList){
			
			MyNode.CheckLimit();
			
		}
		
	}
	
	public void AddNodes(){
		OctreeNode TempNode = new OctreeNode(new Vector3f(centerPoint.x + Size/2, centerPoint.y + Size/2, centerPoint.z + Size/2), Size/2);
		TempNode.SetNodeDepth(1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x - Size/2, centerPoint.y + Size/2, centerPoint.z + Size/2), Size/2);
		TempNode.SetNodeDepth(1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x + Size/2, centerPoint.y + Size/2, centerPoint.z - Size/2), Size/2);
		TempNode.SetNodeDepth(1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x - Size/2, centerPoint.y + Size/2, centerPoint.z - Size/2), Size/2);
		TempNode.SetNodeDepth(1);
		NodeList.add(TempNode);
		
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x + Size/2, centerPoint.y - Size/2, centerPoint.z + Size/2), Size/2);
		TempNode.SetNodeDepth(1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x - Size/2, centerPoint.y - Size/2, centerPoint.z + Size/2), Size/2);
		TempNode.SetNodeDepth(1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x + Size/2, centerPoint.y - Size/2, centerPoint.z - Size/2), Size/2);
		TempNode.SetNodeDepth(1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x - Size/2, centerPoint.y - Size/2, centerPoint.z - Size/2), Size/2);
		TempNode.SetNodeDepth(1);
		NodeList.add(TempNode);
	}
	
	public void AddTri(TriangleFace Temp){
		
		FaceList.add(Temp);
		
	}
	
	public void ShuffleFaces(){
		
		for(TriangleFace Tri: FaceList){
			
			boolean GotOne = false;
			
			for(OctreeNode MyNode: NodeList){
				
				if (GotOne == false){
					if(MyMath.PointInCube(Tri.VertA, MyNode.GetCenterPoint(), Size/2) == true && MyMath.PointInCube(Tri.VertB, MyNode.GetCenterPoint(), Size/2) == true && MyMath.PointInCube(Tri.VertC, MyNode.GetCenterPoint(), Size/2) == true){
						MyNode.AddTri(Tri);
						GotOne = true;
					}
				}

			}
			
			if (GotOne == false){
				
				UnSortableFaceList.add(Tri);
				
			}
			
			//FaceList.remove(Tri);
			
		}
		FaceList.clear();
		
	}
	
	
	public void clearlist(){
		FaceList.clear();
		
		//System.out.println(UnSortableFaceList.size());
		
		UnSortableFaceList.clear();
		
		NodeList.clear();
		
	}
	
	public ArrayList<TriangleFace> GetFaces(Vector3f CheckPoint){
		
		//System.out.println(FaceList.size());
		
		ArrayList<TriangleFace> TempFaces = new ArrayList<>();
		//TempFaces.addAll(FaceList);
		TempFaces.addAll(UnSortableFaceList);
		//System.out.println(UnSortableFaceList.size());
		//System.out.println("new check");
		for(OctreeNode MyNode: NodeList){
			if(MyMath.PointInCube(CheckPoint, MyNode.GetCenterPoint(), Size/2)){
				TempFaces.addAll(MyNode.GetFaces(CheckPoint));
				
				//System.out.println("In a node");
			}
			
		}
		
		return TempFaces;
		 
	}
	
	

}
