package nia.chapter1;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/**
 * Netty—asynchronous connection
 */
public class ConnectExample {
    private static final Channel CHANNEL = new NioSocketChannel();

    public static void connect(int portNum) {
        Channel channel = CHANNEL;
        //异步地连接到远程节点
        ChannelFuture future = channel.connect(
                new InetSocketAddress("localhost", portNum));
        //注册一个 ChannelFutureListener，以便在操作完成时获得通知
        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    //创建Buffer
                    ByteBuf buffer = Unpooled.copiedBuffer(
                            "Hello", Charset.defaultCharset());
                    //数据异步发送，返回future
                    ChannelFuture wf = future.channel().writeAndFlush(buffer);
                    // ...
                } else {
                    //发生错误
                    future.cause().printStackTrace();
                }
            }
        });
    }
}
