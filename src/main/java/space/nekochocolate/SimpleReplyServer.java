package space.nekochocolate;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class SimpleReplyServer
{
	public static void main(String[] args)
	{
		try
		{
			HttpServer obj_HttpServer = HttpServer.create(new InetSocketAddress(718), 0);
			System.out.println("Listening on port 718...");
			obj_HttpServer.createContext("/", exchange ->
			{
				byte[] SystemInfoResults;
				SystemInfoResults = "功能失效,系统维护中.".getBytes(StandardCharsets.UTF_8);
				exchange.getResponseHeaders()
						.add("Content-Type", "text/html; charset=UTF-8");
				exchange.sendResponseHeaders(200, SystemInfoResults.length);
				exchange.getResponseBody()
						.write(SystemInfoResults);
				exchange.close();
			});
			obj_HttpServer.start();
		} catch (IOException e)
		{
			e.printStackTrace();
			System.exit(500);
		}
	}
}