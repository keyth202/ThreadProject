
package finalv2_krc;
/**
 * Clock.java
 * Created on Aug 3, 2020
 * @author Keith Combs II
 * Purpose: Creates a master clock that shows the time and date in the gui 
 */

import java.util.Timer;
import java.util.TimerTask;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.Instant;

public class Clock {
    public int clock = 0;
    private SimpleDateFormat sdf = new SimpleDateFormat("hh.mm.ss");
    Timer nextSec = new Timer("Clock");
    
    Clock(){
        nextSec.scheduleAtFixedRate(new Ticker(),500,1000);
    }
    
    public int getClock(){
        return this.clock;
    }     
    class Ticker extends TimerTask{
        @Override
        public void run() {
             
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            GUI.timer.setText("Current Time: "+sdf.format(timestamp));
            
            clock++;
            GUI.setMasterTimer(clock);
            //System.out.println(clock);
        }
    }

}

