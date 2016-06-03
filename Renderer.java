public class Renderer {
	
	private String content = "";

	public Renderer(String path) {
		content = (new HTMLReader(path)).getContent();
		System.out.println("HTMLReader output: " + content);
		System.out.println();
		content = readCSS(content);
		System.out.println("CSSReader output: " + content);
		System.out.println();
		content = readJS(content);
		System.out.println("JSReader output: " + content);
		System.out.println();
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
