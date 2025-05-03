package br.com.starter.infrastructure.services.utils;

public class DocumentValidatorUtil {
    public boolean checkCpf(String cpf) {
        int sum = 0;
        int index = cpf.length() - 1;

        // First loop for first digit calculation
        for (int i = 0; i < cpf.length(); i++) {
            int parsed = Integer.parseInt(cpf.substring(i, i + 1));
            if (i < 9) {
                int aux = parsed * index;
                sum += aux;
                index--;
            }
        }

        int mod = sum % 11;
        int firstDigit = (mod < 2) ? 0 : 11 - mod;

        // Second loop for second digit calculation
        index = cpf.length();
        sum = 0;
        for (int i = 0; i < cpf.length(); i++) {
            if (i < 10) {
                int parsed = Integer.parseInt(cpf.substring(i, i + 1));
                sum += parsed * index;
                index--;
            }
        }

        mod = sum % 11;
        int secondDigit = (mod < 2) ? 0 : 11 - mod;

        String validator = "" + cpf.charAt(9) + cpf.charAt(10);
        String result = "" + firstDigit + secondDigit;

        return validator.equals(result);
    }

    public boolean checkCnpj(String cnpj) {
        int sum = 0;
        int index = 1;
        int[] order = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        // Checking for invalid CNPJ numbers
        if (cnpj.equals("00000000000000") || cnpj.equals("11111111111111") ||
                cnpj.equals("22222222222222") || cnpj.equals("33333333333333") ||
                cnpj.equals("44444444444444") || cnpj.equals("55555555555555") ||
                cnpj.equals("66666666666666") || cnpj.equals("77777777777777") ||
                cnpj.equals("88888888888888") || cnpj.equals("99999999999999")) {
            return false;
        }

        // First loop for first digit calculation
        for (int i = 0; i < cnpj.length(); i++) {
            if (index <= 12) {
                int parsed = Integer.parseInt(cnpj.substring(i, i + 1));
                int aux = order[index] * parsed;
                sum += aux;
                index++;
            }
        }

        int mod = sum % 11;
        int firstDigit = (mod < 2) ? 0 : 11 - mod;

        // Second loop for second digit calculation
        index = 0;
        sum = 0;
        for (int i = 0; i < cnpj.length(); i++) {
            if (index < 12) {
                int parsed = Integer.parseInt(cnpj.substring(i, i + 1));
                int aux = order[index] * parsed;
                sum += aux;
                index++;
            }
        }

        // Add the first digit to the sum to calculate the second digit
        sum += firstDigit * 2;

        mod = sum % 11;
        int secondDigit = (mod < 2) ? 0 : 11 - mod;

        String validator = "" + cnpj.charAt(12) + cnpj.charAt(13);
        String result = "" + firstDigit + secondDigit;

        return validator.equals(result);
    }
}
