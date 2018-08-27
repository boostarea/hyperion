package com.ooooor.advanced.reactor;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 */
public class Reactor implements Runnable {
    final Selector selector;
    final ServerSocketChannel serverSocket;

    Reactor(int port) throws IOException {
        selector = Selector.open();
        serverSocket = ServerSocketChannel.open();
        serverSocket.socket().bind(new InetSocketAddress(port));

        serverSocket.configureBlocking(false);
        SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
        sk.attach(new Acceptor());

    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set selected = selector.selectedKeys();
                Iterator it = selected.iterator();
                while(it.hasNext()) {
                    dispatch((SelectionKey) it.next());
                }
                selected.clear();
            }

        } catch (IOException e) {

        }
    }


    void dispatch(SelectionKey k) {
        Runnable r = (Runnable) k.attachment();
        if (r != null) r.run();
    }

    class Acceptor implements Runnable {
        public void run() {
            try {
                SocketChannel c = serverSocket.accept();
                if (c != null) new Handler(selector, c);
            } catch (IOException e) {

            }
        }
    }

    boolean inputIsComplete() {return true;}

    boolean outputIsComplete() {return true;}

    void process() {

    }

    final class Handler implements Runnable {
        final SocketChannel socket;
        final SelectionKey sk;
        final int MAXIN = 512;
        final int MAXOUT = 1024;

        ByteBuffer input = ByteBuffer.allocate(MAXIN);
        ByteBuffer output = ByteBuffer.allocate(MAXOUT);

        static final int READING = 0, SENDING = 1;
        int state = READING;

        Handler(Selector se1, SocketChannel c) throws IOException {
            socket = c;
            c.configureBlocking(false);
            sk = socket.register(se1, 0);
            sk.attach(this);
            sk.interestOps(SelectionKey.OP_READ);
            selector.wakeup();
        }

        public void run() {
            try {
                if (state == READING) read();
                else if (state == SENDING) send();
            } catch (IOException e) {

            }
        }

        void read() throws IOException {
            socket.read(input);
            if (inputIsComplete()) {
                process();
                state = SENDING;
                sk.interestOps(SelectionKey.OP_WRITE);
            }
        }

        void send() throws IOException {
            socket.write(output);
            if (outputIsComplete()) sk.cancel();
        }

    }



}
