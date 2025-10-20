package dao;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import bean.Product;

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
