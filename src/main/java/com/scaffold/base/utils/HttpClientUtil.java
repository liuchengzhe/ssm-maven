package com.scaffold.base.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * http请求和接收
 */
@Slf4j
public class HttpClientUtil {
    private static String charSet = "UTF-8"; // 编码格式

    public static final int CONNECT_TIMEOUT = 3 * 1000; // 默认连接超时时间

    public static final int READ_TIMEOUT = 10 * 1000; // 读取超时时间

    /**
     * 文本请求,文本返回
     */
    public static String doHttp(String goURL, String method, String body, String contentType, Map<String, String> headers, int connectTimeout, int readTimeout) {
        HttpURLConnection connection = null;
        BufferedOutputStream outputStream = null;
        try {
            connection = (HttpURLConnection) new URL(goURL).openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(connectTimeout);
            connection.setReadTimeout(readTimeout);
            if (contentType != null) {
                connection.setRequestProperty("Content-Type", contentType);
            }

            if (headers != null && headers.size() > 0) {
                for (String header : headers.keySet()) {
                    connection.setRequestProperty(header, headers.get(header));
                }
            }

            // 请求参数处理
            if (body != null && !body.equals("")) {
                byte[] buff = body.getBytes(charSet);
                connection.setRequestProperty("Content-Length", String.valueOf(buff.length));
                outputStream = new BufferedOutputStream(connection.getOutputStream());
                outputStream.write(buff);
                outputStream.flush();
            }
            // 返回参数处理
            return IOUtils.toString(connection.getInputStream(), charSet);
        } catch (Exception e) {
            log.error("http请求异常! 地址:{} 参数:{}", goURL, body, e);
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (outputStream != null) {
                IOUtils.closeQuietly(outputStream);
            }
        }
        return null;
    }

    /**
     * application/x-www-form-urlencoded
     */
    public static String doPostFormUrlencoded(String strUrl, String content) {
        String result = "";

        try {
            URL url = new URL(strUrl);
            // 通过调用 url.openConnection() 来获得一个新的 URLConnection 对象，并且将其结果强制转换为 HttpURLConnection.
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("POST");
            // 设置连接的超时值为 30000 毫秒，超时将抛出 SocketTimeoutException 异常
            urlConnection.setConnectTimeout(30000);
            // 设置读取的超时值为 30000 毫秒，超时将抛出 SocketTimeoutException 异常
            urlConnection.setReadTimeout(30000);
            // 将 url 连接用于输出，这样才能使用 getOutputStream()。getOutputStream() 返回的输出流用于传输数据
            urlConnection.setDoOutput(true);
            // 设置通用请求属性为默认浏览器编码类型
            urlConnection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //getOutputStream() 返回的输出流，用于写入参数数据。
            OutputStream outputStream = urlConnection.getOutputStream();
            outputStream.write(content.getBytes());
            outputStream.flush();
            outputStream.close();
            // 此时将调用接口方法。getInputStream() 返回的输入流可以读取返回的数据。
            InputStream inputStream = urlConnection.getInputStream();
            int count = 0;
            while (count == 0) {
                count = inputStream.available();
            }
            byte[] data = new byte[count];
            StringBuilder sb = new StringBuilder();
            //inputStream 每次就会将读取 1024 个 byte 到 data 中，当 inputSteam 中没有数据时，inputStream.read(data) 值为 - 1
            while (inputStream.read(data) != -1) {
                String s = new String(data, Charset.forName("utf-8"));
                sb.append(s);
            }
            result = sb.toString();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}

