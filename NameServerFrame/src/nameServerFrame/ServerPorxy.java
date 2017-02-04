package nameServerFrame;



import ProxyExchange.ClientToServer;


import nameServerFun.RecDataCall;
import nameServerInterface.IServer;

/**
 * ��һ��ͨ�Ž���ʵ����������ʵ��
 * @author jinyu
 * ��������м�����
 * ͬһ������ʵ���ĵĽ���Ҳֻ��һ��
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