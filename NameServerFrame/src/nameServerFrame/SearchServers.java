  
/**    
* �ļ�����SearchServers.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��2��18��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package nameServerFrame;

import ISearchInfo.ISearchPrxoy;

/**    
*     
* ��Ŀ���ƣ�NameServerFrame    
* �����ƣ�SearchServers    
* ��������    
* �����ˣ�jinyu    
* ����ʱ�䣺2017��2��18�� ����2:06:11    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��2��18�� ����2:06:11    
* �޸ı�ע��    
* @version     

*     
*/
public class SearchServers implements  ISearchPrxoy{

	/*
	* Title: SendData
	*Description: 
	* @param infokey
	* @param addr
	* @param data 
	 
	*/
	@Override
	public void SendData(String infokey, String addr, byte[] data) {
		ServerInfo server=ServerInstances.servers.getOrDefault(infokey, null);
		if(server!=null)
		{
			try
			{
				int num=10;
				//����10��
		    	String[]address=addr.split(":");
		    	while(num>1)
		    	{
		         server.porxy.SendTCPNatPackage(address[0], Integer.valueOf(address[1]));
		         num--;
		    	}
			}
			catch(Exception ex)
			{
				
			}
		}
		
	}

		
	


}