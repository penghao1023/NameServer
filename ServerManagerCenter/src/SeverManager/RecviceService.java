package SeverManager;

import java.util.Map.Entry;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import ProxyExchange.IDataCallBack;
import ServerRecInfo.RecServerInfo;

/**
 * 
*     
* 项目名称：ServerManagerCenter    
* 类名称：RecviceService    
* 类描述：     接收服务注册
* 创建人：jinyu    
* 创建时间：2017年2月18日 下午7:22:48    
* 修改人：jinyu    
* 修改时间：2017年2月18日 下午7:22:48    
* 修改备注：    
* @version     
*
 */
public class RecviceService implements IRecMsg {
	IDataCallBack  sinfo=new RecServerInfo();
	String managerInfo="";
	private IDDS_Protocol protocol;
	@Override
	public void RecData(String address, byte[] data) {
		//ProxyPlugin的InitRecSeverInfo方法一致
		sinfo.DataRec(address, data);
		if(managerInfo.length()==0)
		{
			managerInfo= ManagerInfo();
		}
		byte[] info=managerInfo.getBytes();
		try
		{
			protocol.ServerSocketSendData(info);
		}
		catch(Exception ex)
		{
			System.out.println("注册通讯传送信息给服务端失败！");
		}
	}

	  
	/**
	 * 
	 */
	@Override
	public void SaveInstance(Object call) {
		  protocol=(IDDS_Protocol)call;
		
	}
	/**
	 * 
	* @Name: ManagerInfo 
	* @Description: 组织管理器信息给服务端
	* @return  参数说明 
	* @return String   管理器地址信息xml
	* @throws
	 */
	private String ManagerInfo()
	{
		StringBuilder xml=new StringBuilder();
		xml.append("<ManagerInfo>");
		for(Entry<String, String> map:ManagerAddrInfo.hashMap.entrySet())
		{
			xml.append("<"+map.getKey()+">");
			xml.append(map.getValue());
			xml.append("</"+map.getKey()+">");
		}
		xml.append("</ManagerInfo>");
		return xml.toString();
		
	}

}
