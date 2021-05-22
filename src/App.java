import java.util.ArrayList;
import java.util.Scanner;

public class App {
    static ArrayList<Variable> _variables = new ArrayList<Variable>();

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        requestAttributions(scanner);
        requestOperation(scanner);
        //printVariables();

        scanner.close();
    }

    static void requestAttributions(Scanner scanner) {
        System.out.println("\nInforme todas as atribuições iniciais e depois uma linha em branco para continuar.\n");

        String line = "";
        while (line != null) {
            line = scanner.nextLine();

            if (line == "") { // Pular pra próxima etapa.
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

        if (!data[1].contains("+") || !data[1].contains("-") || !data[1].contains("*") || !data[1].contains("/")) {
            closeProgram("Nenhuma operação encontrada.");
        }

        // TODO: Separar os valores de cada operação.
        if (data[1].contains("/")) {
            String[] values = data[1].split("/");
            for (String s : values) {
                System.out.println(s);
            }
        }

        //System.out.println("Alterando o valor da variável " + hostVariable.getName() + ".");
    }

    static Variable getVariableByName(String varName) {
        for (Variable variable : _variables) {
            if (variable.getName().equals(varName)) {
                return variable;
            }
        }

        return null;
    }

    static void printVariables() {
        for (Variable variable : _variables) {
            System.out.println(variable.getName() + ": " + variable.getValue());
        }
    }

    static void closeProgram(String msg) {
        System.out.println(msg + " Programa encerrado.");
        System.exit(0);
    }
}