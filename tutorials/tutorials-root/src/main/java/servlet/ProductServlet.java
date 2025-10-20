package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Product;
import dao.ProductDAO;
import dao.common.DAOException;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	
	/**
	 * シリアルバージョンUID：「保存したときのクラスと、今のクラスが同じ構造かどうか」をチェックするためのID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// finallyでクローズ処理をするための事前宣言
		ProductDAO dao = null;
		try {
			// ProductDAOをインスタンス化
			dao = new ProductDAO();
			// すべての商品リストを取得
			List<Product> list = dao.findAll();
			// 取得した商品リストをリクエストスコープに登録
			request.setAttribute("products", list);
			// 遷移先URLの設定
			String nextURL = "/WEB-INF/views/product/list.jsp";
			// 画面遷移実行オブジェクトを取得
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextURL);
			// 画面遷移
			dispatcher.forward(request, response);
		} catch (DAOException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示
			e.printStackTrace();
			// あらためてServletExceptionをスロー
			throw new ServletException(e.getMessage(), e);
		} finally {
			try {
				dao.close();
			} catch (DAOException e) {
				// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示：例外を上書きするリスクがあるのでスタックトレース表示だけ
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
