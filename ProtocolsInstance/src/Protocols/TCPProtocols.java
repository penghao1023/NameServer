package Protocols;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Arrays;


import DDS_Transfer.IDDS_Protocol;
import DDS_Transfer.IRecMsg;
import DDS_Transfer.ProtocolType;



@ProtocolType(Name="tcp")
public class TCPProtocols implements IDDS_Protocol{
	
	//保存接收数据的注册
	String curBindAddress="";
	String curLocalAddress="";
	IRecMsg curRec=null;
	ServerSocket serverSocket = null;
	Socket clientSocket=null;
	boolean isRun=true;
	public void SetBindAddress(String addr)
	{
		//curBindAddress=addr;
	}
private void SendMsg(String ip,int port,byte[]data) throws IOException, Exception
{
	  Socket client = new Socket(ip , port);
      OutputStream out = client.getOutputStream();
      out.write(data);
      out.flush();
      out.close();
      curLocalAddress= client.getLocalAddress().toString()+":"+client.getLocalPort();
      client.close();
     
}

@Override
public boolean SendData(String adress, byte[] data) {
	String[] addr=adress.split(":");
	if(addr.length==2)
	{
		String serverIP=addr[0];
		int port=Integer.valueOf(addr[1]);
		// TCP发送
		try {
			
			SendMsg(serverIP,port,data);
		} catch (IOException e) {
		  System.out.println("数据通信失败,IP:"+serverIP+",端口："+port);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	return true;
}

@Override
public void RecData(String Address, IRecMsg rec) {
	 System.out.println(Address);
         curRec=rec;
		//避免同一实例启动
	  
 	    SocketRec(Address);
}

/**
 * 开启网络接收
 * @param Address
 */
private void SocketRec(String Address)
{
	Thread rec1=new Thread(new Runnable()
			{
		
		
		
		public void run() {
			String[] addr=Address.split(":");
			curBindAddress=Address;
			 System.out.println("准备绑定地址");
			if(addr.length==2)
			{
				
				String serverIP=addr[0];
				int port=Integer.valueOf(addr[1]);
				ServerSocket listen = null;
				if(serverIP.equals("*"))
				{   
					
					   try {
						listen = new ServerSocket();
						listen.setReuseAddress(true);
					    listen.bind(new InetSocketAddress(port));
						  System.out.println("绑定端口:"+port);
					} 
					   catch (IOException ex) 
					   {
						
						   System.out.println(ex.getLocalizedMessage());
					}
					
				}
				else
				{
			      
					try {
						 listen = new ServerSocket();
						 listen.setReuseAddress(true);
						 SocketAddress endpoint = new InetSocketAddress(serverIP, port); 
						 System.out.println("绑定端口IP:"+port+";"+serverIP);
						 listen.bind(endpoint);
						 
					} catch (IOException ex) {
					
						System.out.println(ex.getLocalizedMessage());
					}
			    }
				serverSocket=listen;
				while(isRun)
				{
					 byte[]datas=new byte[1024];  
		             Socket server = null;
				
					try {
						server = listen.accept();
						clientSocket=server;
						InputStream in = null;
						in = server.getInputStream();
						String ip=server.getInetAddress().getHostAddress();
			            	int remoteport=server.getPort();
							int r=  in.read(datas);
							 byte[]realData=new byte[r];
							 System.arraycopy(datas, 0, realData, 0, r);
							 //没有考虑分包或接收不全
							 CallData(ip,remoteport,realData);
					} catch (IOException e) {
						 System.out.println(e.getLocalizedMessage());
						e.printStackTrace();
					}
			
		           
				}
		
			}
		}
			});
	rec1.setDaemon(true);
	rec1.setName("recTCPDDSData");
	rec1.start();
}
/**
 * 解析数据
 * @param src
 * @param port
 * @param data
 */
private void CallData(String src,int port,byte[]data)
{
	if(curRec!=null)
	{
		String address=src+":"+port;
		curRec.SaveInstance(this);
		curRec.RecData(address, data);
	}
	
}

@Override
public String GetBindAddress() {

	return curBindAddress;
}

	

@Override
public String GetLocalAddress() {
	return curLocalAddress;
}
  
/**  
* 关闭服务端   
* @see DDS_Transfer.IDDS_Protocol#Close()    
*/  

@Override
public void Close() {
	if(serverSocket!=null)
	{
	     try {
	    	 isRun=false;
			 serverSocket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
  
/**  
* 连接 
* @see DDS_Transfer.IDDS_Protocol#Connect()    
*/  

  
/**  
* 关闭客户端的连接
* @see DDS_Transfer.IDDS_Protocol#ClientSocketClose()    
*/  

@Override
public void ClientSocketClose() {
  
	if(clientSocket!=null)
	{
		try {
			clientSocket.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
}
  
/**  
* 发送数据给服务端   
* @see DDS_Transfer.IDDS_Protocol#ClientSocketSend(byte[])    
*/  

@Override
public void ClientSocketSend(byte[] data) {
	if(clientSocket!=null)
	{
		 OutputStream out;
		try {
			out = clientSocket.getOutputStream();
			 out.write(data);
		      out.flush();
		      out.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	     
	}
	
}
  
/**  
*  开启客户端接受    
* @see DDS_Transfer.IDDS_Protocol#RecClientSocket()    
*/  

@Override
public byte[] RecClientSocket() {
	ByteArrayOutputStream  out=new ByteArrayOutputStream();
	byte[]realData=null;
	if(clientSocket!=null)
	{
		byte[] recData=new byte[1024];
		while(true)
		{
			InputStream in = null;
			try {
				in = clientSocket.getInputStream();
				int r=in.read(recData);
				if(r<1024)
				{
					realData=Arrays.copyOf(recData, r);
					out.write(realData);
					
					break;
				}
				else
				{
					out.write(recData);
				}
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
	}
	realData=out.toByteArray();;
	 try {
		out.close();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	 return realData;
}
  
/**  
*  创建连接   
* @see DDS_Transfer.IDDS_Protocol#CreateClient(java.lang.String, int)    
*/  

@Override
public void CreateClient(String ip, int port) {
	
	try {
		clientSocket=new Socket(ip,port);
	} catch (UnknownHostException e) {
		
		e.printStackTrace();
	} catch (IOException e) {
		
		e.printStackTrace();
	}
	
}
  
/**  
* 服务端回传数据      
*/  

@Override
public void ServerSocketSend(byte[] data) {
	
	if(clientSocket!=null)
	{
		 OutputStream out;
		try {
			  out = clientSocket.getOutputStream();
			  out.write(data);
		      out.flush();
		      out.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	     
	}
	
}
/*
* Title: CreateClient
*Description:  创建Socket
*/
@Override
public void CreateClient() {
	clientSocket=new Socket();
}
/*
* Title: BindLocal
*Description: 
* @param locahost 本地IP
* @param port 本地端口
* @return 成功与否
*/
@Override
public boolean BindLocal(String locahost, int port) {
	 if(clientSocket!=null)
	 {
		 SocketAddress bindpoint=new InetSocketAddress(locahost, port);
		try {
			clientSocket.bind(bindpoint);
			return true;
		} catch (IOException e) {
		
			e.printStackTrace();
			
			return false;
		}
	 }
	 else
	 {
		 return false;
	 }
}
/*
* Title: Connect
*Description: 创建客户端连接，一般与CreateClient使用
* @param remoteaddr
* @param port
* @return  
*/
@Override
public boolean Connect(String remoteaddr, int port) {
	 if(clientSocket!=null)
	 {
		 SocketAddress endpoint=new InetSocketAddress(remoteaddr, port);
		 try {
			clientSocket.connect(endpoint);
			return true;
		} catch (IOException e) {
			
			e.printStackTrace();
			return false;
		}
		 
	 }
	 else
	 {
	 
	return false;
	 }
}
/*
* Title: ClientSocketSendData
*Description: 客户端发送数据
* @param 发送数据
 
*/
@Override
public void ClientSocketSendData(byte[] data) {
	if(clientSocket!=null)
	{
		 OutputStream out;
		try {
			out = clientSocket.getOutputStream();
			 out.write(data);
		     out.flush();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	     
	}
	
}
/*
* Title: ServerSocketSendData
*Description: 服务端向客户端发送数据
* @param 发送数据 
 
*/
@Override
public void ServerSocketSendData(byte[] data) {
	if(clientSocket!=null)
	{
		 OutputStream out;
		try {
			  out = clientSocket.getOutputStream();
			  out.write(data);
		      out.flush();
		      
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	     
	}
	
}


}
