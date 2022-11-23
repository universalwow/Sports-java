package com.astra.actionconfig.config.data;

public class Vector2D {
    public double x;
    public double y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;

    }

    public int quadrant() {
        if (this.x > 0 && this.y > 0) {
            return 1;
        }  else if (this.x > 0 && this.y < 0) {
            return 2;
        } else if (this.x < 0 && this.y < 0) {
            return 3;
        } else if (this.x < 0 && this.y > 0) {
            return 4;
        } else if (this.x == 0 && this.y == 0) {
            return 0;
        } else if (this.x == 0 && this.y > 0) {
            return 1;
        }else if (this.x == 0 && this.y < 0) {
            return 3;
        } else if (this.x > 0 && this.y ==0) {
            return 2;
        } else if (this.x < 0 && this.y == 0) {
            return 4;
        }
        return 0;
    }

    public Vector2D oppositeY() {
        return new Vector2D(x, y * -1);
    }

    public double magnitude() {
        return Math.sqrt(x*x + y*y);
    }

    public double distance(Vector2D vector) {
        return Vector2D.minus(this, vector).magnitude();
    }

    public Vector2D normalized() {
        double magn = this.magnitude();
        return new Vector2D(x/magn, y/magn);
    }

    public double angle() {
        switch (this.quadrant()) {
            case 1:
                return this.angleTo(new Vector2D(0,1));
            case 2:
                return this.angleTo(new Vector2D(1, 0)) + 90;
            case 3:
                return this.angleTo(new Vector2D(0, -1)) + 180;
            case 4:
                return this.angleTo(new Vector2D(-1, 0)) + 270;
            default:
                return 0;
        }
    }

    public double angleTo(Vector2D vector) {
        return Math.acos(
                Vector2D.dot(this.normalized(), vector.normalized()))/Math.PI * 180;
    }

    public static double angle(Vector2D left, Vector2D right) {
        return left.angle() - right.angle();
    }

    public static double dot(Vector2D left, Vector2D right) {
        return left.x*right.x + left.y*right.y;
    }


    public static Vector2D add(Vector2D left, Vector2D right) {
        return new Vector2D(left.x + right.x, left.y + right.y);
    }

    public static Vector2D minus(Vector2D left, Vector2D right) {
        return new Vector2D(left.x - right.x, left.y - right.y);
    }

    public static Vector2D negation(Vector2D vector) {
        return new Vector2D(vector.x * -1, vector.y * -1);
    }






}
