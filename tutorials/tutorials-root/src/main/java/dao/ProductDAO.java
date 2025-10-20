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

}
