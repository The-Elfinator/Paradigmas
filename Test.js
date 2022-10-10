class CPoint {
    constructor(x, y) {
        this.x = x;
        this.y = y;
    }
    getX() {
        return this.x;
    }
    setX(x) {
        this.x = x;
    }
    toString() {
        return "Point(" + this.x + ", " + this.y + ")"
    }
}

let a = parseInt("aba");
console.log(a);
