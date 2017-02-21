  
/**    
* �ļ�����PostWallCmd.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��16��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SeverManager;

import java.util.concurrent.TimeUnit;

import DDS_Transfer.IDDS_Protocol;
import ProcessMessage.InnerMessage;

import UDPPeerToPeer.ServerXml;

/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�PostWallCmd    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��16�� ����11:58:47    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��16�� ����11:58:47    
* �޸ı�ע��    
* @version     
*     
*/
public class PostWallCmd {
	public static String UDPwall(String udpaddr,ServerXml serverinfo)
	{
		//֪ͨ��ǽ׼��
		String key=serverinfo.name+"@"+serverinfo.address;
		IDDS_Protocol protocol=	HeartBeatSocket.fireBeat.getOrDefault(key, null);
		String recAddr="";
		if(protocol!=null)
		{
			String wallInfo= udpaddr+"#"+key;
			byte[] data =wallInfo.getBytes();
			//���߷���˴�ǽ�����͵������Ǵ�ǽIP,��������Ƽ���ַ)
			protocol.ServerSocketSendData(data);
			//
		while(true)
		 {
		   try {
			 //ȡ�����մ�ǽ�ɹ��ķ���
			String info=FireWallPack.sucess.poll(4, TimeUnit.SECONDS);
			if(info==null)
			{
				//�ٴη�������ˣ��ٴη��ʹ�ǽ��
				protocol.ClientSocketSendData(data);
				InnerMessage.getInstance().PostMessage(null, "AddUILog", "֪ͨ�Ѿ����ʹ�ǽ��!");
			}
			String[] tmpaddr=info.split("#");
			
			if(tmpaddr[0].equals(key))
			{
				recAddr=tmpaddr[1];
				//�Ѿ����յ�����˷����Ĵ�ǽ֪ͨ
				break;
			}
			else
			{
				//���ǵ�ǰ����İ��ٴδ��룬��������̼߳���ʹ���ж�
				FireWallPack.sucess.put(info);
			}
			   
			 } catch (InterruptedException e) {
			
			e.printStackTrace();
		      }
		 }
		}
		return recAddr;
	}
}