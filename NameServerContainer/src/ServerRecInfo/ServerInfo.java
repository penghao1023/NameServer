package ServerRecInfo;

import DDS_Transfer.IDDS_Protocol;
import ProtocolsManager.ProtocolManager;
import ProxyExchange.IDataCallBack;
import ProxyExchange.MulticastRec;

import PublicModel.ServerBinds;
import Tools.MsgPackTool;
import nameServerContainer.ManagerConfig;


public class  ServerInfo {
	IDataCallBack  sinfo=new RecServerInfo();

  /**
 *  ���շ������Ϣ����
 */
public void AddServer()
  {
	//��ʼ�����շ���˵ķ�����Ϣ������223�˿�
	MulticastRec  rec=new MulticastRec();
	rec.port=223;
	
	rec.Start(sinfo);
  }
  /**
   * ���������ӷ�����Ϣ���ͻ���
 * @param info
 */
public void SendInfo(ServerBinds info)
  {
	  StringBuilder error=new StringBuilder();
	  byte[]data=null;
	  MsgPackTool tool=new MsgPackTool();
 	  data=tool.Serialize(info, error);
	Boolean value=  ManagerConfig.hashConfig.getOrDefault(info.name, null);
	if(value==null)
	{
		value=ManagerConfig.IsManager;
	}
	 if(value)
	 {
		 try {
			 IDDS_Protocol protocols=(IDDS_Protocol)ProtocolManager.getInstance().CreateObject(ManagerConfig.Communication_Type);
			 protocols.CreateClient();
			 protocols.Connect(ManagerConfig.ManagerAddr, ManagerConfig.ManagerPort);
			 protocols.ClientSocketSend(data);
			 protocols.ClientSocketClose();
		 
		 } catch (InstantiationException e) {
			
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			
			e.printStackTrace();
		}
	 }
//	  MulticastSend sendClient=new MulticastSend();
//	  sendClient.SendData(data);
	
  }



}