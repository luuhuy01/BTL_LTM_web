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

        ArrayList<String> arr = new ArrayList<>();
        Document doc = Jsoup.parse(resHttp);
        Element e1 = doc.getElementById("menu-main-menu");
        Elements e2 = e1.children();
        System.out.println(e2.size());
        int a[] = new int[1000];
        for (int i = 0; i < 1000; i++) {
            a[i] = 0;
        }
        Try(e2, 0, a);
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
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Host", "qldt.ptit.edu.vn");
//            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4302.0 Safari/537.36");
//            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
//            connection.setRequestProperty("Cache-Control", "max-age=0");
//            connection.setRequestProperty("Accept-Language", "vi,en;q=0.9,vi-VN;q=0.8,fr-FR;q=0.7,fr;q=0.6,en-US;q=0.5,ko;q=0.4");
//            connection.setRequestProperty("Origin", "http://qldt.ptit.edu.vn");
//            String cookie = "ASP.NET_SessionId=41nvge45rhcjyz45q4zqlxnb; SL_GWPT_Show_Hide_tmp=1; SL_wptGlobTipTmp=1";
//            String[] temp = cookie.split(";");
//            for (int i = 0; i < temp.length; i++) {
//                connection.setRequestProperty("Cookie", temp[i]);
//            }
//
//            connection.setRequestProperty("Connection", "keep-alive");
//            connection.setRequestProperty("Referer", "http://qldt.ptit.edu.vn/default.aspx");   
//            connection.setRequestProperty("Upgrade-Insecure-Requests","1");

//            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundarysbqhLkOyWSQGlpIE");
//            connection.setRequestProperty("Content-Length", "30033");
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            //      login bằng cookie
            int status = connection.getResponseCode();
            System.out.println(status);

            connection.connect();
            System.out.println(connection.getResponseMessage());

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = reader.readLine()) != null) {
                responseContent.append(line);
                osw.write(line);
            }
            reader.close();

            // System.out.println(responseContent.toString());
            // sử dụng jsoup để query html 
            osw.close();
            fos.close();

            connection.disconnect();
        } catch (MalformedURLException ex) {

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

    // đệ qui để lấy hết menu
    public static void Try(Elements e2, int j, int a[]) {
        //       System.out.println(e2.toString());

        for (int i = 0; i < e2.size(); i++) {
            String link = e2.get(i).attr("href");
            Elements e4 = e2.get(i).getElementsByTag("span");
            String title = "";

            for (int k = 0; k < e4.size(); k++) {
                title = e4.text();
            }
            if (!link.isEmpty()) {
                System.out.println("sub " + j  + "; title" + ": " + title + "; Link " + ": " + link);
                System.out.println(a[i]);
            } else {
                a[i]++;
                Elements e5 = e2.get(i).children();
//                System.out.println(e5.toString());
//                for (int m = 0; m < e5.size(); m++) {
//                    Elements e6 = e5.get(m).getElementsByTag("a");
//                    System.out.println(e6.text());
//                }
                Try(e5, i, a);
                j--;
            }
//            }
//            String str = e.text();
//            arr.add(str);
//            System.out.println("context"+(i+1)+": "+str);
        }

    }
}
