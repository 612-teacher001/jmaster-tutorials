package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Product;
import dao.common.BaseDAO;
import dao.common.DAOException;

/**
 * productsテーブルにアクセスするDAOクラス
 */
public class ProductDAO extends BaseDAO {

	/**
	 * クラス定数：SQL文字列定数群
	 */
	private static final String SQL_FIND_ALL = "SELECT * FROM products ORDER BY id";
	private static final String SQL_FIND_BY_CATEGORY_ID = "SELECT * FROM products WHERE category_id = ? ORDER By id";
	private static final String SQL_FIND_ALL_ORDER_BY_PRICE_ASC = "SELECT * FROM products ORDER BY price ASC";
	private static final String SQL_FIND_ALL_ORDER_BY_PRICE_DESC = "SELECT * FROM products ORDER BY price DESC";
	private static final String SQL_FIND_NAME_LIKE_KEYWORD = "SELECT * FROM products WHERE name LIKE ? ORDER BY id";
	private static final String SQL_FIND_BY_NAME_LIKE_KEYWORD_ORDER_BY_PRICE_ASC = "SELECT * FROM products WHERE name LIKE ? ORDER BY price ASC";
	private static final String SQL_FIND_BY_NAME_LIKE_KEYWORD_ORDER_BY_PRICE_DESC = "SELECT * FROM products WHERE name LIKE ? ORDER BY price DESC";
	
	/**
	 * 引数なしコンストラクタ
	 * @throws DAOException データベース接続オブジェクトの処理でエラーが発生した場合
	 */
	public ProductDAO() throws DAOException {
		super();
	}

	/**
	 * productsテーブルからすべての商品を取得する
	 * @return 商品IDの昇順に並べ替えられた商品リスト
	 * @throws DAOException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	public List<Product> findAll() throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_ALL);
			 ResultSet rs = pstmt.executeQuery();) {
			List<Product> list = new ArrayList<>();
			while (rs.next()) {
				int id = rs.getInt("id");
				int categoryId =  rs.getInt("category_id");
				String name = rs.getString("name");
				int price = rs.getInt("price");
				int quantity = rs.getInt("quantity");
				list.add(new Product(id, categoryId, name, price, quantity));
			}
			return list;
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

	/**
	 * 指定されたカテゴリIDに合致する商品をproductsテーブルから取得する
	 * @param categoryId 検索するカテゴリID
	 * @return 商品IDで昇順に並べ替えられた商品リスト
	 * @throws DAOException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	public List<Product> findByCategoryId(int categoryId) throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_BY_CATEGORY_ID);) {
			// パラメータバインディング
			pstmt.setInt(1, categoryId);
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

	/**
	 * 価格の並び替え順を指定してすべての商品をproductsテーブルから取得する。
	 * @param sortOrder 並び順（"asc" または "desc"）、nullまたは"asc"の場合は昇順
	 * @return 価格順に並べ替えられた商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findAllOrderByPrice(String sortOrder) throws DAOException {
		// 引数によって実行するSQLを分岐
		String sql = this.createSqlOrderByPrice(sortOrder);
		// SQLの実行と結果セットの取得
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql);
			 ResultSet rs= pstmt.executeQuery();) {
			List<Product> list = this.convertToProductList(rs);
			return list;
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}
	
	/**
	 * キーワードを含んだ商品名の商品を取得する
	 * @param keyword キーワード
	 * @return キーワードを含んだ商品名の商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findByNameLikeKeyword(String keyword) throws DAOException {
		try (PreparedStatement pstmt = this.conn.prepareStatement(SQL_FIND_NAME_LIKE_KEYWORD);) {
			// パラメータバインディング
			pstmt.setString(1, "%" + keyword + "%");
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}
	
	/**
	 * キーワードを含んだ商品名の商品を指定した価格の並び順で取得する
	 * @param sortOrder 並び替え順序
	 * @param keyword キーワード
	 * @return 指定した価格の並び順でキーワードを含んだ商品名の商品リスト
	 * @throws DAOException データベース処理中にエラーが発生した場合
	 */
	public List<Product> findByNameLikeKeywordOrderByPrice(String sortOrder, String keyword) throws DAOException {
		// 引数によって実行するSQLを分岐
		String sql = this.createSqlByKeywordOrderByPrice(sortOrder);
		try (PreparedStatement pstmt = this.conn.prepareStatement(sql);) {
			//パラメータバインディング
			pstmt.setString(1, "%" + keyword + "%");
			try (ResultSet rs = pstmt.executeQuery();) {
				List<Product> list = this.convertToProductList(rs);
				return list;
			}
		} catch (SQLException e) {
			// 例外が発生した場合：スタックトレース（必要最低限のエラー情報）を表示してDAOExceptionをスロー
			e.printStackTrace();
			throw new DAOException("レコードを取得に失敗しました。", e);
		}
	}

	/**
	 * 指定された価格の並び替え順ですべての商品をproductsテーブルから取得するSQLを取得する
	 * @param orderBy 昇順の場合またはnullの場合は「asc」、それ以外は「desc」
	 * @return 価格で並べ替えた商品リストを取得するSQL
	 */
	private String createSqlOrderByPrice(String orderBy) {
		if (orderBy == null || orderBy.equals("asc")) {
			return SQL_FIND_ALL_ORDER_BY_PRICE_ASC;
		}
		return SQL_FIND_ALL_ORDER_BY_PRICE_DESC;
	}
	
	/**
	 * 指定された価格の並び替え順ですべての商品をproductsテーブルからキーワード検索するSQLを取得する
	 * @param orderBy 昇順の場合またはnullの場合は「asc」、それ以外は「desc」
	 * @return 価格で並べ替えた商品リストを取得するSQL
	 */
	private String createSqlByKeywordOrderByPrice(String sortOrder) {
		if (sortOrder == null || sortOrder.equals("asc")) {
			return SQL_FIND_BY_NAME_LIKE_KEYWORD_ORDER_BY_PRICE_ASC;
		}
		return SQL_FIND_BY_NAME_LIKE_KEYWORD_ORDER_BY_PRICE_DESC;
	}

	/**
	 * 結果セットから商品リストに変換する
	 * @param rs 結果セット
	 * @return 商品リスト
	 * @throws SQLException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	private List<Product> convertToProductList(ResultSet rs) throws SQLException {
		List<Product> list = new ArrayList<>();
		while (rs.next()) {
			list.add(this.convertToProduct(rs));
		}
		return list;
	}
	
	/**
	 * 結果セットの現在行から商品インスタンスに変換する
	 * @param rs 結果セットの現在行
	 * @return 商品インスタンス
	 * @throws SQLException データベース接続関連オブジェクトの処理でエラーが発生した場合
	 */
	private Product convertToProduct(ResultSet rs) throws SQLException {
		int id = rs.getInt("id");
		int categoryId =  rs.getInt("category_id");
		String name = rs.getString("name");
		int price = rs.getInt("price");
		int quantity = rs.getInt("quantity");
		return new Product(id, categoryId, name, price, quantity);
	}

}
