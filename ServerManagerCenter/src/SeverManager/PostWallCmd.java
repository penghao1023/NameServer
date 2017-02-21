  
/**    
* 文件名：PostWallCmd.java    
*    
* 版本信息：    
* 日期：2017年2月16日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package SeverManager;

import java.util.concurrent.TimeUnit;

import DDS_Transfer.IDDS_Protocol;
import ProcessMessage.InnerMessage;

import UDPPeerToPeer.ServerXml;

/**    
*     
* 项目名称：ServerManagerCenter    
* 类名称：PostWallCmd    
* 类描述：    
* 创建人：jinyu    
* 创建时间：2017年2月16日 下午11:58:47    
* 修改人：jinyu    
* 修改时间：2017年2月16日 下午11:58:47    
* 修改备注：    
* @version     
*     
*/
public class PostWallCmd {
	public static String UDPwall(String udpaddr,ServerXml serverinfo)
	{
		//通知穿墙准备
		String key=serverinfo.name+"@"+serverinfo.address;
		IDDS_Protocol protocol=	HeartBeatSocket.fireBeat.getOrDefault(key, null);
		String recAddr="";
		if(protocol!=null)
		{
			String wallInfo= udpaddr+"#"+key;
			byte[] data =wallInfo.getBytes();
			//告诉服务端穿墙（发送的数据是穿墙IP,服务的名称及地址)
			protocol.ServerSocketSendData(data);
			//
		while(true)
		 {
		   try {
			 //取出接收穿墙成功的服务
			String info=FireWallPack.sucess.poll(4, TimeUnit.SECONDS);
			if(info==null)
			{
				//再次发给服务端，再次发送穿墙包
				protocol.ClientSocketSendData(data);
				InnerMessage.getInstance().PostMessage(null, "AddUILog", "通知已经发送穿墙包!");
			}
			String[] tmpaddr=info.split("#");
			
			if(tmpaddr[0].equals(key))
			{
				recAddr=tmpaddr[1];
				//已经接收到服务端发来的穿墙通知
				break;
			}
			else
			{
				//不是当前服务的包再次存入，供另外的线程继续使用判断
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
