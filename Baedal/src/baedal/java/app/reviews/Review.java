package baedal.java.app.reviews;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Review {
	private Timestamp reviewDate;
	private String writerId;
	private String writerNickname;
	private int star;
	private String content;
	private String storeName;
	private long storeNum;
	private Timestamp orderDate;
	
	
	@Override
	public String toString() {
		String star = "⭐";
		String me = "작성일 : " + String.valueOf(this.reviewDate).substring(0, 16)  + "\n작성자 : " + this.writerNickname + "\n별점 : " + star.repeat(this.star) + "\n작성내용 : " + this.content;
		return me;
	}
}
