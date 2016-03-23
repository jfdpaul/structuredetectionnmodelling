import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by SONY on 2/21/2016.
 */
public class Application extends JPanel implements Runnable{

    JFrame f;
    JTextArea ta;
    private int bufferX,bufferY,holdX,holdY;
    int mat[][];
    int W,H;
    final int scale=2;

    enum Orientation {
        UP,DOWN,LEFT,RIGHT
    }

    Orientation o;
    public Application()
    {
        super();

        W=700;
        H=700;

        o=Orientation.UP;

        mat=new int[W][H];

        holdX=150;
        holdY=150;

        JFrame jj=new JFrame();
        jj.setVisible(true);
        jj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jj.setSize(400,350);
        JPanel p=new JPanel();
        p.setLayout(null);
        p.setBackground(Color.darkGray);
        Label l1=new Label("Structure & 2D Modelling");
        l1.setForeground(Color.WHITE);
        l1.setFont(new Font(null,Font.PLAIN,25));
        TextField urlField=new TextField("192.168.173.8:8086",20);
        Button b1=new Button("Scan");
        Button b2=new Button("Model");
        b1.setBackground(Color.cyan);
        b2.setBackground(Color.orange);
        l1.setBounds(60,10,300,40);
        urlField.setBounds(90,80,200,40);
        b1.setBounds(90,180,200,40);
        b2.setBounds(90,250,200,40);
        ta=new JTextArea(5,5);
        JScrollPane scroll = new JScrollPane (ta,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setBounds(50,340,300,120);
        p.add(l1);
        p.add(urlField);
        p.add(b1);
        p.add(b2);
        p.add(scroll);
        jj.add(p);

        b2.addActionListener(new ActionListener() {
                                 @Override
                                 public void actionPerformed(ActionEvent e) {
                                     showModel();
                                 }
                             });
        b1.addActionListener(e -> scan(urlField.getText()));
    }

    private void setPosition(ModelVector mv)
    {
        if(mv.dir== ModelVector.Direction.FORWARD)
        {
            if(o==Orientation.UP)
            {
                holdY-=mv.dist;
            }
            if(o==Orientation.DOWN)
            {
                holdY+=mv.dist;
            }
            if(o==Orientation.LEFT)
            {
                holdX-=mv.dist;
            }
            if(o==Orientation.RIGHT)
            {
                holdX+=mv.dist;
            }
        }

        if(mv.dir== ModelVector.Direction.RIGHT)
        {
            if(o==Orientation.UP)
            {
                o=Orientation.RIGHT;
            }
            else
            if(o==Orientation.RIGHT)
            {
                o=Orientation.DOWN;
            }
            else
            if(o==Orientation.DOWN)
            {
                o=Orientation.LEFT;
            }
            else
            if(o==Orientation.LEFT)
            {
                o=Orientation.UP;
            }
        }

        if(mv.dir== ModelVector.Direction.LEFT)
        {
            if(o==Orientation.UP)
            {
                o=Orientation.LEFT;
            }
            else
            if(o==Orientation.LEFT)
            {
                o=Orientation.DOWN;
            }
            else if(o==Orientation.DOWN)
            {
                o=Orientation.RIGHT;
            }
            else if(o==Orientation.RIGHT)
            {
                o=Orientation.UP;
            }
        }


        ///
        if(o==Orientation.UP)
        {
            bufferY=holdY;
            if(mv.left<=100)
            {
                bufferX = holdX - mv.left;
                mat[bufferX][bufferY] = 1;
            }
            if(mv.right<=100)
            {
                bufferX=holdX+mv.right;
                mat[bufferX][bufferY]=1;
            }
            if(mv.front<=100)
            {
                bufferX=holdX;
                bufferY=holdY-mv.front;
                mat[bufferX][bufferY]=1;
            }
        }
        if(o==Orientation.DOWN)
        {
            bufferY=holdY;
            if(mv.left<=100) {
                bufferX = holdX + mv.left;
                mat[bufferX][bufferY] = 1;
            }
            if(mv.right<=100)
            {
                bufferX=holdX-mv.right;
                mat[bufferX][bufferY]=1;
            }
            if(mv.front<=100)
            {
                bufferX=holdX;
                bufferY=holdY+mv.front;
                mat[bufferX][bufferY]=1;
            }
        }
        if(o==Orientation.LEFT)
        {
            bufferX=holdX;
            if(mv.left<=100)
            {
                bufferY=holdY+mv.left;
                mat[bufferX][bufferY]=1;
            }
            if(mv.right<=100)
            {
                bufferY=holdY-mv.right;
                mat[bufferX][bufferY]=1;
            }
            if(mv.front<=100)
            {
                bufferY=holdY;
                bufferX=holdX-mv.front;
                mat[bufferX][bufferY]=1;
            }
        }
        if(o==Orientation.RIGHT)
        {
            bufferX=holdX;
            bufferY=holdY-mv.left;
            mat[bufferX][bufferY]=1;
            bufferY=holdY+mv.right;
            mat[bufferX][bufferY]=1;
            if(mv.front<=100)
            {
                bufferY=holdY;
                bufferX=holdX+mv.front;
                mat[bufferX][bufferY]=1;
            }
        }
///
        repaint();
    }

    public void run()
    {
        f=new JFrame("Structure Modeling");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setSize(700,700);
/*
        int v=ScrollPaneConstants. VERTICAL_SCROLLBAR_ALWAYS;
        int h=ScrollPaneConstants. HORIZONTAL_SCROLLBAR_ALWAYS;
        JScrollPane jsp = new JScrollPane(this,v,h);
        f. getContentPane(). add(jsp);
*/
        f.add(this);

        ArrayList<ModelVector> list=ModelVector.getModelList("C:\\Users\\SONY\\IdeaProjects\\StructureModelling\\data.txt");

        for(ModelVector l:list)
        {
            l.printValues();
            setPosition(l);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void showModel()
    {
        Thread t=new Thread(this);
        t.start();
    }

    private void scan(String url)
    {
        ServerConnect sc=new ServerConnect();
        try{
            String response=sc.sendGet(url);
            ta.setText(response);
        }
        catch(Exception e){e.printStackTrace();}
    }

    public void paintComponent(Graphics g)
    {
        //super.paintComponent(g);
        setBackground(Color.WHITE);
        g.setColor(Color.RED);
        for(int i=0;i<W;i++)
        {
            for(int j=0;j<H;j++)
            {
                if(mat[i][j]==1)
                {
                    g.fillArc(i*scale,j*scale,5,5,0,360);
                }
            }
        }
        //g.setColor(Color.GREEN);
        //g.drawString(bufferX+" "+bufferY,bufferX,bufferY);
    }
}
