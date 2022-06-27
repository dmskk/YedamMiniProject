package baedal.java.app.owners;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import baedal.java.app.common.DAO;

public class OwnerDAO extends DAO {
	private static OwnerDAO dao = null;

	private OwnerDAO() {
	}

	public static OwnerDAO getInstance() {
		if (dao == null)
			dao = new OwnerDAO();
		return dao;
	}

	// 점포등록
	public void signUp(Owner owner) {
		try {
			connect();
			String sql = "INSERT INTO owners VALUES (?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, owner.getCorpNum());
			pstmt.setString(2, owner.getPassword());
			pstmt.setString(3, owner.getStoreName());
			pstmt.setInt(4, owner.getStoreValue());
			pstmt.setInt(5, owner.getTimeOpen());
			pstmt.setInt(6, owner.getTimeClose());

			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println();
				System.out.println();
				System.out.println(".      　。　　　　•　    　ﾟ　　。");
				System.out.println("　　.　　　.　　　  　　.　　　　　。　　   。　.");
				System.out.println(" 　.　　      。　        ඞ   。　    .    •");
				System.out.println(" •.  정상적으로  점포가  등록되었습니다 !");
				System.out.println("　 　　。　　　　　　ﾟ　　　.　　　　　    .");
				System.out.println("                환영합니다~    .");
				System.out.println(",　　　　.　 .　　       .               。");
				System.out.println();
				System.out.println();

			} else {
				System.out.println();
				System.out.println("정상적으로 등록되지 않았습니다.");
				System.out.println("다시 시도해주세요.");
				System.out.println();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 영업시간변경 - 오픈
	public void updateOpen(Owner owner) {
		try {
			connect();
			String sql = "UPDATE owners SET time_open = " + owner.getTimeOpen() + " WHERE corp_num = "
					+ owner.getCorpNum();
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("정상적으로 수정되었습니다.");
			} else {
				System.out.println("수정을 실패하였습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 영업시간변경 - 클로즈
	public void updateClose(Owner owner) {
		try {
			connect();
			String sql = "UPDATE owners SET time_close = " + owner.getTimeClose() + " WHERE corp_num = "
					+ owner.getCorpNum();
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("정상적으로 수정되었습니다.");
			} else {
				System.out.println("수정을 실패하였습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	// 가게 정보 조회
	public Owner viewStoreProfile(long num) {
		Owner owner = null;
		try {
			connect();
			String sql = "SELECT * FROM owners WHERE corp_num = " + num;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				owner = new Owner();
				owner.setCorpNum(rs.getInt("corp_num"));
				owner.setPassword(rs.getString("password"));
				owner.setStoreName(rs.getString("store_name"));
				owner.setStoreValue(rs.getInt("store_value"));
				owner.setTimeClose(rs.getInt("time_close"));
				owner.setTimeOpen(rs.getInt("time_open"));
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		return owner;
	}
	
	// 영업 중인 가게 리스트
	public List<Owner> openList() {
		List<Owner> list = new ArrayList<>();
		try {
			connect();
			String sql = "SELECT o.* FROM owners o JOIN owners_invalid_vu v ON (o.corp_num = v.corp_num) WHERE v.open = 'true'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Owner owner = new Owner();
				owner.setCorpNum(rs.getInt("corp_num"));
				owner.setPassword(rs.getString("password"));
				owner.setStoreName(rs.getString("store_name"));
				owner.setStoreValue(rs.getInt("store_value"));
				owner.setTimeClose(rs.getInt("time_close"));
				owner.setTimeOpen(rs.getInt("time_open"));
				list.add(owner);
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
	
	// 영업 중인 가게 리스트 - 업종
		public List<Owner> openValueList(int value) {
			List<Owner> list = new ArrayList<>();
			try {
				connect();
				String sql = "SELECT o.* FROM owners o JOIN owners_invalid_vu v ON (o.corp_num = v.corp_num) WHERE v.open = 'true' AND o.value = " + value;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					Owner owner = new Owner();
					owner.setCorpNum(rs.getInt("corp_num"));
					owner.setPassword(rs.getString("password"));
					owner.setStoreName(rs.getString("store_name"));
					owner.setStoreValue(rs.getInt("store_value"));
					owner.setTimeClose(rs.getInt("time_close"));
					owner.setTimeOpen(rs.getInt("time_open"));
					list.add(owner);
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
	
	
	// 탈퇴
	public void deleteAccount(long num) {
		try {
			connect();
			String sql = "DELETE FROM owners WHERE corp_num = " + num;
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if(result > 0) {
				System.out.println("탈퇴가 완료되었습니다.");
			} else {
				System.out.println("정상적으로 탈퇴되지 않았습니다.");
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
