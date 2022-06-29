package baedal.java.app.common;

import java.util.Scanner;

import baedal.java.app.customers.CustomerDAO;
import baedal.java.app.menus.MenuDAO;
import baedal.java.app.orders.OrderDAO;
import baedal.java.app.owners.OwnerDAO;
import baedal.java.app.reviews.ReviewDAO;

public class Management {
	// 필드
	protected Scanner sc = new Scanner(System.in);
	protected CustomerDAO customerDAO = CustomerDAO.getInstance();
	protected MenuDAO menuDAO = MenuDAO.getInstance();
	protected OrderDAO orderDAO = OrderDAO.getInstance();
	protected OwnerDAO ownerDAO = OwnerDAO.getInstance();
	protected ReviewDAO reviewDAO = ReviewDAO.getInstance();

	// 실행
	public void run() {
		int checkSystem = 0;
		while (true) {
			// 메뉴
			menuPrint();

			try {
				// 메뉴입력
				int num = inputSelectNum();

				// 각 메뉴
				if (num == 1) {
					
					checkSystem = new SignUpControl().runCheck();
					if (checkSystem == 1) {
						new LoginControl().run();
					}
				} else if (num == 2) {
					new LoginControl().run();
				} else if (num == 9) {
					System.out.println();
					System.out.println("✅✅✅✅☁️☁️✅☁️☁️☁️✅☁️✅✅✅✅✅");
					System.out.println("✅☁️☁️☁️✅☁️☁️✅☁️✅☁️☁️✅☁️☁️☁️☁️");
					System.out.println("✅✅✅✅☁️☁️☁️☁️✅☁️☁️☁️✅✅✅✅✅");
					System.out.println("✅☁️☁️☁️✅☁️☁️☁️✅☁️☁️☁️✅☁️☁️☁️☁️");
					System.out.println("✅✅✅✅☁️☁️☁️☁️✅☁️☁️☁️✅✅✅✅✅");
					System.out.println();
					break;
				} else {
					System.out.println("잘못된 선택입니다.");
				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요.");
			}
		}
	}

	protected void menuPrint() {
		System.out.println();
		System.out.println("          . ^   ^          (              )");
		System.out.println("          ꒰  ´ㅅ`꒱   .oO      어서오세요···   }");
		System.out.println("          (  つ旦O          ( 배달의 민족입니다)  ");
		System.out.println();
		System.out.println();    
		System.out.println("     .　　　☆ *　. 　☆   ♡♡♡   메뉴를 선택하세요   ♡♡♡   ☆ 　.　* ☆　　　. ");
		System.out.println("＞　 1. 회원가입  ＜          ＞　  2. 로그인 　＜             ＞　 9. 종료 　＜");
		System.out.println();
	}

	protected int inputSelectNum() {
		System.out.print("━ 선 ━━ 택 ━━ ▶ ");
		return Integer.parseInt(sc.nextLine());
	}
	protected int inputNum() {
		System.out.print("━ 입 ━━ 력 ━━ ▶ ");
		return Integer.parseInt(sc.nextLine());
	}
	
	protected void enter() {
		System.out.println("");
	}

}
