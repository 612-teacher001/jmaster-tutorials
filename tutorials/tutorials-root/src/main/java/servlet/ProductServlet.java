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
@WebServlet({"/ProductServlet", "/ProductServlet/*"})
public class ProductServlet extends HttpServlet {
	
	/**
	 * シリアルバージョンUID：「保存したときのクラスと、今のクラスが同じ構造かどうか」をチェックするためのID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * クラス定数
	 */
	// 遷移先URL
	private static final String URL_PRODUCT_LIST = "/ProducctServlet/list";
	private static final String JSP_PRODUCT_LIST = "/WEB-INF/views/product/list.jsp";
	// actionキー定数
	private static final String ACTION_LIST = "/list";
	
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
	 * GET送信の場合に呼び出される
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 遷移先URLの初期化
		String nextURL = "/";
		
		try (ProductDAO dao = new ProductDAO()) {
			// リクエストパラメータのactionキーを取得
			String action = request.getParameter("action");
			
			// actionキーによる処理の分岐
			if (isNullOrEmpty(action) || action.equals(ACTION_LIST)) {
				nextURL = displayProductList(request, dao);
			} else {
				// 登録・更新・削除がここに追記されていく
			}
			
			// 画面遷移実行オブジェクトを取得
			RequestDispatcher dispatcher = request.getRequestDispatcher(nextURL);
			// 画面遷移
			dispatcher.forward(request, response);
			
		} catch (DAOException | NumberFormatException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示
			e.printStackTrace();
			// あらためてServletExceptionをスロー
			throw new ServletException(e.getMessage(), e);
		}
	}

	/**
	 * POST送信での場合に呼び出される
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * 商品一覧を表示する
	 * @param  request HttpServletRequestオブジェクト
	 * @param  dao     ProductDAOオブジェクト（CRUD操作実行オブジェクト）
	 * @return nextURL 遷移先URL
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	private String displayProductList(HttpServletRequest request, ProductDAO dao) throws DAOException {
		// リクエストパラメータを取得
		String categoryIdString = request.getParameter("categoryId");
		String sortOrder = request.getParameter("sortOrder");
		String keyword = request.getParameter("keyword");
		String maxPriceString = request.getParameter("maxPrice");
		
		List<Product> productList = null;
		if (isNullOrEmpty(categoryIdString)) {
			
			// リクエストパラメータの送信状況
			boolean hasSortOrder = !isNullOrEmpty(sortOrder);
			boolean hasKeyword = !isNullOrEmpty(keyword);
			boolean hasMaxPrice = !isNullOrEmpty(maxPriceString);
			
			if (!hasMaxPrice) {
				if (!hasSortOrder && !hasKeyword) {
					productList = dao.findAll();
				}
				if ( hasSortOrder && !hasKeyword) {
					productList = dao.findAllOrderByPrice(sortOrder);
				}
				if (!hasSortOrder &&  hasKeyword) {
					productList = dao.findByNameLikeKeyword(keyword);
				}
				if ( hasSortOrder &&  hasKeyword) {
					productList = dao.findByNameLikeKeywordOrderByPrice(sortOrder, keyword);
				}
			} else {
				// データ型変換
				int maxPrice = Integer.parseInt(maxPriceString);
				if (!hasSortOrder && !hasKeyword &&  hasMaxPrice) {
					productList = dao.findByPriceLessThanEqual(maxPrice);
				}
				if ( hasSortOrder && !hasKeyword &&  hasMaxPrice) {
					productList = dao.findByPriceLessThanEqualOrderByPrice(sortOrder, maxPrice);
				}
				if (!hasSortOrder &&  hasKeyword &&  hasMaxPrice) {
					productList = dao.findByNameLikeKeywordAndPriceLessThanEqual(keyword, maxPrice);
				}
				if ( hasSortOrder &&  hasKeyword &&  hasMaxPrice) {
					productList = dao.findByNameLikeKeywordAndPriceLessThanEqualOrderByPrice(sortOrder, keyword, maxPrice);
				}
			}
		} else {
			int categoryId = Integer.parseInt(categoryIdString);
			productList = dao.findByCategoryId(categoryId);
		}
		
		// 取得した商品リストをリクエストスコープに登録
		request.setAttribute("productList", productList);
		// 検索条件をリクエストスコープに登録：検索条件の再現
		request.setAttribute("categoryId", categoryIdString);
		request.setAttribute("sort", sortOrder);
		request.setAttribute("keyword", keyword);
		request.setAttribute("maxPrice", maxPriceString);
		
		// 遷移先URLの設定
		return JSP_PRODUCT_LIST;
		
	}
	
	/**
	 * 引数の文字列がnullまたは空文字列であるかどうかを判定する
	 * @param  target 判定対象文字列
	 * @return nullまたは空文字列である場合はtrue、それ以外はfalse
	 */
	private boolean isNullOrEmpty(String target) {
		return (target == null || target.isEmpty());
	}
	
}
