package baedal.java.app.customers;

import java.util.List;

import baedal.java.app.common.Management;
import baedal.java.app.orders.Order;
import baedal.java.app.orders.OrderControl;
import baedal.java.app.owners.Owner;
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

		// 지난달 주문내역에 따른 등급
		int lastCount = orderDAO.lastMonthOrderCount(id);
		if (lastCount < 5) {
			customer.setGrade(4);
		} else if (lastCount < 10) {
			customer.setGrade(3);
		} else if (lastCount < 20) {
			customer.setGrade(2);
		} else {
			customer.setGrade(1);
		}
		customerDAO.updateProfileGrade(customer);

		showLoginInfo();

		while (true) {
			// 메뉴출력
			menuPrint();
			try {
				// 메뉴입력
				int num = inputSelectNum();

				// 기능
				if (num == 1) {
					// 전체가게조회
					orderControlAll();
				} else if (num == 2) {
					// 업종별가게조회
					orderControlValue();
				} else if (num == 3) {
					// 회원정보관리
					infoControl();
				} else if (num == 4) {
					// 주문내역조회
					viewOrderList();
				} else if (num == 5) {
					// 후기관리
					reviewControl();
				} else if (num == 0) {
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

	private void reviewControl() {
		// 후기미작성 주문내역
		List<Order> list = orderDAO.viewCustomerOrdersNoReview(id);
		if (list.size() > 0) {
			System.out.println("후기 미작성 주문내역");
			System.out.println();
			
			for(int idx=0; idx<list.size(); idx++) {
				System.out.println("[선택번호:"+(idx+1)+"]");
				System.out.println(list.get(idx));
				System.out.println();
			}
		} else {
			System.out.println("작성한 후기가 없습니다.");
		}

		// 1.후기작성 2.작성후기보기 9.뒤로가기
		try {
			System.out.println("　　　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　　");
			System.out.println("　　　　　｜1.후기작성 |　　｜2.작성한후기보기｜　　 |9.뒤로가기|　　 　 ");
			System.out.println("　　　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　　");
			////////////////
			
		} catch(NumberFormatException e) {
			System.out.println("숫자만 입력하세요!");
		}
	}

	private void infoControl() {
		viewProfile();
		System.out.println();
		try {
			System.out.println("　　　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　　");
			System.out.println("　　　　　｜1.주소수정 |　　｜2.닉네임수정수정｜　　 |9.뒤로가기|　　 　 ");
			System.out.println("　　　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　　");

			int selectProfile = inputSelectNum();
			if (selectProfile == 1) {
				updateAddr();
				return;
			} else if (selectProfile == 2) {
				updateNickname();
				return;
			} else if (selectProfile == 3) {
				return;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력하세요!");
		}

	}

	private void orderControlValue() {
		System.out.println(" ￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣");
		System.out.println("|　가게업종 선택　　　　　　　　　　　　　　　　　　　　　[－][口][×] |");
		System.out.println("|￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣ |");
		System.out.println("|　숫자만 입력가능합니다.　　　　　　　　　　　　　　　　　　　　　　　 |");
		System.out.println("|　　　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　　|");
		System.out.println("|　　　　　｜　1. 한식　|　　｜　2. 분식　｜　　|　3. 치킨　|　　　　|");
		System.out.println("|　　　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　　|");
		System.out.println("|　　　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　　|");
		System.out.println("|　　　　　｜　4. 피자　|　　｜　5. 일식　｜　　|　6. 양식　|　　　　|");
		System.out.println("|　　　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　　|");
		System.out.println("|　　　 ＿＿＿＿＿＿＿＿＿＿　　＿＿＿＿＿＿　　　　 ＿＿＿＿＿＿　　　　 |");
		System.out.println("|　　　｜　7. 패스트푸드　|　｜　8. 야식　｜　　|　9. 카페　|　　　　|");
		System.out.println("|　　　 ￣￣￣￣￣￣￣￣￣￣　　￣￣￣￣￣￣　　　　 ￣￣￣￣￣￣　　　　 |");
		System.out.println("|　　　　　　＿＿＿＿＿＿　　　　　　　　　　　　　　　　　　　　　　　　　|");
		System.out.println("|　　　　　｜　0. 중식　|　　　　　　　　　　　　　　　　　　　　　　　　|");
		System.out.println("|　　　　　　￣￣￣￣￣￣　　　　　　　　　　　　　　　　　　　　　　　　　|");
		System.out.println(" ￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣");
		int selectValue = inputSelectNum();
		List<Owner> list = ownerDAO.openValueList(selectValue);
		for (int idx = 0; idx < list.size(); idx++) {
			System.out.println("[선택번호:" + (idx + 1) + "]");
			System.out.println(list.get(idx));
			System.out.println();
		}

		try {
			// 메뉴창
			System.out.println("　　　　　　＿＿＿＿＿＿＿　　　 ＿＿＿＿＿＿　　　　 ");
			System.out.println("　　　　　｜ 1.주문하기 |　　|9.뒤로가기|　　 　 ");
			System.out.println("　　　　　　￣￣￣￣￣￣￣　　　 ￣￣￣￣￣￣　　　　 ");
			// 메뉴입력
			int menuSelect = inputSelectNum();

			if (menuSelect == 1) {
				int orderSelect = orderSelect();
				if (orderSelect > 0 && orderSelect <= list.size()) {
					new OrderControl(id, list.get(orderSelect - 1)).runCheck();
				}
			}
		} catch (NumberFormatException e) {
			System.out.println(" 숫자만 입력하세요 ! ");
		}
	}

	private void orderControlAll() {
		List<Owner> list = ownerDAO.openList();
		for (int idx = 0; idx < list.size(); idx++) {
			System.out.println("[선택번호:" + (idx + 1) + "]");
			System.out.println(list.get(idx));
			System.out.println();
		}

		try {
			// 메뉴창
			System.out.println("　　　　　　＿＿＿＿＿＿＿　　　 ＿＿＿＿＿＿　　　　 ");
			System.out.println("　　　　　｜ 1.주문하기 |　　|9.뒤로가기|　　 　 ");
			System.out.println("　　　　　　￣￣￣￣￣￣￣　　　 ￣￣￣￣￣￣　　　　 ");
			// 메뉴입력
			int menuSelect = inputSelectNum();

			if (menuSelect == 1) {
				int orderSelect = orderSelect();
				if (orderSelect > 0 && orderSelect <= list.size()) {
					new OrderControl(id, list.get(orderSelect - 1)).runCheck();
				}
			}
		} catch (NumberFormatException e) {
			System.out.println(" 숫자만 입력하세요 ! ");
		}
	}

	private int orderSelect() {
		int orderSelect = 0;
		System.out.println("주문을 할 가게의 선택번호를 입력하세요.");
		orderSelect = inputNum();
		return orderSelect;
	}

	private void showLoginInfo() {
		String grade = "";
		if (customer.getGrade() == 4) {
			grade = "고마운분";
		} else if (customer.getGrade() == 3) {
			grade = "귀한분";
		} else if (customer.getGrade() == 2) {
			grade = "더귀한분";
		} else if (customer.getGrade() == 1) {
			grade = "천생연분";
		}
		System.out.println(
				"  ҉    ----ะะะۣۨ ۣۨۨ> [" + customer.getNickname() + "] 님은 이번 달 [" + grade + "]입니다. < ۣۨۨะะะۜ---- ҉  ");
		System.out.println("  ҉    ----ะะะۣۨ ۣۨۨ> 현재 총 적립 포인트는 [" + customer.getPoint() + "]점입니다. < ۣۨۨะะะۜ---- ҉  ");
		System.out.println();
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

	private void viewOrderList() {
		List<Order> list = orderDAO.viewCustomerOrders(id);
		for (Order order : list) {
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
		System.out.println("; ♡⋆.ೃ࿔*");
		System.out.println("│﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀  ﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀  ﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀");
		System.out.println("│　1.전체가게조회　　　　2.업종별가게조회　　　　3.회원정보관리");
		System.out.println("│　4.주문내역조회　　　　5.후기관리　　　　　　　9.로그아웃");
		System.out.println("│　　　　　　　　　　　　　　　　　　　　　　　　　　0.탈퇴하기");
		System.out.println("└——————————————————————————————————————————————— - [ 📼 ]. +");
		System.out.println();
	}

}
