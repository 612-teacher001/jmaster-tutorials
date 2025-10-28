package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PageNavigator {
	
	/**
	 * 指定された遷移先URLにフォワードする
	 * @param request  HttpServletRequestオブジェクト
	 * @param response HttpServletResponseオブジェクト
	 * @param nextURL  遷移先URL
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void forward(HttpServletRequest request, HttpServletResponse response, String nextURL) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher(nextURL);
		dispatcher.forward(request, response);
	}
	
	/**
	 * 指定された遷移先URLにリダイレクトする
	 * @param response HttpServletResponseオブジェクト
	 * @param nextURL  遷移先URL
	 * @throws IOException
	 */
	public static void redirect(HttpServletResponse response, String nextURL) throws IOException {
		response.sendRedirect(nextURL);
	}
	
}
