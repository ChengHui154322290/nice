package com.nice.good.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

@Slf4j
public class GetResponse {
        private boolean myResult;
        private String imgsUrl;
        private String responseResult;

        public GetResponse(String imgsUrl) {
            this.imgsUrl = imgsUrl;
        }

        public boolean is() {
            return myResult;
        }

        public String getResponseResult() {
            return responseResult;
        }

        public GetResponse invoke() {
            responseResult = "";
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                URL realUrl = new URL(imgsUrl);
                // 打开和URL之间的连接
                URLConnection conn = realUrl.openConnection();
                // 设置通用的请求属性
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 建立实际的连接
                conn.connect();

                // 获取所有响应头字段
                Map<String, List<String>> map = conn.getHeaderFields();
                // 遍历所有的响应头字段
                for (String key : map.keySet()) {
                    System.out.println(key + "--->" + map.get(key));
                }
                in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    responseResult += line;
                }
            } catch (Exception e) {
                log.info("发送GET请求出现异常！" + e);
                e.printStackTrace();
                myResult = true;
                return this;
            }
            //使用finally块来关闭输出流、输入流
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                    myResult = true;
                    return this;
                }
            }
            myResult = false;
            return this;
        }
    }