package servlet;

import java.io.IOException;
import java.util.ArrayList;
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
	private static final String REDIRECT_PRODUCT_LIST = "/tutorials-root/ProductServlet/list";
	private static final String JSP_PRODUCT_DIR = "/WEB-INF/views/product"; 
	private static final String JSP_PRODUCT_LIST = JSP_PRODUCT_DIR + "/list.jsp";
	private static final String JSP_PRODUCT_ENTRY = JSP_PRODUCT_DIR + "/entry.jsp";
	private static final String JSP_PRODUCT_CONFIRM = JSP_PRODUCT_DIR + "/confirm.jsp";
	//パスインフォ定数
	private static final String OPERATION_LIST   = "/list";
	private static final String OPERATION_ADD = "/insert";
	// actionキー定数
	private static final String ACTION_ENTRY = "entry";
	private static final String ACTION_CONFIRM = "confirm";
	private static final Object ACTION_EXECUTE = "execute";

	
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
		
		// 遷移先URLの初期化：表示確認用リンクページURL（/inndex.htmlに遷移）
		String nextURL = "/";
		// アプリケーションスコープに登録
		getServletContext().setAttribute("operation", "商品管理機能");
		
		try (ProductDAO dao = new ProductDAO()) {
			// リクエストパラメータのactionキーを取得
			String action = request.getParameter("action");
			String pathInfo = request.getPathInfo();
			// actionキーによる処理の分岐
			if (isNullOrEmpty(pathInfo) || pathInfo.equals(OPERATION_LIST)) {
				// 遷移先URLを取得
				nextURL = displayProductList(request, dao);
				getServletContext().setAttribute("operation", "共通機能");
				// 画面遷移実行オブジェクトを取得
				RequestDispatcher dispatcher = request.getRequestDispatcher(nextURL);
				// 画面遷移
				dispatcher.forward(request, response);
			} else if (pathInfo.equals(OPERATION_ADD)) {
				// 遷移先URLを取得
				nextURL = addProduct(request, dao, action);
				// 遷移先URLによって遷移方法を分岐：リダイレクト化フォワードの分岐
				if (nextURL.equals(REDIRECT_PRODUCT_LIST)) {
					// リダイレクトの場合
					response.sendRedirect(nextURL);
				} else {
					// 画面遷移実行オブジェクトを取得
					RequestDispatcher dispatcher = request.getRequestDispatcher(nextURL);
					// 画面遷移
					dispatcher.forward(request, response);
				}
			} else {
				// 更新・削除がここに追記されていく
			}
					
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

		// アプリケーションスコープに登録
		getServletContext().setAttribute("operation", "共通機能");
		// リクエストスコープに登録
		request.setAttribute("title", "商品一覧");
		// 遷移先URLの設定
		return JSP_PRODUCT_LIST;
		
	}
	
	private String addProduct(HttpServletRequest request, ProductDAO dao, String action) throws DAOException {
		// 遷移先URLの初期化
		String nextURL = "";
		if (isNullOrEmpty(action) || action.equals(ACTION_ENTRY)) {
			// 登録処理初期画面表示：登録画面
			nextURL = JSP_PRODUCT_ENTRY;
		} else if (action.equals(ACTION_CONFIRM)) {
			// リクエストパラメータを取得
			String categoryIdString = request.getParameter("categoryId");
			String name = request.getParameter("name");
			String priceString = request.getParameter("price");
			String quantityString = request.getParameter("quantity");
			
			// 検索条件をリクエストスコープに登録：検索条件の再現
			request.setAttribute("categoryId", categoryIdString);
			request.setAttribute("name", name);
			request.setAttribute("price", priceString);
			request.setAttribute("quantity", quantityString);
			
			// リクエストパラメータの入力値チェック
			List<String> errorList = new ArrayList<>();
			// カテゴリIDの入力値チェック：必須入力チェック・数値チェック
			if (!Validator.isRequired(categoryIdString)) {
				errorList.add("カテゴリIDは必須です。");
			} else if (!Validator.isNumeric(categoryIdString)) {
				errorList.add("カテゴリIDは正の整数を選んでください。");
			}
			// 商品名の入力値チェック：必須入力チェック
			if (!Validator.isRequired(name)) {
				errorList.add("商品名は必須です。");
			}
			// 価格の入力値チェック：必須入力チェック・数値チェック
			if (!Validator.isRequired(priceString)) {
				errorList.add("価格は必須です。");
			} else if (!Validator.isNumeric(priceString)) {
				errorList.add("価格は正の整数を入力してください。");
			}
			// 数量の入力値チェック：必須入力チェック・数値チェック
			if (!Validator.isRequired(quantityString)) {
				errorList.add("数量は必須です。");
			} else if (!Validator.isNumeric(quantityString)) {
				errorList.add("数量は正の整数を入力してださい。");
			}
			
			// エラーメッセージの有無で遷移先を分岐
			if (errorList.size() > 0) {
				request.setAttribute("errorList", errorList);
				request.setAttribute("title", "商品登録");
				request.setAttribute("display", "hidden");
				nextURL = JSP_PRODUCT_ENTRY;
				return nextURL;
			}
			
			// 遷移先URLを返却
			nextURL = JSP_PRODUCT_CONFIRM;
		} else if (action.equals(ACTION_EXECUTE)) {
			// リクエストパラメータを取得
			String categoryIdString = request.getParameter("categoryId");
			String name = request.getParameter("name");
			String priceString = request.getParameter("price");
			String quantityString = request.getParameter("quantity");
			
			// リクエストパラメータをデータ型変換
			int categoryId = Integer.parseInt(categoryIdString);
			int price = Integer.parseInt(priceString);
			int quantity = Integer.parseInt(quantityString);
			
			// 登録対象商品をインスタンス化
			Product target = new Product(categoryId, name, price, quantity);
			
			// 商品を登録
			dao.save(target);
			
			// 商品一覧にリダイレクト：/tutorials-root/ProdductServlet/listを呼び出す
			return REDIRECT_PRODUCT_LIST;
		}
		// リクエストスコープに登録
		request.setAttribute("title", "商品登録");
		request.setAttribute("mode", action);
		request.setAttribute("display", "hidden");
		// 遷移先URLを返却
		return nextURL;
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
