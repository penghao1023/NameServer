package nameServerContainer;



import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import PublicModel.ServerBinds;


/**
 * 服务信息存储
 * @author jinyu
 *
 */
public class ServersContains {
	/**
	 * 保存一般服务信息
	 */
public static  ConcurrentHashMap<String,ArrayList<ServerBinds>> servers=new ConcurrentHashMap<String, ArrayList<ServerBinds >>();

/**
 * 保存主从服务
 */
public static  ConcurrentHashMap<String,ServerBinds> masterServers=new ConcurrentHashMap<String,ServerBinds>();

/**
 * 主服务
 */
public static  ConcurrentHashMap<String,ArrayList<ServerBinds>> slaveServers=new ConcurrentHashMap<String, ArrayList<ServerBinds >>();

}
