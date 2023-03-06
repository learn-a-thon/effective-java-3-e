package com.example.effective.item14.composition;

public class NamedPoint implements Comparable<NamedPoint> {

    private final String name;
    private final Point point;

    public NamedPoint(Point point, String name) {
        this.point = point;
        this.name = name;
    }

    @Override
    public int compareTo(NamedPoint o) {
        int result = this.point.compareTo(o.point);
        if (result == 0) {
            result = this.name.compareTo(o.name);
        }
        return result;
    }
}
