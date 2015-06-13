import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;


public class OctreeNode {
	
	ArrayList<TriangleFace> FaceList = new ArrayList<>();
	ArrayList<TriangleFace> UnSortableFaceList = new ArrayList<>();
	
	ArrayList<OctreeNode> NodeList = new ArrayList<>();
	
	float MaxFaces = 50;
	
	Vector3f centerPoint = new Vector3f(0,0,0);
	
	int NodeDepth = 0;
	
	float Size;
	
	public OctreeNode(Vector3f CenterPoint, float size){
		centerPoint = CenterPoint;
		Size = size;
	}
	
	public void AddTri(TriangleFace Temp){
		//System.out.println("ADDED");
		FaceList.add(Temp);
		
	}
	
	public void AddNodes(){
		OctreeNode TempNode = new OctreeNode(new Vector3f(centerPoint.x + Size/2, centerPoint.y + Size/2, centerPoint.z + Size/2), Size/2);
		TempNode.SetNodeDepth(NodeDepth + 1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x - Size/2, centerPoint.y + Size/2, centerPoint.z + Size/2), Size/2);
		TempNode.SetNodeDepth(NodeDepth + 1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x + Size/2, centerPoint.y + Size/2, centerPoint.z - Size/2), Size/2);
		TempNode.SetNodeDepth(NodeDepth + 1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x - Size/2, centerPoint.y + Size/2, centerPoint.z - Size/2), Size/2);
		TempNode.SetNodeDepth(NodeDepth + 1);
		NodeList.add(TempNode);
		
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x + Size/2, centerPoint.y - Size/2, centerPoint.z + Size/2), Size/2);
		TempNode.SetNodeDepth(NodeDepth + 1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x - Size/2, centerPoint.y - Size/2, centerPoint.z + Size/2), Size/2);
		TempNode.SetNodeDepth(NodeDepth + 1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x + Size/2, centerPoint.y - Size/2, centerPoint.z - Size/2), Size/2);
		TempNode.SetNodeDepth(NodeDepth + 1);
		NodeList.add(TempNode);
		
		TempNode = new OctreeNode(new Vector3f(centerPoint.x - Size/2, centerPoint.y - Size/2, centerPoint.z - Size/2), Size/2);
		TempNode.SetNodeDepth(NodeDepth + 1);
		NodeList.add(TempNode);
	}

	
	
	public ArrayList<TriangleFace> GetFaces(Vector3f CheckPoint){
		
		//System.out.println(FaceList.size());
		
		ArrayList<TriangleFace> TempFaces = new ArrayList<>();
		TempFaces.addAll(FaceList);
		TempFaces.addAll(UnSortableFaceList);
		
		for(OctreeNode MyNode: NodeList){
			if(MyMath.PointInCube(CheckPoint, MyNode.GetCenterPoint(), Size/2)){
				TempFaces.addAll(MyNode.GetFaces(CheckPoint));
				//System.out.println("In a node");
			}
			
		}
		
		return TempFaces;
		 
	}
	
	
	
	
	public void CheckLimit(){
		
		if (FaceList.size() > MaxFaces){
			if (NodeDepth < 8){
				if(NodeList.size() < 8){
					AddNodes();
				}
			}
			ShuffleFaces();
		}
		
		for(OctreeNode MyNode: NodeList){
			
			MyNode.CheckLimit();
			
		}
		
	}
	
	public void SetNodeDepth(int depth){
		NodeDepth = depth;
	}
	
	
	public void clearlist(){
		FaceList.clear();
		UnSortableFaceList.clear();
		
		for(OctreeNode MyNode: NodeList){
			MyNode.clearlist();
		}
		
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
	
	
	
	public Vector3f GetCenterPoint(){
		return centerPoint;
	}
	
}
