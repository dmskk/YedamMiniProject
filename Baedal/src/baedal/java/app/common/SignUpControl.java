package baedal.java.app.common;

import baedal.java.app.customers.Customer;
import baedal.java.app.owners.Owner;

public class SignUpControl extends Management {

	public void run() {
		while (true) {
			// 메뉴
			menuPrint();

			try {
				// 메뉴입력
				int num = inputNum();

				// 각 메뉴
				if (num == 1) {
					insertStoreInfo();
				} else if (num == 2) {
					insertCustomerInfo();
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
		System.out.println("=======================================");
		System.out.println("   1.점포가입    2.고객가입    9.뒤로가기    ");
		System.out.println("========================================");
	}

	private void insertStoreInfo() {
		// 가입정보 입력
		Owner owner = inputOwnerInfo();
		// DB에 저장
		ownerDAO.signUp(owner);
	}

	private Owner inputOwnerInfo() {
		Owner owner = null;
		while (true) {
			System.out.print("사업자번호(숫자10자리)>> ");
			long num = Long.parseLong(sc.nextLine());
			if (num < 10000000000L && num > 999999999) {
				// 중복체크
				owner = ownerDAO.viewStoreProfile(num);
				if (owner == null) {
					owner = new Owner();
					owner.setCorpNum(num);
					break;
				} else {
					System.out.println("존재하는 사업자번호입니다.");
				}
			} else {
				System.out.println("다시 입력하세요.");
			}
		}
		System.out.print("비밀번호>> ");
		owner.setPassword(sc.nextLine());
		System.out.print("가게 이름>> ");
		owner.setStoreName(sc.nextLine());
		while (true) {
			System.out.println("---1.한식  2.분식  3.치킨  4.피자  5.고기  6.양식  7.패스트푸드  8.야식  9.카페  0.중식---");
			System.out.print("업종>> ");
			int value = Integer.parseInt(sc.nextLine());
			if (value < 10) {
				owner.setStoreValue(value);
				break;
			} else
				System.out.println("다시 입력하세요.");
		}
		while (true) {
			try {
				System.out.print("오픈시간>> ");
				int open = Integer.parseInt(sc.nextLine());
				if (open >= 0 && open < 24) {
					owner.setTimeOpen(open);
					break;
				} else {
					System.out.println("0시 ~ 23시만 입력 가능합니다.");
				}

			} catch (NumberFormatException e) {
				System.out.println("정시만 입력가능합니다. 숫자만 입력하세요.");
			}
		}
		while (true) {
			try {
				System.out.print("마감시간>> ");
				int close = Integer.parseInt(sc.nextLine());
				if (close > 0 && close <= 24) {
					owner.setTimeClose(close);
					break;
				} else {
					System.out.println("1시 ~ 24시만 입력 가능합니다.");
				}

			} catch (NumberFormatException e) {
				System.out.println("정시만 입력가능합니다. 숫자만 입력하세요.");
			}
		}
		return owner;
	}

	private void insertCustomerInfo() {
		// 가입정보 입력
		Customer customer = inputCustomerInfo();
		// DB에 저장
		customerDAO.signUp(customer);
	}

	private Customer inputCustomerInfo() {
		Customer customer = null;
		while (true) {
			System.out.print("ID>> ");
			String id = sc.nextLine();
			if (id.equals("")) {
				System.out.println("다시 입력하세요.");
			} else {
				// 중복체크
				customer = customerDAO.showProfile(id);
				if (customer == null) {
					customer = new Customer();
					customer.setId(id);
					;
					break;
				} else {
					System.out.println("존재하는 ID입니다.");
				}
			}
		}
		System.out.print("비밀번호>> ");
		customer.setPassword(sc.nextLine());
		System.out.print("이름>> ");
		customer.setName(sc.nextLine());
		while (true) {
			try {
				System.out.print("휴대폰번호(숫자 8자리)>> 010 ");
				int number = Integer.parseInt(sc.nextLine());
				if (number > 9999999 && number < 100000000) {
					customer.setPhoneNumber(number);
					break;
				} else {
					System.out.println("다시 입력하세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력하세요.");
			}
		}
		System.out.print("주소>> ");
		customer.setAddr(sc.nextLine());
		while (true) {
			System.out.print("닉네임>> ");
			String nickname = sc.nextLine();
			// 공백인지 체크
			if (nickname.equals("")) {
				System.out.println("다시 입력하세요.");
			} else {
				// 중복 체크
				Customer temp = customerDAO.showProfileNickname(nickname);
				if (temp == null) {
					customer.setNickname(nickname);
					break;

				}
			}
		}
		return customer;
	}
}
