package baedal.java.app.reviews;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Review {
	private Date reviewDate;
	private String writerId;
	private String writerNickname;
	private int star;
	private String content;
	private String storeName;
	private int storeNum;
}
