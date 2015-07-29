package com.example.joey.masterkey;

import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by joey on 7/29/15.
 */
public abstract class serverDao {

    public static String sendMessage(Message message){
        try {
            //connect to socket
            SocketChannel socket = SocketChannel.open();
            socket.configureBlocking(false);
            socket.connect(new InetSocketAddress("soerendonk.iwa.nu", 44445));
            while(!socket.finishConnect()) {
            }
            //open bytebuffer
            ByteBuffer buf = ByteBuffer.allocate(1500);
            buf.clear();

            //fill bytebuffer & send
            buf.put(message.toString().getBytes());
            buf.flip();
            while(buf.hasRemaining()){
                socket.write(buf);
            }

            //clear bytebuffer, read bytes from socket, and return
            buf.clear();
            while(true){
                if(socket.read(buf)>0){
                    buf.flip();
                    String data = new String(buf.array(), buf.position(), buf.limit());
                    //data = data.trim();
                    return data;
                }
            }

        } catch (IOException e) {
            Log.d("MrKey", "Error sending message: " + e.getMessage());
        }
        return null;

    }
}
