package a5812070.action2d;


class Shape {
	boolean collisionDetection(Shape shp) {
		if (this instanceof Point) {
			if (shp instanceof Point) {
				return ((Point)shp).collisionDetection((Point)this);
			} else if (shp instanceof Rectangle) {
				return ((Rectangle)shp).collisionDetection((Point)this);
			}
		}
		if (this instanceof Rectangle) {
			if (shp instanceof Point) {
				return ((Point)shp).collisionDetection((Rectangle)this);
			} else if (shp instanceof Rectangle) {
				return ((Rectangle)shp).collisionDetection((Rectangle)this);
			}
		}
		System.out.println("Bug in Shape.collisionDetection");
		return false;
	}
}

class Point extends Shape {

	float x, y;

	Point(float x, float y) {
		this.x = x;
		this.y = y;
	}

	boolean collisionDetection(Point p) {
		return x == p.x && y == p.y;
	}

	boolean collisionDetection(Rectangle rect) {
		return rect.collisionDetection(this);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
}

class Rectangle extends Shape {

	float x, y, w, h;

	Rectangle(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}

	public boolean collisionDetection(Point p) {
		return !(p.x < x || p.x > x + w || p.y < y || p.y > y + h);
	}

	public boolean collisionDetection(Rectangle rect) {
		float t1 = y;
		float b1 = y + h;
		float l1 = x;
		float r1 = x + w;

		float t2 = rect.y;
		float b2 = rect.y + h;
		float l2 = rect.x;
		float r2 = rect.x + w;

		return !(b1 < t2 || t1 > b2 || r1 < l2 || l1 > r2);
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y +  ", " + w +  ", " + h + ")";
	}

}
