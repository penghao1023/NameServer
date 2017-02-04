package nameServerClient;

import ProxyExchange.BridgeClient;

/**
 * @author jinyu
 * 用于连接服务端
 * 中间代理类
 */
public class ServerConnectPorxy {

private	BridgeClient client=new BridgeClient();
	/**
	 * 
	* 创建一个新的实例 ServerConnectPorxy.    
	* 创建实例并且连接服务
	* @param address
	* @param port
	* @param type_name
	 */
	public ServerConnectPorxy(String address, int port,String type_name) {
		
		client.IP=address;
		client.port=port;
		client.type_name=type_name;
		client.Create();
 		client.Connect();
	}
  public void Close()
  {
	  if(client!=null)
	  {
		  client.Close();
	  }
  }
	public byte[] GetData(byte[] para) {
	
	byte[]data=	client.RecData(para);
	return data;
	}

	public void SetData(byte[] data) {
	
		client.SendData(data);
		
	}

}
