public class Renderer {
	
	private String content = "";

	public Renderer(String path) {
		System.out.println("Entering HTMLReader");		// DEBUG
		System.out.println();							// DEBUG
		content = (new HTMLReader(path)).getContent();
		System.out.println(content);					// DEBUG
		System.out.println();							// DEBUG
		
		System.out.println("Entering CSSReader");		// DEBUG
		System.out.println();							// DEBUG
		content = readCSS(content);
		System.out.println(content);					// DEBUG
		System.out.println();							// DEBUG
		
		
		//content = readJS(content);
	}

	public String readCSS(String content) {
		return (new CSSReader(content)).getContent();
	}
	
	public String readJS(String content) {
		return (new JSReader(content)).getContent();
	}
	
	public String readTXT() {
		return (new TXTReader(content)).getContent();
	}
	
	public String getContent() {
		return content;
	}
}
