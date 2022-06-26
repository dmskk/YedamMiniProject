package baedal.java.app.reviews;

import java.util.List;

import baedal.java.app.common.Management;
import baedal.java.app.customers.Customer;
import baedal.java.app.orders.Order;

@SuppressWarnings("unused")
public class ReviewControl extends Management {
	private static String writerId;
	private static String writerNickname;
	private static String storeName;
	private static long storeNum;
	private static List<Order> list;
	private static int listSize;

	public ReviewControl(Customer customer) {
		ReviewControl.writerId = customer.getId();
		ReviewControl.writerNickname = customer.getNickname();
	}

	public void run() {
		// 주문내역 중에서 후기 안 쓴거만 & 배달완료된것만 보여주기
		list = orderDAO.viewCustomerOrdersNoReview(writerId);
		listSize = list.size();

		for (int idx = 0; idx < listSize; idx++) {
			System.out.println("------------------------");
			System.out.println("[선택번호:" + (idx + 1) + "]");
			System.out.println(list.get(idx));
			System.out.println("------------------------");
		}
		while (true) {
			// 메뉴출력
			menuPrint();

			try {
				// 메뉴입력
				int num = inputNum();

				// 기능
				if (num == 1) {
					// 1.후기작성
					writeReview();
				} else if (num == 2) {
					// 2.후기작성내역
					viewReviews();
				} else if (num == 3) {
					// 5.뒤로가기
					break;
				} else {
					System.out.println("잘못된 입력입니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자만 입력하세요.");
			}
		}

	}

	private void writeReview() {
		System.out.println("리뷰를 작성하고자 하는 주문내역의 선택번호를 입력하세요.");
		int num = inputNum();
		if (num > listSize || num < 1) {
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
		review.setWriterId(writerId);
		review.setWriterNickname(writerNickname);
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

	private void viewReviews() {
		List<Review> list = reviewDAO.viewReviewCustomer(writerId);
		for(Review review : list) {
			System.out.println(review);
		}
	}

	@Override
	protected void menuPrint() {
		System.out.println("-------------------------------------");
		System.out.println("  1.후기작성   2.후기작성내역   3.뒤로가기  ");
		System.out.println("-------------------------------------");
	}

}
