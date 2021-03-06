package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.GroupListBean;
import bean.SessionBean;
import bean.UserListBean;
import model.GetGroupListModel;
import model.GetUserListModel;

public class MainPageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		HttpSession session = req.getSession();
		String direction = "/WEB-INF/jsp/mainPage.jsp";
		// 初期化
		GetUserListModel userListModel = new GetUserListModel();
		GetGroupListModel groupListModel = new GetGroupListModel();
		ArrayList<UserListBean> userListBeanList = new ArrayList<>();
		ArrayList<GroupListBean> groupListBeanList = new ArrayList<>();
		SessionBean sesBean = (SessionBean) session.getAttribute("session");
		if (session == null || session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
		} else {
			// セッションからユーザーNo取得

			String sesUserNo = sesBean.getUserNo();
			// 2～3処理
			try {
				// 2）他会員一覧取得処理
				userListBeanList = userListModel.getUserList(sesUserNo);
				// 3）最新メッセージ取得処理
				userListBeanList = userListModel.getUserLatestMessage(userListBeanList, sesUserNo);
				// 4) 参加グループ一覧取得処理
				// グループ一覧取得
				groupListBeanList = groupListModel.getGroupList(sesUserNo);
				// グループメッセージ取得
				groupListBeanList = groupListModel.getGroupLatestMessage(groupListBeanList, sesUserNo);
			} catch (Exception e) {
				// 諸々のエラーはここに来る
				e.printStackTrace();
				// エラーはいてるのでuserListBeanList初期化してエラー情報入れる
				userListBeanList.clear();
				// エラー情報入れたbeanだけセット
				UserListBean bean = new UserListBean();
				bean.setErrorFlag(1);
				userListBeanList.add(bean);
			}
			// 途中でエラーはいている場合
			if (userListBeanList.isEmpty() || userListBeanList.get(0).getErrorFlag() == 1 || groupListBeanList.isEmpty()
					|| groupListBeanList.get(0).getErrorFlag() == 1) {
				// エラーメッセージ送りつつ行き先をエラーページに
				direction = "/errorPage";
				req.setAttribute("errorMsg", "DB接続に失敗しました");
			} else {
				// リクエストに送る
				req.setAttribute("userbean", userListBeanList);
				req.setAttribute("groupbean", groupListBeanList);
				// 初期表示用のパラメータ変更
				if (session.getAttribute("from") != null) {
					// セッションに入れたパラメータ削除
					session.removeAttribute("from");
					// グループ作成ページから来ていると判断
					req.setAttribute("fromGM", "GMから来た");
				}

			}
		}
		req.getRequestDispatcher(direction).forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		// postからpostにくる
		// リロード防止用にgetへリダイレクトで移動
		res.sendRedirect("/chat/main");


	}

}