package com.nice.good.rfid.netty;

import com.basung.common.command.Command;
import com.basung.common.core.model.Response;
import com.nice.good.utils.SpringUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;

public class ClientHandler extends SimpleChannelInboundHandler<Response> {

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, Response response) throws Exception {
//		System.out.println("####command:"+HexConvertUtils.bytesToHexString(new byte[]{response.getCommand()}));
		handlerResponse(response);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
//		Request request = Request.valueOf(Command.CMD_INVENTORY_ONCE, null);
//		ctx.channel().writeAndFlush(request);
		System.out.println("channelActive");
	}

	private void handlerResponse(Response response){
		if (Command.CMD_INVENTORY_ONCE == response.getCommand()) {
			SpringUtil.getBean(CodcUtil.class).inventoryOnce(response.getData());
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Disconnect the connection with the server~~~");
	}
}
