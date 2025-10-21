package dao;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import bean.Category;

class CategoryDaoTest {

	/** テスト対象クラス：system under test */
	CategoryDAO sut;
	
	@BeforeEach
	void setUp() throws Exception {
		sut = new CategoryDAO();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	@DisplayName("CategoryDAO#findAll()メソッドのテストクラス")
	class FindAllTest {
		@ParameterizedTest
		@MethodSource("DataProvider")
		@DisplayName("Test01: すべてのカテゴリを取得できる")
		void testFindAll(List<Category> expectedList) throws Exception {
			// execute
			List<Category> actualList = sut.findAll();
			// verify
			Category actual = null;
			Category expected = null;
			for (int i = 0; i < expectedList.size(); ++i) {
				actual = actualList.get(i);
				expected = expectedList.get(i);
				assertEquals(expected.toCompare(), actual.toCompare());
			}
		}
		
		/**
		 * testFIndAll()用のテストパラメータを提供する
		 * @return テストパラメータ（期待されるカテゴリリスト）
		 */
		static Stream<Arguments> DataProvider() {
			// setup
			List<Category> expected = new ArrayList<>();
			expected.add(new Category(1, "本"));
			expected.add(new Category(2, "DVD"));
			expected.add(new Category(3, "ゲーム"));
			// テストパラメータの返却
			return Stream.of(Arguments.of(expected));
		}
	}

}
