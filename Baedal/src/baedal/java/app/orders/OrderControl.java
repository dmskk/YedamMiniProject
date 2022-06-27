package baedal.java.app.orders;

import java.util.ArrayList;
import java.util.List;

import baedal.java.app.common.Management;
import baedal.java.app.customers.Customer;
import baedal.java.app.menus.Menu;
import baedal.java.app.owners.Owner;

public class OrderControl extends Management {

	@SuppressWarnings("unused")
	private String customerId;
	private Customer customer;
	private Owner owner;
	private static List<Menu> list;
	private static int listSize;
	private static List<Menu> cart = new ArrayList<>();
	private Order order;
	private static int totalPrice = 0;
	private static String menus = "";
	private double point;

	public OrderControl(String customerId, Owner owner) {
		this.owner = owner;
		this.customerId = customerId;
		this.customer = customerDAO.showProfile(customerId);
		this.order = new Order();
		this.order.setCustomerId(customerId);
		this.order.setStoreName(owner.getStoreName());
		this.order.setStoreNum(owner.getCorpNum());
		this.point = customer.getPoint();
	}

	public void runCheck() {
		// 가게정보
		System.out.println("------------------------------------");
		System.out.println("[선택가게]");
		System.out.println(owner);
		System.out.println("------------------------------------");
		// 메뉴조회
		System.out.println();
		System.out.println("[메뉴]");
		list = menuDAO.viewMenu(owner);
		listSize = list.size();
		for (int idx = 0; idx < listSize; idx++) {
			System.out.println("=================");
			System.out.println("선택번호:" + (idx + 1));
			System.out.println(list.get(idx));
		}
		System.out.println("=================");
		System.out.println("------------------------------------");
		System.out.println();

		while (true) {
			// 메뉴출력
			menuPrint();
			try {
				// 메뉴입력
				int num = inputSelectNum();

				// 기능
				if (num == 1) {
					// 장바구니 담기
					insertCart();
				} else if (num == 2) {
					// 장바구니 확인
					viewCart();
				} else if (num == 3) {
					// 결제하기
					// 결제 완료하면 회원초기화면으로 돌아가기
					orderPay();
					break;
				} else if (num == 9) {
					break;
				} else {
					System.out.println("잘못된 입력입니다.");
				}

			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력하세요.");
			}
		}
	}

	private void insertCart() {
		System.out.println("장바구니에 담을 메뉴 선택번호를 입력하세요.");
		try {
			int num = inputNum();
			if (num > listSize || num < 1) {
				System.out.println("잘못된 입력입니다.");
			} else {
				Menu select = list.get(num - 1);
				cart.add(select);
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력하세요.");
		}
	}

	private void viewCart() {
		for (Menu menu : cart) {
			System.out.println(menu);
		}
	}

	private void orderPay() {
		System.out.println("--------------------------------------");
		System.out.println("   1.바로결제   2.만나서결제   9.뒤로가기   ");
		System.out.println("--------------------------------------");
		try {
			int num = inputNum();
			if (num == 1 || num == 2) {
				
				// 결제방식 저장
				order.setPay(num);
				// 메뉴이름 정리
				for (Menu menu : cart) {
					menus += (menu.getMenuName() + ", ");
					totalPrice += menu.getMenuPrice();
				}
				// 주문메뉴 저장
				order.setOrderMenu(menus);
				// 결제금액 저장
				order.setOrderPrice(totalPrice);
				// 주문내역 추가
				orderDAO.insertOrder(order);

				// 포인트 적립
				// 4번등급 -> 적립없음, 3번등급->0.1%, 2번등급->0.5%, 1번등급->1%
				int grade = customer.getGrade();
				if (grade == 3) {
					point += totalPrice * 0.01;
				} else if (grade == 2) {
					point += totalPrice * 0.05;
				} else if (grade == 1) {
					point += totalPrice * 0.1;
				}
				customer.setPoint(point);
				customerDAO.updateProfilePoint(customer);
				
			} else if (num != 9) {
				System.out.println("잘못된 입력입니다.");
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력하세요.");
		}
	}

	@Override
	protected void menuPrint() {
		System.out.println("-----------------------------------------------------");
		System.out.println("  1.장바구니담기   2.장바구니확인    3.결제하기    9.뒤로가기  ");
		System.out.println("-----------------------------------------------------");
	}
	

}
