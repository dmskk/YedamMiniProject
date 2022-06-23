package baedal.java.app.customers;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Customer {
	private String id;
	private String password;
	private String name;
	private int phoneNumber;
	private String addr;
	private String nickname;
	private int point;
	private int grade;
	
	
	@Override
	public String toString() {
		int grade = this.grade;
		String str = "";
		
		if (grade == 4) str = "고마운분";
		else if (grade == 3) str = "귀한분";
		else if (grade == 2) str = "더귀한분";
		else if (grade == 1) str = "천생연분";
		
		return "customer [id=" + id + ", password=" + password + ", name=" + name + ", phoneNumber=" + phoneNumber
				+ ", addr=" + addr + ", nickname=" + nickname + ", point=" + point + "점, grade=" + str + "]";
	}
	
	
}
