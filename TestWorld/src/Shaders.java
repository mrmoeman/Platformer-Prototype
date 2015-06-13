
class Shaders {
	
	static ShaderProgram NewShader;
	static ShaderProgram BloomShader;
	
	static void ShaderSetup(){
		
		NewShader = new ShaderProgram("res/shader.vert", "res/shader.frag");
		BloomShader = new ShaderProgram("res/shader.vert", "res/bloom.frag");
	
	}

}
