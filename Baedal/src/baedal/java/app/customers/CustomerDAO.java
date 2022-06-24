package baedal.java.app.customers;

import java.sql.SQLException;

import baedal.java.app.common.DAO;

public class CustomerDAO extends DAO {
	// 싱글톤
	private static CustomerDAO dao = null;

	private CustomerDAO() {
	}

	public static CustomerDAO getInstance() {
		if (dao == null)
			dao = new CustomerDAO();
		return dao;
	}

	// CRUD
	// 회원가입
	public void signUp(Customer customer) {
		try {
			connect();
			String sql = "INSERT INTO customers VALUES (?, ?, ?, ?, ?, ?, DEFAULT, DEFAULT)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, customer.getId());
			pstmt.setString(2, customer.getPassword());
			pstmt.setString(3, customer.getName());
			pstmt.setInt(4, customer.getPhoneNumber());
			pstmt.setString(5, customer.getAddr());
			pstmt.setString(6, customer.getNickname());
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("가입완료");
			} else {
				System.out.println("정상적으로 등록되지 않았습니다.");
				System.out.println("다시 시도해주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 정보조회
	public Customer showProfile(String id) {
		Customer customer = null;

		try {
			connect();
			String sql = "SELECT * FROM customers WHERE id = '" + id + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				customer = new Customer();
				customer.setId(id);
				customer.setPassword(rs.getString("password"));
				customer.setName(rs.getString("name"));
				customer.setPhoneNumber(rs.getInt("phone_number"));
				customer.setAddr(rs.getString("addr"));
				customer.setNickname(rs.getString("nickname"));
				customer.setPoint(rs.getDouble("point"));
				customer.setGrade(rs.getInt("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return customer;
	}

	// 닉네임 검색
	public Customer showProfileNickname(String nickname) {
		Customer customer = null;

		try {
			connect();
			String sql = "SELECT * FROM customers WHERE nickname = '" + nickname + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				customer = new Customer();
				customer.setId(rs.getString("id"));
				customer.setPassword(rs.getString("password"));
				customer.setName(rs.getString("name"));
				customer.setPhoneNumber(rs.getInt("phone_number"));
				customer.setAddr(rs.getString("addr"));
				customer.setNickname(rs.getString("nickname"));
				customer.setPoint(rs.getDouble("point"));
				customer.setGrade(rs.getInt("grade"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return customer;
	}

	// 정보수정 - 주소
	public void updateProfileAddr(Customer customer) {
		try {
			connect();
			String sql = "UPDATE customers SET addr = '" + customer.getAddr() + "' WHERE id = '" + customer.getId()
					+ "'";
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("수정완료");
			} else {
				System.out.println("정상적으로 수정되지 않았습니다.");
				System.out.println("다시 시도해주세요.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 정보수정 - 닉네임
	public void updateProfileNickname(Customer customer) {
		try {
			connect();
			String sql = "UPDATE customers SET nickname = '" + customer.getNickname() + "' WHERE id = '"
					+ customer.getId() + "'";
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("수정완료");
			} else {
				System.out.println("정상적으로 수정되지 않았습니다.");
				System.out.println("다시 시도해주세요.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 정보수정 - 등급
	public void updateProfileGrade(Customer customer) {
		try {
			connect();
			String sql = "UPDATE customers SET grade = " + customer.getGrade() + " WHERE id = '" + customer.getId()
					+ "'";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	
	// 정보수정 - 포인트
	public void updateProfilePoint(Customer customer) {
		try {
			connect();
			String sql = "UPDATE customers SET point = " + customer.getPoint() + " WHERE id = '" + customer.getId()
					+ "'";
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	// 회원탈퇴
	public void deleteAccount(String id) {
		try {
			connect();
			String sql = "DELETE FROM customers WHERE id = '" + id + "'";
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("탈퇴가 완료되었습니다.");
			} else {
				System.out.println("정상적으로 탈퇴되지 않았습니다.");
				System.out.println("다시 시도해주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
}
