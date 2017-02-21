package nameServerFrame;

import java.util.HashMap;
import java.util.UUID;

import PublicModel.ServerBinds;
import RequestServerInfo.ReqServerInfo;
import nameServerContainer.ManagerConfig;
import nameServerContainer.ManagerInfo;
import nameServerContainer.ProxyPlugin;
import nameServerContainer.ServerInfoSave;
import nameServerInterface.IServer;

public class ServiceRegistry {
	private  static ProxyPlugin plugin = null;
	private  static ReqServerInfo reqInfo=null;
	private  static volatile boolean isStart=false;
	public ServiceRegistry() {
		
	}

	/**
	 * 注册服务
	 * hamp 服务信息
	 * @param server 服务实例
	 * @param connect 连接信息， -h 127.0.0.1 -p 223 t tcp
	 */
	
	public static void AddServers(IServer server, String connect,HashMap<String,String> hamp) {
		//
		
		
		// 启动服务接受客户端调用
		Sever_BindsInfo binds = AnalysisConnection.Aysy(connect);
		ServerPorxy proxy = new ServerPorxy(binds.address, binds.port,binds.t_type);
		
		proxy.InitServerThread(binds.name,server);
		
       //保存服务及通信代理
		ServerInfo server_Info = new ServerInfo();
		server_Info.porxy = proxy;
		server_Info.server = server;
		server_Info.type_Name=binds.t_type;
        
		//向客户端注册信息
		ServerBinds  info=new ServerBinds();
		info.address=binds.address;
		info.port=binds.port;
		info.name=binds.name;
		info.sid= UUID.randomUUID().toString();
		info.communicationType=binds.t_type;
		
		//
		ProxyPlugin.GetInstance().NoticeServerInfo(info);
	     //将服务端信息保存起来，用于客户端请求
	    ServerInfoSave saveFrame=new ServerInfoSave();
	    saveFrame.Add(info);
		 //长时间保持
	    ServerInstances.servers.put(info.name, server_Info);
	    
	    if(hamp!=null)
	    {
	    	server.Start(info.name, hamp);
	    }
	    if(!isStart)
	    {
	    	isStart=true;
	    	InitStart();
	    	
	    	
	    }
	}
	/**
	 * 
	* @Name: AddServeBox 
	* @Description: 添加服务到服务盒子 
	* @param boxName 服务盒子名称
	* @param serverName 服务名称
	* @param server 服务
	* @param hamp  服务参数
	* @return void    返回类型 
	* @throws
	 */
	public static void AddServeBox(String boxName,String serverName, IServer server,HashMap<String,String> hamp) {
		//
		
		//添加进去，所有的服务盒子在启动时都应该添加好
		ServerBoxSet.AddServer(boxName, serverName, server, hamp);
		if(!isStart)
	    {
			isStart=true;
			InitStart();
		
	    }
		
	}
	/**
	 * 
	* @Name: InitStart 
	* @Description: 服务启动时初始化
	* @return void    返回类型 
	* @throws
	 */
	private static void InitStart()
	{
		if(reqInfo==null)
		{
			reqInfo=new ReqServerInfo();
			reqInfo.RecReqInfo();
	    }
		if(plugin==null)
		{
		 plugin=ProxyPlugin.GetInstance();
		// plugin.InitRecSeverInfo();
		}
		//
		
			 //发送心跳
			if(ManagerConfig.isBeat)
			{
			if(!ManagerInfo.hashConfig.isEmpty())
			{
			PostHeartbeat post=new PostHeartbeat();
			String wall=ManagerInfo.hashConfig.getOrDefault("ManagerWallSucess", "");
			String beat=ManagerInfo.hashConfig.getOrDefault("ManagerBeat", "");
			post.Start(wall,beat);
				
			}
			if(ManagerConfig.isTCPNat)
			{
				String addr=ManagerInfo.hashConfig.getOrDefault("ManagerTCPBeat", "");
				TCPNatBeat  tcpNat=new TCPNatBeat();
				String[] addrport=addr.split(":");
				tcpNat.Start(addrport[0],Integer.valueOf(addrport[1]));
			}
			}
	    
		
	}
	

}
