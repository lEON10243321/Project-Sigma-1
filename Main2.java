package test2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

2class Main {
    private static final Map<Character, Integer> ROMAN_NUMERALS = new HashMap<>();

    static {
        ROMAN_NUMERALS.put('I', 1);
        ROMAN_NUMERALS.put('V', 5);
        ROMAN_NUMERALS.put('X', 10);
        ROMAN_NUMERALS.put('L', 50);
        ROMAN_NUMERALS.put('C', 100);
        ROMAN_NUMERALS.put('D', 500);
        ROMAN_NUMERALS.put('M', 1000);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение (например, 2 + 3):");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static String calc(String input) {
        String[] tokens = input.split(" ");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Некорректный формат выражения");
        }

        try {
            int num1 = parseNumber(tokens[0]);
            int num2 = parseNumber(tokens[2]);
            char operator = tokens[1].charAt(0);

            int result;
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        throw new IllegalArgumentException("Ошибка: деление на ноль");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая арифметическая операция");
            }

            return isRoman(tokens[0]) ? toRoman(result) : String.valueOf(result);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка: некорректные числа в выражении");
        }
    }

    private static int parseNumber(String str) {
        if (isRoman(str)) {
            return romanToArabic(str);
        } else {
            int num = Integer.parseInt(str);
            if (num < 1 || num > 10) {
                throw new IllegalArgumentException("Число должно быть от 1 до 10 включительно");
            }
            return num;
        }
    }

    private static boolean isRoman(String str) {
        return str.matches("[IVXLCDM]+");
    }

    private static int romanToArabic(String roman) {
        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int curValue = ROMAN_NUMERALS.get(roman.charAt(i));

            if (curValue < prevValue) {
                result -= curValue;
            } else {
                result += curValue;
            }

            prevValue = curValue;
        }

        if (result < 1 || result > 10) {
            throw new IllegalArgumentException("Римское число должно быть от I до X включительно");
        }

        return result;
    }

    private static String toRoman(int num) {
        if (num <= 0 || num > 10) {
            throw new IllegalArgumentException("Римское число должно быть от I до X включительно");
        }

        StringBuilder result = new StringBuilder();
        for (Map.Entry<Character, Integer> entry : ROMAN_NUMERALS.entrySet()) {
            char romanChar = entry.getKey();
            int arabicValue = entry.getValue();

            while (num >= arabicValue) {
                result.append(romanChar);
                num -= arabicValue;
            }
        }

        return result.toString();
    }
}