package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Category;
import bean.Product;
import dao.CategoryDAO;
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

	
	@Override
	public void init() throws ServletException {
		// スーパークラスのinit()メソッドによる初期化
		super.init();
		// カテゴリリンク用のカテゴリリストを取得のためのCategoryDAOをインスタンス化
		try (CategoryDAO dao = new CategoryDAO();) {
			// カテゴリリンク用のカテゴリリストをアプリケーションスコープから取得
			@SuppressWarnings("unchecked")
			List<Category> categoryList = (List<Category>) getServletContext().getAttribute("categories");
			// カテゴリリストがアプリケーションスコープに登録されているか判定
			if (categoryList != null) {
				// 登録されている場合は終了
				return;
			}
			// 登録されていない場合はカテゴリリンクをアプリケーションスコープに登録
			categoryList = dao.findAll();
			getServletContext().setAttribute("categories", categoryList);
			
		} catch (DAOException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示
			e.printStackTrace();
			// あらためてServletExceptionをスロー
			throw new ServletException(e.getMessage(), e);
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try (ProductDAO dao = new ProductDAO()) {
			// すべての商品リストを取得
			List<Product> productList = dao.findAll();
			// 取得した商品リストをリクエストスコープに登録
			request.setAttribute("products", productList);
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
