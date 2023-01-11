package com.astra.actionconfig.config.ruler;


import com.astra.actionconfig.config.data.Point2F;

public class UniPolygon
{

    public static boolean onSegment(Point2F p, Point2F q, Point2F r)
    {
        if (q.x <= Math.max(p.x, r.x) && q.x >= Math.min(p.x, r.x)
                && q.y <= Math.max(p.y, r.y) && q.y >= Math.min(p.y, r.y))
            return true;
        return false;
    }

    public static int orientation(Point2F p, Point2F q, Point2F r)
    {
        double val = (q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y);

        if (val == 0)
            return 0;
        return (val > 0) ? 1 : 2;
    }

    public static boolean pointInPolygon(Point2F[] polygon, Point2F point) {

        if (polygon.length < 3) {
            return false;
        }
        //A point is in a polygon if a line from the point to infinity crosses the polygon an odd number of times
        boolean odd = false;
        // int totalCrosses = 0; // this is just used for debugging
        //For each edge (In this case for each point of the polygon and the previous one)
        for (int i = 0, j = polygon.length - 1; i < polygon.length; i++) { // Starting with the edge from the last to the first node
            //If a line from the point into infinity crosses this edge
            if (((polygon[i].y > point.y) != (polygon[j].y > point.y)) // One point needs to be above, one below our y coordinate
                    // ...and the edge doesn't cross our Y corrdinate before our x coordinate (but between our x coordinate and infinity)
                    && (point.x < (polygon[j].x - polygon[i].x) * (point.y - polygon[i].y) / (polygon[j].y - polygon[i].y) + polygon[i].x)) {
                // Invert odd
                // System.out.println("Point crosses edge " + (j + 1));
                // totalCrosses++;
                odd = !odd;
            }
            //else {System.out.println("Point does not cross edge " + (j + 1));}
            j = i;
        }
        // System.out.println("Total number of crossings: " + totalCrosses);
        //If the number of crossings was odd, the point is in the polygon
        return odd;
    }

    public static boolean doIntersect(Point2F p1, Point2F q1, Point2F p2, Point2F q2)
    {

        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(p1, p2, q1))
            return true;

        if (o2 == 0 && onSegment(p1, q2, q1))
            return true;

        if (o3 == 0 && onSegment(p2, p1, q2))
            return true;

        if (o4 == 0 && onSegment(p2, q1, q2))
            return true;

        return false;
    }

    public static boolean isInside(Point2F[] polygon, int n, Point2F p)
    {
        int INF = 10000;
        if (n < 3)
            return false;

        Point2F extreme = new Point2F(INF, p.y);

        int count = 0, i = 0;
        do
        {
            int next = (i + 1) % n;
            if (doIntersect(polygon[i], polygon[next], p, extreme))
            {
                if (orientation(polygon[i], p, polygon[next]) == 0)
                    return onSegment(polygon[i], p, polygon[next]);

                count++;
            }
            i = next;
        } while (i != 0);

        return (count & 1) == 1 ? true : false;
    }

    public static void main(String args[])
    {
        Point2F polygon1[] = { new Point2F(0, 0), new Point2F(10, 0),
                new Point2F(10, 10), new Point2F(0, 10) };
        int n = 4;

        Point2F p = new Point2F(20, 20);
        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon1: " + isInside(polygon1, n, p));
        p = new Point2F(5, 5);
        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon1: " + isInside(polygon1, n, p));

        Point2F polygon2[] = { new Point2F(0, 0), new Point2F(5, 5), new Point2F(5, 0) };
        n = 3;

        p = new Point2F(3, 3);
        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon2: " + isInside(polygon2, n, p));
        p = new Point2F(5, 1);
        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon2: " + isInside(polygon2, n, p));
        p = new Point2F(8, 1);
        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon2: " + isInside(polygon2, n, p));

        Point2F polygon3[] = { new Point2F(0, 0), new Point2F(10, 0),
                new Point2F(10, 10), new Point2F(0, 10), new Point2F(5, 5) };
        n = 5;

        p = new Point2F(-1, 10);
        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon3: " + isInside(polygon3, n, p));


//         [{"x":324.0,"y":1406.4},{"x":756.0,"y":1406.4},{"x":756.0,"y":1665.6},{"x":324.0,"y":1665.6}]  LeftAnkle 513.7883055210114/1547.4263763427734 - false
//            [Ljava.lang.String;@130f889
//        landmark type [{"x":324.0,"y":1406.4},{"x":756.0,"y":1406.4},{"x":756.0,"y":1665.6},{"x":324.0,"y":1665.6}]  RightAnkle 592.7315425872803/1562.0816802978516 - true
//
        Point2F polygon4[] = { new Point2F(324, 1406), new Point2F(756.0, 1406),
                new Point2F(756.0, 1665), new Point2F(324.0, 1665)};
        p = new Point2F(513, 1547);

        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon4: " + pointInPolygon(polygon4, p));
        p = new Point2F(592, 1562);

        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon4: " + pointInPolygon(polygon4,  p));

        p = new Point2F(324, 1407);

        System.out.println("Point P(" + p.x + ", " + p.y
                + ") lies inside polygon4: " + pointInPolygon(polygon4,  p));

    }

}
