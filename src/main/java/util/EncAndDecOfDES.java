/*
package util;


*/
/**
 * @Author:Tang
 * @Description:
 * @Date:Created in 2018/5/30-15:41
 * Modified By:
 *//*

public class EncAndDecOfDES {


    public static String strEnc(String data, String firstKey, String secondKey, String thirdKey) {
        int leng = data.length();
        String encData = "";
        byte[]  firstKeyBt={},secondKeyBt={},thirdKeyBt={};
        int firstLength=0,secondLength=0,thirdLength=0;
        if(firstKey != null && firstKey != ""){
            firstKeyBt = getKeyBytes(firstKey);
            firstLength = firstKeyBt.length;
        }
        if(secondKey != null && secondKey != ""){
            secondKeyBt = getKeyBytes(secondKey);
            secondLength = secondKeyBt.length;
        }
        if(thirdKey != null && thirdKey != ""){
            thirdKeyBt = getKeyBytes(thirdKey);
            thirdLength = thirdKeyBt.length;
        }

        if(leng > 0){
            if(leng < 4){
                byte [] bt = strToBt(data);
                byte encByte ;
                if(firstKey != null && firstKey !="" && secondKey != null && secondKey != "" && thirdKey != null && thirdKey != ""){
                    byte [] tempBt;
                    int x,y,z;
                    tempBt = bt;
                    for(x = 0;x < firstLength ;x ++){
                        tempBt = enc(tempBt,firstKeyBt[x]);
                    }
                    for(y = 0;y < secondLength ;y ++){
                        tempBt = enc(tempBt,secondKeyBt[y]);
                    }
                    for(z = 0;z < thirdLength ;z ++){
                        tempBt = enc(tempBt,thirdKeyBt[z]);
                    }
                    encByte = tempBt;
                }else{
                    if(firstKey != null && firstKey !="" && secondKey != null && secondKey != ""){
                        var tempBt;
                        var x,y;
                        tempBt = bt;
                        for(x = 0;x < firstLength ;x ++){
                            tempBt = enc(tempBt,firstKeyBt[x]);
                        }
                        for(y = 0;y < secondLength ;y ++){
                            tempBt = enc(tempBt,secondKeyBt[y]);
                        }
                        encByte = tempBt;
                    }else{
                        if(firstKey != null && firstKey !=""){
                            var tempBt;
                            var x = 0;
                            tempBt = bt;
                            for(x = 0;x < firstLength ;x ++){
                                tempBt = enc(tempBt,firstKeyBt[x]);
                            }
                            encByte = tempBt;
                        }
                    }
                }
                encData = bt64ToHex(encByte);
            }else{
                var iterator = parseInt(leng/4);
                var remainder = leng%4;
                var i=0;
                for(i = 0;i < iterator;i++){
                    var tempData = data.substring(i*4+0,i*4+4);
                    var tempByte = strToBt(tempData);
                    var encByte ;
                    if(firstKey != null && firstKey !="" && secondKey != null && secondKey != "" && thirdKey != null && thirdKey != ""){
                        var tempBt;
                        var x,y,z;
                        tempBt = tempByte;
                        for(x = 0;x < firstLength ;x ++){
                            tempBt = enc(tempBt,firstKeyBt[x]);
                        }
                        for(y = 0;y < secondLength ;y ++){
                            tempBt = enc(tempBt,secondKeyBt[y]);
                        }
                        for(z = 0;z < thirdLength ;z ++){
                            tempBt = enc(tempBt,thirdKeyBt[z]);
                        }
                        encByte = tempBt;
                    }else{
                        if(firstKey != null && firstKey !="" && secondKey != null && secondKey != ""){
                            var tempBt;
                            var x,y;
                            tempBt = tempByte;
                            for(x = 0;x < firstLength ;x ++){
                                tempBt = enc(tempBt,firstKeyBt[x]);
                            }
                            for(y = 0;y < secondLength ;y ++){
                                tempBt = enc(tempBt,secondKeyBt[y]);
                            }
                            encByte = tempBt;
                        }else{
                            if(firstKey != null && firstKey !=""){
                                var tempBt;
                                var x;
                                tempBt = tempByte;
                                for(x = 0;x < firstLength ;x ++){
                                    tempBt = enc(tempBt,firstKeyBt[x]);
                                }
                                encByte = tempBt;
                            }
                        }
                    }
                    encData += bt64ToHex(encByte);
                }
                if(remainder > 0){
                    var remainderData = data.substring(iterator*4+0,leng);
                    var tempByte = strToBt(remainderData);
                    var encByte ;
                    if(firstKey != null && firstKey !="" && secondKey != null && secondKey != "" && thirdKey != null && thirdKey != ""){
                        var tempBt;
                        var x,y,z;
                        tempBt = tempByte;
                        for(x = 0;x < firstLength ;x ++){
                            tempBt = enc(tempBt,firstKeyBt[x]);
                        }
                        for(y = 0;y < secondLength ;y ++){
                            tempBt = enc(tempBt,secondKeyBt[y]);
                        }
                        for(z = 0;z < thirdLength ;z ++){
                            tempBt = enc(tempBt,thirdKeyBt[z]);
                        }
                        encByte = tempBt;
                    }else{
                        if(firstKey != null && firstKey !="" && secondKey != null && secondKey != ""){
                            var tempBt;
                            var x,y;
                            tempBt = tempByte;
                            for(x = 0;x < firstLength ;x ++){
                                tempBt = enc(tempBt,firstKeyBt[x]);
                            }
                            for(y = 0;y < secondLength ;y ++){
                                tempBt = enc(tempBt,secondKeyBt[y]);
                            }
                            encByte = tempBt;
                        }else{
                            if(firstKey != null && firstKey !=""){
                                var tempBt;
                                var x;
                                tempBt = tempByte;
                                for(x = 0;x < firstLength ;x ++){
                                    tempBt = enc(tempBt,firstKeyBt[x]);
                                }
                                encByte = tempBt;
                            }
                        }
                    }
                    encData += bt64ToHex(encByte);
                }
            }
        }
        return encData;
    }

    private static byte[] enc(byte[] tempBt, byte b) {




    }

    private static byte[] getKeyBytes(String key) {

        byte [] keyBytes={};
        int leng = key.length() ;
        int iterator = leng/4;
        int remainder = leng%4;
        int i = 0;
        for(i = 0;i < iterator; i ++){
            keyBytes = strToBt(key.substring(i*4+0,i*4+4));
        }
        if(remainder > 0){
            keyBytes  = strToBt(key.substring(i*4+0,leng));
        }
        return keyBytes;
    }

    private static byte[] strToBt(String str) {

        int leng = str.length();
        byte [] bt =new byte[64];
        if(leng < 4){
            int i=0,j=0,p=0,q=0;
            for(i = 0;i<leng;i++){
                char k = str.charAt(i);
                for(j=0;j<16;j++){
                    int pow=1,m=0;
                    for(m=15;m>j;m--){
                        pow *= 2;
                    }
                    bt[16*i+j]= (byte)(k/pow%2);
                }
            }
            for(p = leng;p<4;p++){
                int k = 0;
                for(q=0;q<16;q++){
                    int pow=1,m=0;
                    for(m=15;m>q;m--){
                        pow *= 2;
                    }
                    bt[16*p+q]= (byte) (k/pow%2);
                }
            }
        }else{
            for(int i = 0;i<4;i++){
                char k = str.charAt(i);
                for(int j=0;j<16;j++){
                    int pow=1;
                    for(int m=15;m>j;m--){
                        pow *= 2;
                    }
                    bt[16*i+j]= (byte) (k/pow%2);
                }
            }
        }
        return bt;

    }


    public static void main(String[] args) {
        System.out.println("123131".length());
        System.out.println(strToBt("2222"));
    }
}
*/
