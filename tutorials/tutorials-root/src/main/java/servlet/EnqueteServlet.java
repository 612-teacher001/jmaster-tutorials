package servlet;

import java.io.IOException;
import java.io.PrintWriter;

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
		message += "このページはアンケートのページです。<br />";
		message += "このチュートリアルでおもな入力部品の使い方を覚えましょう。";
		
		// HttpServletResponseの文字コード設定
		response.setContentType("text/html; charset=utf-8");
		// ブラウザへの出力オブジェクトを取得とブラウザへの出力
		try (PrintWriter out = response.getWriter();) {
			out.println("<html lang=\"ja\">");
			out.println("<head>");	
			out.println("<title>アンケート</title>");
			out.println("<link rel=\"stylesheet\" href=\"" + request.getContextPath() + "/static/css/style.css\">");
			out.println("</head>");
			out.println("<body>");
			out.println("<h1>アンケート</h1>");
			out.println("<p>" + message + "</p>");
			out.println("</body>");
			out.println("</html>");
		}
		
	}

}
