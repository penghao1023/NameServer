package nameServerClient;



import Tools.MsgPackTool;
import nameServerInterface.IPoxyObj;
import nameServerInterface.NetData;


 /**
 * @author jinyu
 * ����ͨ�ſͻ��˴���ʵ����
 * ������ͨѶ
 */
class ClientToServer implements IPoxyObj{

	private ServerConnectPorxy proxy;
	public String ServerName="";
	public ClientToServer(ServerConnectPorxy initproxy)
	{
		proxy=initproxy;
	}
	@Override
	public byte[] GetData(byte[] data) {
	
	    byte[]dataStream=NetStream("GetData",data);
		return proxy.GetData(dataStream);
	}

	@Override
	public void SetData(byte[] data) {
	
		byte[]dataStream=NetStream("SetData",data);
		proxy.SetData(dataStream);
	}

	@Override
	public void CallData(byte[] data) {
		
		byte[]dataStream=NetStream("CallData",data);
		proxy.SetData(dataStream);
	}
	/**
	 * 
	* @Name: NetStream 
	* @Description: �����ݴ����ɹ�����Ϣ
	* @param name
	* @param data
	* @return  ����˵�� 
	* @return byte[]    �������� 
	* @throws
	 */
   private byte[] NetStream(String name,byte[]data)
   {
	    StringBuilder error=new StringBuilder();
	    NetData curData=new NetData();
	    curData.fun_Name=name;
	    curData.serverName=ServerName;
		curData.data=data;
		MsgPackTool tool=new MsgPackTool();
	    byte[] curBytes=	tool.Serialize(curData, error);
	    return curBytes;
   }

	/** 
	* @Name: Close 
	* @Description: TODO(������һ�仰�����������������)   ����˵�� 
	* @return void    �������� 
	* @throws 
	*/
	public void Close() {
		proxy.Close();
		
	}
}