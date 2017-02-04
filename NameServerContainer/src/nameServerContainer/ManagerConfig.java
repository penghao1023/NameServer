  
/**    
* 文件名：ManagerConfig.java    
*    
* 版本信息：    
* 日期：2017年1月26日    
* Copyright 足下 Corporation 2017     
* 版权所有    
*    
*/  

package nameServerContainer;

import java.util.HashMap;

/**    
*     
* 项目名称：NameServerContainer    
* 类名称：ManagerConfig    
* 类描述：    服务是否启用管理中心
* 创建人：jinyu    
* 创建时间：2017年1月26日 上午1:46:27    
* 修改人：jinyu    
* 修改时间：2017年1月26日 上午1:46:27    
* 修改备注：    
* @version     
*     
*/
public class ManagerConfig {
/**
 * 与服务管理中心通讯方式
 * 如果不启用管理中心设置无效
 */
public static String Communication_Type="TCP";
/**
 * 全局参数 该服务是否传递给管理中心
 */
public static  boolean  IsManager=true;
/**
 * 针对各个服务设置是否传递给中心
 */
public static HashMap<String,Boolean> hashConfig=new HashMap<String,Boolean>();

/**
 * 管理中心IP
 */
public static String ManagerAddr="127.0.0.1";
/**
 * 管理中心端口
 */
public static int ManagerPort=3333;
}
