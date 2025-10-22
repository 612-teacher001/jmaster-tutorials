package dao.helper;

public class Parameters {

	/**
	 * フィールド
	 */
	private String sortOrder; // 並べ替え順序
	private String keyword;   // キーワード
	private String maxPrice;  // 価格上限値
	
	/**
	 * コンストラクタ
	 * @param sortOrder 並べ替え順序：昇順は「asc」、降順は「desc」
	 * @param keyword   キーワード
	 */
	public Parameters(String sortOrder, String keyword) {
		this.sortOrder = sortOrder;
		this.keyword = keyword;
	}

	/**
	 * コンストラクタ
	 * @param sortOrder 並べ替え順序：昇順は「asc」、降順は「desc」
	 * @param keyword   キーワード
	 * @param maxPrice  価格上限値
	 */
	public Parameters(String sortOrder, String keyword, String maxPrice) {
		this(sortOrder, keyword);
		this.maxPrice = maxPrice;
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

	public int getMaxPrice() {
		int price = Integer.parseInt(maxPrice);
		return price;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

}
