public class Variable {
    String name;
    double value;

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public static Variable create(String attribution) {
        Variable variable = new Variable();

        if (!attribution.contains(" = ")) {
            return null;
        }

        String[] values = attribution.split(" = ");
        String varName = values[0];
        if (varName.trim().length() == 0) {
            return null;
        }

        String regex = "[a-z]([a-z]|[0-9]|[_])*";
        if (!varName.matches(regex)) {
            return null;
        }

        try {
            double value = Double.parseDouble(values[1]);

            variable.name = varName;
            variable.value = value;
        }
        catch (Exception ex) {
            return null;
        }

        return variable;
    }
}

/*
if (!Character.isLetter(varName.charAt(0))) {
            return null;
        }
*/