import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите арифметическое выражение (например, 2 + 3):");
        String input = scanner.nextLine();

        try {
            String result = calc(input);
            System.out.println("Результат: " + result);
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }

    static String calc(String input) {
        String[] tokens = input.split(" ");

        if (tokens.length != 3) {
            throw new IllegalArgumentException("Некорректный формат выражения");
        }

        try {
            String num1Str = tokens[0];
            String operator = tokens[1];
            String num2Str = tokens[2];

            int num1, num2;

            if (isRomanNumber(num1Str) && isRomanNumber(num2Str)) {
                num1 = RomanToArabic(num1Str);
                num2 = RomanToArabic(num2Str);
            } else if (isArabicNumber(num1Str) && isArabicNumber(num2Str)) {
                num1 = Integer.parseInt(num1Str);
                num2 = Integer.parseInt(num2Str);
            } else {
                throw new IllegalArgumentException("Нельзя комбинировать римские и арабские цифры");
            }

            int result;
            switch (operator) {
                case "+":
                    result = num1 + num2;
                    break;
                case "-":
                    result = num1 - num2;
                    break;
                case "*":
                    result = num1 * num2;
                    break;
                case "/":
                    if (num2 == 0) {
                        throw new ArithmeticException("Деление на ноль");
                    }
                    result = num1 / num2;
                    break;
                default:
                    throw new IllegalArgumentException("Неподдерживаемая арифметическая операция");
            }

            if (isArabicNumber(num1Str)) {
                return String.valueOf(result);
            } else {
                if (result <= 0) {
                    throw new IllegalArgumentException("Результат работы с римскими числами должен быть положительным");
                }
                return arabicToRoman(result);
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Ошибка: некорректные числа в выражении");
        }
    }

    static boolean isArabicNumber(String str) {
        try {
            int num = Integer.parseInt(str);
            return num >= 1 && num <= 10;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean isRomanNumber(String str) {
        return str.matches("^(I|II|III|IV|V|VI|VII|VIII|IX|X)$");
    }

    static int RomanToArabic(String roman) {
        Map<Character, Integer> romanMap = new HashMap<>();
        romanMap.put('I', 1);
        romanMap.put('V', 5);
        romanMap.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            int currentValue = romanMap.get(roman.charAt(i));

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
            }

            prevValue = currentValue;
        }

        return result;
    }

    static String arabicToRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Невозможно представить число в римской системе");
        }

        String[] romanSymbols = {"I", "IV", "V", "IX", "X", "XL", "L", "XC", "C", "CD", "D", "CM", "M"};
        int[] values = {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};

        StringBuilder result = new StringBuilder();

        for (int i = values.length - 1; i >= 0; i--) {
            while (num >= values[i]) {
                result.append(romanSymbols[i]);
                num -= values[i];
            }
        }

        return result.toString();
    }
}