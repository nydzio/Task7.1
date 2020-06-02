package com.codebind;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class Main {

    public final static  int DEFAULT_PORT = 37;
    public final static String DEFAULT_HOST = "time.nist.gov";
    public static void main(String [] args){
        String hotName = DEFAULT_HOST;
        int port = DEFAULT_PORT;
        if(args.length > 0){
            hotName=args[0];
        }
        if(args.length > 1){
            port = Integer.parseInt(args[1]);
        }
        long differenceBetweenEpochs = 2208988800l;
        InputStream raw = null;
        try
        {
            Socket socket = new Socket(hotName, port);
            raw = socket.getInputStream();
            long secondsSince1900 = 0;
            for(int i = 0; i < 4; i++){
                secondsSince1900 = (secondsSince1900 << 8) | raw.read();
            }
            long secondsSince1970 = secondsSince1900 - differenceBetweenEpochs;
            long msSince1970 = secondsSince1970 * 1000;
            System.out.println("Seconds since 1900: " + String.valueOf(secondsSince1900));
            Date time = new Date(msSince1970);
            System.out.println("It is " + time + " at " + hotName);
        } catch (UnknownHostException uhEx) {
            System.err.println(uhEx);
        } catch (IOException ioEx) {
            System.err.println(ioEx);
        } finally {
            try{
                if (raw != null)
                    raw.close();
            }catch (IOException ex) {}
        }
    }
}
