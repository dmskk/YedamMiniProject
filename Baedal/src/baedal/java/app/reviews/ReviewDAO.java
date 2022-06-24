package baedal.java.app.reviews;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import baedal.java.app.common.DAO;

public class ReviewDAO extends DAO {
	private static ReviewDAO dao = null;

	private ReviewDAO() {
	}

	public static ReviewDAO getInstance() {
		if (dao == null)
			dao = new ReviewDAO();
		return dao;
	}

	// 리뷰등록
	public void insertReview(Review review) {
		try {
			connect();
			String sql = "INSERT INTO reviews VALUES (DEFAULT, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, review.getWriterId());
			pstmt.setString(2, review.getWriterNickname());
			pstmt.setInt(3, review.getStar());
			pstmt.setString(4, review.getContent());
			pstmt.setString(5, review.getStoreName());
			pstmt.setLong(6, review.getStoreNum());
			pstmt.setTimestamp(7, review.getOrderDate());
			int result = pstmt.executeUpdate();
			if (result > 0) {
				System.out.println("리뷰 등록");
			} else {
				System.out.println("등록 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 리뷰수정 - 내용
	public void updateReview(Review review) {
		try {
			connect();
			String sql = "UPDATE reviews SET content = '" + review.getContent() + "' WHERE writer_id = '"
					+ review.getWriterId() + "' AND store_num = " + review.getStoreNum();
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if (result > 0) {
				System.out.println("수정 완료");
			} else {
				System.out.println("수정 실패");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			disconnect();
		}
	}

	// 리뷰조회 - 회원아이디
	public List<Review> viewReviewCustomer(String id) {
		List<Review> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM reviews WHERE writer_id = '" + id + "'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Review review = new Review();
				review.setContent(rs.getString("content"));
				review.setReviewDate(rs.getTimestamp("review_date"));
				review.setStar(rs.getInt("star"));
				review.setStoreName(rs.getString("store_name"));
				review.setStoreNum(rs.getLong("store_num"));
				review.setWriterId(rs.getString("writer_id"));
				review.setWriterNickname(rs.getString("writer_nickname"));
				review.setOrderDate(rs.getTimestamp("order_date"));
				list.add(review);
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

	// 리뷰조회 - 가게
	public List<Review> viewReviewStore(long num) {
		List<Review> list = new ArrayList<>();

		try {
			connect();
			String sql = "SELECT * FROM reviews WHERE store_num = " + num;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				Review review = new Review();
				review.setContent(rs.getString("content"));
				review.setReviewDate(rs.getTimestamp("review_date"));
				review.setStar(rs.getInt("star"));
				review.setStoreName(rs.getString("store_name"));
				review.setStoreNum(rs.getLong("store_num"));
				review.setWriterId(rs.getString("writer_id"));
				review.setWriterNickname(rs.getString("writer_nickname"));
				review.setOrderDate(rs.getTimestamp("order_date"));
				list.add(review);
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

	// 리뷰삭제
	public void deleteReview(Review review) {
		try {
			connect();
			String sql = "DELETE FROM reviews WHERE writer_id = '" + review.getWriterId() + "' AND store_name = '"
					+ review.getStoreName() + "' AND review_date = '" + review.getReviewDate() + "'";
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			if(result > 0) {
				System.out.println("삭제 완료");
			} else {
				System.out.println("삭제 실패");
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
