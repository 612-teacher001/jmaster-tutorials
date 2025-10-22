package dao;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import bean.Product;
import dao.helper.Parameters;

class ProductDaoTest {

	/** テスト対象クラス：system under test */
	private ProductDAO sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new ProductDAO();
	}

	@AfterEach
	void tearDown() throws Exception {
	}
	
	@Nested
	@DisplayName("ProductDAO#findByNameLikeKeywordOrderByPrice(String, String)メソッドのテストクラス")
	class FindByNameLikeKeywordOrderByPriceTest {
		@ParameterizedTest
		@MethodSource("DataProvider")
		@DisplayName("Test-01:【正常系】キーワードを含んだ商品名の商品を指定した価格の並び順で取得できる")
		void testFindByNameLikeKeywordOrderByPrice(Parameters target, List<Product> expectedList) throws Exception {
			// execute
			List<Product> actualList = sut.findByNameLikeKeywordOrderByPrice(target.getSortOrder(), target.getKeyword());
			// verify
			Product actual = null;
			Product expected = null;
			for (int i = 0; i < expectedList.size(); ++i) {
				actual = actualList.get(i);
				expected = expectedList.get(i);
				assertEquals(expected.toCompare(), actual.toCompare());
			}
		}
		
		static Stream<Arguments> DataProvider() {
			// setup
			List<Parameters> target = new ArrayList<>();
			List<Product> expectedList = new ArrayList<>();
			List<List<Product>> expected = new ArrayList<>();
			
			// Test-01: キーワード「a」で昇順（sortOrder=asc）で検索できる
			target.add(new Parameters("asc", "a"));
			expectedList.add(new Product(2, "The Racer", 1000, 19));
			expectedList.add(new Product(2, "Space Wars 3", 1800, 33));
			expectedList.add(new Product(3, "Play the BasketBall", 2200, 2));
			expectedList.add(new Product(1, "Javaの基本", 2500, 22));
			expectedList.add(new Product(3, "Invader Fighter", 3400, 9));
			expected.add(expectedList);
			// Test-02: キーワード「a」で昇順（sortOrder=asc）で検索できる
			target.add(new Parameters("desc", "の"));
			expectedList = new ArrayList<>();
			expectedList.add(new Product(1, "Javaの基本", 2500, 22));
			expectedList.add(new Product(2, "懐かしのアニメシリーズ", 2000, 15));
			expected.add(expectedList);
			
			// テストパラメータを返却
			return Stream.of(
					  Arguments.of(target.get(0), expected.get(0))
					, Arguments.of(target.get(1), expected.get(1))
					);
		}
	}
	
	@Nested
	@DisplayName("ProducctDAO#findNameByKeyword(String)メソッドのテストクラス")
	class FindNameByKeywordTest {
		@ParameterizedTest
		@MethodSource("DataProvider")
		@DisplayName("Test-01:【正常系】キーワードが含まれる商品名のあいまい検索ができる")
		void testFindNameByKeyword(String target, List<Product> expectedList) throws Exception {
			//execute
			List<Product> actualList = sut.findByNameLikeKeyword(target);
			// verify
			Product actual = null;
			Product expected = null;
			for (int i = 0; i < expectedList.size(); ++i) {
				actual = actualList.get(i);
				expected = expectedList.get(i);
				assertEquals(expected.toCompare(), actual.toCompare());
			}
		}
		
		static Stream<Arguments> DataProvider() {
			// setup
			List<String> target = new ArrayList<>();
			List<Product> expectedList = new ArrayList<>();
			List<List<Product>> expected = new ArrayList<>();
			
			// Test-01: キーワード「a」であいまい検索する
			target.add("a");
			expectedList.add(new Product(1, "Javaの基本", 2500, 22));
			expectedList.add(new Product(2, "The Racer", 1000, 19));
			expectedList.add(new Product(2, "Space Wars 3", 1800, 33));
			expectedList.add(new Product(3, "Invader Fighter", 3400, 9));
			expectedList.add(new Product(3, "Play the BasketBall", 2200, 2));
			expected.add(expectedList);
			
			// Test-02: キーワード「の」であいまい検索する
			target.add("の");
			expectedList = new ArrayList<>();
			expectedList.add(new Product(1, "Javaの基本", 2500, 22));
			expectedList.add(new Product(2, "懐かしのアニメシリーズ", 2000, 15));
			expected.add(expectedList);
			
			// テストパラメータを返却
			int maxInndex = expected.size() - 1;
			return Stream.of(
					  Arguments.of(target.get(0), expected.get(0))
					, Arguments.of(target.get(maxInndex), expected.get(maxInndex))
					);
		}
	}

	@Nested
	@DisplayName("ProductDAO#findAllOrderByPrice(String)メソッドのテストクラス")
	class FindAllOrderByPriceTest {
		@ParameterizedTest
		@MethodSource("DataProvider")
		@DisplayName("Test-01:【正常系】価格の降順に並べ替える")
		void  testFindAllOrder(String target, List<Product> expectedList) throws Exception {
			//execute
			List<Product> actualList = sut.findAllOrderByPrice(target);
			// verify
			Product actual = null;
			Product expected = null;
			for (int i = 0; i < expectedList.size(); ++i) {
				actual = actualList.get(i);
				expected = expectedList.get(i);
				assertEquals(expected.toCompare(), actual.toCompare());
			}
		}
		
		static Stream<Arguments> DataProvider() {
			// setup
			List<String> target = new ArrayList<>();
			List<Product> expectedList = new ArrayList<>();
			List<List<Product>> expected = new ArrayList<>();
			
			// Test-01: 価格の昇順に並べ変える
			target.add("asc");
			expectedList.add(new Product(3, "パズルゲーム", 780, 6));
			expectedList.add(new Product(1, "MLB Fun", 980, 23));
			expectedList.add(new Product(2, "The Racer", 1000, 19));
			expectedList.add(new Product(1, "料理 BOOK!", 1200, 14));
			expectedList.add(new Product(2, "Space Wars 3", 1800, 33));
			expectedList.add(new Product(2, "懐かしのアニメシリーズ", 2000, 15));
			expectedList.add(new Product(3, "Play the BasketBall", 2200, 2));
			expectedList.add(new Product(1, "Javaの基本", 2500, 22));
			expectedList.add(new Product(3, "Invader Fighter", 3400, 9));
			expected.add(expectedList);
			
			// Test-02: 価格の降順に並べ替える
			target.add("desc");
			expectedList = new ArrayList<>();
			expectedList.add(new Product(3, "Invader Fighter", 3400, 9));
			expectedList.add(new Product(1, "Javaの基本", 2500, 22));
			expectedList.add(new Product(3, "Play the BasketBall", 2200, 2));
			expectedList.add(new Product(2, "懐かしのアニメシリーズ", 2000, 15));
			expectedList.add(new Product(2, "Space Wars 3", 1800, 33));
			expectedList.add(new Product(1, "料理 BOOK!", 1200, 14));
			expectedList.add(new Product(2, "The Racer", 1000, 19));
			expectedList.add(new Product(1, "MLB Fun", 980, 23));
			expectedList.add(new Product(3, "パズルゲーム", 780, 6));
			expected.add(expectedList);

			// テストパラメータを返却
			int maxIndex = expected.size() - 1;
			return Stream.of(
					  Arguments.of(target.get(0), expected.get(0))
					, Arguments.of(target.get(maxIndex), expected.get(maxIndex))
					);
			
		}
	}
	
	@Nested
	@DisplayName("ProductDAO#findByCategoryId(int)メソッドのテストクラス")
	class FindByCategoryIdTest {
		@ParameterizedTest
		@MethodSource("dataProvider")
		@DisplayName("Test-01:【正常系】登録されているカテゴリIDの商品を取得できる")
		void testFindByCategoryId_01(int targetId, List<Product> expectedList) throws Exception {
			// execute
			List<Product> actualList = sut.findByCategoryId(targetId);
			// verify
			Product actual = null;
			Product expected = null;
			for (int i = 0; i < expectedList.size(); ++i) {
				actual = actualList.get(i);
				expected = expectedList.get(i);
				assertEquals(expected.toCompare(), actual.toCompare());
			}
		}
		
		static Stream<Arguments> dataProvider() {
			// setup
			List<Integer> target = new ArrayList<>();
			List<Product> products = new ArrayList<>();
			List<List<Product>> expected = new ArrayList<>();
			// Test-01: 商品カテゴリ「1」の商品を取得できる
			target.add(1);
			products = new ArrayList<>();
			products.add(new Product(1, "Javaの基本", 2500, 22));
			products.add(new Product(1, "MLB Fun", 980, 23));
			products.add(new Product(1, "料理 BOOK!", 1200, 14));
			expected.add(products);
			// Test-02: 商品カテゴリ「3」の商品を取得できる
			target.add(3);
			products = new ArrayList<>();
			products.add(new Product(3, "パズルゲーム", 780, 6));
			products.add(new Product(3, "Invader Fighter", 3400, 9));
			products.add(new Product(3, "Play the BasketBall", 2200, 2));
			expected.add(products);
			// Test-03: 登録されていない商品カテゴリ「-1」の商品は存在しない
			target.add(1);
			products = new ArrayList<>();
			expected.add(products);
			// テストパラメータの返却
			return Stream.of(
					  Arguments.of(target.get(0), expected.get(0))
					, Arguments.of(target.get(1), expected.get(1))
					, Arguments.of(target.get(2), expected.get(2))
					);
		}
	}
	
	@Nested
	@DisplayName("ProductDAO#findAll()メソッドのテストクラス")
	class FindAllTest {
		@Test
		@DisplayName("Test-01:【正常系】すべてのレコードを取得できる")
		void testFindAll_01() throws Exception {
			// setup
			List<Product> expectedList = new ArrayList<Product>();
			expectedList.add(new Product(1, "Javaの基本", 2500, 22));
			expectedList.add(new Product(1, "MLB Fun", 980, 23));
			expectedList.add(new Product(1, "料理 BOOK!", 1200, 14));
			expectedList.add(new Product(2, "懐かしのアニメシリーズ", 2000, 15));
			expectedList.add(new Product(2, "The Racer", 1000, 19));
			expectedList.add(new Product(2, "Space Wars 3", 1800, 33));
			expectedList.add(new Product(3, "パズルゲーム", 780, 6));
			expectedList.add(new Product(3, "Invader Fighter", 3400, 9));
			expectedList.add(new Product(3, "Play the BasketBall", 2200, 2));
			// execute
			List<Product> actualList = sut.findAll();
			// verify
			Product actual = null;
			Product expected = null;
			for (int i = 0; i < expectedList.size(); ++i) {
				actual = actualList.get(i);
				expected = expectedList.get(i);
				assertEquals(expected.toCompare(), actual.toCompare());
			}
		}
	}

}
