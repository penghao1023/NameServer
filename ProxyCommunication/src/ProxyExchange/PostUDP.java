  
/**    
* 文件名：PostUDP.java    
*    
* 版本信息：    
* 日期：2017年2月9日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package ProxyExchange;

import DDS_Transfer.IDDS_Protocol;
import ISearchInfo.ISearchPrxoy;
import ProtocolsManager.ProtocolManager;

/**    
*     
* 项目名称：ProxyCommunication    
* 类名称：PostUDP    
* 类描述：    发送心跳以及UDP穿墙
* 通过心跳UDP穿墙；
* 创建人：jinyu    
* 创建时间：2017年2月9日 上午1:56:06    
* 修改人：jinyu    
* 修改时间：2017年2月9日 上午1:56:06    
* 修改备注：    
* @version     
*     
*/
public class PostUDP {
private	IDDS_Protocol curObj=null;
private	IDDS_Protocol recUDP=null;
private	String walladdr="";//管理器接收穿墙准备成功的地址
private  String beat="";//发送心跳地址
public   ISearchPrxoy search=null;
	public PostUDP(String addr,String beataddr)
	{
		walladdr=addr;
		beat=beataddr;
	}
	/**
	 * 
	* @Name: SendBeat 
	* @Description: 发送心跳包（UDP发送）
	* @param xml  参数说明 
	* @return void    返回类型 
	* @throws
	 */
public void  SendBeat(String xml)
{
	if(curObj==null)
	{
		Object obj = null;
	
			try {
				obj = ProtocolManager.getInstance().CreateObject("UDP");
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			}
		
		  if(obj!=null)
		  {
			    curObj = (IDDS_Protocol)obj;
			    curObj.CreateClient();
			    String[]beataddr=beat.split(":");
			    curObj.Connect(beataddr[0], Integer.valueOf(beataddr[1]));
		  }
	}
	//发送心跳
	byte[]data=xml.getBytes();
	curObj.ClientSocketSendData(data);
}

/**
 * 
* @Name: StartRec 
* @Description: 开始接收管理器发来的穿墙消息,并且发出准备好的信息
* @return void    返回类型 
* @throws
 */
public void StartRec()
{
	if(curObj==null)
	{
		Object obj = null;
	
			try {
				obj = ProtocolManager.getInstance().CreateObject("UDP");
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
			
				e.printStackTrace();
			}
		
		  if(obj!=null)
		  {
			    curObj = (IDDS_Protocol)obj;
			    curObj.CreateClient();
			    String[]beataddr=beat.split(":");
			    curObj.Connect(beataddr[0], Integer.valueOf(beataddr[1]));
		  }
	}
	if(curObj!=null)
	{
		Thread rec=new Thread(new Runnable()
				{

					@Override
					public void run() {
					while(true)
					{
						//心跳端口接收穿墙消息
					 byte[]data=curObj.RecClientSocketData();
					 //接收管理器传过来的穿墙地址；解析穿墙的地址
					 String wallInfo=new String(data);
					 String[]info=wallInfo.split("#");
					 //穿墙挖洞，发送穿墙消息
					 if(search!=null)
					 {
						 String[]servername=info[1].split("@");
						 search.SendData(servername[0], info[0], data);
						
					 }
//					 if(recUDP==null)
//					 {
//						 Object obj=null;
//						 try {
//							 obj = ProtocolManager.getInstance().CreateObject("UDP");
//						} catch (InstantiationException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						} catch (IllegalAccessException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//						 if(obj!=null)
//						  {
//							 //通过对应的服务发送穿墙
//							 recUDP = (IDDS_Protocol)obj;
//							 recUDP.CreateClient();
//							 String[]addr=info[0].split(":");
//							 recUDP.Connect(addr[0],Integer.valueOf(addr[1]));
//							 //
//							 byte[] test="test".getBytes();
//							 recUDP.ClientSocketSendData(test);
//						  }
//					 }
					 //
					 String localhost=recUDP.GetClientAddress();
					 String tmpaddr=info[1]+"#"+localhost;
					 //data=info[1].getBytes();//让准备的服务转一圈
					 data=tmpaddr.getBytes();
					 recUDP.ClientSocketSendData(data);
					 //告诉管理器准备好了，发送消息给管理器
					 try {
						 if(walladdr!=null)
						 {
						Object obj = ProtocolManager.getInstance().CreateObject("UDP");
						IDDS_Protocol fire=(IDDS_Protocol)obj;
						
						fire.SendData(walladdr, data);
						 }
					} catch (InstantiationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
					}
				});
		rec.setDaemon(true);
		rec.setName("serverwallinfo");
		rec.start();
	}
}
///**
// * 
//* @Name: RecFireWallInf 
//* @Description: 接收客户端通过穿墙发来的数据
//* @return void    返回类型 
//* @throws
// */
//public void RecFireWallInf()
//{
//	if(recUDP==null)
//	{
//		Thread rec=new Thread(new Runnable()
//				{
//
//					@Override
//					public void run() {
//					while(true)
//					{
//					//挖墙数据
//					//要求所有信息携带服务信息
//					 byte[]data=recUDP.RecClientSocketData();
//					 System.out.println("服务端接收到客户端传来的数据");
//					 //
//					}
//					}
//				});
//		rec.setDaemon(true);
//		rec.setName("clientRequest");
//		rec.start();
//		//接收客户端穿墙而来的信息
//	}
//}
}
