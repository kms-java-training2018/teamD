package bean;

public class MyPageBean {
	/** 会員ID */
	private String userId;

	/** パスワード */
	private String password;

	/** エラーメッセージ */
	private String erroMsg;
	
	private String myPageText;

	/** エラーメッセージ */
	private String errorMessage;

	/** 表示名 */
	private String userName;

	/** 会員番号 */
	private String userNo;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getMyPageText() {
		return myPageText;
	}

	public void setMyPageText(String myPageText) {
		this.myPageText = myPageText;
	}

	public String getErroMsg() {
		return erroMsg;
	}

	public void setErroMsg(String erroMsg) {
		this.erroMsg = erroMsg;
	}
}
