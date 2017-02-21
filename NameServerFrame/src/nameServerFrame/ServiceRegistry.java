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
	 * ע�����
	 * hamp ������Ϣ
	 * @param server ����ʵ��
	 * @param connect ������Ϣ�� -h 127.0.0.1 -p 223 t tcp
	 */
	
	public static void AddServers(IServer server, String connect,HashMap<String,String> hamp) {
		//
		
		
		// ����������ܿͻ��˵���
		Sever_BindsInfo binds = AnalysisConnection.Aysy(connect);
		ServerPorxy proxy = new ServerPorxy(binds.address, binds.port,binds.t_type);
		
		proxy.InitServerThread(binds.name,server);
		
       //�������ͨ�Ŵ���
		ServerInfo server_Info = new ServerInfo();
		server_Info.porxy = proxy;
		server_Info.server = server;
		server_Info.type_Name=binds.t_type;
        
		//��ͻ���ע����Ϣ
		ServerBinds  info=new ServerBinds();
		info.address=binds.address;
		info.port=binds.port;
		info.name=binds.name;
		info.sid= UUID.randomUUID().toString();
		info.communicationType=binds.t_type;
		
		//
		ProxyPlugin.GetInstance().NoticeServerInfo(info);
	     //���������Ϣ�������������ڿͻ�������
	    ServerInfoSave saveFrame=new ServerInfoSave();
	    saveFrame.Add(info);
		 //��ʱ�䱣��
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
	* @Description: ���ӷ��񵽷������ 
	* @param boxName �����������
	* @param serverName ��������
	* @param server ����
	* @param hamp  �������
	* @return void    �������� 
	* @throws
	 */
	public static void AddServeBox(String boxName,String serverName, IServer server,HashMap<String,String> hamp) {
		//
		
		//���ӽ�ȥ�����еķ������������ʱ��Ӧ�����Ӻ�
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
	* @Description: ��������ʱ��ʼ��
	* @return void    �������� 
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
		
			 //��������
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