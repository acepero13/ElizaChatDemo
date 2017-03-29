/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.eliza.saSimulate;

import de.dfki.connection.LiveSenderReceiver;
import de.dfki.connection.Sender;

/**
 *
 * @author Robbie
 */
public class SA_Simulate {
    LiveSenderReceiver saSystemLiveSenderReceiver;
    LiveSenderReceiver saUserenderReceiver;
    int count=0;
    
    private String receiveSystemMessage = "";
    
    private String receiveUserMessage = "";
    Sender messageSender;
    
    public SA_Simulate(){
        
    }
    
    public final void initial()
    {
        count=0;
        
        LiveSenderReceiver saSystemLiveSenderReceiver = new LiveSenderReceiver(8000);
        saSystemLiveSenderReceiver.start();
        
        LiveSenderReceiver saUserenderReceiver = new LiveSenderReceiver(8002);
        saUserenderReceiver.start();
        
        messageSender = new Sender(8003);
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    receiveSystemMessage = saSystemLiveSenderReceiver.recvString();
                    
                    if (!receiveSystemMessage.equalsIgnoreCase(""))
                    {
                        handleReceiveMessage(receiveUserMessage);
                    }
                }
            }
        }).start();
        
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                while (true)
                {
                    receiveUserMessage = saUserenderReceiver.recvString();
                    
                    if (!receiveUserMessage.equalsIgnoreCase(""))
                    {
                        handleReceiveMessage(receiveUserMessage);
                    }
                }
            }
        }).start();
    }
    
    public void handleReceiveMessage(String s){
        count++;
        if(count == 6){
            count =0;
        }
        
        String str = "{emoton: " + count + "; text: "+ "\"" + s + "\""+  "}";
        messageSender.send(str);
    }
    
    
}
