package servlet;

/**
 * 05_他ユーザプロフィール確認機能
 * @author hanawa-tomonori
 */


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.MyPageBean;
import model.MyPageModel;

public class ShowProfileServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		// -------------------------------------------------------------
		// 初期化
		MyPageBean bean = new MyPageBean();
		MyPageModel model = new MyPageModel();
		String direction = "/WEB-INF/jsp/showProfile.jsp";
		String errorMsg = "";


		if(req.getParameter("otherUserNo").equals("")) {
			errorMsg = "ユーザーを取得できません";
			direction = "/errorPage";
			req.setAttribute("errorMsg", errorMsg);
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}


		// userNoを取得し、beanへセット
		bean.setUserNo(req.getParameter("otherUserNo"));
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// ユーザ情報確認処理
		// ユーザ情報が確認できなければ、エラーページへ遷移
		// Sessionの取得
		HttpSession session = req.getSession();

		// Sessionにユーザ情報がなければ、エラーページへ遷移
        if (session==null || session.getAttribute("userId")==null) {
            // セッション情報なし
            // 行き先をエラーページに
            direction = "/errorPage";
            req.setAttribute("errorMsg", "セッション情報が無効です");
            req.getRequestDispatcher(direction).forward(req, res);
            return;
        }
		// -------------------------------------------------------------

		// -------------------------------------------------------------
		// SQL実行
		try {
			bean = model.output(bean);
		} catch (Exception e) {
			errorMsg = "DB接続に失敗しました ";
			req.setAttribute("errorMsg", errorMsg);
			direction = "/errorPage";
			req.getRequestDispatcher(direction).forward(req, res);
			return;
		}
		// --------------------------------------------------------------

		// --------------------------------------------------------------
		// 他ユーザ情報が取得できなければ、エラーページへ遷移
		if (bean.getErrorMessage()!=null) {
			req.setAttribute("errorMsg", bean.getErrorMessage());
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, res);
			return;
		} else {
			req.setAttribute("showMyPageText", bean.getMyPageText());
			req.setAttribute("showName", bean.getUserName());
		}
		// --------------------------------------------------------------

		// directionへ遷移
		req.getRequestDispatcher(direction).forward(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

		req.getRequestDispatcher("/main").forward(req, res);

	}
}
