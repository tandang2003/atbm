package model.common;

import java.util.*;

public class Alphabet {

    //    public static Map<Integer, String> VIETNAMESE_CHAR_LOWER_MAP = getVietnameseCharLower();

    private static String[] ENGLISH_CHAR = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };
    private static String[] VIETNAMESE_CHAR_LOWER = {
            "a", "à", "ả", "ã", "á", "ạ",
            "â", "ầ", "ẩ", "ẫ", "ấ", "ậ",
            "ä", "b", "c", "d", "đ", "e", "è", "ẻ", "ẽ", "é", "ẹ",
            "ê", "ề", "ể", "ễ", "ế", "ệ",
            "f", "g", "h", "i", "ì", "ỉ", "ĩ", "í", "ị",
            "j", "k", "l", "m", "n", "o", "ò", "ỏ", "õ", "ó", "ọ",
            "ô", "ồ", "ổ", "ỗ", "ố", "ộ",
            "ơ", "ờ", "ở", "ỡ", "ớ", "ợ",
            "p", "q", "r", "s", "t", "u", "ù", "ủ", "ũ", "ú", "ụ",
            "ư", "ừ", "ử", "ữ", "ứ", "ự",
            "v", "w", "x", "y", "ỳ", "ỷ", "ỹ", "ý", "ỵ", "z"
    };

    private static String[] NUMBER_CHAR = {
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"
    };
    private static String[] VIETNAMESE_CHAR = {
            "A", "À", "Á", "Ả", "Ã", "Ạ", "Ă", "Ằ", "Ắ", "Ẳ", "Ẵ", "Ặ", "Â", "Ầ", "Ấ", "Ẩ", "Ẫ", "Ậ",
            "B", "C", "D", "Đ", "E", "È", "É", "Ẻ", "Ẽ", "Ẹ", "Ê", "Ề", "Ế", "Ể", "Ễ", "Ệ",
            "G", "H", "I", "Ì", "Í", "Ỉ", "Ĩ", "Ị", "K", "L", "M", "N",
            "O", "Ò", "Ó", "Ỏ", "Õ", "Ọ", "Ô", "Ồ", "Ố", "Ổ", "Ỗ", "Ộ", "Ơ", "Ờ", "Ớ", "Ở", "Ỡ", "Ợ",
            "P", "Q", "R", "S", "T",
            "U", "Ù", "Ú", "Ủ", "Ũ", "Ụ", "Ư", "Ừ", "Ứ", "Ử", "Ữ", "Ự",
            "V", "X", "Y", "Ỳ", "Ý", "Ỷ", "Ỹ", "Ỵ"
    };

    public static List<String> ENGLISH_CHAR_SET = englishCharSet();
    public static List<String> VIETNAMESE_CHAR_SET = vietnameseCharSet();
    public static List<String> NUMBER_CHAR_SET = numberCharSet();

    private static List<String> englishCharSet() {
        List<String> r = new ArrayList<>();
        Collections.addAll(r, ENGLISH_CHAR);
        return r;
    }

    private static List<String> vietnameseCharSet() {
        List<String> r = new ArrayList<>();
        Collections.addAll(r, VIETNAMESE_CHAR);
        return r;
    }

    private static List<String> numberCharSet() {
        List<String> r = new ArrayList<>();
        Collections.addAll(r, NUMBER_CHAR);
        return r;
    }

    public static Map<Integer, String> getVietnameseCharLower() {
        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < VIETNAMESE_CHAR_LOWER.length; i++) {
            map.put(i, String.valueOf(VIETNAMESE_CHAR_LOWER[i]));
        }
        return map;
    }

}
