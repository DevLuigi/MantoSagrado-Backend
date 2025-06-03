package com.dev.manto_sagrado.utils;

public class CpfValidator {

    public static boolean isValid(String cpf) {
        if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d{11}")) {
            return false;
        }

        int[] weightsFirstDigit = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weightsSecondDigit = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        String partialCpf = cpf.substring(0, 9);
        int firstDigit = calculateDigit(partialCpf, weightsFirstDigit);
        int secondDigit = calculateDigit(partialCpf + firstDigit, weightsSecondDigit);

        return cpf.equals(partialCpf + firstDigit + secondDigit);
    }

    private static int calculateDigit(String str, int[] weights) {
        int sum = 0;
        for (int i = 0; i < str.length(); i++) {
            sum += Integer.parseInt(str.substring(i, i + 1)) * weights[i];
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
