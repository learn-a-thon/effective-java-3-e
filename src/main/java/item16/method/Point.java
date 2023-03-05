package item16.method;

class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        // 부가 작업
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        // 부가 작업
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
