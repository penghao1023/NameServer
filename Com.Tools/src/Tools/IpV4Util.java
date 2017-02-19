package Tools;
import java.util.regex.Pattern;  
  
/** 
* <pre> 
* IP��ַ��Χ�� 
* 0.0.0.0��255.255.255.255��������mask��ַ�� 
* 
* IP��ַ����: 
* A���ַ��1.0.0.1��126.255.255.254 
* B���ַ��128.0.0.1��191.255.255.254 
* C���ַ��192.168.0.0��192.168.255.255 
* D���ַ��224.0.0.1��239.255.255.254 
* E���ַ��240.0.0.1��255.255.255.254 
* 
* ����ж�����IP��ַ�Ƿ���ͬһ��������: 
* Ҫ�ж�����IP��ַ�ǲ�����ͬһ�����Σ��ͽ����ǵ�IP��ַ�ֱ������������������㣬�õ��Ľ��һ����ţ�����������ͬ������ͬһ���������򣬲���ͬһ������ 
* �����ٶ�ѡ������������255.255.254.0�����ڷֱ���������IP��ַ�ֱ��������������㣬����ͼ��ʾ�� 
* 211.95.165.24 11010011 01011111 10100101 00011000 
* 255.255.254.0 11111111 11111111 111111110 00000000 
* ��Ľ����: 11010011 01011111 10100100 00000000 
* 
* 211.95.164.78 11010011 01011111 10100100 01001110 
* 255.255.254.0 11111111 11111111 111111110 00000000 
* ��Ľ����: 11010011 01011111 10100100 00000000 
* ���Կ���,�õ��Ľ��(���������������ַ)����һ���ģ���˿����ж�������IP��ַ��ͬһ�������� 
* 
* ���û�н����������֣�A���������������Ϊ255.0.0.0��B���������������Ϊ255.255.0.0��C���������������Ϊ255.255.255.0��ȱʡ�����������Ϊ255.255.255.0 
* 
* 
*/  
public class IpV4Util  
{  
     // IpV4���������ʽ�������ж�IpV4��ַ�Ƿ�Ϸ�  
     private static final String IPV4_REGEX = "((\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3})";  
      
     // ϵͳ�������룬����ip���һ����ַ  
     private int mask;  
      
     // 1����A�࣬2����B�࣬3����C�ࣻ4������������  
     public final static int IP_A_TYPE = 1;  
     public final static int IP_B_TYPE = 2;  
     public final static int IP_C_TYPE = 3;  
     public final static int IP_OTHER_TYPE = 4;  
      
     // A���ַ��Χ��1.0.0.1---126.255.255.254  
     private static int[] IpATypeRange;  
     // B���ַ��Χ��128.0.0.1---191.255.255.254  
     private static int[] IpBTypeRange;  
     // C���ַ��Χ��192.168.0.0��192.168.255.255  
     private static int[] IpCTypeRange;  
      
     // A,B,C���ַ��Ĭ��mask  
     private static int DefaultIpAMask;  
     private static int DefaultIpBMask;  
     private static int DefaultIpCMask;  
      
     // ��ʼ��  
     static  
     {  
          IpATypeRange = new int[2];  
          IpATypeRange[0] = getIpV4Value("1.0.0.1");  
          IpATypeRange[1] = getIpV4Value("126.255.255.254");  
           
          IpBTypeRange = new int[2];  
          IpBTypeRange[0] = getIpV4Value("128.0.0.1");  
          IpBTypeRange[1] = getIpV4Value("191.255.255.254");  
           
          IpCTypeRange = new int[2];  
          IpCTypeRange[0] = getIpV4Value("192.168.0.0");  
          IpCTypeRange[1] = getIpV4Value("192.168.255.255");  
           
          DefaultIpAMask = getIpV4Value("255.0.0.0");  
          DefaultIpBMask = getIpV4Value("255.255.0.0");  
          DefaultIpCMask = getIpV4Value("255.255.255.0");  
     }  
      
     /** 
     * Ĭ��255.255.255.0 
     */  
     public IpV4Util()  
     {  
          mask = getIpV4Value("255.255.255.0");  
     }  
      
     /** 
     * @param mask �������"255.255.254.0"�ȸ�ʽ�������ʽ���Ϸ����׳�UnknownError�쳣���� 
     */  
     public IpV4Util(String masks)  
     {  
          mask = getIpV4Value(masks);  
          if(mask == 0)  
          {  
               throw new UnknownError();  
          }  
     }  
      
     public int getMask()  
     {  
          return mask;  
     }  
      
     /** 
     * �Ƚ�����ip��ַ�Ƿ���ͬһ�������У�����������ǺϷ���ַ���������ǷǷ���ַʱ�����������Ƚϣ� 
     * �������һ���ǺϷ���ַ�򷵻�false�� 
     * ע��˴���ip��ַָ�����硰192.168.1.1����ַ����������mask 
     * @return 
     */  
     public boolean checkSameSegment(String ip1,String ip2)  
     {  
          return checkSameSegment(ip1,ip2,mask);  
     }  
      
     /** 
     * �Ƚ�����ip��ַ�Ƿ���ͬһ�������У�����������ǺϷ���ַ���������ǷǷ���ַʱ�����������Ƚϣ� 
     * �������һ���ǺϷ���ַ�򷵻�false�� 
     * ע��˴���ip��ַָ�����硰192.168.1.1����ַ 
     * @return 
     */  
     public static boolean checkSameSegment(String ip1,String ip2, int mask)  
     {  
          // �ж�IPV4�Ƿ�Ϸ�  
          if(!ipV4Validate(ip1))  
          {  
               return false;  
          }  
          if(!ipV4Validate(ip2))  
          {  
               return false;  
          }  
          int ipValue1 = getIpV4Value(ip1);  
          int ipValue2 = getIpV4Value(ip2);  
          return (mask & ipValue1) == (mask & ipValue2);  
     }  
      
     /** 
     * �Ƚ�����ip��ַ�Ƿ���ͬһ�������У�����������ǺϷ���ַ���������ǷǷ���ַʱ�����������Ƚϣ� 
     * �������һ���ǺϷ���ַ�򷵻�false�� 
     * ע��˴���ip��ַָ�����硰192.168.1.1����ַ 
     * @return 
     */  
     public static boolean checkSameSegmentByDefault(String ip1,String ip2)  
     {  
          int mask = getDefaultMaskValue(ip1);     // ��ȡĬ�ϵ�Mask  
          return checkSameSegment(ip1,ip2,mask);  
     }  
      
     /** 
     * ��ȡipֵ��maskֵ��Ľ�� 
     * @param ipV4 
     * @return  32bitֵ 
     */  
     public int getSegmentValue(String ipV4)  
     {  
          int ipValue = getIpV4Value(ipV4);  
          return (mask & ipValue);  
     }  
      
     /** 
     * ��ȡipֵ��maskֵ��Ľ�� 
     * @param ipV4 
     * @return  32bitֵ 
     */  
     public static int getSegmentValue(String ip, int mask)  
     {  
          int ipValue = getIpV4Value(ip);  
          return (mask & ipValue);  
     }  
      
     /** 
     * �ж�ipV4����mask��ַ�Ƿ�Ϸ���ͨ���������ʽ��ʽ�����ж� 
     * @param ipv4 
     */  
    public static boolean ipV4Validate(String ipv4)  
    {  
         return ipv4Validate(ipv4,IPV4_REGEX);  
    }  
     
    private static boolean ipv4Validate(String addr,String regex)  
    {  
          if(addr == null)  
          {  
              return false;  
         }  
         else  
         {  
              return Pattern.matches(regex, addr.trim());  
         }  
     }  
     
     /** 
     * �Ƚ�����ip��ַ������������ǺϷ���ַ����1����ip1����ip2��-1����ip1С��ip2,0������ȣ� 
     * �������һ���ǺϷ���ַ����ip2���ǺϷ���ַ����ip1����ip2������1����֮����-1���������ǷǷ���ַʱ���򷵻�0�� 
     * ע��˴���ip��ַָ�����硰192.168.1.1����ַ����������mask 
     * @return 
     */  
     public static int compareIpV4s(String ip1,String ip2)  
     {  
          int result = 0;  
          int ipValue1 = getIpV4Value(ip1);     // ��ȡip1��32bitֵ  
          int ipValue2 = getIpV4Value(ip2); // ��ȡip2��32bitֵ  
          if(ipValue1 > ipValue2)  
          {  
               result =  -1;  
          }  
          else if(ipValue1 <= ipValue2)  
          {  
               result = 1;  
          }  
          return result;  
     }  
      
     /** 
     * ���ipV4 �����ͣ�����A�࣬B�࣬C�࣬������C,D�͹㲥����� 
     * @param ipV4 
     * @return     ����1����A�࣬����2����B�࣬����3����C�ࣻ����4����D�� 
     */  
     public static int checkIpV4Type(String ipV4)  
     {  
          int inValue = getIpV4Value(ipV4);  
          if(inValue >= IpCTypeRange[0] && inValue <= IpCTypeRange[1])  
          {  
               return IP_C_TYPE;  
          }  
          else if(inValue >= IpBTypeRange[0] && inValue <= IpBTypeRange[1])  
          {  
               return IP_B_TYPE;  
          }  
          else if(inValue >= IpATypeRange[0] && inValue <= IpATypeRange[1])  
          {  
               return IP_A_TYPE;  
          }  
          return IP_OTHER_TYPE;  
     }  
      
     /** 
     * ��ȡĬ��maskֵ�����IpV4��A���ַ���򷵻�{@linkplain #DefaultIpAMask}�� 
     * ���IpV4��B���ַ���򷵻�{@linkplain #DefaultIpBMask}���Դ����� 
     * @param anyIpV4 �κκϷ���IpV4 
     * @return mask 32bitֵ 
     */  
     public static int getDefaultMaskValue(String anyIpV4)  
     {  
          int checkIpType = checkIpV4Type(anyIpV4);  
          int maskValue = 0;  
          switch (checkIpType)  
          {  
               case IP_C_TYPE:  
                    maskValue = DefaultIpCMask;  
                    break;  
               case IP_B_TYPE:  
                    maskValue = DefaultIpBMask;  
                    break;  
               case IP_A_TYPE:  
                    maskValue = DefaultIpAMask;  
                    break;  
               default:  
                    maskValue = DefaultIpCMask;  
          }  
          return maskValue;  
     }  
      
     /** 
     * ��ȡĬ��mask��ַ��A���ַ��Ӧ255.0.0.0��B���ַ��Ӧ255.255.0.0�� 
     * C�༰������Ӧ255.255.255.0 
     * @param anyIp 
     * @return mask �ַ�����ʾ 
     */  
     public static String getDefaultMaskStr(String anyIp)  
     {  
          return trans2IpStr(getDefaultMaskValue(anyIp));  
     }  
      
     /** 
     * ��ip 32bitֵת��Ϊ�硰192.168.0.1���ȸ�ʽ���ַ��� 
     * @param ipValue 32bitֵ 
     * @return 
     */  
     public static String trans2IpStr(int ipValue)  
     {  
          // ��֤ÿһλ��ַ����������  
          return ((ipValue >> 24) & 0xff) + "." + ((ipValue >> 16) & 0xff) + "." + ((ipValue >> 8) & 0xff) + "." + (ipValue & 0xff);  
     }  
      
     /** 
     * ��ip byte����ֵת��Ϊ�硰192.168.0.1���ȸ�ʽ���ַ��� 
     * @param ipBytes 32bitֵ 
     * @return 
     */  
    public static String trans2IpV4Str(byte[] ipBytes)  
    {  
         // ��֤ÿһλ��ַ����������  
         return (ipBytes[0] & 0xff) + "." + (ipBytes[1] & 0xff) + "." + (ipBytes[2] & 0xff) + "." + (ipBytes[3] & 0xff);  
    }  
  
     public static int getIpV4Value(String ipOrMask)  
     {  
          byte[] addr = getIpV4Bytes(ipOrMask);  
          int address1  = addr[3] & 0xFF;  
          address1 |= ((addr[2] << 8) & 0xFF00);  
          address1 |= ((addr[1] << 16) & 0xFF0000);  
          address1 |= ((addr[0] << 24) & 0xFF000000);  
          return address1;  
     }  
  
     public static byte[] getIpV4Bytes(String ipOrMask)  
     {  
          try  
          {  
               String[] addrs = ipOrMask.split("\\.");  
               int length = addrs.length;  
               byte[] addr = new byte[length];  
               for (int index = 0; index < length; index++)  
               {  
                    addr[index] = (byte) (Integer.parseInt(addrs[index]) & 0xff);  
               }  
               return addr;  
          }  
          catch (Exception e)  
          {  
          }  
          return new byte[4];  
     }  
}  