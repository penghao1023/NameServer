  
/**    
* 文件名：ProceRequest.java    
*    
* 版本信息：    
* 日期：2017年1月17日    
* Copyright 足下 Corporation 2017     
* 版权所有    
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
* 项目名称：ServerManagerCenter    
* 类名称：ProceRequest    
* 类描述：     解析传送的内容
* 创建人：jinyu    
* 创建时间：2017年1月17日 下午8:40:15    
* 修改人：jinyu    
* 修改时间：2017年1月17日 下午8:40:15    
* 修改备注：    
* @version     
*     
*/
public class ProceRequest {
	
/**
 * 
* @Name: RespondClient 
* @Description: 接收客户端信息，处理请求
* @param address 来源
* @param data  数据（参数）
* @return  参数说明 
* @return byte[]    如果客户端是请求服务地址则返回地址，否则为null
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
* @Description: 获取代理传递数据 
* @param data  参数说明 
* void    返回类型 
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
* @Description:  获取服务信息，转出地址
* @param name  传来的参数
* @return  参数说明 
* @return byte[]    服务信息
* @throws
 */
public byte[] ClientFun(String serverName)
{
	 //String serverName=new String(name);
	 StringBuilder error=new StringBuilder();
	 if(serverName==null||serverName.length()==0||serverName.equals(" "))
	 {
		 serverName="初始化验证";
	 }
	 ServerBinds serverinfo=LocalServer.GetServerInfo().GetCur(serverName);
	 MsgPackTool tool=new MsgPackTool();
	 byte[]bytes= tool.Serialize(serverinfo, error);
	 return bytes;
	
}
}
