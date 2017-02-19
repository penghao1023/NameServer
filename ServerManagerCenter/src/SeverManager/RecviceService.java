package SeverManager;

import java.util.Map.Entry;

import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import ProxyExchange.IDataCallBack;
import ServerRecInfo.RecServerInfo;

/**
 * 
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�RecviceService    
* ��������     ���շ���ע��
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��18�� ����7:22:48    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��18�� ����7:22:48    
* �޸ı�ע��    
* @version     
*
 */
public class RecviceService implements IRecMsg {
	IDataCallBack  sinfo=new RecServerInfo();
	String managerInfo="";
	private IDDS_Protocol protocol;
	@Override
	public void RecData(String address, byte[] data) {
		//ProxyPlugin��InitRecSeverInfo����һ��
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
			System.out.println("ע��ͨѶ������Ϣ�������ʧ�ܣ�");
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
	* @Description: ��֯��������Ϣ�������
	* @return  ����˵�� 
	* @return String   ��������ַ��Ϣxml
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