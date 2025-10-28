package servlet;

/**
 * 入力データの妥当性検査を実行するクラス
 */
public class Validator {

	/**
	 * 必須入力検査
	 * @param target 検査対象文字列
	 * @return 検査対象文字列がnullまたは空文字列でなければtrue、それ以外はfalse
	 */
	public static boolean isRequired(String target) {
		return !(target == null || target.isEmpty());
	}

	/**
	 * 数値検査
	 * @param target 検査対象文字列
	 * @return 検査対象文字列が数字のみで構成されている場合はtrue、それ以外はfalse
	 */
	public static boolean isNumeric(String target) {
		try {
			Integer.parseInt(target);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}

	/**
	 * 上限範囲検査
	 * @param target 検査対象整数
	 * @param limit  上限値
	 * @return 検査対象整数が上限値以下である場合はtrue、それ以外はfalse
	 */
	public static boolean isLower(int target, int limit) {
		return (target <= limit);
	}

	/**
	 * 下限範囲検査
	 * @param target 検査対象整数
	 * @param limit  下限値
	 * @return 検査対象整数が下限値以上である場合はtrue、それ以外はfalse
	 */
	public static boolean isUpper(int target, int limit) {
		return (target >= limit);
	}

}
