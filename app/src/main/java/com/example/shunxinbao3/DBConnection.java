package com.example.shunxinbao3;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
public class DBConnection {
    public static void link (){
        //要连接的数据库url,注意：此处连接的应该是服务器上的MySQl的地址
        String url = "jdbc:mysql:cdb-q5b2ruxz.gz.tencentcdb.com:10073/android?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        //连接数据库使用的用户名
        String userName = "root";
        //连接的数据库时使用的密码
        String password = "masihua1997";
        Connection connection=null;
        try {
            //1、加载驱动
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("驱动加载成功！！！");
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("驱动加载失败！！！");
        }

        try {
            //2、获取与数据库的连接
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("连接数据库成功！！！");
            //3.sql语句
            String sql = "INSERT INTO d (id, name) VALUES ( '24100413', 'ljy')";
            //4.获取用于向数据库发送sql语句的ps
            PreparedStatement ps=connection.prepareStatement(sql);
            ps.execute(sql);

        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接数据库失败！！！");
        }
        finally {
            if(connection!=null){
                try {
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
}
