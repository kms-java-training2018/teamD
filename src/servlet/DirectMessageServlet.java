package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.DirectMessageBean;
import model.DirectMessageModel;

public class DirectMessageServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		/*
		* 画面表示処理
		*/
		//メッセージ文字化け防止
		req.setCharacterEncoding("UTF-8");
		// bean用意
		DirectMessageBean bean = new DirectMessageBean();
		// モデル用意
		DirectMessageModel model = new DirectMessageModel();
		//(1)パラメーターチェック
		//(1)-1セッション確認
		//sessionスコープを使う下準備
		HttpSession session = req.getSession();

		String direction = "/WEB-INF/jsp/directMessage.jsp";
		//修正箇所　左側の式を消しました。----------------------------------------------
		if (session.getAttribute("userId") == null) {
			// セッション情報なし
			// 行き先をエラーページに
			direction = "/errorPage";
			req.setAttribute("errorMsg", "セッション情報が無効です");
		} else {

			//LoginServletでsessionスコープに入れた値が正しいか(入っているか)判断
			//まずはsessionスコープに入っている値を取得
			String userId = (String) session.getAttribute("userId");

			//mainPage.jspで指定されたuserNoというパラメータを受け取り、変数に格納(データの降り口)
			bean.setUserNo(Integer.parseInt(req.getParameter("userNo")));

			//(2)会話情報取得処理
			bean = model.dispDM(bean, userId);

			// 結果をセット
			req.setAttribute("bean", bean);
		}
		// フォワードで遷移
		req.getRequestDispatcher(direction).forward(req, res);

	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		/*
		* メッセージ送信処理
		*/
		//メッセージ文字化け防止
		req.setCharacterEncoding("UTF-8");
		//(1)パラメーターチェック
		//(1)-1セッション確認
		//sessionスコープを使う下準備
		HttpSession session = req.getSession();
		//LoginServletでsessionスコープに入れた値が正しいか(入っているか)判断
		//まずはsessionスコープに入っている値を取得
		String userId = (String) session.getAttribute("userId");

		// bean用意
		DirectMessageBean bean = new DirectMessageBean();
		// モデル用意
		DirectMessageModel model = new DirectMessageModel();
		String check = req.getParameter("check");

		//mainPage.jspで指定されたuserNoというパラメータを受け取り、変数に格納(データの降り口)
		bean.setUserNo(Integer.parseInt(req.getParameter("userNo")));

		if (check.equals("1")) {
			// メッセージ削除処理
			// 削除処理用のメッセージ番号取得
			int deleteMessageNo = (Integer.parseInt(req.getParameter("deleteMessageNo")));
			bean = model.deleteDirectMessage(bean, userId, deleteMessageNo);

		} else {
			// メッセージ登録処理
			//directMessage.jspで指定されたsendMessageというパラメータを受け取り、変数に格納(データの降り口)

			String sendMessage = req.getParameter("sendMessage");
			bean = model.setNewDirectMessage(bean, userId, sendMessage);
		}

		//(2)会話情報取得処理
		bean = model.dispDM(bean, userId);

		// 結果をセット
		req.setAttribute("bean", bean);
		// フォワードで遷移
		req.getRequestDispatcher("/WEB-INF/jsp/directMessage.jsp").forward(req, res);
	}

}
