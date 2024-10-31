package model.common;

import java.util.HashMap;
import java.util.Map;

public class Alphabet {
    public static char UPPER_A = 65;
    public static char UPPER_Z = 90;
    public static char LOWER_A = 97;
    public static char LOWER_Z = 122;
    public static char ENGLISH_ALPHABET = 26;
    public static char VIETNAMESE_ALPHABET = 29;
    public static Map<Integer, String> VIETNAMESE_CHAR_LOWER_MAP = getVietnameseCharLower();
    private static char[] vietnamese_char_lower = {
            'a', 'à', 'ả', 'ã', 'á', 'ạ',
            'â', 'ầ', 'ẩ', 'ẫ', 'ấ', 'ậ',
            'ä', 'b', 'c', 'd', 'đ', 'e', 'è', 'ẻ', 'ẽ', 'é', 'ẹ',
            'ê', 'ề', 'ể', 'ễ', 'ế', 'ệ',
            'f', 'g', 'h', 'i', 'ì', 'ỉ', 'ĩ', 'í', 'ị',
            'j', 'k', 'l', 'm', 'n', 'o', 'ò', 'ỏ', 'õ', 'ó', 'ọ',
            'ô', 'ồ', 'ổ', 'ỗ', 'ố', 'ộ',
            'ơ', 'ờ', 'ở', 'ỡ', 'ớ', 'ợ',
            'p', 'q', 'r', 's', 't', 'u', 'ù', 'ủ', 'ũ', 'ú', 'ụ',
            'ư', 'ừ', 'ử', 'ữ', 'ứ', 'ự',
            'v', 'w', 'x', 'y', 'ỳ', 'ỷ', 'ỹ', 'ý', 'ỵ', 'z'
    };

    public static Map<Integer, String> getVietnameseCharLower() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < vietnamese_char_lower.length; i++) {
            map.put(i, String.valueOf(vietnamese_char_lower[i]));
        }
        return map;
    }

    public static char[] VIETNAMESE_CHAR_UPPER = {
            'A', 'À', 'Ả', 'Ã', 'Á', 'Ạ',
            'Â', 'Ầ', 'Ẩ', 'Ẫ', 'Ấ', 'Ậ',
            'Ä', 'B', 'C', 'D', 'Đ', 'E', 'È', 'Ẻ', 'Ẽ', 'É', 'Ẹ',
            'Ê', 'Ề', 'Ể', 'Ễ', 'Ế', 'Ệ',
            'F', 'G', 'H', 'I', 'Ì', 'Ỉ', 'Ĩ', 'Í', 'Ị',
            'J', 'K', 'L', 'M', 'N', 'O', 'Ò', 'Ỏ', 'Õ', 'Ó', 'Ọ',
            'Ô', 'Ồ', 'Ổ', 'Ỗ', 'Ố', 'Ộ',
            'Ơ', 'Ờ', 'Ở', 'Ỡ', 'Ớ', 'Ợ',
            'P', 'Q', 'R', 'S', 'T', 'U', 'Ù', 'Ủ', 'Ũ', 'Ú', 'Ụ',
            'Ư', 'Ừ', 'Ử', 'Ữ', 'Ứ', 'Ự',
            'V', 'W', 'X', 'Y', 'Ỳ', 'Ỷ', 'Ỹ', 'Ý', 'Ỵ', 'Z'
    };
}
