package baedal.java.app.reviews;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Review {
	private Timestamp reviewDate;
	private String writerId;
	private String writerNickname;
	private int star;
	private String content;
	private String storeName;
	private long storeNum;
	private Timestamp orderDate;
}
