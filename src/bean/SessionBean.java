package bean;

public class SessionBean {
	/** 会員番号 */
	private String userNo;

	/** 表示名 */
	private String userName;

	private String userId;

	private String otherUsersNo;

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOtherUsersNo() {
		return otherUsersNo;
	}

	public void setOtherUsersNo(String otherUsersNo) {
		this.otherUsersNo = otherUsersNo;
	}

}
