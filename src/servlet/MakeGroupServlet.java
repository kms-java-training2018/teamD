package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.SessionBean;

public class MakeGroupServlet extends HttpServlet {
	/**
	 * グループ名の最大長
	 */
	final int GROUP_NAME_LENGTH = 30;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// グループ作成ページに移動する処理
		/**
		 * 1)パラメータチェック
		 */
		int errorFlag = 0;
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();
		if (sesUserNo.equals(null)) {
			// セッション情報なし
			// エラーページへ
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
		}
		/**
		 * 1)会員一覧取得処理
		 */
		StringBuilder sb = new StringBuilder();
		/*
		 * DBログイン
		 */
		Connection conn = null;
		String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
		String user = "DEV_TEAM_D";
		String dbPassword = "D_DEV_TEAM";
		// JDBCドライバーのロード
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		// 接続作成
		try {
			conn = DriverManager.getConnection(url, user, dbPassword);
			Statement stmt = conn.createStatement();
			// SQL作成
			/*
			 * ユーザ一覧取得
			 */
			sb.append("SELECT ");
			sb.append(" user_no ");
			sb.append(", user_id ");
			sb.append(", user_name ");
			sb.append("FROM ");
			sb.append(" m_user ");
			sb.append(" ORDER BY ");
			sb.append(" user_no ");
			// SQL実行
			ResultSet rs = stmt.executeQuery(sb.toString());
			// それぞれArrayListに入れる
			ArrayList<Integer> userNo = new ArrayList<>();
			ArrayList<String> userID = new ArrayList<>();
			ArrayList<String> userName = new ArrayList<>();
			while (rs.next()) {
				// ログインしている自分自身は除く
				int check = rs.getInt("USER_NO");
				int check2 = Integer.parseInt(sesUserNo);
				if (check == check2) {
					// 自分なので追加しない
				} else {
					// 自分以外なのでリストに追加
					userNo.add(rs.getInt("USER_NO"));
					userID.add(rs.getString("USER_ID"));
					userName.add(rs.getString("USER_NAME"));
				}
			}
			// リクエストに送る
			req.setAttribute("userlist", userName);
			req.setAttribute("userNo", userNo);
			// 初期化
			sb.delete(0, sb.length());

		} catch (SQLException e) {
			// エラーはすべてここにくる
			e.printStackTrace();
			errorFlag = 1;
			// SQLの接続は絶対に切断
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		// 出力
		if (errorFlag == 1) {
			req.getRequestDispatcher("/WEB-INF/jsp/errorPage.jsp").forward(req, res);
		} else {
			req.getRequestDispatcher("/WEB-INF/jsp/makeGroup.jsp").forward(req, res);
		}

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// グループ作成を行いメインページに移動する処理
		/**
		 * 1)パラメータチェック
		 */
		req.setCharacterEncoding("UTF-8");
		// セッション情報確認
		String errorMsg="";
		int errorFlag = 0;
		HttpSession session = req.getSession();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		String sesUserNo = sesBean.getUserNo();
		if (sesUserNo.equals(null)) {
			// セッション情報なし
			// エラーページへ
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
		}
		// 入力値チェック
		String groupName = req.getParameter("groupName");
		String[] groupMemberNo = req.getParameterValues("userNo");
		if (groupName.equals("")) {
			// グループ名が空
			errorMsg="グループ名が空です";
			errorFlag = 1;
		} else if (groupName.length() > GROUP_NAME_LENGTH) {
			// グループ名が所定の文字数より長い
			errorMsg="グループ名が規定の30文字より長いです";
			errorFlag = 1;
		} else if (groupMemberNo==null) {
			// グループメンバーがいない
			errorMsg="グループメンバーがあなた以外いません";
			errorFlag = 1;
		}
		if (errorFlag == 1) {
			// エラーページへ

		} else {
			// それ以外の場合処理続ける
			/**
			 * 2)グループ登録処理
			 */
			StringBuilder sb = new StringBuilder();
			/*
			 * DBログイン
			 */
			Connection conn = null;
			String url = "jdbc:oracle:thin:@192.168.51.67:1521:XE";
			String user = "DEV_TEAM_D";
			String dbPassword = "D_DEV_TEAM";
			// JDBCドライバーのロード
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			// 接続作成
			try {
				conn = DriverManager.getConnection(url, user, dbPassword);
				Statement stmt = conn.createStatement();
				// SQL作成
				/*
				 * group_noの自動採番
				 */
				// 最大値取得
				sb.append("SELECT ");
				sb.append(" MAX(group_no) ");
				sb.append("FROM ");
				sb.append(" m_group ");
				// SQL実行
				ResultSet rs = stmt.executeQuery(sb.toString());
				// 結果を代入
				// ArrayList<Integer> maxGroupNoList = new ArrayList<>();
				int maxGroupNo = 0;
				while (rs.next()) {
					// 値を取得、その次の番号に
					maxGroupNo = rs.getInt("MAX(GROUP_NO)") + 1;
				}
				// 初期化
				sb.delete(0, sb.length());
				/*
				 * グループ作成
				 */
				sb.append("INSERT INTO ");
				sb.append(" m_group ");
				sb.append(" ( ");
				sb.append(" group_no ");
				sb.append(", group_name ");
				sb.append(", regist_user_no ");
				sb.append(", regist_date ");
				sb.append(" ) ");
				sb.append("VALUES ");
				sb.append(" ( ");
				sb.append(" '" + maxGroupNo + "' , ");
				sb.append(" '" + groupName + "' , ");
				sb.append(" '" + sesUserNo + "' , ");
				sb.append(" sysdate ");
				sb.append(" ) ");
				// SQL実行
				ResultSet rs2 = stmt.executeQuery(sb.toString());
				// 初期化
				sb.delete(0, sb.length());

				/**
				 * 3)グループ会員登録処理
				 */
				// 自分
				sb.append("INSERT INTO ");
				sb.append(" t_group_info ");
				sb.append(" ( ");
				sb.append(" group_no ");
				sb.append(", user_no ");
				sb.append(", regist_date ");
				sb.append(" ) ");
				sb.append("VALUES ");
				sb.append(" ( ");
				sb.append(" '" + maxGroupNo + "' , ");
				sb.append(" '" + sesUserNo + "' , ");
				sb.append(" sysdate ");
				sb.append(" ) ");
				// SQL実行
				ResultSet rs3 = stmt.executeQuery(sb.toString());
				// 初期化
				sb.delete(0, sb.length());

				// 他メンバー
				for (int i = 0; i < groupMemberNo.length; i++) {
					sb.append("INSERT INTO ");
					sb.append(" t_group_info ");
					sb.append(" ( ");
					sb.append(" group_no ");
					sb.append(", user_no ");
					sb.append(", regist_date ");
					sb.append(" ) ");
					sb.append("VALUES ");
					sb.append(" ( ");
					sb.append(" '" + maxGroupNo + "' , ");
					sb.append(" '" + groupMemberNo[i] + "' , ");
					sb.append(" sysdate ");
					sb.append(" ) ");
					// SQL実行
					ResultSet rs4 = stmt.executeQuery(sb.toString());
					// 初期化
					sb.delete(0, sb.length());

				}

			} catch (SQLException e) {
				// エラーはすべてここにくる
				e.printStackTrace();
				errorMsg="データベース登録時にエラーが発生しました";
				errorFlag = 1;
				// SQLの接続は絶対に切断
			} finally {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		// 出力
		if (errorFlag == 1) {
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher("/errorPage").forward(req, res);
		} else {
			req.getRequestDispatcher("/main").forward(req, res);
		}

	}

}
