"use strict"

const debug = false;

let BinaryOperation = {
    evaluate: function(x, y, z) {
        const a = this.expr1.evaluate(x, y, z);
        const b = this.expr2.evaluate(x, y, z);
        return this.result(a, b);
    },
    toString: function() {
        const a = this.expr1.toString();
        const b = this.expr2.toString();
        return a + " " + b + " " + this.tag;
    },
    diff: function (vrb) {
        const a = this.expr1;
        const b = this.expr1.diff(vrb);
        const c = this.expr2;
        const d = this.expr2.diff(vrb);
        return this.differ(a, b, c, d);
    },
    prefix: function () {
        const a = this.expr1.prefix();
        const b = this.expr2.prefix();
        return "(" + this.tag + " " + a + " " + b + ")"
    }
}

let UnaryOperation = {
    evaluate: function(x, y, z) {
        const a = this.expr.evaluate(x, y, z);
        return this.result(a);
    },
    toString: function() {
        const a = this.expr.toString();
        return a + " " + this.tag;
    },
    prefix: function () {
        const a = this.expr.prefix();
        return "(" + this.tag + " " + a + ")"
    }
}

let MultyOperation = {
    evaluate: function (x, y, z) {
        let results = []
        for (let v of this.expressions) {
            results.push(v.evaluate(x, y, z));
        }
        return this.result(results);
    },
    prefix: function () {
        let strings = []
        for (let v of this.expressions) {
            strings.push(v.prefix());
        }
        let ret = "(" + this.tag;
        for (let s of strings) {
            ret += " " + s;
        }
        ret += ')'
        return ret;
    }
}

function Add(expr1, expr2) {
    let add = Object.create(BinaryOperation);
    add.expr1 = expr1;
    add.expr2 = expr2;
    add.result = (a, b) => a + b;
    add.tag = "+";
    add.differ = function (a, b, c, d) {
        return new Add(b, d);
    }
    return add;
}

function Subtract(expr1, expr2) {
    let sub = Object.create(BinaryOperation);
    sub.expr1 = expr1;
    sub.expr2 = expr2;
    sub.result = (a, b) => a - b;
    sub.tag = "-";
    sub.differ = function (a, b, c, d) {
        return new Subtract(b, d);
    }
    return sub;
}

function Multiply(expr1, expr2) {
    let mul = Object.create(BinaryOperation);
    mul.expr1 = expr1;
    mul.expr2 = expr2;
    mul.result = (a, b) => a*b;
    mul.tag = "*";
    mul.differ = function (a, b, c, d) {
        return new Add(new Multiply(b, c), new Multiply(a, d));
    }
    return mul;
}

function Divide(expr1, expr2) {
    let div = Object.create(BinaryOperation);
    div.expr1 = expr1;
    div.expr2 = expr2;
    div.result = (a, b) => a/b;
    div.tag = "/";
    div.differ = function (a, b, c, d) {
        return new Divide(new Subtract(new Multiply(b, c), new Multiply(a, d)), new Multiply(c, c))
    }
    return div;
}

function Negate(expr) {
    let neg = Object.create(UnaryOperation);
    neg.expr = expr;
    neg.tag = "negate";
    neg.result = (a) => -1*a;
    neg.diff = function (vrb) {
        const diffExpr = expr.diff(vrb);
        return new Negate(diffExpr);
    }
    return neg
}

function Mean(...expressions) {
    let mean = Object.create(MultyOperation);
    mean.expressions = expressions;
    mean.tag = "mean";
    mean.result = function (arg) {
        let s = 0;
        for (let a of arg) {
            s += a;
        }
        return s / (arg.length)
    }
    mean.diff = function (vrb) {
        let diffExpressions = [];
        for (let e of expressions) {
            diffExpressions.push(e.diff(vrb));
        }
        let ret = diffExpressions[0];
        for (let i = 1; i < diffExpressions.length; i++) {
            ret = new Add(ret, diffExpressions[i]);
        }
        return new Divide(ret, new Const(expressions.length));
    }
    return mean;
}

function Var(...expressions) {
    let _var = Object.create(MultyOperation);
    _var.expressions = expressions;
    _var.tag = "var";
    _var.result = (args) => {
        let s = 0;
        let sq = 0;
        for (let a of args) {
            s += a;
            sq += a*a;
        }
        return sq / args.length - (s / args.length) * (s / args.length);
    }
    _var.diff = function (vrb) {
        let diffExpressions = [];
        let diffQuadExpressions = [];
        for (let e of expressions) {
            diffExpressions.push(e.diff(vrb));
            diffQuadExpressions.push((new Multiply(e, e)).diff(vrb))
        }
        let ret1 = diffQuadExpressions[0];
        for (let i = 1; i < diffQuadExpressions.length; i++) {
            ret1 = new Add(ret1, diffQuadExpressions[i]);
        }
        ret1 = new Divide(ret1, new Const(expressions.length));
        let sumExpr = expressions[0];
        let sumDiffExpr = diffExpressions[0];
        for (let i = 1; i < diffExpressions.length; i++) {
            sumExpr = new Add(sumExpr, expressions[i]);
            sumDiffExpr = new Add(sumDiffExpr, diffExpressions[i]);
        }
        sumExpr = new Divide(sumExpr, new Const(expressions.length));
        sumDiffExpr = new Divide(sumDiffExpr, new Const(expressions.length));
        const ret2 = new Multiply(new Const(2), new Multiply(sumExpr, sumDiffExpr));
        return new Subtract(ret1, ret2);
    }
    return _var;
}

function Sinh(expr) {
    let sinh = Object.create(UnaryOperation);
    sinh.expr = expr;
    sinh.tag = "sinh";
    sinh.result = (a) => Math.sinh(a);
    sinh.diff = function (vrb) {
        return new Multiply(new Cosh(expr), expr.diff(vrb));
    }
    return sinh;
}

function Cosh(expr) {
    let cosh = Object.create(UnaryOperation);
    cosh.expr = expr;
    cosh.tag = "cosh";
    cosh.result = (a) => Math.cosh(a);
    cosh.diff = function (vrb) {
        return new Multiply(new Sinh(expr), expr.diff(vrb));
    }
    return cosh;
}

function Const(c) {
    return {
        evaluate: function () {
            return c;
        },
        toString: function () {
            return c.toString();
        },
        diff: function () {
            return new Const(0);
        },
        prefix: function () {
            return c.toString();
        }
    };
}

function Variable(s) {
    const map = {"x": 0, "y": 1, "z": 2};
    return {
        evaluate: function (...args) {
            return args[map[s]];
        },
        toString: function () {
            return s;
        },
        prefix: function () {
            return s;
        },
        diff: function (vrb) {
            if (vrb === s) {
                return new Const(1);
            }
            return new Const(0);
        }
    };
}

let AbstractParser = {
    skipSpaces: function () {
        while (this.parsingLine[this.pos] === " ") {
            this.pos++;
        }
    },
    isEnd: function () {
        return this.pos >= this.parsingLine.length;
    },
    parseElement: function() {
        this.skipSpaces();
        let result = "";
        const variables = ['x', 'y', 'z'];
        while (this.parsingLine[this.pos] !== " " && !this.isEnd()) {
            result += this.parsingLine[this.pos];
            this.pos++;
            if (result === ')' || (!this.isEnd() && this.parsingLine[this.pos] === ')')
                || (result === '(' && !this.isEnd() && variables.indexOf(this.parsingLine[this.pos]) === -1)
                || (!this.isEnd() && this.parsingLine[this.pos] === '(')) {
                break;
            }
        }
        return result;
    }
}

function ParseError(message) {
    Error.call(this, message);
    this.message = message;
}
ParseError.prototype = Object.create(Error.prototype);
ParseError.prototype.name = "ParseError"
ParseError.prototype.constructor = ParseError;

function CountOfBracketsError(message) {
    Error.call(this, message);
    this.message = message;
}
CountOfBracketsError.prototype = Object.create(Error.prototype);
CountOfBracketsError.prototype.name = "CountOfBracketsError";
CountOfBracketsError.prototype.constructor = CountOfBracketsError;

function parse(parsingLine) {
    let MyParser = Object.create(AbstractParser);
    MyParser.parsingLine = parsingLine;
    MyParser.pos = 0;
    let stack = [];
    while (!MyParser.isEnd()) {
        const sym = MyParser.parseElement();
        if (debug) {console.log(sym);}
        const binOperations = {"+": Add, "-": Subtract, "*": Multiply, "/": Divide};
        const unOperations = {"negate": Negate, "sinh": Sinh, "cosh": Cosh};
        if (binOperations.hasOwnProperty(sym)) {
            if (debug) {console.log(sym + " " + "is operation");}
            const expr2 = stack.pop();
            const expr1 = stack.pop();
            stack.push(new binOperations[sym](expr1, expr2));
        } else if (unOperations.hasOwnProperty(sym)) {
            const expr = stack.pop();
            stack.push(new unOperations[sym](expr));
        } else if (["x", "y", "z"].indexOf(sym) !== -1) {
            if (debug) {console.log(sym + " " + "is variable");}
            stack.push(new Variable(sym));
        } else {
            if (debug) {console.log(sym + " " + "is const");}
            stack.push(new Const(parseInt(sym)))
        }
        if (debug) {console.log(stack)}
    }
    return stack[0];
}

function parsePrefix(parsingLine) {
    if (debug) {console.log("we had to parse: " + parsingLine);}
    let MyPrefixParser = Object.create(AbstractParser);
    MyPrefixParser.parsingLine = parsingLine.trim();
    MyPrefixParser.pos = 0;
    const check = function(elem) {
        let start;
        if (elem[0] === '-') {
            start = 1;
        } else {
            start = 0;
        }
        for (let i = start; i < elem.length; i++) {
            if (!(elem[i] >= '0' && elem[i] <= '9')) {
                return false;
            }
        }
        return true;
    }
    let cntOfBrackets = 0;
    const prefixParse = function (parsingLine) {
        let stack = [];
        let queue = [];
        while (!MyPrefixParser.isEnd()) {
            const sym = MyPrefixParser.parseElement();
            if (debug) {console.log(sym);}
            const correctOperations = ['+', '-', '*', '/', 'negate', 'sinh', 'cosh', 'var', 'mean'];
            if (sym === "(") {
                cntOfBrackets++;
                // new Expression
                stack.push(prefixParse(parsingLine))
            } else if (correctOperations.indexOf(sym) !== -1) {
                if (debug) {console.log(sym + " " + "is operation");}
                // new Operation
                queue.push(sym);
            } else if (["x", "y", "z"].indexOf(sym) !== -1) {
                if (debug) {console.log(sym + " " + "is variable");}
                // new Variable
                stack.push(new Variable(sym));
            } else if (sym === ")") {
                cntOfBrackets--;
                if (cntOfBrackets < 0) {
                    throw new CountOfBracketsError("Wrong count of brackets. Found extra ')'")
                }
                if (queue.length === 0) {
                    throw new ParseError("Expected operation inside brackets");
                }
                if (debug) {console.log("Queue: " + queue.toString()); console.log("Stack: " + stack.toString())}
                const operation = queue.pop();
                if (queue.length !== 0) {
                    throw new ParseError("Parsing error. Found more than 1 operation in brackets")
                }
                const possibleBinaryOperations = {"+": Add, "-": Subtract, "*": Multiply, "/":Divide};
                const possibleUnaryOperation = {"negate": Negate, "sinh": Sinh, "cosh": Cosh};
                const possibleMultyOperation = {"mean": Mean, "var": Var};
                if (possibleBinaryOperations.hasOwnProperty(operation)) {
                    if (stack.length !== 2) {
                        if (debug) {console.log(stack.toString());}
                        throw new ParseError("Parsing error. Expected 2 arguments of binary operation. Found: " + stack.length);
                    }
                    const binExpr2 = stack.pop();
                    const binExpr1 = stack.pop();
                    stack.push(new possibleBinaryOperations[operation](binExpr1, binExpr2));
                } else if (possibleUnaryOperation.hasOwnProperty(operation)) {
                    if (stack.length !== 1) {
                        throw new ParseError("Parsing error. Expected 1 arguments of unary operation. Found: " + stack.length);
                    }
                    const unExpr = stack.pop();
                    stack.push(new possibleUnaryOperation[operation](unExpr));
                } else if (possibleMultyOperation.hasOwnProperty(operation)) {
                    if (stack.length === 0) {
                        throw new ParseError("Parsing error. Expected at least 1 argument for multy operation. Found 0");
                    }
                    stack = [new possibleMultyOperation[operation](...stack)];
                } else {
                    throw new ParseError("Found unknown operation: " + operation);
                }
                return stack[0];
            } else {
                // new Const or UnknownSymbol
                if (debug) {console.log(sym + " " + "is const?");}
                if (!check(sym)) {
                    throw new ParseError("Unknown symbol found " + sym);
                }
                stack.push(new Const(parseInt(sym)));
            }
        }
        if (queue.length !== 0) {
            throw new ParseError("Parsing error. Found operation outside brackets!");
        }
        if (debug) {console.log(stack.toString())}
        if (stack.length !== 1) {
            throw new ParseError("Wrong format of parsing line. Please make sure your input data is correct!");
        }
        if (cntOfBrackets !== 0) {
            throw new CountOfBracketsError("Wrong count of brackets. Found extra bracket '('");
        }
        return stack[0];
    }
    return prefixParse(MyPrefixParser.parsingLine);
}
