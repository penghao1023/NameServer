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
   private  boolean  Is_Start=false;
   private  RecDataCall recClient=null;
   public String type_Name="";

     /**
      * 
     * 创建一个新的实例 ServerPorxy.    
     *    
     * @param address 服务通讯地址
     * @param port   端口
     * @param t_type 通讯类型
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
   * @Description: 初始化启动 通讯代理
   * @param serverName 服务名称
   * @param serverObj  服务实例说明 
   * @return void    返回类型 
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
* @Description: 绑定的服务代理发送TCP穿墙包，只有TCP类型
* @param addr 管理器地址
* @param port  参数说明 
* @return void    返回类型 
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
