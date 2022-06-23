package baedal.java.app.orders;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class Order {
	private Date orderDate;
	private String customerId;
	private int storeNum;
	private String orderMenu;
	private int orderPrice;
	private int pay;
	private int deliveryStatus;
	private String storeName;
	@Override
	public String toString() {
		String pay = "";
		if(this.pay == 1) pay = "바로결제";
		else if(this.pay == 2) pay = "만나서결제";
		
		String status = "";
		if(this.deliveryStatus == 1) status = "조리중";
		else if(this.deliveryStatus == 2) status = "배달중";
		else if(this.deliveryStatus == 3) status = "배달완료";
		
		return "Order [orderDate=" + orderDate + ", customerId=" + customerId + ", storeNum=" + storeNum
				+ ", orderMenu=" + orderMenu + ", orderPrice=" + orderPrice + ", pay=" + pay + ", deliveryStatus="
				+ status + ", storeName=" + storeName + "]";
	}
	
	
}
