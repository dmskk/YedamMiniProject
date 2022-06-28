package baedal.java.app.owners;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Owner {
	private long corpNum;
	private String password;
	private String storeName;
	private int storeValue;
	private int timeOpen;
	private int timeClose;
	
	@Override
	public String toString() {
		String value = "";
		if(this.storeValue == 1) value = "한식";
		else if(this.storeValue == 2) value = "분식";
		else if(this.storeValue == 3) value = "치킨";
		else if(this.storeValue == 4) value = "피자";
		else if(this.storeValue == 5) value = "일식";
		else if(this.storeValue == 6) value = "양식";
		else if(this.storeValue == 7) value = "패스트푸드";
		else if(this.storeValue == 8) value = "야식";
		else if(this.storeValue == 9) value = "카페";
		else if(this.storeValue == 0) value = "중식";
		
		String me = "가게이름 : " + this.storeName + "\n업종 : " + value + "\n오픈시간 : " + this.timeOpen + "시\n마감시간 : " + this.timeClose + "시";
		
		return me;
	}
	
	
}
