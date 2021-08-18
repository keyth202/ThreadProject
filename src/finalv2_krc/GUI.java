package finalv2_krc;

/**
 * GUI.java
 * Created on Aug 5, 2020
 * @author Keith Combs II
 * Purpose: Contains the logic for the GUI. The creation of the stoplights and cars 
 * are in this class as well as starting the threads
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Random;

public class GUI {
    
    public static int masterTimer = 0;
    public int masterStopNum = 3; 
    public int masterCarNum = 0; 
    private static boolean running = false;
    private static boolean repeat = false;
    private static boolean isPaused = false;
    private Random r = new Random();
    private ArrayList<StopLightPanel> slArrayList = new ArrayList<>();
    
    //private JPanel jPanel1 = new JPanel();
    private JPanel controlPanel = new JPanel();
    private JPanel attrPanel = new JPanel();
    private JPanel viewPanel = new JPanel();
     
    private JToggleButton loopToggle = new JToggleButton();
    private JButton simulationBtn = new JButton();
    private JButton intersectionBtn = new JButton();
    private JButton carBtn = new JButton();
    private JButton pauseBtn = new JButton();
    private JButton startBtn = new JButton();
    
    public static JLabel timer = new JLabel();
   

    public GUI() {
        //super("Final");
        initComponents();
    }
    
    public static int getMasterTimer(){
        return masterTimer;
    }
    
    private void initComponents(){
         JFrame mainFrame = new JFrame("Final");
         
         mainFrame.setLayout(new BorderLayout());
         
        // Control Panel
        simulationBtn.setText("Begin Simulation");
        loopToggle.setText("Loop");
        pauseBtn.setText("Pause");
        startBtn.setText("Start");
        intersectionBtn.setText("Add Intersection");
        carBtn.setText("Add Car");
        timer.setText("Time");
         
                 
         //Adding to Control Pane
        
        controlPanel.add(simulationBtn);
        controlPanel.add(loopToggle);
        controlPanel.add(pauseBtn);
        controlPanel.add(startBtn);
        controlPanel.add(intersectionBtn);
        controlPanel.add(carBtn);
        controlPanel.add(timer);
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        
        
        
        //---------------Add action events to control Panel----------------------
        simulationBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int press = 0;
                try {
                    if(running == false && press == 0){
                        running = true;
                        press++;
                        Clock time = new Clock();
                    
                        //Building three stoplights
                        viewPanel.setLayout(new GridLayout(0,2));
                        StopLightPanel light1 = new StopLightPanel(1000);
                        StopLightPanel light2 = new StopLightPanel(2000);
                        StopLightPanel light3 = new StopLightPanel(3000);
                        
                        viewPanel.add(light1);
                        
                        viewPanel.add(light2);
                        
                        viewPanel.add(light3);
                        
                        
                         Thread td1 = new Thread(light1);
                         Thread td2 = new Thread(light2);
                         Thread td3 = new Thread(light3);
                         
                         td1.start();
                         
                         td2.start();
                         
                         td3.start();
                         
                         
                        slArrayList.add(light1);
                        slArrayList.add(light2);
                        slArrayList.add(light3);
                        //Build first car
                        CarPanel cp1 = new CarPanel(masterCarNum, time,15, slArrayList);
                            
                        attrPanel.add(cp1);
                        Thread td4 = new Thread(cp1);
                        td4.start();
                                                
                        attrPanel.setLayout(new BoxLayout(attrPanel, BoxLayout.PAGE_AXIS));
                        attrPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Attribute Panel"));
                        mainFrame.add(viewPanel, BorderLayout.CENTER);
                        
                    }

                } catch (InterruptedException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        loopToggle.addItemListener(new ItemListener() { 
  
            @Override
            public void itemStateChanged(ItemEvent itemEvent){ 
  
                // event is generated in button 
                int state = itemEvent.getStateChange(); 
  
                // if selected print selected in console 
                if (state == ItemEvent.SELECTED) { 
                    System.out.println("Loop Activated"); 
                    if(repeat == false){
                        repeat = true;
                    }
                } 
                else { 
                    System.out.println("Loop Deactivated"); 
                    if(repeat == false){
                        repeat = true;
                    }
                } 
            } 
        });
        pauseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(isPaused == false){
                    isPaused = true;
                }
            }
        });
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if(isPaused == true){
                    isPaused = false;
                }
            }
        });
        
        intersectionBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JFrame frame = new JFrame();
                masterStopNum++;
                if(masterStopNum<6){
                     try {
                        StopLightPanel light = new StopLightPanel(masterStopNum*1000);
                        Thread td = new Thread(light);
                        td.start();
                        viewPanel.add(light);
                        slArrayList.add(light);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(frame,"Sorry, you can only have 5 stoplights in this simulation", "Stop Right There",
                        JOptionPane.ERROR_MESSAGE);
                }
                   
            }
        });
        
        carBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                JFrame frame = new JFrame();
                masterCarNum++;
                if(masterCarNum <3){
                    Clock time = new Clock();
                    CarPanel cp = new CarPanel(masterCarNum,time,r.nextInt(35-10)+10, slArrayList);
                    Thread td = new Thread(cp);
                    td.start();
                    attrPanel.add(cp);
                }else{
                    JOptionPane.showMessageDialog(frame,"Only 3 cars allowed", "Stop Right There",
                        JOptionPane.ERROR_MESSAGE);
                }
                
             
            }
        });
        
        
         
        mainFrame.add(controlPanel, BorderLayout.NORTH);
        mainFrame.add(attrPanel, BorderLayout.WEST);
         
         
         
         
        
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900,800);
        mainFrame.setVisible(true);
        
    }
    

    
    public static void setMasterTimer(int t){
        masterTimer = t;
    }
    
    public static boolean getRunningState(){
        return running;
    }
    public static boolean getloopState(){
        return repeat;
    }
    public static boolean getPausedState(){
        return isPaused;
    }
}
