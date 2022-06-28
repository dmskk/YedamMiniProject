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
	private double point;
	private int grade;
	

	@Override
	public String toString() {
		int grade = this.grade;
		String gradeStr = "";
		
		if (grade == 4) gradeStr = "고마운분";
		else if (grade == 3) gradeStr = "귀한분";
		else if (grade == 2) gradeStr = "더귀한분";
		else if (grade == 1) gradeStr = "천생연분";
		
		String me = "\n\n" + "ID : " + this.id + "\n이름 : " + this.name + "\n휴대폰번호 : 010" +
					this.phoneNumber + "\n주소 : " + this.addr + "\n닉네임 : " + this.nickname + "\n적립포인트 : " + this.point + "\n등급 : " + gradeStr
					+ "\n\n🛸　　　 　🌎　°　　🌓　•　　.°•　　　🚀 ✯\n" + "　　　★　*　　　　　°　　　　🛰 　°·　　   🪐\n" + ".　　　•　° ★　•  ☄\n" + "▁▂▃▄▅▆▇▇▆▅▄▃▁▂\n";
		return me;
	}
	
	
}
