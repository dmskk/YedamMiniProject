package baedal.java.app.menus;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Menu {
	private long storeNum;
	private String menuName;
	private int menuPrice;
	private String menuContent;
	
	
	@Override
	public String toString() {
		String me = "[" + this.menuName + "] " + this.menuPrice + "원";
		String content = " |" + this.menuContent + "|";
		
		if(this.menuContent == null) return me;
		else return me + content;
	}
}


