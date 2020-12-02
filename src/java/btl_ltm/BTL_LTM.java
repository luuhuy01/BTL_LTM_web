/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package btl_ltm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.Menu;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author luuhuy
 */
public class BTL_LTM {

    public static void main(String[] args) {
        String resHttp = getHttp("https://portal.ptit.edu.vn/");
        Document doc = Jsoup.parse(resHttp);
        Element e1 = doc.getElementById("menu-main-menu");
        Elements e2 = e1.children();
        System.out.println(e2.size());
        int a[] = new int[1000];
        ArrayList<Menu> arr = new ArrayList<>();
        arr = Try(e2, 0);                // gán mảng các menu.

        // crawl data từng menu
        for (int i = 0; i < arr.size(); i++) {
            String html = getHttp(arr.get(i).getUrl());
            Document d = Jsoup.parse(html);
            Elements ex1 = d.getElementsByClass("sections_group");
            for(int j=0; j<ex1.size(); j++){
//               Elements ex2 = ex1.get(j).getElementsByTag("a");
//               for(int k=0; k<ex2.size(); k++){
//                   System.out.println(ex2.get(k).text());
//               }
                 System.out.println(ex1.get(j).text());
            }
        }
//        String menu1 = RegexHtml(resHttp,"<div class=\"menu_wrapper\\\">(.*?)<\\/div>");
//        System.out.println(menu1);
//        System.out.println(e.text());
    }

    public static String getHttp(String link) {
        HttpURLConnection connection;
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            // ghi file index.html
            File file = new File("index.html");
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");

            // dùng httpURLConnection gửi request để lấy về html
            URL url = new URL(link);
            connection = (HttpURLConnection) url.openConnection();

            connection.setUseCaches(false);
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            //      login bằng cookie
            int status = connection.getResponseCode();
            System.out.println(status);

            connection.connect();
            System.out.println(connection.getResponseMessage());
            if (status < 400) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                    osw.write(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                    osw.write(line);
                }
                reader.close();
            }
            // System.out.println(responseContent.toString());
            // sử dụng jsoup để query html 
            osw.close();
            fos.close();

            connection.disconnect();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(BTL_LTM.class.getName()).log(Level.SEVERE, null, ex);
        }
        return responseContent.toString();
    }

    // lay ra content match = regex
    public static String RegexHtml(String html, String regex) {
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(html);
        String match = "";
        while (matcher.find()) {
            match = matcher.group(0);
        }
        return match;
    }

    public static ArrayList<Menu> arrMenu = new ArrayList<>();

    // đệ qui để lấy hết menu
    public static ArrayList<Menu> Try(Elements e2, int j) {

        for (int i = 0; i < e2.size(); i++) {

            String link = e2.get(i).attr("href");
            Elements e4 = e2.get(i).getElementsByTag("span");
            String title = "";

            for (int k = 0; k < e4.size(); k++) {
                title = e4.text();                           // get tiêu đề menu
            }
            if (!link.isEmpty()) {
                Menu menu = new Menu(title, link);
                System.out.println(menu);
                arrMenu.add(menu);
            } else {
                Elements e5 = e2.get(i).children();
                Try(e5, i);
                j--;
            }
        }
        return arrMenu;
    }
    
    // tách nội dung trong từng menu
    
}
