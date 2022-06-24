package baedal.java.app.customers;

import java.util.List;

import baedal.java.app.common.Management;
import baedal.java.app.orders.OrderControl;
import baedal.java.app.owners.Owner;

public class CustomerOrderManagement extends Management {
	private static String customerId;
	private static Customer customer;
	private List<Owner> openList;
	private int listSize;

	@SuppressWarnings("static-access")
	public CustomerOrderManagement(String id) {
		this.customerId = id;
		this.customer = customerDAO.showProfile(id);
		this.openList = ownerDAO.openList();
		listSize = openList.size();
	}

	public void run() {
		
		while (true) {
			//영업중인 가게 리스트
			for(int idx=0; idx<listSize; idx++) {
				System.out.println("--------------");
				System.out.println("[선택번호:"+(idx+1)+"]");
				System.out.println(openList.get(idx));
			}
			System.out.println("--------------");
			// 메뉴출력
			menuPrint();
			try {
				// 메뉴입력
				int num = inputNum();

				// 기능
				if (num == 1) {
					// 가게선택
					selectStore();
				} else if (num == 2) {
					// 현재주소확인
					viewAddrInfo();
				} else if (num == 3) {
					// 뒤로가기
					return;
				} else {
					// 다시입력
					System.out.println("잘못된 입력입니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력하세요.");
			}
		}
	}

	private void selectStore() {
		System.out.println("선택번호를 입력하세요.");
		int num = inputNum();
		if (num > listSize || num < 1) {
			System.out.println("다시 입력하세요.");
		} else {
			new OrderControl(customerId, openList.get(num-1)).run();
		}
	}

	private void viewAddrInfo() {
		System.out.print("현재 주소 : ");
		System.out.println(customer.getAddr());
	}

	@Override
	protected void menuPrint() {
		System.out.println("-----------------------------------------");
		System.out.println("   1.주문하기    2.현재주소확인    3.뒤로가기   ");
		System.out.println("-----------------------------------------");
	}

}
