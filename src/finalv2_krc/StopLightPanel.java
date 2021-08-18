package finalv2_krc;

/**
 * StopLightPanel.java
 * Created on Aug 6, 2020
 * @author Keith Combs II
 * Purpose: Runnable class that creates a panel and when its thread starts it gathers the 
 * data for the cars to show location and speed. The first car created has a hardcoded speed
 * the next cars are random
 */
import java.awt.Color;
import javax.swing.*;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StopLightPanel extends JPanel implements Runnable{
   
    
    SLDraw light = new SLDraw();
    int slTimer = 0;
    int meterMark = 0;
    
    String color = "";
    
    public StopLightPanel(int m) throws InterruptedException{
        this.meterMark = m; 
        
         light.setPreferredSize(new Dimension(160,160));
         
         add(light);
         add(new JLabel(this.meterMark+" Meters"));
  
    }
    @Override
    public void run(){
        
         String name = Thread.currentThread().getName();
        System.out.println("MeterMark"+ " on: " + this.meterMark );
        System.out.println("Building Stoplight"+ " on: " + name );
        
        while(GUI.getRunningState() == true){
            if(!GUI.getPausedState()){
                try {

                        if(light.activeLight.equals("red")){

                            light.changeColor();
                            Thread.sleep(6000);
                        }else if( light.activeLight.equals("yellow")){

                            light.changeColor();
                            Thread.sleep(15000); 
                        }else{

                            light.changeColor();
                            Thread.sleep(4000); 
                        }

                    } catch (InterruptedException ex) {
                        Logger.getLogger(StopLightPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }

            }
        }
    }
    public String getActiveLight(){
        return light.activeLight;
    }
    public int getMeterMark(){
        return this.meterMark;
    }

}

class SLDraw extends JComponent{
    Color go = Color.gray;
    Color slow = Color.gray;
    Color stop = Color.RED;
    
    String activeLight = "red";
       
    @Override
    public void paintComponent(Graphics g) {
            
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 100, 140);
        
        g.setColor(Color.BLACK);
        g.drawRect(0,0,100,140);
        
        g.setColor(stop);
        g.fillOval(35,15,30,30);
        g.setColor(slow);
        g.fillOval(35,55,30,30);
        g.setColor(go);
        g.fillOval(35,95,30,30);
        
       
    }

    public synchronized void  changeColor() throws InterruptedException{
        go = Color.gray;
        slow = Color.gray;
        stop = Color.gray;        
                
           
        
        
            if(activeLight.equals("red")){
                
                activeLight = "green";
                go = Color.GREEN;
                
            }else if(activeLight.equals("green")){
                
                activeLight ="yellow";
                slow = Color.YELLOW;
                
            }else{
                
                activeLight ="red";
                stop = Color.red;
                
            }

        repaint();
    }
    
    public synchronized String getCurrentColor(){
        return this.activeLight;
    }
    

}