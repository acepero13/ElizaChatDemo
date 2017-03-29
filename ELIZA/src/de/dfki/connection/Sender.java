/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.dfki.connection;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author EmpaT
 */
public class Sender {
    DatagramSocket senderSocket;
    DatagramPacket sendPacket;
    InetAddress inetAddress;
    int remotePort = 9876;
    
    public  Sender(int port )
    {
        remotePort = port;
        try {
            inetAddress = InetAddress.getByName("localhost");
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
    
    public void send(String message)
    {
        byte[] sendData = message.getBytes();
        try {
            senderSocket = new DatagramSocket();
            sendPacket = new DatagramPacket(sendData, sendData.length, inetAddress, remotePort);
            senderSocket.send(sendPacket);
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        senderSocket.close();
    }

    public InetAddress getInetAddress()
    {
        return inetAddress;
    }

    public void setInetAddress(InetAddress inetAddress)
    {
        this.inetAddress = inetAddress;
    }
    
    
    
}
