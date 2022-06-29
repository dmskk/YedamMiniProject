package baedal.java.app.orders;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
	private Timestamp orderDate;
	private String customerId;
	private long storeNum;
	private String orderMenu;
	private int orderPrice;
	private int pay;
	private int deliveryStatus;
	private String storeName;


	@Override
	public String toString() {
		String pay = "";
		if (this.pay == 1)
			pay = "바로결제";
		else if (this.pay == 2)
			pay = "만나서결제";

		String status = "";
		String emogi = "";
		if (this.deliveryStatus == 1) {
			status = "고객님의 주문을 맛있게 조리중이에요😋😋";
			emogi = "\n.　 ∧,,∧" + "\n`（´･ ω ･）　ヽヽ" + "\n　/つ　   〇━⊂二二フ";
			status += emogi;
			
		} else if (this.deliveryStatus == 2) {
			status = "고객님의 주문을 안전하게 배달중이에요🤗🤗";
			emogi = "\n.　　  ∧_∧" + "\n　　  ( ・ω・)" + "\n    [) ⊃┳⊃" + "\n  = ◎・┗◎┻⊃";
			status += emogi;
		} else if (this.deliveryStatus == 3) {
			status = "배달상태 : 배달완료";
		}

		int menuStringLength = this.orderMenu.length();

		String me = "주문일자 : " + String.valueOf(this.orderDate).substring(0, 19) + "\n주문가게 : " + this.storeName
				+ "\n주문메뉴 : " + this.orderMenu.substring(0, menuStringLength - 2) + "\n주문금액 : " + this.orderPrice
				+ "\n결제방식 : " + pay + "\n" + status;

		return me;
	}

}
