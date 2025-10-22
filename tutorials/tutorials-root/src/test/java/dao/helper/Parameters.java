package dao.helper;

public class Parameters {

	/**
	 * フィールド
	 */
	private String sortOrder; // 並べ替え順序
	private String keyword;   // キーワード
	
	/**
	 * コンストラクタ
	 * @param sortOrder 並べ替え順序：昇順は「asc」、降順は「desc」
	 * @param keyword   キーワード
	 */
	public Parameters(String sortOrder, String keyword) {
		this.sortOrder = sortOrder;
		this.keyword = keyword;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
