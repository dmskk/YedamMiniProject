package baedal.java.app.menus;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import baedal.java.app.common.DAO;

public class MenuDAO extends DAO{
	
	private static MenuDAO dao = null;
	private MenuDAO() {}
	public static MenuDAO getInstance() {
		if(dao == null) dao = new MenuDAO();
		return dao;
	}
	
	//메뉴추가
	public void insertMenu(Menu menu) {
		try {
			connect();
			String sql = "INSERT INTO menus VALUES (?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, menu.getStoreNum());
			pstmt.setString(2, menu.getMenuName());
			pstmt.setInt(3, menu.getMenuPrice());
			pstmt.setString(4, menu.getMenuContent());
			int result = pstmt.executeUpdate();
			if(result > 0 ) {
				System.out.println("메뉴가 정상적으로 등록되었습니다.");
			} else {
				System.out.println("메뉴가 정상적으로 등록되지 않았습니다.");
				System.out.println("다시 시도해주세요.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	//매장 메뉴 전체조회
	public List<Menu> viewMenu() {
		List<Menu> list = new ArrayList<>();
		
		try {
			connect();
			String sql = "SELECT * FROM menus ORDERY BY menu_name";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				Menu menu = new Menu();
				menu.setMenuContent(rs.getString("menu_content"));
				menu.setMenuName(rs.getString("menu_name"));
				menu.setMenuPrice(rs.getInt("menu_price"));
				menu.setStoreNum(rs.getInt("store_num"));
				list.add(menu);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return list;
	}
	
	//가격 수정
	public void updatePrice(Menu menu) {
		try {
			connect();
			String sql = "UPDATE menus SET menu_price = " + menu.getMenuPrice() + " WHERE menu_name '" + menu.getMenuName() + "' AND store_num = " + menu.getStoreNum();
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			
			if(result > 0) {
				System.out.println("정상적으로 수정되었습니다.");
			} else {
				System.out.println("정상적으로 수정되지 않았습니다.");
				System.out.println("다시 시도해주세요.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	//메뉴삭제
	public void deleteMenu(Menu menu) {
		try {
			connect();
			String sql = "DELETE FROM menus WHERE store_num = " + menu.getStoreNum() + " AND menu_name = '" + menu.getMenuName() + "'";
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if(result > 0) {
				System.out.println("정상적으로 삭제되었습니다.");
			} else {
				System.out.println("정상적으로 삭제되지 않았습니다.");
				System.out.println("다시 시도해주세요.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
}
