package baedal.java.app.common;

import baedal.java.app.customers.Customer;
import baedal.java.app.customers.CustomerInfoManagement;
import baedal.java.app.owners.Owner;
import baedal.java.app.owners.StoreInfoManagement;

public class LoginControl extends Management {
	private static int systemCheck = 0;

	public void run() {
		while (true) {

			if (systemCheck == 1) {
				systemCheck = 0;
				break;
				// 초기화면으로 바로 돌아가기
			}

			// 메뉴
			menuPrint();

			try {
				// 메뉴입력
				int num = inputSelectNum();

				// 각 메뉴
				if (num == 1) {
					loginStore();
				} else if (num == 2) {
					loginCustomer();
				} else if (num == 9) {
					break;
				} else {
					System.out.println("잘못된 선택입니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요.");
			}
		}
	}

	@Override
	protected void menuPrint() {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("  ┊　　┊　　┊ 　 ┊    　┊　   ┊　 ┊");
		System.out.println("  ┊　　┊　　┊ 　 ☆    　┊　   ┊　 ┊");
		System.out.println("  ┊　　┊　　 ✬ 　 　   　✬ 　  ┊　 ┊");
		System.out.println("  ┊　　★ 　　　 　 　    　　　 ★　 ┊");
		System.out.println("  ☆ 　　 　　　 　 　    　　　　　　 ☆");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("....∧__∧");
		System.out.println("..( ̳• y• ̳) ♡");
		System.out.println("┏ーーー∪━∪━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━┓");
		System.out.println("  ♡°•. 1.점포로그인 .•°♡      ♡°•. 2.고객로그인 .•°♡      ♡°•. 9.뒤로가기 .•°♡");
		System.out.println("┗━━━━━━━━━•━━━━━━━━━━━━----------━━━━━━━━━━━━━━━━━━━━•━━━━━━━━━━━━━━━━┛");
		System.out.println();
	}

	private void loginStore() {
		// 로그인정보 입력
		Owner info = inputStoreLoginInfo();
		// 아이디 정보 확인
		Owner owner = ownerDAO.viewStoreProfile(info.getCorpNum());
		// 아이디 없으면 안내
		if (owner == null) {
			System.out.println("존재하지 않는 점포입니다.");
			return;
		}

		// 확인되면 비밀번호 일치하는지 확인
		if (owner.getPassword().equals(info.getPassword())) {
			// 로그인정보 확인되면 로그인
			systemCheck = new StoreInfoManagement(owner.getCorpNum()).runCheck();
		} else {
			// 비밀번호 안 맞으면 안내
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}

	}

	private Owner inputStoreLoginInfo() {
		Owner owner = new Owner();
		while (true) {
			System.out.print("사업자번호(숫자10자리)>> ");
			long num = Long.parseLong(sc.nextLine());
			if (num < 10000000000L && num > 999999999) {
				owner.setCorpNum(num);
				break;
			} else {
				System.out.println("다시 입력하세요.");
			}
		}
		System.out.print("비밀번호>> ");
		owner.setPassword(sc.nextLine());
		return owner;
	}

	private void loginCustomer() {
		// 로그인정보 입력
		Customer info = inputCustomerLoginInfo();
		// 아이디 정보 확인
		Customer customer = customerDAO.showProfile(info.getId());
		// 아이디 없으면 안내
		if (customer == null) {
			System.out.println("존재하지 않는 회원입니다.");
			return;
		}

		// 확인되면 비밀번호 일치하는지 확인
		if (customer.getPassword().equals(info.getPassword())) {
			// 로그인정보 확인되면 로그인
			systemCheck = new CustomerInfoManagement(customer.getId()).runout();
		} else {
			// 비밀번호 안 맞으면 안내
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}

	}

	private Customer inputCustomerLoginInfo() {
		Customer customer = new Customer();
		System.out.print("ID>> ");
		customer.setId(sc.nextLine());
		System.out.print("비밀번호>> ");
		customer.setPassword(sc.nextLine());
		return customer;
	}
}
