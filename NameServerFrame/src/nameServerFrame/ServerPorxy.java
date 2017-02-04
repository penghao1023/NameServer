package nameServerFrame;



import ProxyExchange.ClientToServer;


import nameServerFun.RecDataCall;
import nameServerInterface.IServer;

/**
 * 有一个通信接收实例与服务对象实例
 * @author jinyu
 * 服务代理中间生成
 * 同一个代理实例的的接收也只有一个
 */
public class ServerPorxy {
private	ClientToServer server = null;
     boolean  Is_Start=false;
     RecDataCall recClient=null;
//	  ServerPorxy(String host, int port,String type_nme) {
//		
//		server = new ClientToServer();
//		server.ip = host;
//		server.port = port;
//	}
   public ServerPorxy(String address, int port, String t_type) {
		server = new ClientToServer();
		server.ip = address;
		server.port = port;
		server.type_Name=t_type;
	}
public  void InitServerThread(String serverName,IServer serverObj)
   
   {
	   if(!Is_Start)
	   {
	      StringBuilder error = new StringBuilder();
	      recClient = new RecDataCall(serverName,serverObj);
	      server.InitServer(recClient, error);
	      Is_Start=true;
	   }
	   else
	   {
		   recClient.AddServer(serverName, serverObj);
	   }
   }
}
