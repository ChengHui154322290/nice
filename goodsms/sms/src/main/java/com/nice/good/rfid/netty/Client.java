package com.nice.good.rfid.netty;

import com.basung.common.core.codc.CmdDecoder;
import com.basung.common.core.codc.CmdEncoder;
import com.basung.common.core.model.Request;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.net.InetSocketAddress;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Client {
	Bootstrap bootstrap = new Bootstrap();
	private Channel channel;
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	@PostConstruct
	public void init() {
		bootstrap.group(workerGroup);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ch.pipeline().addLast(new CmdDecoder());
				ch.pipeline().addLast(new CmdEncoder());
				ch.pipeline().addLast(new ClientHandler());
			}
		});
	}

	public void connect() throws InterruptedException {
		ChannelFuture connect = bootstrap.connect(new InetSocketAddress("192.168.254.201", 20058));
		connect.sync();
		channel = connect.channel();
		channel.closeFuture().sync();
	}

	public void shutdown() {
		workerGroup.shutdownGracefully();
	}

	public Channel getChannel() {
		return channel;
	}

	public void sendRequest(Request request) throws InterruptedException{
		if(channel == null || !channel.isActive()){
			Thread thread = new Thread(() -> {
				try {
					connect();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			thread.start();
			Thread.sleep(1000);
		}
		channel.writeAndFlush(request);
	}
}
