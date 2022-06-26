package baedal.java.app.customers;

import java.util.List;

import baedal.java.app.common.Management;
import baedal.java.app.orders.Order;
import baedal.java.app.reviews.ReviewControl;

public class CustomerInfoManagement extends Management {
	private static String id;
	private static Customer customer;


	@SuppressWarnings("static-access")
	public CustomerInfoManagement(String id) {
		this.id = id;
		this.customer = customerDAO.showProfile(id);
	}

	public int runout() {
		int login = 0;
		
		//지난달 주문내역에 따른 등급
		int lastCount = orderDAO.lastMonthOrderCount(id);
		if(lastCount < 5) {
			customer.setGrade(4);
		} else if(lastCount < 10) {
			customer.setGrade(3);
		} else if(lastCount < 20) {
			customer.setGrade(2);
		} else {
			customer.setGrade(1);
		}
		customerDAO.updateProfileGrade(customer);

		while (true) {
			// 메뉴출력
			menuPrint();
			try {
				// 메뉴입력
				int num = inputNum();

				// 기능
				if (num == 1) {
					// 주문하기
					order();
				} else if (num == 2) {
					// 주소변경
					updateAddr();
				} else if (num == 3) {
					// 닉네임 변경
					updateNickname();
				} else if (num == 4) {
					// 고객정보확인
					viewProfile();
				} else if (num == 5) {
					// 주문내역
					viewOrderList();
				} else if (num == 6) {
					// 후기작성
					writeReview();
				} else if (num == 7) {
					// 탈퇴
					deleteAccount();
					login = 1;
					break;
				} else if (num == 9) {
					// 로그아웃
					login = 1;
					break;
				} else {
					System.out.println("잘못된 입력입니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요.");
			}
		}

		return login;
	}

	private void viewProfile() {
		Customer customer = customerDAO.showProfile(id);
		System.out.println(customer);
	}

	private void updateAddr() {
		System.out.println("변경할 주소를 입력하세요.");
		System.out.print("입력> ");
		String addr = sc.nextLine();
		customer.setAddr(addr);
		customerDAO.updateProfileAddr(customer);
	}

	private void updateNickname() {
		String origin = customer.getNickname();
		while (true) {
			System.out.println("변경할 닉네임을 입력하세요.");
			System.out.print("입력> ");
			String newer = sc.nextLine();
			Customer temp = customerDAO.showProfileNickname(newer);
			if (origin.equals(newer)) {
				System.out.println("현재 닉네임입니다.");
			} else if (temp == null) {
				customer.setNickname(newer);
				customerDAO.updateProfileNickname(customer);
				break;
			} else {
				System.out.println("존재하는 닉네임입니다.");
			}
		}
	}

	private void order() {
		//주문창
		new CustomerOrderManagement(id).run();
	}

	private void viewOrderList() {
		List<Order> list = orderDAO.viewCustomerOrders(id);
		for(Order order : list) {
			System.out.println(order);
		}
	}

	private void writeReview() {
		new ReviewControl(customer).run();
	}

	private void deleteAccount() {
		System.out.println("--------------------");
		System.out.println(" 정말로 탈퇴하시겠습니까? ");
		System.out.println("   1:Yes    2:No    ");
		System.out.println("--------------------");
		int num = 0;
		while (true) {
			try {
				num = inputNum();
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요.");
			}

			if (num == 1) {
				customerDAO.deleteAccount(id);
				break;
			}
			if (num == 2) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		}
	}

	@Override
	protected void menuPrint() {
		System.out.println("------------------------------------------");
		System.out.println("  1.주문하기        2.주소변경     3.닉네임변경  ");
		System.out.println("  4.고객정보확인     5.주문내역     6.후기작성    ");
		System.out.println("  7.탈퇴			  9.로그아웃");
		System.out.println("------------------------------------------");
	}

}
