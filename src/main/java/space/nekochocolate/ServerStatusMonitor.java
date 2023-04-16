package space.nekochocolate;

/*
制作成常驻程序
HTTP服务
*/

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ServerStatusMonitor
{
	public static void main(String[] args)
	{
		SystemLoadInfo obj_SystemLoadInfoClass = new SystemLoadInfo();
		try
		{
			HttpServer obj_HttpServer = HttpServer.create(new InetSocketAddress(6789), 0);
			System.out.println("Listening on port 6789...");
			obj_HttpServer.createContext("/", exchange ->
			{
				/*
				 curl localhost:6789/hhh?a=1?v=114
				 System.out.println(exchange.getRequestURI().getPort()); // -1
				 System.out.println(exchange.getRequestURI().getPath()); // /hhh
				 System.out.println(exchange.getRequestURI().getQuery()); // a=1
				 System.out.println(exchange.getRequestURI().getRawQuery()); // a=1
				 System.out.println(exchange.getRequestURI().toString()); // /hhh?a=1
				*/
				byte[] SystemInfoResults;
				SystemInfoResults = obj_SystemLoadInfoClass.getSystemInfo()
						.getBytes(StandardCharsets.UTF_8);
				// byte[] SystemInfoResults = "114514".getBytes(StandardCharsets.UTF_8);
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