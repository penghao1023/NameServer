  
/**    
* �ļ�����ProceRequest.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��17��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package SeverManager;




import PublicModel.ServerBinds;
import RequestServerInfo.AnalysisParam;
import RequestServerInfo.RequestModel;
import RequestServerInfo.ServerManagerType;
import Tools.MsgPackTool;
import nameServerClient.LocalServer;
import nameServerClient.ProxyClient;

import nameServerInterface.IPoxyObj;


/**    
*     
* ��Ŀ���ƣ�ServerManagerCenter    
* �����ƣ�ProceRequest    
* ��������     �������͵�����
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��17�� ����8:40:15    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��17�� ����8:40:15    
* �޸ı�ע��    
* @version     
*     
*/
public class ProceRequest {
	
/**
 * 
* @Name: RespondClient 
* @Description: ���տͻ�����Ϣ����������
* @param address ��Դ
* @param data  ���ݣ�������
* @return  ����˵�� 
* @return byte[]    ����ͻ�������������ַ�򷵻ص�ַ������Ϊnull
* @throws
 */
public byte[]   RespondClient (String address,byte[]data)
{
	byte[] param=null;
	RequestModel model=AnalysisParam.GetParam(data);
	model.managerType=ServerManagerType.ServerIndirectMode;
	switch(model.managerType)
	{
	case ClientDirectMode:
	   break;
	case ServerDirectMode:
		ServerFun(model.serverName,model.dataParam);
		break;
	case ServerIndirectMode:
 		param=ClientFun(model.serverName);
		break;
	default:
		break;
	}
	return param;
}

/**
 * 
* @Name: ServerFun 
* @Description: ��ȡ������������ 
* @param data  ����˵�� 
* void    �������� 
* @throws
 */
public void ServerFun(String servername,byte[]data)
{
	StringBuilder error=new StringBuilder();
    IPoxyObj proxy=	ProxyClient.CastObj(servername, error);
    if(proxy!=null)
    {
        proxy.SetData(data);
    }
}

/**
 * 
* @Name: ClientFun 
* @Description:  ��ȡ������Ϣ��ת����ַ
* @param name  �����Ĳ���
* @return  ����˵�� 
* @return byte[]    ������Ϣ
* @throws
 */
public byte[] ClientFun(String serverName)
{
	 //String serverName=new String(name);
	 StringBuilder error=new StringBuilder();
	 if(serverName==null||serverName.length()==0||serverName.equals(" "))
	 {
		 serverName="��ʼ����֤";
	 }
	 ServerBinds serverinfo=LocalServer.GetServerInfo().GetCur(serverName);
	 MsgPackTool tool=new MsgPackTool();
	 byte[]bytes= tool.Serialize(serverinfo, error);
	 return bytes;
	
}
}