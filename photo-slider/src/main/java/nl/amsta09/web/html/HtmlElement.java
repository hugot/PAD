package nl.amsta09.web.html;

public abstract class HtmlElement {
	protected String id;
	protected String elementClass;
	protected String styling = "\n";
	protected String top = "\n";
	protected String middle = "\n";
	protected String bottom = "\n";
	

	public HtmlElement(String id, String elementClass){
		this.id = id;
		this.elementClass = elementClass;
	}

	public void addContent(String content){
		middle += content;
	}

	public void addElement(HtmlElement element){
		middle += element.getHtml();
	}
	
	public String getHtml(){
		return styling + top + middle + bottom;
	}

	public void setClass(String elementClass){
		this.elementClass = elementClass;
	}

	public void setId(String id){
		this.id = id;
	}
}
