import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class App {
    static ArrayList<Variable> _variables = new ArrayList<Variable>();
    static String[] operators = {"/", "*", "+", "-"};

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        requestAttributions(scanner);
        requestOperation(scanner);

        scanner.close();
    }

    static void requestAttributions(Scanner scanner) {
        System.out.println("\nInforme todas as atribuições iniciais e depois uma linha em branco para continuar.\n");

        String line = "";
        while (line != null) {
            line = scanner.nextLine();

            if (line.trim().length() == 0) { // Pular pra próxima etapa.
                if (_variables.size() > 0) {
                    line = null;
                    break;
                } else {
                    closeProgram("A lista de variáveis está vazia.");
                }
            }

            Variable variable = Variable.create(line);
            if (variable == null) {
                closeProgram("A atribuição informada não é válida.");
            } else {
                _variables.add(variable);
            }
        }
    }

    static void requestOperation(Scanner scanner) {
        String line = scanner.nextLine();
        
        if (line.trim().length() == 0 || !line.contains(" = ")) {
            closeProgram("A operação informada não é válida.");
        }

        String[] data = line.split(" = ");
        Variable hostVariable = getVariableByName(data[0]);
        if (hostVariable == null) {
            closeProgram("A variável de destino não existe.");
        }

        if (!data[1].contains("+") && !data[1].contains("-") && !data[1].contains("*") && !data[1].contains("/")) {
            closeProgram("Nenhuma operação encontrada.");
        }

        try {
            String operationStr = replaceVariablesWithValues(data[1]);
            for (int i = 0; i < operators.length; i++) {
                while (operationStr.contains(operators[i])) {
                    ArrayList<String> map = mapOperations(operationStr);
                    operationStr = replaceVariablesWithValues(generateNewLine(map, operators[i]));
                }
            }

            System.out.println(hostVariable.name + " = " + operationStr);
        } catch (Exception e) {
            closeProgram("As operações informadas não são válidas.");
        }
    }

    static String generateNewLine(ArrayList<String> map, String operator) {
        String newLine = "";

        int operatorIndex = map.indexOf(operator);
        double previous = Double.parseDouble(map.get(operatorIndex - 1));
        double next = Double.parseDouble(map.get(operatorIndex + 1));

        for (int i = 0; i < map.size(); i++) {
            if (i == operatorIndex + 1 || i == operatorIndex -1) {
                continue;
            } else if (i == operatorIndex) {
                newLine += makeOperation(operator, previous, next);
            } else {
                newLine += map.get(i);
            }
        }

        return newLine;
    }

    static double makeOperation(String operator, double a, double b) {
        switch (operator) {
            case "/":
                return a / b;
            case "*":
                return a * b;
            case "+":
                return a + b;
            case "-":
                return a - b;
        }

        return 0;
    }

    static ArrayList<String> mapOperations(String line) {
        ArrayList<String> map = new ArrayList<String>();

        String current = "";
        for (int i = 0; i < line.length(); i++) {
            String currentChar = String.valueOf(line.charAt(i));
            if (Arrays.asList(operators).contains(currentChar)) {
                map.add(current);
                current = "";
                map.add(currentChar);
            } else {
                current += line.charAt(i);
            }
        }
        map.add(current);

        return map;
    }

    static String replaceVariablesWithValues(String line) {
        String newLine = line;

        for (Variable variable : _variables) {
            if (newLine.contains(variable.name)) {
                newLine = newLine.replace(variable.name, String.valueOf(variable.value));
            }
        }

        return newLine;
    }

    static Variable getVariableByName(String varName) {
        for (Variable variable : _variables) {
            if (variable.getName().equals(varName)) {
                return variable;
            }
        }

        return null;
    }

    static void closeProgram(String msg) {
        System.out.println(msg + " Programa encerrado.");
        System.exit(0);
    }
}