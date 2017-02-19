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
   private  boolean  Is_Start=false;
   private  RecDataCall recClient=null;
   public String type_Name="";

     /**
      * 
     * ����һ���µ�ʵ�� ServerPorxy.    
     *    
     * @param address ����ͨѶ��ַ
     * @param port   �˿�
     * @param t_type ͨѶ����
      */
   public ServerPorxy(String address, int port, String t_type) {
		server = new ClientToServer();
		server.ip = address;
		server.port = port;
		server.type_Name=t_type;
		type_Name=t_type;
	}
   /**
    * 
   * @Name: InitServerThread 
   * @Description: ��ʼ������ ͨѶ����
   * @param serverName ��������
   * @param serverObj  ����ʵ��˵�� 
   * @return void    �������� 
   * @throws
    */
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

/**
 * 
* @Name: SendTCPNatPackage 
* @Description: �󶨵ķ����������TCP��ǽ����ֻ��TCP����
* @param addr ��������ַ
* @param port  ����˵�� 
* @return void    �������� 
* @throws
 */
public void SendTCPNatPackage(String addr,int port)
{
	if(server!=null)
	{
		server.ProxySendNatPackage(addr, port);
		
	}
}
}