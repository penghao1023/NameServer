package nameServerClient;


import AppConfig.ClientGlobalConfig;
import RequestServerInfo.ServerManagerType;
import nameServerContainer.ProxyPlugin;

/**
 * 
*     
* ��Ŀ���ƣ�NameServerClient    
* �����ƣ�LocalServer    
* ��������    ���յķ�����Ϣ,���ؽ�����Ϣ���������
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��20�� ����11:35:58    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��20�� ����11:35:58    
* �޸ı�ע��    
* @version     
*
 */
public class LocalServer {
 static	ProxyPlugin plugin=null;

/**
 * ��װһ��Ϊ�˳�ʼ��
* @Name: GetServerInfo 
* @Description: ���ط�����Ϣ���ҳ�ʼ���������շ���ע��
* @return  ����˵�� 
* @return InitPlugin    �������� 
* @throws
 */
public static ProxyPlugin GetServerInfo()
   {
	   if(plugin==null)
	   {
		   plugin=ProxyPlugin.GetInstance();
		   ClientGlobalConfig.ServerProType=ServerManagerType.ServerIndirectMode;
		   if(ClientGlobalConfig.ServerProType==ServerManagerType.ClientDirectMode)
		   {
			   //����ͻ�����ֱ�ӵ��÷���ķ�ʽ���������շ�����Ϣ
			   //����ֻ��Ҫ��ServerManger�л�ȡ
		      plugin.InitRecSeverInfo();
		      plugin.ReqServerInfo();
		   }
		   try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	   }
	   return plugin;
   }
	
}