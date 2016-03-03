import java.io.*;
import java.util.ArrayList;

/**
 * Created by Jonathan Fidelis Paul on 1/8/2016.
 */
public class SVReader {

    BufferedReader br;
    String dlim;

    /*Constructor*/
    public SVReader(String dlm)
    {
        dlim=dlm;
    }
    public ArrayList<ModelVector> read(String path)
    {
        int field_count=0;
        String s="";
        ArrayList<ModelVector> list=new ArrayList<>();

        /*Calculating number of columns*/
        try {
            br=new BufferedReader(new FileReader(path));

            while((s=br.readLine())!=null)
            {
                String[]arr=s.split(dlim);
                field_count=arr.length;
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Loading the values from file*/
        try {

            br=new BufferedReader(new FileReader(path));

            while((s=br.readLine())!=null)
            {
                ModelVector v=new ModelVector(s.split(dlim));
                list.add(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
