package finalv2_krc;

/**
 * CarPanel.java
 * Created on Aug 9, 2020
 * @author Keith Combs II
 * Purpose: Creates a jpanel to add to the GUI with the car information on it.
 * Once the thread starts it updates itself with the time and location interacting 
 * with all of the stoplight threads by passing them in as an arraylist
 *
 */
import javax.swing.*;
import java.awt.Dimension;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;


public class CarPanel extends JPanel implements Runnable{
    
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    int time = 0; 
    int speed = 0;
    int xloc = 0;
    private JLabel carLabel = new JLabel();
    private JLabel timeStamp = new JLabel();
    private JTextField carSpdField = new JTextField();
    private JTextField carLocField = new JTextField();
    private JLabel status = new JLabel();
    private JLabel curLight = new JLabel();
    
    private int currentSpeed = 0;
    private ArrayList<StopLightPanel> slArrayList = new ArrayList<>();
    private Clock cl;
    
    
    
    
    String name = Thread.currentThread().getName();
   
    CarPanel(int num, Clock cl,int s, ArrayList<StopLightPanel> slA){
        this.speed = s;
        this.cl = cl;
        this.slArrayList = slA;
        
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setPreferredSize(new Dimension(150, 110));
        setMaximumSize(new Dimension(150, 110));
        
        System.out.println("We are here "+ name);
        int c = num+1;
        carLabel.setText("Car: "+ c);
        carSpdField.setText("Speed : "+speed);
        carLocField.setText("X-position: " +xloc);
        timeStamp.setText("Time: ");
        status.setText(" ");
        curLight.setText(" ");
        
        add(carLabel);
        add(carSpdField);
        add(carLocField);
        add(timeStamp);
        add(status);
        add(curLight);
        

    }
    
    
    
    //  1609/3600 to conver miles per hour to meters per second
    private void calcDist(int s){
        
        double add = (s/.447);
        this.xloc +=  Math.floor(add);
        //System.out.println("Dist "+ this.xloc);
    }
    
    @Override
    public void run(){
       
        while(GUI.getRunningState()== true){
            if(!GUI.getPausedState()){
                timeStamp.setText("Time: " +cl.clock+ "s");
                time++;
                timeStamp.setText("Running Time: "+time);
                if(GUI.getloopState() == true && this.xloc >=6000){
                    this.time = 0; 
                    this.xloc = 0;
                    System.out.println(this.time + this.xloc);
                    
                }
                    
                    status.setText(" ");
                    curLight.setText(" ");
                
                
                //System.out.println(slArrayList.get(0).getActiveLight());
                if(xloc<960){
                    calcDist(speed);
                } else if(xloc>961 &&  xloc <= 1000){
                    
                    switch(slArrayList.get(0).getActiveLight()){
                        case "red": 
                            carSpdField.setText("Speed : 0");
                           
                            break;
                        default:
                            calcDist(speed);
                            break;
                    }
                    status.setText("40m from 1000 m mark.");
                    curLight.setText("Light is "+slArrayList.get(0).getActiveLight());
                   
                }else if( xloc>1961 && xloc <= 2000){
                    
                    switch(slArrayList.get(1).getActiveLight()){
                        case "red": 
                            carSpdField.setText("Speed : 0");
                            
                            break;
                        default:
                            calcDist(speed);
                            break;
                    }
                    status.setText("40m from  2000 m mark.");
                    curLight.setText("Light is "+slArrayList.get(1).getActiveLight());
                }else if( xloc>2971 && xloc <= 3000){
                    
                    switch(slArrayList.get(2).getActiveLight()){
                        case "red": 
                            carSpdField.setText("Speed : 0");
                            
                            break;
                        default:
                            calcDist(speed);
                            break;
                    }
                    status.setText("40m from 3000 m mark.");
                    curLight.setText("Light is "+slArrayList.get(2).getActiveLight());
                }else if(slArrayList.size()== 4 &&xloc>3971 && xloc <= 4000){
                    switch(slArrayList.get(3).getActiveLight()){
                        case "red": 
                            carSpdField.setText("Speed : 0");
                            
                            break;
                        default:
                            calcDist(speed);
                            break;
                    }
                    status.setText("40m from  4000 m mark. \n Light is "+slArrayList.get(3).getActiveLight());
                }else if (slArrayList.size()== 5 &&xloc>4971 && xloc <= 5000){
                    switch(slArrayList.get(4).getActiveLight()){
                        case "red": 
                            carSpdField.setText("Speed : 0");
                            
                            break;
                        default:
                            calcDist(speed);
                            break;
                    }
                    status.setText("40m from  5000 m mark.");
                    curLight.setText("Light is "+slArrayList.get(4).getActiveLight());
                }else {
                    calcDist(speed);
                }
                
            }    
               
            //System.out.println(time+ " line 42 " + df2.format(xloc));
            carSpdField.setText("Speed : "+speed);
            carLocField.setText("X-Location: " +df2.format(xloc));
             try {
                 Thread.sleep(1000);
             } catch (InterruptedException ex) {
                 Logger.getLogger(CarPanel.class.getName()).log(Level.SEVERE, null, ex);
             }
            
        }
        
    }
}
