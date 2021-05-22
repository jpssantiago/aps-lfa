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
        if (values[0].trim().length() == 0) {
            return null;
        }

        try {
            double value = Double.parseDouble(values[1]);

            variable.name = values[0];
            variable.value = value;
        }
        catch (Exception ex) {
            return null;
        }

        return variable;
    }
}