package skily_leyu.mistyrain.common.math;

public class Pos3 {

    private float x;
    private float y;
    private float z;

    public Pos3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public Pos3 getZYX(){
        return new Pos3(z,y,x);
    }

    public Pos3 getXYX(){
        return new Pos3(x,y,x);
    }

    public  Pos3 getZYZ(){
        return new Pos3(z,y,z);
    }

}
