package com.bmb.chatbot;

/**
 * Author Bassem Al Mahow
 */
public class Client 
{
    /**
     *
     * @param args
     */
    public static void main(String[] args) 
    {
        Client_Chat client=new Client_Chat("127.0.0.1");
        client.startRunning();
    }
    //---------------------------------------------------------------------------
}
