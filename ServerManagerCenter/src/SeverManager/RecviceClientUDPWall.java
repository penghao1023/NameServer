  
/**    
* �ļ�����RecviceClientUDPWall.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��18��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SeverManager;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import UDPPeerToPeer.ServerXml;
import UDPPeerToPeer.ServerXmlInfo;

/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�RecviceClientUDPWall    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��18�� ����7:04:59    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��18�� ����7:04:59    
* �޸ı�ע��    
* @version     
*     
*/
public class RecviceClientUDPWall implements IRecMsg {
	private IDDS_Protocol protocol;
	/*
	* Title: RecData
	*Description: 
	* @param address
	* @param data 
	 
	*/
	@Override
	public void RecData(String address, byte[] data) {
		String xml=new String(data);
		ServerXml server=ServerXmlInfo.Analysis(xml);
		//���͸�����˴�ǽ��Ϣ
		PostWallCmd.UDPwall(address, server);
		//
	   String  coninfo=	ServerXmlInfo.HeaderXml(server.name, address,"");
	   data=coninfo.getBytes();
	   //��ǰ���Ӹ��߿ͻ��˿������ӷ������
		protocol.ServerSocketSendData(data);
		
	}

	/*
	* Title: SaveInstance
	*Description: 
	* @param call 
	 
	*/
	@Override
	public void SaveInstance(Object call) {
		protocol=(IDDS_Protocol)call;
		
	}

}