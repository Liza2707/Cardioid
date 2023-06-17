import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CoordinatedPlane extends JFrame {
private static final int WIDTH = 800;
private static final int HEIGHT = 600;
private int  minDiapason, maxDiapason, step;
private double a;

    CoordinatedPlane(double a, int minDiapason, int maxDiapason, int step, boolean draw){
        this.a = a;
        this.minDiapason = minDiapason;
        this.maxDiapason = maxDiapason;
        this.step = step;
      setup(draw);
    }

    private void setup(boolean draw){
        this.getContentPane().setBackground(Color.WHITE);
        this.setSize(WIDTH, HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        if(draw) draw();

        JLabel function = new JLabel("Function: r= a(1+cos(f)), x=r cos(f), y=r sin(f)");
        function.setBounds(10, 10, 600, 20);


        JLabel diapason = new JLabel("Diapason: [" + this.minDiapason + ", " + this.maxDiapason + "]");
        diapason.setBounds(10, 30, 200, 20);
        this.setLayout(null);

        JLabel step = new JLabel("Step: " + this.step + ". Value 'a': " + this.a);
        step.setBounds(10, 50, 400, 20);

        this.add(function);
        this.add(diapason);
        this.add(step);


    }
    @Override
    public void paint(Graphics g) {

        super.paint(g);
        int x1 = 0;
        g.drawLine(x1, HEIGHT/2, WIDTH, HEIGHT/2);

        g.drawLine(WIDTH / 2, 0, WIDTH / 2, HEIGHT);

        double[] xPoints = new double[(maxDiapason - minDiapason) / step + 1];
        double[] yPoints = new double[(maxDiapason - minDiapason) / step + 1];
        double angleCos;
        double angleSin;
        double r;
        double x;
        double y;

        int count=0;
        for(int i = minDiapason; i <= maxDiapason; i = i + step){
            angleCos = Math.toRadians(i);
            angleSin = Math.toRadians(i);
            r = a * (1 + Math.cos(angleCos));
            x = r * Math.cos(angleCos) * 30;
            y = r * Math.sin(angleSin) * 30;
            xPoints[count] = x;
            yPoints[count] = y;
            count++;
        }

        int[] pointsX = new int[(maxDiapason - minDiapason) / step + 1];
        int[] pointsY = new int[(maxDiapason - minDiapason) / step + 1];
        for (int i = 0; i < xPoints.length; i++) {
            pointsX[i] = (int) xPoints[i] + WIDTH /2; // Translate x coordinate to frame coordinates
            pointsY[i] = (int) yPoints[i] + HEIGHT /2; // Translate y coordinate to frame coordinates
        }
        g.drawPolyline(pointsX, pointsY, pointsX.length);


        JLabel x2 = new JLabel("y");
        x2.setBounds(WIDTH/2+10, 0, 20, 20);
        this.add(x2);

        JLabel y2 = new JLabel("x");
        y2.setBounds(WIDTH -20, HEIGHT/2 -30, 20 ,10);
        this.add(y2);

    }

    public void draw(){
        JLabel[] labelsX = new JLabel[12];
        JLabel[] labelsY = new JLabel[9];
        int j = 0;
        int s =0;
        int step = 0;
        for(int i =0; i < 6; i++){
            labelsX[i] = new JLabel(String.valueOf(step));
            labelsX[i].setForeground(Color.RED);
            step+= 2;
            labelsX[i].setBounds(WIDTH /2 + s, HEIGHT/2 - 40, 40, 40);
            s+= 55;
            this.add(labelsX[i]);
        }
        step = 2;
        s=110;
        for(int i =5; i < 9; i++){
            labelsX[i] = new JLabel(String.valueOf(step));
            labelsX[i].setForeground(Color.RED);
            step+= 2;
            labelsX[i].setBounds(WIDTH/2 + 5, HEIGHT/2 - s, 40, 40);
            s+=50;
            this.add(labelsX[i]);

        }
        step=2;
        s = 60;
        for(int i =0; i < 5; i++){
            labelsY[i] = new JLabel("-" + (step));
            step += 2;
            labelsY[i].setForeground(Color.RED);
            labelsY[i].setBounds(WIDTH/2 -s, HEIGHT/2 -40, 40, 40);
            s+= 60;//////
            this.add(labelsY[i]);
        }

        step = 2;
        s=5;
        for(int i =5; i < 9; i++){
            labelsY[i] = new JLabel("-" + step);
            step+= 2;
            labelsY[i].setForeground(Color.RED);
            labelsY[i].setBounds(WIDTH /2 + 5, HEIGHT /2 + s, 40, 40);
            s+= 50;
            this.add(labelsY[i]);
        }

    }

    public void makeAnImage(String nameOfFile) throws IOException, AWTException {
        File imageFile;
        try {
            Robot robot = new Robot();
            // Capture screen from the top left in 800 by 600 pixel size
            BufferedImage bufferedImage = robot.createScreenCapture(
                    new Rectangle(new Dimension(WIDTH, HEIGHT)));
            // The captured image will the written into a file
            imageFile = new File(nameOfFile + ".png");
            ImageIO.write(bufferedImage, "png", imageFile);
        } catch (AWTException | IOException e) {
            JOptionPane.showMessageDialog(null, "Impossible to make an image");
        }

    }
}
