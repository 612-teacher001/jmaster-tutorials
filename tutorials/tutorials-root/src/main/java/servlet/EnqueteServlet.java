package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BasicServlet
 */
@WebServlet("/EnqueteServlet")
public class EnqueteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 表示するメッセージを変数に代入
		String message = "";
		message += "このページはJSPによるアンケートのページです。<br />";
		message += "このチュートリアルでおもな入力部品の使い方を覚えましょう。";
		
		// リクエストスコープに表示メッセージを登録
		request.setAttribute("message", message);
		// 遷移先URLの設定：アンケートページ
		String nextPage = "/WEB-INF/views/enquete/entry.jsp";
		
		// 画面遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
		
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの文字コードを設定
		request.setCharacterEncoding("utf-8");
		
		// リクエストパラメータを取得
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String gender = request.getParameter("gender");
		String age = request.getParameter("age");
		String[] languages = request.getParameterValues("languages");
		String description = request.getParameter("description");
		
		// 表示するメッセージを変数に代入
		String message = "送信されたデータは以下の通りです：";
		
		// リクエストスコープにパラメータを登録
		request.setAttribute("name", name);
		request.setAttribute("email", email);
		request.setAttribute("gender", gender);
		request.setAttribute("age", age);
		request.setAttribute("languages", languages);
		request.setAttribute("description", description);
		// リクエストスコープにメッセージを登録
		request.setAttribute("message", message);
		
		// 遷移先URLの設定：アンケートページ
		String nextPage = "/WEB-INF/views/enquete/result.jsp";
		
		// 画面遷移
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextPage);
		dispatcher.forward(request, response);
	}

}
