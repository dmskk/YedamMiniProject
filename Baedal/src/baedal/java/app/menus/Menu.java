package baedal.java.app.menus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Menu {
	private long storeNum;
	private String menuName;
	private int menuPrice;
	private String menuContent;
}
