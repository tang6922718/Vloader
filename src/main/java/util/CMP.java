package util;


import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * created by Administrator on 2018/2/27.
 * Copyright (c)2018 @lcrawler
 */
public abstract class CMP {
    private static JedisPool jedisPool;
    private String source_key;
    private static String dest_key;
    private String redis_hp;
    private static String auth;
    private static String extra_key;
    private boolean alive=true;
    private static ExecutorService es;

    public CMP( String [] params){
        // 0.数据源key 1.返回数据的key 2.redis host port 3.auth 4.extra_key
        //如果有额外参数 都在 extra_key里面
        if (params.length!=5){
            System.err.println("启动参数长度必须是5");
        }else {
            es=Executors.newFixedThreadPool( 2 );
           this.source_key=params[0];
           dest_key=params[1];
           this.redis_hp=params[2];
           auth=params[3];
           extra_key=params[4];
            JedisPoolConfig config=new JedisPoolConfig();
            String[] hp = redis_hp.split( ":" );
            config.setMaxIdle( 20 );
            if(auth.equals( "%" )){
                auth="";
            }
            jedisPool=new JedisPool(hp[0],Integer.valueOf( hp[1] ));
            this.run();
        }
    }
    private CMP(){}
    abstract public  void onUrlReceive(String url);
     public   void sendData(Object map){
         if ( jedisPool!=null ){
             Jedis jedis = jedisPool.getResource();
             if(!auth.equals( "" ))
             jedis.auth( auth );
             if ( map instanceof List ){
                 List l = ( List ) map;
                 String[]p=new String[l.size()];
                 for ( int i = 0; i < l.size(); i++ ) {
                     Object o = l.get( i );
                    p[i]=JSON.toJSONString( o );
                 }
                 jedis.lpush(dest_key,p);
             }else {
                 jedis.lpush( dest_key,JSON.toJSONString( map ) );
             }


             jedisPool.returnResource( jedis );
         }else {
         throw  new NullPointerException( "redis didn't init please use [CMP(String[]args)] to init pool" );
         }
     }

     public void run(){
         es.execute( new Runnable() {

             public void run() {
                 while ( alive ){
                     Jedis jedis = jedisPool.getResource();
                     if(!auth.equals( "" ))
                         jedis.auth( auth );
                     String url = jedis.rpop( source_key );
                     if ( url!=null ){
                         onUrlReceive( url );
                     }
                     jedisPool.returnResource( jedis );
                 }
             }
         });
         es.execute( new Runnable() {

             public void run() {
                 while ( alive ){
                     Jedis jedis = jedisPool.getResource();
                     if(!auth.equals( "" ))
                         jedis.auth( auth );
                     String word = jedis.rpop( extra_key );
                     if ( word!=null ){
                         if ( word.equals( "stop" ) ){
                             System.out.println("received [stop] command");
                             stop();
                         }
                     }
                     jedisPool.returnResource( jedis );
                 }
             }
         });

     }

public  void stop(){
    alive=false;
    es.shutdownNow();
    afterStop();
}
public abstract void afterStop();
}
