package expression.generic;

import expression.MyTripleExpression;
import expression.parser.ExpressionParser;

public class GenericTabulator implements Tabulator {
    @Override
    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws Exception {
        Object[][][] Result = new Object[x2-x1+1][y2-y1+1][z2-z1+1];
        if (mode.equals("i")) {
            ExpressionParser parser = new ExpressionParser();
            MyTripleExpression exp = parser.parse(expression);
            // System.err.println(exp);
            for (int i = 0; i < x2-x1+1; i++) {
                for (int j = 0; j < y2-y1+1; j++) {
                    for (int k = 0; k < z2-z1+1; k++) {
                        try {
                            Result[i][j][k] = exp.evaluate(x1+i, y1+j, z1+k);
                        } catch (RuntimeException e) {
                            Result[i][j][k] = null;
                            // System.err.println("Catched expression");
                        }
                    }
                }
            }
        } else if (mode.equals("d")) {
            // System.err.println("Another mode");
            for (int i = 0; i < x2-x1+1; i++) {
                for (int j = 0; j < y2-y1+1; j++) {
                    for (int k = 0; k < z2-z1+1; k++) {
                        try {
                            MyTripleExpression exp = new ExpressionParser().parse(expression);
                            Result[i][j][k] = exp.evaluate(x1 + i, y1 + j, z1 + k);
                        } catch (RuntimeException e) {
                            Result[i][j][k] = null;
                        }
                    }
                }
            }
        } else if (mode.equals("bi")) {
            for (int i = 0; i < x2-x1+1; i++) {
                for (int j = 0; j < y2-y1+1; j++) {
                    for (int k = 0; k < z2-z1+1; k++) {
                        try {
                            MyTripleExpression exp = new ExpressionParser().parse(expression);
                            Result[i][j][k] = exp.evaluate(x1 + i, y1 + j, z1 + k);
                        } catch (RuntimeException e) {
                            Result[i][j][k] = null;
                        }
                    }
                }
            }
        } else {
            System.err.println("Unknown mode. There could be only \"i\", \"d\" or \"bi\"");
            return null;
        }
        return Result;
    }
}
