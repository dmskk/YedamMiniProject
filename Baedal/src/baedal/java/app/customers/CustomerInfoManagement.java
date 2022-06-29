package baedal.java.app.customers;

import java.util.List;

import baedal.java.app.common.Management;
import baedal.java.app.orders.Order;
import baedal.java.app.orders.OrderControl;
import baedal.java.app.owners.Owner;
import baedal.java.app.reviews.Review;

public class CustomerInfoManagement extends Management {
	private String id;
	private Customer customer;
	private int login;

	public CustomerInfoManagement(String id) {
		this.id = id;
		this.customer = customerDAO.showProfile(id);
	}

	public int runout() {
		login = 0;

		// 지난달 주문내역에 따른 등급
		int lastCount = orderDAO.lastMonthOrderCount(id);
		if (lastCount < 5) {
			this.customer.setGrade(4);
		} else if (lastCount < 10) {
			this.customer.setGrade(3);
		} else if (lastCount < 20) {
			this.customer.setGrade(2);
		} else {
			this.customer.setGrade(1);
		}
		customerDAO.updateProfileGrade(this.customer);

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
					if (login == 1) {
						break;
					}
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
			System.out.println();
			System.out.println();
		}

		return login;
	}

	private void reviewControl() {
		// 후기미작성 주문내역
		List<Order> list = orderDAO.viewCustomerOrdersNoReview(id);
		if (list.size() > 0) {
			System.out.println("리뷰 미작성 주문내역");
			System.out.println();

			for (int idx = 0; idx < list.size(); idx++) {
				listHeaderSelectNum(idx);
				System.out.println(list.get(idx));
				System.out.println("╘◖═════════════════════════════════◗╛");
				System.out.println();
			}
		} else {
			System.out.println("리뷰작성이 가능한 주문내역이 없습니다.");
		}

		// 1.후기작성 2.작성후기보기 9.뒤로가기
		try {
			System.out.println("　　　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　　");
			System.out.println("　　　　　｜1.리뷰작성 |　　｜2.작성한리뷰보기｜　　 |9.뒤로가기|　　 　 ");
			System.out.println("　　　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　　");
			int selectControl = inputSelectNum();
			if (selectControl == 1 && list.size() == 0) {
				System.out.println("리뷰작성이 가능한 주문 내역이 없습니다!");
			} else if (selectControl == 1) {
				writeReview(list);
			} else if (selectControl == 2) {
				showReviewed();
			} else if (selectControl == 3) {
				return;
			} else {
				System.out.println("잘못된 입력입니다!");
			}

		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력하세요!");
		}
	}

	private void showReviewed() {
		List<Review> list = reviewDAO.viewReviewCustomer(id);
		try {
			if (list.size() > 0) {
				for (int idx = 0; idx < list.size(); idx++) {
					listHeaderSelectNum(idx);
					System.out.println(list.get(idx));
					System.out.println("╘◖═════════════════════════════════◗╛");
					System.out.println();
				}

				System.out.println("　　　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　　");
				System.out.println("　　　　　｜1.리뷰수정 |　　｜2.리뷰삭제｜　　 |9.뒤로가기|　　 　 ");
				System.out.println("　　　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　　");
				int selectControl = inputSelectNum();

				if (selectControl == 1 && list.size() == 0) {
					System.out.println("수정 가능한 리뷰 내역이 없습니다!");
				} else if (selectControl == 1) {
					updateReview(list);
				} else if (selectControl == 2 && list.size() == 0) {
					System.out.println("삭제 가능한 리뷰 내역이 없습니다!");
				} else if (selectControl == 2) {
					deleteReview(list);
				} else if (selectControl == 9) {
					return;
				} else {
					System.out.println("잘못된 입력입니다!");
				}
			} else {
				System.out.println("작성한 리뷰가 없습니다 !");
			}
		} catch (NumberFormatException e) {
			System.out.println(" 숫자만 입력하세요! ");
		}
		System.out.println();
	}

	private void deleteReview(List<Review> list) {
		try {
			System.out.println("삭제할 리뷰의 선택번호를 입력하세요.");
			int num = inputNum();
			if (num > 0 && num <= list.size()) {
				Review review = list.get(num - 1);
				reviewDAO.deleteReview(review);
				return;
			} else {
				System.out.println("잘못된 입력입니다!");
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력하세요!");
		}
		System.out.println();
	}

	private void updateReview(List<Review> list) {
		try {
			System.out.println("작성내용을 수정할 리뷰의 선택번호를 입력하세요.");
			int num = inputNum();
			if (num > 0 && num <= list.size()) {
				Review review = list.get(num - 1);
				while (true) {
					System.out.print("별점(1~5)>	");
					int star = Integer.parseInt(sc.nextLine());
					if (star > 0 && star < 6) {
						review.setStar(star);
						break;
					} else {
						System.out.println("1~5의 숫자만 입력하세요.");
					}
				}
				System.out.print("내용입력> ");
				review.setContent(sc.nextLine());
				reviewDAO.updateReview(review);
				return;
			} else {
				System.out.println("잘못된 입력입니다!");
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자만 입력하세요!");
		}
		System.out.println();
	}

	private void infoControl() {
		viewProfile();
		System.out.println();
		try {
			System.out.println("　　　　　　＿＿＿＿＿＿　　　　＿＿＿＿＿＿＿　　　　＿＿＿＿＿＿＿＿＿　　　　＿＿＿＿＿＿　　　　　");
			System.out.println("　　　　　｜1.주소수정 |　　｜2.닉네임수정｜　　 ｜3.비밀번호수정｜　　 |9.뒤로가기|　　 　 ");
			System.out.println("　　　　　　￣￣￣￣￣￣　　　　￣￣￣￣￣￣￣　　　　￣￣￣￣￣￣￣￣￣　　　　￣￣￣￣￣￣　　　　　");

			int selectProfile = inputSelectNum();
			if (selectProfile == 1) {
				updateAddr();
				return;
			} else if (selectProfile == 2) {
				updateNickname();
				return;
			} else if (selectProfile == 3) {
				updatePwd();
			} else if (selectProfile == 9) {
				return;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		} catch (NumberFormatException e) {
			System.out.println("숫자를 입력하세요!");
		}

	}

	private void updatePwd() {
		while (true) {
			System.out.print("현재 비밀번호를 입력하세요 >  ");
			String oldPwd = sc.nextLine();
			if (oldPwd.equals(customer.getPassword()))
				break;
			else
				System.out.println("비밀번호를 틀렸습니다. 다시 입력하세요.");
		}
		while (true) {
			System.out.print("새로운 비밀번호를 입력하세요 > ");
			String newPwd = sc.nextLine();
			if (newPwd.equals(""))
				System.out.println("공백은 입력할 수 없습니다. 다시 입력하세요.");
			else {
				customer.setPassword(newPwd);
				customerDAO.updateProfilePwd(customer);
				break;
			}
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
		int listPage;
		if(list.size()%3==0) {
			listPage = list.size()/3;
		} else {
			listPage = (list.size() / 3) + 1;
		}
		int page = 1;

		if (list.size() > 0) {
			try {
				while (true) {
					// 리스트 페이징
					valueListPaging(list, page, selectValue);

					// 메뉴창
					System.out.println("　＿＿＿＿＿＿＿＿　　　 　 ＿＿＿＿＿＿＿　　　 ＿＿＿＿＿＿　　　　  ＿＿＿＿＿＿＿＿");
					System.out.println("< 1.이전페이지 <　　　　｜ 0.주문하기 |　　|9.뒤로가기|　　 　 > 2.다음페이지 >");
					System.out.println("　￣￣￣￣￣￣￣￣　　　 　 ￣￣￣￣￣￣￣　　　 ￣￣￣￣￣￣　　　　  ￣￣￣￣￣￣￣￣");
					// 메뉴입력
					int menuSelect = inputSelectNum();

					if (menuSelect == 0 && list.size() == 0) {
						System.out.println("주문 가능한 가게가 없습니다!");
					} else if (menuSelect == 0) {
						int orderSelect = orderSelect();
						if (orderSelect > 0 && orderSelect <= list.size()) {
							int checkSystem = new OrderControl(id, list.get(orderSelect - 1)).runCheck();
							if(checkSystem == 1) {
								return;
							}
						}
					} else if (menuSelect == 1 && page > 1) {
						page--;
					} else if (menuSelect == 2 && page < listPage) {
						page++;
					} else if (menuSelect == 1 && page == 1) {
						System.out.println("이전페이지가 존재하지 않습니다!");
					} else if (menuSelect == 2 && page == listPage) {
						System.out.println("다음페이지가 존재하지 않습니다!");
					} else if (menuSelect == 9) {
						break;
					}
				}
			} catch (NumberFormatException e) {
				System.out.println(" 숫자만 입력하세요 ! ");
			}
		} else {
			System.out.println("현재 영업중인 가게가 없습니다!");
		}
	}

	private void valueListPaging(List<Owner> list, int page, int selectValue) {
		int idxLength = 3 * page;
		if (idxLength > list.size()) {
			idxLength = list.size();
		}
		for (int idx = (3 * (page - 1)); idx < idxLength; idx++) {
			listHeaderSelectNum(idx);
			System.out.println(list.get(idx));
			System.out.println("╘◖═════════════════════════════════◗╛");
			System.out.println();
		}
	}

	private void orderControlAll() {
		List<Owner> list = ownerDAO.openList();
		int listPage;
		if(list.size()%3==0) {
			listPage = list.size()/3;
		} else {
			listPage = (list.size() / 3) + 1;
		}
		int page = 1;

		if (list.size() > 0) {
			try {
				while (true) {
					// 리스트 페이징
					listPaging(list, page);

					// 메뉴창
					System.out.println("　＿＿＿＿＿＿＿＿　　　 　 ＿＿＿＿＿＿＿　　　 ＿＿＿＿＿＿　　　　  ＿＿＿＿＿＿＿＿");
					System.out.println("< 1.이전페이지 <　　　　｜ 0.주문하기 |　　|9.뒤로가기|　　 　 > 2.다음페이지 >");
					System.out.println("　￣￣￣￣￣￣￣￣　　　 　 ￣￣￣￣￣￣￣　　　 ￣￣￣￣￣￣　　　　  ￣￣￣￣￣￣￣￣");
					// 메뉴입력
					int menuSelect = inputSelectNum();

					if (menuSelect == 0 && list.size() == 0) {
						System.out.println("주문 가능한 가게가 없습니다!");
					} else if (menuSelect == 0) {
						int orderSelect = orderSelect();
						if (orderSelect > 0 && orderSelect <= list.size()) {
							int checkSystem = new OrderControl(id, list.get(orderSelect - 1)).runCheck();
							if(checkSystem == 1) {
								return;
							}
						}
					} else if (menuSelect == 1 && page > 1) {
						page--;
					} else if (menuSelect == 2 && page < listPage) {
						page++;
					} else if (menuSelect == 1 && page == 1) {
						System.out.println("이전페이지가 존재하지 않습니다!");
					} else if (menuSelect == 2 && page == listPage) {
						System.out.println("다음페이지가 존재하지 않습니다!");
					} else if (menuSelect == 9) {
						break;
					}
				}
			} catch (NumberFormatException e) {
				System.out.println(" 숫자만 입력하세요 ! ");
			}
		} else {
			System.out.println("현재 영업중인 가게가 없습니다!");
		}
	}

	private void listPaging(List<Owner> list, int page) {
		int idxLength = 3 * page;
		if (idxLength > list.size()) {
			idxLength = list.size();
		}
		for (int idx = (3 * (page - 1)); idx < idxLength; idx++) {
			listHeaderSelectNum(idx);
			System.out.println(list.get(idx));
			System.out.println("╘◖═════════════════════════════════◗╛");
			System.out.println();
		}
	}

	private void listHeaderSelectNum(int idx) {
		System.out.println("ᚹ ——-.･:*:･ﾟ'✫,' [선택번호 : " + (idx + 1) + "] ( ̲̅:̲̅:̲̅:̲̅♡:̲̅:̲̅:̲̅ )");
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
			System.out.println("•:•.•:•.•:•:•:•:•:•:•:•☾☼☽•:•.•:•.•:•:•:•:•:•:•:•");
			System.out.println(order);
		}
		System.out.println("•:•.•:•.•:•:•:•:•:•:•:•☾☼☽•:•.•:•.•:•:•:•:•:•:•:•");
	}

	private void writeReview(List<Order> list) {
		System.out.println("리뷰를 작성하고자 하는 주문내역의 선택번호를 입력하세요.");
		int num = inputNum();
		if (num > list.size() || num < 1) {
			System.out.println("잘못된 입력입니다.");
		} else {
			Review review = inputReview(list.get(num - 1));
			reviewDAO.insertReview(review);
		}
	}

	private Review inputReview(Order select) {
		Review review = new Review();
		review.setOrderDate(select.getOrderDate());
		review.setStoreName(select.getStoreName());
		review.setStoreNum(select.getStoreNum());
		review.setWriterId(id);
		review.setWriterNickname(customer.getNickname());
		while (true) {
			try {
				System.out.print("별점(1~5)>	");
				int star = Integer.parseInt(sc.nextLine());
				if (star > 0 && star < 6) {
					review.setStar(star);
					break;
				} else {
					System.out.println("1~5의 숫자만 입력하세요.");
				}
			} catch (NumberFormatException e) {
				System.out.println("1~5의 숫자만 입력하세요.");
			}
		}
		System.out.print("내용입력> ");
		review.setContent(sc.nextLine());

		return review;
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
				login = 1;
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
		System.out.println("│　4.주문내역조회　　　　5.리뷰관리　　　　　　　9.로그아웃");
		System.out.println("│　　　　　　　　　　　　　　　　　　　　　　　　　　0.탈퇴하기");
		System.out.println("└——————————————————————————————————————————————— - [ 📼 ]. +");
		System.out.println();
	}

}
