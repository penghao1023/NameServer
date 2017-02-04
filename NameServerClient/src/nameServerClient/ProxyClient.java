package nameServerClient;


import AppConfig.ClientGlobalConfig;
import PublicModel.ServerBinds;
import RequestServerInfo.AnalysisParam;
import RequestServerInfo.ServerManagerType;
import Tools.MsgPackTool;
import nameServerInterface.IPoxyObj;


/**
 * 客户端代理
 * 该类在程序中与Manager共用
 * 通过配置决定使用方式；
 * 在manager中负载与服务通讯
 * 在程序中负载与服务或者manager通讯
 * @author jinyu
 *
 */
public class ProxyClient {
	
/**
 * 获取服务端代理
 * @param name 服务名称
 * @param error 错误信息
 * @return 服务代理
 */
public static IPoxyObj CastObj(String name,StringBuilder error)

{
	error=new StringBuilder();
	if(name==null)
	{
		error.append("名称不能为空");
		return null;
		
	}
	//
	ServerManagerType  serverType= ClientGlobalConfig.mapConfig.getOrDefault(name, null);
	if(serverType==null)
	{
		serverType=ClientGlobalConfig.ServerProType;
	}
	if(serverType==ServerManagerType.ClientDirectMode)
	{
	//客户端有该服务信息，直接连接服务
	ServerBinds serverinfo=LocalServer.GetServerInfo().GetCur(name);
	if(serverinfo!=null)
	{
		//ServerConnectPorxy tempPorxy=new ServerConnectPorxy(serverinfo.address,serverinfo.port,serverinfo.communicationType);
		ClientToServer client=ProcessClient(name,serverinfo.address,serverinfo.port,serverinfo.communicationType);
		//client.proxy=tempPorxy;
		//client.ServerName=name;
	    return client;
	}
	else
	{
		error.append("没有该服务");
		return null;
	}
	}
	else
	{
		//连接Manager
		//返回地址
		//ServerConnectPorxy tempPorxy=new ServerConnectPorxy(GlobalConfig.ManagerAddress,GlobalConfig.ManagerPort,GlobalConfig.protol_Type);
		//ClientToServer client=new ClientToServer();
		//client.proxy=tempPorxy;
		//client.ServerName=name;
 		ClientToServer client=ProcessClient(name,ClientGlobalConfig.ManagerAddress,ClientGlobalConfig.ManagerPort,ClientGlobalConfig.protol_Type);
	    byte[] bytes=AnalysisParam.PackParam(name, ClientGlobalConfig.ServerProType, null);
	    byte[] result=	client.GetData(bytes);
	    if(result!=null)
	    {
	    
	    	//有返回则
	    	 client.Close();//关闭与Manager的连接
	    	 MsgPackTool tool=new MsgPackTool();
	    	 ServerBinds serverinfo= tool.Deserialize(result, ServerBinds.class, error);
	    	 //tempPorxy=new ServerConnectPorxy(serverinfo.address,serverinfo.port,serverinfo.communicationType);
	 		 client=ProcessClient(name,serverinfo.address,serverinfo.port,serverinfo.communicationType);
	 		
	 	    
	    }
	    return client;
	    
	   //解析地址
	}
}
private static   ClientToServer ProcessClient(String name,String addr,int port,String protol_Type)
{
 	ServerConnectPorxy tempPorxy=new ServerConnectPorxy(addr,port,protol_Type);
	ClientToServer client=new ClientToServer(tempPorxy);
	
	client.ServerName=name;
	return client;
}
}
