import expression.*;
import expression.exceptions.ExpressionParser;

public class RunGenericExpressionCmd {
    public static void main(String[] args) {
        Operations<?> operations;
        switch (args[0]) {
            case "-i" -> operations = new IntOperations();
            case "-d" -> operations = new DoubleOperations();
            case "-bi" -> operations = new BigIntOperations();
            case "u" -> operations = new UncheckedIntOperations();
            case "l" -> operations = new LongOperations();
            case "s" -> operations = new ShortOperations();
            default -> throw new AssertionError("Unknown mode");
        }
        ExpressionParser<?> parser = new ExpressionParser<>(operations);
        MyGenericExpression<?> expr = parser.parse(args[1]);
        Object[][][] result = new Object[5][5][5];
        for (int x = -2; x <= 2; x++) {
            for (int y = -2; y <= 2; y++) {
                for (int z = -2; z <= 2; z++) {
                    try {
                        result[x+2][y+2][z+2] = expr.evaluate(x, y, z);
                    } catch (RuntimeException e) {
                        result[x+2][y+2][z+2] = null;
                    }
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k < 5; k++) {
                    System.out.println("x= " + (-2 + i) + " y= " + (-2 + j) + " z= " + (-2 + k) + ": res= " + result[i][j][k]);
                }
            }
        }
    }
}

