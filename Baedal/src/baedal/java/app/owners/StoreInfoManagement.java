package baedal.java.app.owners;

import baedal.java.app.common.Management;

public class StoreInfoManagement extends Management {
	private static long corpNum;
	private static Owner owner;

	public StoreInfoManagement(long corpNum) {
		this.corpNum = corpNum;
		this.owner = ownerDAO.viewStoreProfile(corpNum);

		while (true) {
			// 메뉴출력
			menuPrint();

			try {
				// 메뉴선택
				int menu = inputNum();

				// 기능
				if (menu == 1) {
					//가게정보확인
					viewStoreInfo();
				} else if (menu == 2) {
					//영업시간수정
					updateTime();
				} else if (menu == 3) {
					//가게 메뉴 확인
					viewMenuList();
				} else if (menu == 4) {
					//메뉴 추가
					insertMenu();
				} else if (menu == 5) {
					//메뉴 삭제
					deleteMenu();
				} else if (menu == 6) {

				} else if (menu == 7) {

				} else if (menu == 8) {

				} else if (menu == 9) {

				} else {

				}
			} catch (NumberFormatException e) {
				System.out.println("숫자를 입력하세요.");
			}
		}
	}

	private void deleteMenu() {
		// TODO Auto-generated method stub
		
	}

	private void insertMenu() {
		// TODO Auto-generated method stub
		
	}

	private void viewMenuList() {
		// TODO Auto-generated method stub
		
	}

	private void updateTime() {
		// TODO Auto-generated method stub
		
	}

	private void viewStoreInfo() {
		
		
	}

	@Override
	protected void menuPrint() {
		System.out.println("=====================================================");
		System.out.println("   1.가게정보확인     2.영업시간 수정     3.가게 메뉴 확인");
		System.out.println("   4.메뉴 추가       5.메뉴 삭제        6.주문내역조회");
		System.out.println("   7.후기보기        8.탈퇴하기         9.뒤로가기");
		System.out.println("=====================================================");
	}

}
