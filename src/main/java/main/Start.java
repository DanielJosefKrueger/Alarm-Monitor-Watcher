package main;

import configuration.Configuration;
import configuration.ConfigurationLoader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

public class Start {

    public static long lastEmailNotifictation =0;

    public static void main(String[] args) {
        EMailSender eMailSender = new EMailSender(new ConfigurationLoader());
        Configuration configuration = new ConfigurationLoader().get();
        while(true){
            boolean ok = ConnectAndTest();
            if(!ok){
                long time = System.currentTimeMillis();
                if(time- lastEmailNotifictation > configuration.getIntervalMinutes()*60*1000){
                    eMailSender.sendEmailToAdmin("Alarmmonitor ist nicht erreichbar", false);
                    lastEmailNotifictation = time;
                }
            }

            try {
                TimeUnit.SECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static boolean ConnectAndTest() {
        Configuration configuration = new ConfigurationLoader().get();
        try{
            Socket socket = new Socket("localhost", configuration.getPort());
            socket.setSoTimeout(10*1000);
            try(InputStreamReader in = new InputStreamReader(socket.getInputStream())){
                int read = in.read();
                if(read==1){
                    System.out.println("Alarmmonitor reagiert auf Anfrage.");
                    return true;
                }else{
                    System.out.println("Keine Reaktion des Alarmmonitors");
                    return false;
                }
            }finally {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
