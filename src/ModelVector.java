import java.util.ArrayList;

/**
 * Created by SONY on 2/21/2016.
 */
class ModelVector {
    enum Direction{
        FORWARD,BACKWARD,LEFT,RIGHT
    }
    int dist;
    int left,front,right;

    Direction dir;

    public ModelVector()
    {
        dir=Direction.FORWARD;
        dist=0;
        left=0;
        right=0;
        front=0;
    }

    public ModelVector(String params[]) {
        switch(params[0])
        {
            case "f":dir=Direction.FORWARD;break;
            case "l":dir=Direction.LEFT;break;
            case "r":dir=Direction.RIGHT;break;
            case "b":dir=Direction.BACKWARD;break;
        }
        dist = Integer.parseInt(params[1]);
        left = Integer.parseInt(params[2]);
        front = Integer.parseInt(params[3]);
        right = Integer.parseInt(params[4]);
    }

    public void printValues()
    {
        System.out.println(dir+" ,"+dist+" ,"+left+" ,"+front+" ,"+right);
    }
    public static ArrayList<ModelVector> getModelList(String filename)
    {
        SVReader reader=new SVReader(",");
        ArrayList<ModelVector> list=reader.read(filename);
        return list;
    }
}
