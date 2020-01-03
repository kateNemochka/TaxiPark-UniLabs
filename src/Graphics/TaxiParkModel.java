package Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class TaxiParkModel {
    private static boolean active = false;
    private static final Random rnd = new Random();
    private JPanel mainPanel;
    private JButton startAutoModeButton;
    private JButton pauseButton;
    private JPanel modelPanel;


    public TaxiParkModel() {

        startAutoModeButton.addActionListener(e -> {
            ((Circles) modelPanel).startTimer();
            modelPanel.setBackground(Color.WHITE);
        });
        pauseButton.addActionListener(e -> ((Circles) modelPanel).stopTimer());
    }

    private void createUIComponents() {
        modelPanel = new Circles();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }


    class Circles extends JPanel{
        // 320 x 485
        private int RADIUS = 7;
        ArrayList<Ellipse2D> circles;
        ArrayList<Integer> carIDs;
        private Ellipse2D current;

        private Timer carManagementTimer = new Timer(3000, (ActionEvent e) -> {
            double test = rnd.nextDouble();
            if (test > 0.7) {
                ConstantsClass.taxiPark.addRandomCar();
                int id = Collections.max(ConstantsClass.taxiPark.getTaxiIDs());
                add(id, rnd.nextInt(400)+80, rnd.nextInt(150)+80, RADIUS*2);
            }
            else if (test < 0.25 && circles.size() > 0) {
                remove(circles.get(rnd.nextInt(circles.size())));
            }
        });
        private Timer rideManagementTimer = new Timer(700, (ActionEvent e) -> {
            double test = rnd.nextDouble();
            if (test < 0.7) {
                if (circles.size() > 0) {
                    ConstantsClass.taxiPark.addRandomRides(1);
                    System.out.println("Added new ride");
                    update();
                }
            }
        });


        public Circles(){
            circles = new ArrayList<>();
            carIDs = new ArrayList<>();
            current = null;
            addMouseListener(new MouseHandler());
            addMouseMotionListener(new MouseMotionHandler());

            for (int id : ConstantsClass.taxiPark.getTaxiIDs()) {
                add(id, rnd.nextInt(485), rnd.nextInt(320), RADIUS*2);
            }
            update();
        }

        public void startTimer() {
            carManagementTimer.start();
            rideManagementTimer.start();
        }
        public void stopTimer() {
            carManagementTimer.stop();
            rideManagementTimer.stop();
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            int index = 0;
            if (circles.size()>0) {
                for (Ellipse2D c : circles) {
                    g2.draw(c);
                    g2.setColor(getColor(ConstantsClass.taxiPark.getTaxi(carIDs.get(index)).getTaxiType()));
                    g2.fill(c);
                    int taxiID = ConstantsClass.taxiPark.getTaxi(carIDs.get(index++)).getID();
                    g2.setColor(Color.BLACK);
                    g2.drawString("Taxi"+taxiID, (int) c.getX(), (int) c.getY()-5);
                }
            }
        }

        public Ellipse2D find(Point2D p){
            for (Ellipse2D c: circles){
                if(c.contains(p))
                    return c;
            }
            return null;
        }
        public void add(Point2D p){
            ConstantsClass.taxiPark.addRandomCar();
            int id = Collections.max(ConstantsClass.taxiPark.getTaxiIDs());
            double x = p.getX();
            double y = p.getY();
            current = new Ellipse2D.Double(x, y, 2 * RADIUS, 2 * RADIUS);
            circles.add(current);
            carIDs.add(id);
            repaint();
        }
        public void add(int carID, double x, double y, double d){
            current = new Ellipse2D.Double(x, y, d, d);
            circles.add(current);
            carIDs.add(carID);
            repaint();
        }
        public void remove(Ellipse2D c){
            if(c==null)
                return;
            if (c==current)
                current = null;
            carIDs.remove(circles.indexOf(c));
            circles.remove(c);
            repaint();
        }

        public void update() {
            System.out.println("updating");
            int index = 0;
            for (Ellipse2D c : circles) {
                double x = c.getX();
                double y = c.getY();
                double d = 2 * (RADIUS + ConstantsClass.taxiPark.getTaxi(carIDs.get(index)).getProfit()/100);
                c.setFrame(x, y, d, d);
                System.out.println(carIDs.get(index)+" "+x+" "+y+" "+d+" ");
                index++;
            }
            repaint();
        }


        private class MouseHandler extends MouseAdapter {
            @Override
            public void mousePressed(MouseEvent event){
                current = find(event.getPoint());
                if(current == null)
                    add(event.getPoint());
                /*else {
                    double x = current.getX();
                    double y = current.getY();
                    double d = current.getWidth() + 2 * RADIUS_INCREMENT;
                    remove(current);
                    add(x, y, d);
                }*/
            }

            @Override
            public void mouseClicked(MouseEvent event) {
                current = find(event.getPoint());
                if(current!=null&&event.getClickCount()>=2)
                    remove (current);
            }
        }

        private class MouseMotionHandler implements MouseMotionListener {
            public void mouseDragged(MouseEvent event) {
                if (current!=null){
                    int x = event.getX();
                    int y = event.getY();
                    double d = current.getWidth();
                    current.setFrame(x-RADIUS, y-RADIUS, d, d);
                    repaint();
                }
            }

            public void mouseMoved(MouseEvent event) {
                if (find(event.getPoint())==null)
                    setCursor(Cursor.getDefaultCursor());
                else
                    setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
            }
        }

        private Color getColor(String type) {
            if (type.equals("Standard"))
                return new Color(0,0,255,125);
            else if (type.equals("Green"))
                return new Color(0,255,0,125);
            else if (type.equals("Comfort"))
                return new Color(255, 0, 0, 125);
            return new Color(0,0,0,125);
        }
    }


    public static void main(String[] args) {
        JFrame f = new JFrame("Taxi Park Model");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setContentPane(new TaxiParkModel().mainPanel);
        f.pack();
        f.setVisible(true);
    }
}
