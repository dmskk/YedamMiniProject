package baedal.java.app.orders;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import baedal.java.app.common.DAO;

public class OrderDAO extends DAO {
	private static OrderDAO dao = null;

	private OrderDAO() {
	}

	public static OrderDAO getInstance() {
		if (dao == null)
			dao = new OrderDAO();
		return dao;
	}

	// 주문등록
	public void insertOrder(Order order) {
		try {
			connect();
			String sql = "INSERT INTO orders VALUES (DEFAULT, ?, ?, ?, ?, ?, DEFAULT, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, order.getCustomerId());
			pstmt.setLong(2, order.getStoreNum());
			pstmt.setString(3, order.getOrderMenu());
			pstmt.setInt(4, order.getOrderPrice());
			pstmt.setInt(5, order.getPay());
			pstmt.setString(6, order.getStoreName());
			int result = pstmt.executeUpdate();
			if (result == 0) {
				System.out.println("주문이 정상적으로 완료되지 않았습니다.");
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

	// 주문조회 - 회원id
	public List<Order> viewCustomerOrders(String id) {
		List<Order> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM orders WHERE customer_id = '" + id + "' ORDER BY order_date";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Order order = new Order();
				order.setCustomerId(rs.getString("customer_id"));
				order.setDeliveryStatus(rs.getInt("delivery_status"));
				order.setOrderDate(rs.getTimestamp("order_date"));
				order.setOrderMenu(rs.getString("order_menu"));
				order.setOrderPrice(rs.getInt("order_price"));
				order.setPay(rs.getInt("pay"));
				order.setStoreName(rs.getString("store_name"));
				order.setStoreNum(rs.getLong("store_num"));
				list.add(order);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	// 주문조회 - 사업자번호
	public List<Order> viewStoreOrders(long num) {
		List<Order> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM orders WHERE store_num = " + num + " ORDER BY order_date";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Order order = new Order();
				order.setCustomerId(rs.getString("customer_id"));
				order.setDeliveryStatus(rs.getInt("delivery_status"));
				order.setOrderDate(rs.getTimestamp("order_date"));
				order.setOrderMenu(rs.getString("order_menu"));
				order.setOrderPrice(rs.getInt("order_price"));
				order.setPay(rs.getInt("pay"));
				order.setStoreName(rs.getString("store_name"));
				order.setStoreNum(rs.getLong("store_num"));
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}
	
	// 주문조회 - 사업자번호 & 오늘
		public List<Order> viewStoreTodayOrders(long num) {
			List<Order> list = new ArrayList<>();
			try {
				connect();
				String sql = "SELECT * FROM orders_store_today_vu WHERE store_num = " + num;
				stmt = conn.createStatement();
				rs = stmt.executeQuery(sql);
				while(rs.next()) {
					Order order = new Order();
					order.setCustomerId(rs.getString("customer_id"));
					order.setDeliveryStatus(rs.getInt("delivery_status"));
					order.setOrderDate(rs.getTimestamp("order_date"));
					order.setOrderMenu(rs.getString("order_menu"));
					order.setOrderPrice(rs.getInt("order_price"));
					order.setPay(rs.getInt("pay"));
					order.setStoreName(rs.getString("store_name"));
					order.setStoreNum(rs.getLong("store_num"));
					list.add(order);
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
	
	// 주문건수조회 - 사업자번호 & 오늘
	public int calcStoreTodayOrders(long num) {
		int today = 0;
		try {
			connect();
			String sql = "SELECT COUNT(*) count FROM orders_store_today_vu WHERE store_num = " + num;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				today = rs.getInt("count");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
		
		return today;
	}
	
	// 주문조회 - 배달진행중
	public List<Order> viewCustomerOrdersDelivery(String id) {
		List<Order> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM orders WHERE customer_id = '" + id + "' AND delivery_status < 3 ORDER BY order_date";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Order order = new Order();
				order.setCustomerId(rs.getString("customer_id"));
				order.setDeliveryStatus(rs.getInt("delivery_status"));
				order.setOrderDate(rs.getTimestamp("order_date"));
				order.setOrderMenu(rs.getString("order_menu"));
				order.setOrderPrice(rs.getInt("order_price"));
				order.setPay(rs.getInt("pay"));
				order.setStoreName(rs.getString("store_name"));
				order.setStoreNum(rs.getLong("store_num"));
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	// 주문조회 - 후기 안 쓴 거 & 배달완료된거
	public List<Order> viewCustomerOrdersNoReview(String id) {
		List<Order> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM orders_no_review_vu WHERE customer_id = '" + id + "' ORDER BY order_date";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Order order = new Order();
				order.setCustomerId(rs.getString("customer_id"));
				order.setDeliveryStatus(rs.getInt("delivery_status"));
				order.setOrderDate(rs.getTimestamp("order_date"));
				order.setOrderMenu(rs.getString("order_menu"));
				order.setOrderPrice(rs.getInt("order_price"));
				order.setPay(rs.getInt("pay"));
				order.setStoreName(rs.getString("store_name"));
				order.setStoreNum(rs.getLong("store_num"));
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return list;
	}

	// 배달현황 업데이트
	public void updateStatus(Order order) {
		try {
			connect();
			String sql = "UPDATE orders SET delivery_status = " + order.getDeliveryStatus() + " WHERE customer_id = '" + order.getCustomerId() + "' AND order_date = '" + order.getOrderDate() + "' AND store_num = " + order.getStoreNum() + " AND store_name = '" + order.getStoreName() + "'";
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				if(order.getDeliveryStatus() == 2) {
					System.out.println("'배달중'으로 변경되었습니다.");
				} else if(order.getDeliveryStatus() == 3) {
					System.out.println("'배달완료'로 변경되었습니다.");
				}
			} else {
				System.out.println("변경이 정상적으로 완료되지 않았습니다.");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}
	
	// 고객의 지난달 총 주문횟수
	public int lastMonthOrderCount(String id) {
		int cnt = 0;

		try {
			connect();
			String sql ="SELECT COUNT(*) count FROM orders WHERE TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'), 'YYYY-MM-DD') IN "
					+ "( SELECT TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'), 'YYYY-MM-DD') "
					+ "FROM orders WHERE TO_DATE(TO_CHAR(order_date, 'yyyy-mm-dd'), 'YYYY-MM-DD') "
					+ "BETWEEN TRUNC(ADD_MONTHS(sysdate, -1), 'MM') "
					+ "AND TRUNC(sysdate, 'MM')) AND customer_id = '" + id + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				cnt = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}

		return cnt;
	}

	// 주문취소
	public void cancelOrder(Order order) {
		try {
			connect();
			String sql = "UPDATE orders SET delivery_status = 4 WHERE order_date = '" + order.getOrderDate()
					+ "' AND customer_id = '" + order.getCustomerId() + "' AND store_num = " + order.getStoreNum();
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("주문이 취소되었습니다.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 주문내역 삭제
	public void deleteOrder(Order order) {
		try {
			connect();
			String sql = "DELETE FROM orders WHERE order_date = '" + order.getOrderDate() + "' AND customer_id = '"
					+ order.getCustomerId() + "' AND store_num = " + order.getStoreNum();
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("주문내역이 삭제되었습니다.");
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
