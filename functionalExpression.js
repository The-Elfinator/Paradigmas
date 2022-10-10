"use strict"

let abstractBinaryFunc = (f) => (a, x   ) => (...args) => f(a(...args), b(...args));
let add = abstractBinaryFunc((a, b) => a + b);
let subtract = abstractBinaryFunc((a, b) => a - b);
let divide = abstractBinaryFunc((a, b) => a / b);
let multiply = abstractBinaryFunc((a, b) => a * b);
let abstractUnaryFunc = (f) => (a) => (...args) => f(a(...args));
let negate =  abstractUnaryFunc((a) => (-1 * a));
let sinh = abstractUnaryFunc((a) => (Math.sinh(a)));
let cosh = abstractUnaryFunc((a) => (Math.cosh(a)));
const map = {"x" : 0, "y" : 1, "z" : 2}
let cnst = (x) => (...args) => x;
let variable = (s) => (...args) => args[map[s]]
const pi = (...args) => Math.PI;
const e = (...args) => Math.E;