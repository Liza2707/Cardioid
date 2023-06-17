import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class Form{
    private static final int WIDTH = 500;
    private static final int HEIGHT = 400;
    private JPanel panel;
    private JTextField angle, minDiap, maxDiap, stepText;
    private JButton button;
    private CoordinatedPlane c;
    private int minN, maxN, stepN;
    private double a;
    Form(){
        setup();

    }

    private void setup(){
        JFrame frame = new JFrame("Form");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        panel.setBackground(Color.BLACK);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2 + 400, 0);

        JLabel label = new JLabel("Enter the value 'a': ");
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Georgia", Font.PLAIN, 15));
        panel.add(label);

        angle = new JTextField(10);
        panel.add(angle);

        JLabel diapason = new JLabel("Enter the diapason (min and max value) ([0, 360]) in degrees: ");
        diapason.setForeground(Color.WHITE);
        diapason.setFont(new Font("Georgia", Font.PLAIN, 15));
        panel.add(diapason);

        minDiap = new JTextField(5);
        panel.add(minDiap);

        maxDiap = new JTextField(5);
        panel.add(maxDiap);

        JLabel step = new JLabel("Enter step (in degrees): ");
        step.setForeground(Color.WHITE);
        step.setFont(new Font("Georgia", Font.PLAIN, 15));
        panel.add(step);

        stepText = new JTextField(5);
        panel.add(stepText);

        button =  new JButton("Draw");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean right = true;
                String s = angle.getText();
                String min = minDiap.getText();
                String max = maxDiap.getText();
                try {
                    a = Double.parseDouble(s);
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(null, "Not a number!");
                    right = false;
                }

                try {
                    minN = Integer.parseInt(min);
                    if(minN < 0 || minN > 360){
                        throw new Exception("Incorrect");
                    }
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Incorrect!");
                    right = false;
                }

                try {
                    maxN = Integer.parseInt(max);
                    if(maxN < 0 || maxN > 360 || maxN <= minN) throw new Exception("");
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Incorrect!");
                    right = false;
                }

                try {
                    stepN = Integer.parseInt(stepText.getText());
                    if(stepN < 0 ) throw new Exception("");
                } catch (Exception exception){
                    JOptionPane.showMessageDialog(null, "Incorrect step!");
                    right = false;
                }

                if (right) {
                    if(c!= null){
                        c.dispose();
                    }
                    c = new CoordinatedPlane(a, minN, maxN, stepN, false);
                }

            }
        });

        JButton show = new JButton("Show numbers");
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c!= null) c.dispose();
              if(a!= 0 && stepN!=0) c = new CoordinatedPlane(a, minN, maxN, stepN, true);
            }
        });

        JButton hide = new JButton("Hide numbers");
        hide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c!=null) c.dispose();
                if(a!=0 && stepN!= 0) c = new CoordinatedPlane(a, minN, maxN, stepN, false);
            }
        });

        JLabel instructions = new JLabel("If you want to make an image of this graphic, follow instructions");
        instructions.setFont(new Font("Georgia", Font.PLAIN, 15));
        instructions.setForeground(Color.WHITE);


        JLabel nameOfFile = new JLabel("Enter the name of file: ");
        nameOfFile.setForeground(Color.WHITE);
        nameOfFile.setFont(new Font("Georgia", Font.PLAIN, 15));

        JTextField name = new JTextField(20);


        JButton makeImage = new JButton("Make an image");
        makeImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(c!=null && !name.getText().contains(" ") && !name.getText().equals("")) {
                    try {
                        c.makeAnImage(name.getText());
                    } catch (IOException | AWTException ex) {
                        JOptionPane.showMessageDialog(null, "Impossible to make an image");
                    }
                }
            }
        });
        panel.add(button);
        panel.add(show);
        panel.add(hide);
        panel.add(instructions);
        panel.add(nameOfFile);
        panel.add(name);
        panel.add(makeImage);


        frame.add(panel);
        frame.setVisible(true);


    }


}
