package nameServerClient;

import ProxyExchange.BridgeClient;

/**
 * @author jinyu
 * �������ӷ����
 * �м������
 */
public class ServerConnectPorxy {

private	BridgeClient client=new BridgeClient();
	/**
	 * 
	* ����һ���µ�ʵ�� ServerConnectPorxy.    
	* ����ʵ���������ӷ���
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