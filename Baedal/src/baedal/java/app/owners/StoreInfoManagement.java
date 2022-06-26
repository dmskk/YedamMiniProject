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
		while (true) {
			// 메뉴출력
			menuPrint();

			try {
				// 메뉴선택
				int menu = inputNum();

				// 기능
				if (menu == 1) {
					// 가게정보확인
					viewStoreInfo();
				} else if (menu == 2) {
					// 영업시간수정
					updateTime();
				} else if (menu == 3) {
					// 가게 메뉴 확인
					viewMenuList();
				} else if (menu == 4) {
					// 메뉴 추가
					insertMenu();
				} else if (menu == 5) {
					// 메뉴 삭제
					deleteMenu();
				} else if (menu == 6) {
					// 주문내역조회
					showOrderList();
				} else if (menu == 7) {
					// 후기내역조회
					showReviewList();
				} else if (menu == 8) {
					// 탈퇴
					deleteAccount();
					checkSystem = 1;
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

	private void viewStoreInfo() {
		Owner owner = ownerDAO.viewStoreProfile(corpNum);
		System.out.println(owner);
	}

	private void updateTime() {
		while (true) {
			int num = 0;
			System.out.println("------------영업시간 변경------------");
			System.out.println("  1.오픈시간   2.마감시간   3.뒤로가기  ");
			System.out.println("----------------------------------");
			try {
				num = inputNum();
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요.");
			}
			if (num == 1 || num == 2) {
				inputUpdateTime(num);
			} else if (num == 3) {
				return;
			} else {
				System.out.println("잘못된 선택입니다.");
			}
		}
	}

	private void inputUpdateTime(int num) {
		while (true) {
			System.out.println("변경할 시간을 입력하세요.");
			int update = 0;
			try {
				update = inputNum();
			} catch (NumberFormatException e) {
				System.out.println("정시만 입력가능합니다. 숫자만 입력하세요.");
			}

			if (num == 1 && update >= 0 && update < 24) {
				owner.setTimeOpen(update);
				ownerDAO.updateOpen(owner);
				return;
			} else if (num == 1 && (update < 0 || update >= 24)) {
				System.out.println("0시 ~ 23시만 입력 가능합니다.");
			}
			if (num == 2 && update > 0 && update <= 24) {
				owner.setTimeClose(update);
				ownerDAO.updateClose(owner);
				return;
			} else if (num == 2 && (update <= 0 || update > 24)) {
				System.out.println("1시 ~ 24시만 입력 가능합니다.");
			}
		}
	}

	private void viewMenuList() {
		List<Menu> list = menuDAO.viewMenu(owner);
		for (Menu menu : list) {
			System.out.println(menu);
		}
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

	private void deleteMenu() {
		System.out.println("삭제할 메뉴 이름을 입력하세요.");
		System.out.print("입력> ");
		String name = sc.nextLine();
		if (menuDAO.isExistMenu(corpNum, name)) {
			menuDAO.deleteMenu(corpNum, name);
		} else {
			System.out.println("존재하지 않는 메뉴입니다.");
		}
	}

	private void showOrderList() {
		List<Order> list = orderDAO.viewStoreOrders(corpNum);
		int listSize = list.size();

		for (int idx = 0; idx < listSize; idx++) {
			System.out.println("[선택번호:" + (idx + 1) + "]");
			System.out.println(list.get(idx));
			System.out.println();
		}
		while (true) {
			try {
				System.out.println("-----------------------");
				System.out.println(" 1.배달현황변경  2.뒤로가기 ");
				System.out.println("-----------------------");
				int num = inputNum();

				if (num == 1) {
					System.out.println("배달현황을 변경할 주문내역의 선택번호를 입력하세요.");
					int idx = inputNum();
					if (idx > 0 && idx <= listSize) {
						Order order = list.get((idx-1));
						if(order.getDeliveryStatus()==1) {
							order.setDeliveryStatus(2);
							orderDAO.updateStatus(order);
							return;
						} else if(order.getDeliveryStatus()==2) {
							order.setDeliveryStatus(3);
							orderDAO.updateStatus(order);
							return;
						} else if(order.getDeliveryStatus()==3) {
							System.out.println("배달이 완료된 주문건은 변경할 수 없습니다.");
							return;
						}
					} else {
						System.out.println("잘못된 입력입니다.");
					}
				} else if (num == 2) {
					return;
				} else {
					System.out.println("잘못된 입력입니다.");
				}

			} catch (NumberFormatException e) {
				System.out.println("잘못된 입력입니다.");
			}
		}

	}

	private void showReviewList() {
		List<Review> list = reviewDAO.viewReviewStore(corpNum);
		for (Review review : list) {
			System.out.println(review);
		}
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
				ownerDAO.deleteAccount(corpNum);
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
		System.out.println("=====================================================");
		System.out.println("   1.가게정보확인     2.영업시간 수정     3.가게 메뉴 확인");
		System.out.println("   4.메뉴 추가       5.메뉴 삭제        6.주문내역조회");
		System.out.println("   7.후기보기        8.탈퇴하기         9.로그아웃");
		System.out.println("=====================================================");
	}

}
