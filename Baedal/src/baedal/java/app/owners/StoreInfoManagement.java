package baedal.java.app.owners;

import baedal.java.app.common.Management;

public class StoreInfoManagement extends Management {
	private static long corpNum;
	private static Owner owner;
	
	public StoreInfoManagement(long corpNum) {
		this.corpNum = corpNum;
		this.owner = ownerDAO.viewStoreProfile(corpNum);
		
		menuPrint();
	}
	
	@Override
	protected void menuPrint() {
		System.out.println("==========================================");
		System.out.println("   1.가게정보확인    2.영업시간 수정     3.가게 메뉴 확인   ");
		System.out.println("==========================================");
	}

}
