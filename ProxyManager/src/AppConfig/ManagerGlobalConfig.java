  
/**    
* �ļ�����GlobalConfig.java    
*    
* �汾��Ϣ��    
* ���ڣ�2017��1��21��    
* Copyright ���� Corporation 2017     
* ��Ȩ����    
*    
*/  

package AppConfig;

import java.util.HashMap;

/**    
*     
* ��Ŀ���ƣ�NameServerClient    
* �����ƣ�GlobalConfig    
* ��������    ����������
* �����ˣ�jinyu    
* ����ʱ�䣺2017��1��21�� ����10:16:26    
* �޸��ˣ�jinyu    
* �޸�ʱ�䣺2017��1��21�� ����10:16:26    
* �޸ı�ע��    
* @version     
*     
*/
public class ManagerGlobalConfig {
public static  HashMap<String,String> hashMap=new HashMap<String,String>();
public static String ManagerAddress="127.0.0.1";//�ͻ�������IP
public static String ManagerServerAddress="127.0.0.1";//�����ע��IP
public static String ManagerHeart="127.0.0.1";//����IP
public static String ManagerWall="127.0.0.1";//��ǽ�ɹ�����IP
public static String ManagerTCPNat="127.0.0.1";//TCP��ǽIP
public static String ManagerUDPNat="127.0.0.1";//UDP��ǽIP
public static String ManagerTCPBeat="127.0.0.1";//TCP��������IP
public static  int ServerPort=3333;//����ע��˿�
public static  int ReqPort=3334;//�ͻ�������˿�
//
public static  int beatPort=7777;//����
public static  int wallPort=7778;//��ǽ�ɹ��˿�
public static  int tcpbeat=7779;//tcp���Ӷ˿�
//��ǽ
public static  int udpNatPort=6661;
public static  int tcpNatPort=6662;
public static  String protol_Type="udp";

}