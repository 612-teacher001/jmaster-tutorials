package common;

public class LogicUtils {

	public static String determineDaoMethod(String sortOrder, String keyword, String maxPrice) {
		// パラメータの存在判定
		boolean hasSortOrder = !isNullOrEmpty(sortOrder);
		boolean hasKeyword = !isNullOrEmpty(keyword);
		boolean hasMaxPrice = !isNullOrEmpty(maxPrice);
		
		if (!hasSortOrder && !hasKeyword && !hasMaxPrice) {
			return "findAll";
		}
		if (!hasSortOrder && !hasKeyword &&  hasMaxPrice) {
			return "findByPriceLessThanEqual";
		}
		if (!hasSortOrder &&  hasKeyword && !hasMaxPrice) {
			return "findByNameLikeKeyword";
		}
		if ( hasSortOrder && !hasKeyword && !hasMaxPrice) {
			return "findAllOrderByPriceDesc";
		}
		if ( hasSortOrder &&  hasKeyword && !hasMaxPrice) {
			return "findByNameLikeKeywordOrderByPriceDesc";
		}
		if (!hasSortOrder &&  hasKeyword &&  hasMaxPrice) {
			return"findByNameLikeKeywordAndPriceLessThanEqual";
		}
		if ( hasSortOrder && !hasKeyword &&  hasMaxPrice) {
			return "findByPriceLessThanEqualOrderByPriceAsc";
		}
		if ( hasSortOrder &&  hasKeyword &&  hasMaxPrice) {
			return "findByNameLikeKeywordAndPriceLessThanEqualOrderByPriceAsc";
		}
		
		return "undefined";
	}

	private static boolean isNullOrEmpty(String target) {
		return (target == null || target.isEmpty());
	}

}
