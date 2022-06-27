package baedal.java.app.owners;

import java.util.List;

import baedal.java.app.common.Management;
import baedal.java.app.menus.Menu;
import baedal.java.app.orders.Order;
import baedal.java.app.reviews.Review;

public class StoreInfoManagement extends Management {
	protected static long corpNum;
	protected static Owner owner;

	@SuppressWarnings("static-access")
	public StoreInfoManagement(long corpNum) {
		this.corpNum = corpNum;
		this.owner = ownerDAO.viewStoreProfile(corpNum);
	}

	public int runCheck() {
		int checkSystem = 0;
		showLoginInfo();
		while (true) {
			// 메뉴출력
			menuPrint();

			try {
				// 메뉴선택
				int menu = inputSelectNum();

				// 기능
				if (menu == 1) {
					// 가게정보확인
					viewStoreInfo();
				} else if (menu == 2) {
					// 영업시간수정
					updateTime();
				} else if (menu == 3) {
					// 메뉴관리
					menuControl();
				} else if (menu == 4) {
					// 오늘 주문내역
					showTodayOrderList();
				} else if (menu == 5) {
					// 전체 주문내역
					showTotalOrderList();
				} else if (menu == 6) {
					// 후기관리
					showReviewList();
				} else if (menu == 0) {
					// 탈퇴
					checkSystem = deleteAccount();
					break;
				} else if (menu == 9) {
					// 로그아웃
					checkSystem = 1;
					break;
				} else {

				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요.");
			}
		}
		return checkSystem;
	}
	

	private void showTotalOrderList() {
		List<Order> list = orderDAO.viewStoreOrders(corpNum);
		if (list.size() > 0) {
			orderControl(list);
		}
	}

	private void showTodayOrderList() {
		List<Order> list = orderDAO.viewStoreTodayOrders(corpNum);
		if (list.size() > 0) {
			orderControl(list);
		}
	}

	private void orderControl(List<Order> list) {
		for (int idx = 0; idx < list.size(); idx++) {
			System.out.println("[선택번호:" + (idx + 1) + "]");
			System.out.println(list.get(idx));
			System.out.println();
		}

		try {
			System.out.println("　　　　　　＿＿＿＿＿＿＿＿＿　　　 ＿＿＿＿＿＿　　　　 ");
			System.out.println("　　　　　｜ 1.배달상태변경 |　　|9.뒤로가기|　　 　 ");
			System.out.println("　　　　　　￣￣￣￣￣￣￣￣￣　　　 ￣￣￣￣￣￣　　　　 ");
			int select = inputSelectNum();
			if (select == 1) {
				System.out.println("배달상태를 변경할 내역의 선택번호를 입력하세요.");
				int selectOrder = inputNum();
				if (selectOrder > 0 && selectOrder <= list.size()) {
					Order order = list.get((selectOrder - 1));
					if (order.getDeliveryStatus() == 1) {
						order.setDeliveryStatus(2);
						orderDAO.updateStatus(order);
						return;
					} else if (order.getDeliveryStatus() == 2) {
						order.setDeliveryStatus(3);
						orderDAO.updateStatus(order);
						return;
					} else if (order.getDeliveryStatus() == 3) {
						System.out.println("배달이 완료된 주문건은 변경할 수 없습니다.");
						return;
					}
				}
			} else if (select == 9) {
				return;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력하세요 !");
		}
	}

	private void menuControl() {
		System.out.println();
		List<Menu> list = menuDAO.viewMenu(owner);
		if (list.size() > 0) {
			for (int idx = 0; idx < list.size(); idx++) {
				System.out.println("[선택번호:" + (idx + 1) + "]");
				System.out.println(list.get(idx));
				System.out.println();
			}
		} else {
			System.out.println("등록된 메뉴가 없습니다. 추가해주세요! ");
		}

		System.out.println("　　　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　　");
		System.out.println("　　　　　｜1.메뉴추가 |　　｜2.메뉴수정｜　　 |3.메뉴삭제|　　 |9.뒤로가기|　　 　 ");
		System.out.println("　　　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　　");
		while (true) {
			try {
				int select = inputSelectNum();
				if (select == 1) {
					insertMenu();
					return;
				} else if (select == 2) {
					System.out.println("가격을 수정할 메뉴의 선택번호를 입력하세요.");
					int selectMenu = inputNum();
					if (selectMenu > 0 && selectMenu <= list.size()) {
						updateMenu(list.get(selectMenu - 1));
						return;
					}
				} else if (select == 3) {
					System.out.println("삭제할 메뉴의 선택번호를 입력하세요.");
					int selectMenu = inputSelectNum();
					deleteMenu(list.get(selectMenu - 1));
					return;
				} else if (select == 9) {
					return;
				} else {
					System.out.println("잘못된 입력입니다!");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력하세요 !");
			}
		}

	}

	private void updateMenu(Menu menu) {
		while (true) {
			try {
				System.out.println("▶ 수정할 가격을 입력하세요.");
				int price = inputNum();
				if (price > 0) {
					menu.setMenuPrice(price);
					menuDAO.updatePrice(menu);
					return;
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력하세요 !");
			}
		}
	}

	private void showLoginInfo() {
		System.out.print("  ҉    ----ะะะۣۨ ۣۨۨ> [" + owner.getStoreName() + "] 점주님, 안녕하세요 ! < ۣۨۨะะะۜ---- ҉  ");
		System.out.println("  ҉    ----ะะะۣۨ ۣۨۨ> 오늘 [" + orderDAO.calcStoreTodayOrders(corpNum)
				+ "]건의 주문이 있었습니다. < ۣۨۨะะะۜ---- ҉  ");
		System.out.println("  ҉    ----ะะะۣۨ ۣۨۨ> 힘찬 하루 보내세요 ! < ۣۨۨะะะۜ---- ҉  ");
		System.out.println();
	}

	private void viewStoreInfo() {
		Owner owner = ownerDAO.viewStoreProfile(corpNum);
		System.out.println(owner);
	}

	private void updateTime() {
		boolean checkSystem = true;
		while (checkSystem) {
			int num = 0;
			System.out.println(" ￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣");
			System.out.println("|　영업시간 변경　　　　　　　　　　　　　　　　　　　　　[－][口][×] |");
			System.out.println("|￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣ |");
			System.out.println("|　정각 숫자만 입력가능합니다.　　　　　　　　　　　　　　　　　　　　 |");
			System.out.println("|　24시간 단위로 숫자만 입력하세요.　　　　　　　　　　　　　　　　　　|");
			System.out.println("|　　　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　　|");
			System.out.println("|　　　　　｜1.오픈시간 |　　｜2.마감시간｜　　 |9.뒤로가기|　　 　 |");
			System.out.println("|　　　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　　|");
			System.out.println(" ￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣￣");

			try {
				num = inputSelectNum();
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요.");
			}
			if (num == 1 || num == 2) {
				checkSystem = inputUpdateTime(num);
			} else if (num == 9) {
				return;
			} else {
				System.out.println("잘못된 선택입니다.");
			}
		}
	}

	private boolean inputUpdateTime(int num) {
		boolean checkSystem = true;
		while (true) {
			System.out.println("▶ 변경할 시간을 입력하세요.");
			int update = 0;
			try {
				update = inputNum();
			} catch (NumberFormatException e) {
				System.out.println("정각만 입력가능합니다. 숫자만 입력하세요.");
			}

			if (num == 1 && update >= 0 && update < 24) {
				owner.setTimeOpen(update);
				ownerDAO.updateOpen(owner);
				checkSystem = false;
				break;
			} else if (num == 1 && (update < 0 || update >= 24)) {
				System.out.println("0시 ~ 23시만 입력 가능합니다.");
			}
			if (num == 2 && update > 0 && update <= 24) {
				owner.setTimeClose(update);
				ownerDAO.updateClose(owner);
				checkSystem = false;
				break;
			} else if (num == 2 && (update <= 0 || update > 24)) {
				System.out.println("1시 ~ 24시만 입력 가능합니다.");
			}
		}
		return checkSystem;
	}

	private void insertMenu() {
		Menu menu = inputMenuInfo();
		menuDAO.insertMenu(menu);
	}

	private Menu inputMenuInfo() {
		Menu menu = new Menu();
		menu.setStoreNum(owner.getCorpNum());
		while (true) {
			// 중복체크
			System.out.print("메뉴이름> ");
			String name = sc.nextLine();
			if (menuDAO.isExistMenu(corpNum, name)) {
				System.out.println("같은 이름의 메뉴가 존재합니다.");
			} else {
				menu.setMenuName(name);
				break;
			}
		}
		while (true) {
			int price = 0;
			System.out.print("메뉴가격> ");
			try {
				price = Integer.parseInt(sc.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("숫자로만 입력하세요.");
			}
			if (price > 0) {
				menu.setMenuPrice(price);
				break;
			} else {
				System.out.println("정확한 금액을 입력하세요.");
			}
		}
		System.out.print("메뉴설명> ");
		menu.setMenuContent(sc.nextLine());
		return menu;
	}

	private void deleteMenu(Menu menu) {
		if (menuDAO.isExistMenu(corpNum, menu.getMenuName())) {
			menuDAO.deleteMenu(corpNum, menu.getMenuName());
		} else {
			System.out.println("존재하지 않는 메뉴입니다.");
		}
	}

	private void showReviewList() {
		List<Review> list = reviewDAO.viewReviewStore(corpNum);

		if (list.size() > 0) {
			for (int idx = 0; idx < list.size(); idx++) {
				System.out.println("[선택번호:" + (idx + 1) + "]");
				System.out.println(list.get(idx));
				System.out.println();
			}
			while (true) {
				try {
					System.out.println("　　　　　　＿＿＿＿＿＿＿　　　 ＿＿＿＿＿＿　　　　 ");
					System.out.println("　　　　　｜ 1.후기삭제 |　　|9.뒤로가기|　　 　 ");
					System.out.println("　　　　　　￣￣￣￣￣￣￣　　　 ￣￣￣￣￣￣　　　　 ");
					int select = inputSelectNum();
					if (select == 1) {
						System.out.println("삭제할 후기의 선택번호를 입력하세요.");
						int selectReview = inputNum();
						reviewDAO.deleteReview(list.get(selectReview - 1));
						return;
					} else if (select == 9) {
						return;
					} else {
						System.out.println("잘못된 입력입니다!");
					}
				} catch (NumberFormatException e) {
					System.out.println("숫자만 입력하세요!");
				}
			}
		} else {
			System.out.println("등록된 후기가 없습니다.");
		}
	}

	private int deleteAccount() {
		int checkSystem = 0;
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
				ownerDAO.deleteAccount(corpNum);
				checkSystem = 1;
				break;
			}
			if (num == 2) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		}
		return checkSystem;
	}

	@Override
	protected void menuPrint() {
		System.out.println("; ♡⋆.ೃ࿔*");
		System.out.println("│﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀  ﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀  ﹀﹀﹀﹀﹀﹀﹀﹀﹀﹀");
		System.out.println("│　1.가게정보확인　　　　2.영업시간 수정　　　　3.메뉴 관리");
		System.out.println("│　4.오늘주문내역　　　　5.전체주문내역　　　　 6.후기 관리");
		System.out.println("│　9.로그아웃　　　　　　　　　　　　　　　　　　　0.탈퇴하기");
		System.out.println("└——————————————————————————————————————————————— - [ 📼 ]. +");
		System.out.println();
	}

}
