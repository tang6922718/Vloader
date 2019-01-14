package util;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

/**
 * @Auther: tlz
 * @Date: 2018/10/26 17:29
 * @Description:
 */
public class VerifyCode {

    private static final String datapath = "src/main/resources";
    private final String testResourcesDataPath = "src/main/resources/test-data";
    private static final String testResourcesLanguagePath = "src/main/resources/tessdata";


    public static BufferedImage getImage() {
        BufferedImage im = new BufferedImage( 60, 20, BufferedImage.TYPE_INT_RGB );

        Graphics g = im.getGraphics();
        Random rm = new Random();
        Color c = new Color( rm.nextInt( 255 ), rm.nextInt( 255 ), rm.nextInt( 255 ) );
        g.setColor( c );
        //填充整个图片的颜色
        g.fillRect( 0, 0, 60, 20 );

        g.setColor( new Color( rm.nextInt( 255 ), rm.nextInt( 255 ), rm.nextInt( 255 ) ) );
        g.setFont( new Font( "华文隶书", Font.BOLD | Font.ITALIC, 28 ) );
        g.drawString( "t l z", 1, 18 );

    /*    //随机产生4位数字
        for(int i=0;i<4;i++){
            g.setColor(new Color(rm.nextInt(255),rm.nextInt(255),rm.nextInt(255)));
            g.setFont(new Font("Gungsuh",Font.BOLD|Font.ITALIC,22));
            g.drawString(""+rm.nextInt(10), (i*15)+2, 18);
        }
*/
        String str = "胸有激雷而面如平湖者可拜上将军";
        for ( int i = 0; i < 4; i++ ) {
            g.setColor( new Color( rm.nextInt( 255 ), rm.nextInt( 255 ), rm.nextInt( 255 ) ) );
            g.setFont( new Font( "Gungsuh", Font.BOLD | Font.ITALIC, 15 ) );
            g.drawString( "" + str.charAt( rm.nextInt( str.length() ) ), ( i * 15 ) + 2, 18 );
        }
        return im;

    }

    public static InputStream getImgStream( String url ) {
        try {
            HttpURLConnection connection = ( HttpURLConnection ) new URL( url ).openConnection();
            connection.setReadTimeout( 5000 );
            connection.setConnectTimeout( 5000 );
            connection.setRequestMethod( "GET" );
            if ( connection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                InputStream inputStream = connection.getInputStream();
                return inputStream;
            }
        } catch ( IOException e ) {
            System.out.println( "获取网络图片出现异常，图片路径为：" + url );
            e.printStackTrace();
        }

        return null;
    }

    //使用百度API识别处理图片
    public static void main( String[] args ) {
        getImage();

    }




    //tess4j图片识别有问题，待定有时间了在修改
//    public static void main( String[] args ) {
//        String verify = "http://www.variflight.com/flight/index/productcaptcha?AE71649A58c77=";
//        ITesseract instance = new Tesseract();
//        instance.setDatapath( testResourcesLanguagePath );
//        try {
//            BufferedImage bi = ImageIO.read( getImgStream( verify ) );
//
//            String result = instance.doOCR( bi );
//
//            System.out.println( result );
//
//        } catch ( TesseractException e ) {
//            e.printStackTrace();
//        } catch ( IOException e ) {
//            e.printStackTrace();
//        }
//
//    }
}
