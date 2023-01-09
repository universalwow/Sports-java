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

    public static boolean isInside(Point2F polygon[], int n, Point2F p)
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
    }
}
