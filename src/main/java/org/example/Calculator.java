package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

interface Operation {
    double execute(double num1, double num2);
}

// Сами операции
class Addition implements Operation {
    public double execute(double num1, double num2) {
        return num1 + num2;
    }
}

class Subtraction implements Operation {
    public double execute(double num1, double num2) {
        return num1 - num2;
    }
}

class Multiplication implements Operation {
    public double execute(double num1, double num2) {
        return num1 * num2;
    }
}

class Division implements Operation {
    public double execute(double num1, double num2) {
        if (num2 == 0) {
            throw new ArithmeticException("Деление на ноль!");
        }
        return num1 / num2;
    }
}

// Обработка операций
class OperationFactory {
    public static Operation createOperation(String operator) {
        return switch (operator) {
            case "+" -> new Addition();
            case "-" -> new Subtraction();
            case "*" -> new Multiplication();
            case "/" -> new Division();
            default -> throw new IllegalArgumentException("Недопустимый оператор: " + operator);
        };
    }
}

// Калькулятор
public class Calculator {
    private double currentResult;

    public Calculator() {
        currentResult = 0;
    }

    public double getCurrentResult() {
        return currentResult;
    }

    public void calculate(String operator, double operand) {
        Operation operation;
        try {
            operation = OperationFactory.createOperation(operator);
        } catch (IllegalArgumentException e) {
            System.out.println("Такой операции не существует. Пожалуйста, попробуйте снова.");
            return;
        }

        try {
            currentResult = operation.execute(currentResult, operand);
        } catch (ArithmeticException e) {
            System.out.println("Невозможно разделить на ноль! Продолжаем вычисление.");
        }
    }

    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите первое число: ");
        double firstNumber;
        try {
            firstNumber = scanner.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("Неверный ввод! Пожалуйста, введите число.");
            scanner.nextLine();
            return;
        }
        calculator.currentResult = firstNumber;

        while (true) {
            System.out.print("Введите оператор (+, -, *, /) или 'q', чтобы выйти: ");
            String operator = scanner.next();
            if (operator.equals("q")) {
                break;
            }

            System.out.print("Введите число: ");
            double operand;
            try {
                operand = scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Неверный ввод! Пожалуйста, введите число.");
                scanner.nextLine(); // clear input buffer
                continue;
            }

            calculator.calculate(operator, operand);
            System.out.println("Текущий результат: " + calculator.getCurrentResult());
        }

        scanner.close();
    }
}

