import java.util.ArrayList;


class EntityModelContainer {

	static ArrayList<Model> ModelList = new ArrayList<>();
	static boolean exist = false;
	static boolean found = false;
	
	public static void AddModel(String ModelName, String TextureName, boolean CollisionOn){
		
		for(Model MyModel: ModelList){
			if(MyModel.GetModelName().equals(ModelName)){
				if(MyModel.GetTextureName().equals(TextureName)){
					exist = true;
				}
			}
		}
		
		if(exist == false){
			Model TempModel = new Model(ModelName, TextureName, CollisionOn);
			ModelList.add(TempModel);
			System.out.println("Enitity Added: " + ModelName + "  " + TextureName);
		}
		
		exist = false;
	}
	
	
	public static Model FindModel(String ModelName, String TextureName){
		
		//Model TempModel = new Model();
		int Reference = 0;
		
		for(int i = 0; i < ModelList.size(); i++){
			if(ModelList.get(i).GetModelName().equals(ModelName)){
				if(ModelList.get(i).GetTextureName().equals(TextureName)){	
					Reference = i;
				}
			}
		}
		
		if (found = true){
			System.out.println("Reference Passed: " + ModelName + "  " + TextureName);
		}
		
		if (found = false){
			System.out.println("Failed To Pass Reference: " + ModelName + "  " + TextureName);
		}
		
		found = false;
		return ModelList.get(Reference);
	}

}
