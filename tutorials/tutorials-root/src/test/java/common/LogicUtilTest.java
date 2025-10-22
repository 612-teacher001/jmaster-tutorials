package common;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class LogicUtilTest {

	@Nested
	@DisplayName("LogicUtitls#determineDaoMethod(String, Sstring, String)メソッドのテストクラス")
	class DetermineDaoMethodTest {
		@ParameterizedTest
		@MethodSource("DataProvider")
		@DisplayName("Test: 引数に対応したメソッドを決定できる")
		void test(Parameters target, String expected) {
			// execute
			String actual = LogicUtils.determineDaoMethod(target.getSortOrder(), target.getKeyword(), target.getMaxPrice());
			// vetify
			assertThat(actual, is(expected));
		}
		
		static Stream<Arguments> DataProvider() {
			// setup
			List<Parameters> target = new ArrayList<>();
			List<String> expected = new ArrayList<>();
			
			// Test01: すべてのパラメータがnullの場合はfindAllが返される
			target.add(new Parameters(null, null, null));
			expected.add("findAll");
			// Test02: 範囲が設定されている場合はfindByPriceLessThanEqualが返される
			target.add(new Parameters(null, null, "0"));
			expected.add("findByPriceLessThanEqual");
			// Test-03: キーワードが設定されている場合はfindByNameLikeKeywordが返される
			target.add(new Parameters(null, "Perl", null));
			expected.add("findByNameLikeKeyword");
			// Test-04: 並べ替えが指定されている場合はfindAllOrderByPriceDescが返される
			target.add(new  Parameters("desc", null, null));
			expected.add("findAllOrderByPriceDesc");
			// Test-05: 並べ替えとキーワードが指定されている場合はfindByNameLikeKeywordOrderByPriceDescが返される
			target.add(new Parameters("desc", "PHP", ""));
			expected.add("findByNameLikeKeywordOrderByPriceDesc");
			// Test-06: キーワードと範囲が指定されている場合はfindByNameLikeKeywordAndPriceLessThanEqualが返される
			target.add(new Parameters(null, "Ruby", "300"));
			expected.add("findByNameLikeKeywordAndPriceLessThanEqual");
			// Test-07: 並べ替えと範囲が指定されている場合はfindByPriceLessThanEqualOrderByPriceAscが返される
			target.add(new Parameters("asc", null, "200"));
			expected.add("findByPriceLessThanEqualOrderByPriceAsc");
			// Test-08: すべてのパラメータが指定されている場合はfindByNameLikeKeywordAndPriceLessThanEqualOrderByPriceAscが返される
			target.add(new Parameters("asc", "Java", "100"));
			expected.add("findByNameLikeKeywordAndPriceLessThanEqualOrderByPriceAsc");
			
			// テストパラメータの返却
			int maxIndex = expected.size() - 1;
			return Stream.of(
					  Arguments.of(target.get(0), expected.get(0))
					, Arguments.of(target.get(1), expected.get(1))
					, Arguments.of(target.get(2), expected.get(2))
					, Arguments.of(target.get(3), expected.get(3))
					, Arguments.of(target.get(4), expected.get(4))
					, Arguments.of(target.get(5), expected.get(5))
					, Arguments.of(target.get(6), expected.get(6))
					, Arguments.of(target.get(maxIndex), expected.get(maxIndex))
					);
		}
	}
}

class Parameters {
	private String sortOrder;
	private String keyword;
	private String maxPrice;
	
	public Parameters(String sortOrder, String keyword, String maxPrice) {
		this.sortOrder = sortOrder;
		this.keyword = keyword;
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

	public String getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}

}
