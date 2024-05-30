package com.example.aiserver.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.net.URIBuilder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class GenerateAIImgUtil {

    public static final String hostUrl = "https://spark-api.cn-huabei-1.xf-yun.com/v2.1/tti";
    public static final String appid = "95201358";
    public static final String apiSecret = "YTgxNTI4MjQxYWQ0MmM5NDI3NGNlMWEz";
    public static final String apiKey = "ced7dbabde43b6da934e31a9a2759c8f";


    /**
     * @param prompt 提示词 小于1000字   domain: general
     */
    public static void generateAIImg(String prompt) {
        String authUrl = "";
        try {
            authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
            String json = "{\n" +
                    "  \"header\": {\n" +
                    "    \"app_id\": \"" + appid + "\",\n" +
                    "    \"uid\": \"" + UUID.randomUUID().toString().substring(0, 15) + "\"\n" +
                    "  },\n" +
                    "  \"parameter\": {\n" +
                    "    \"chat\": {\n" +
                    "      \"domain\": \"s291394db\",\n" +
                    "      \"temperature\": 0.5,\n" +
                    "      \"max_tokens\": 4096,\n" +
                    "      \"width\": 512,\n" +
                    "      \"height\": 512\n" +
                    "    }\n" +
                    "  },\n" +
                    "  \"payload\": {\n" +
                    "    \"message\": {\n" +
                    "      \"text\": [\n" +
                    "        {\n" +
                    "          \"role\": \"user\",\n" +
                    "          \"content\": \"" + prompt + "\"\n" +
                    "        }\n" +
                    "      ]\n" +
                    "    }\n" +
                    "  }\n" +
                    "}";
            String res = doPostJson(authUrl, null, json);
            parseResImgContent(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void parseResImgContent(String res) {
        JSONObject parseObject = JSONObject.parseObject(res);
        JSONObject payload = parseObject.getJSONObject("payload");
        JSONObject choices = payload.getJSONObject("choices");
        JSONArray text = choices.getJSONArray("text");
        String base64String = text.getJSONObject(0).getString("content");

        // 目标文件路径
        String outputFilePath = System.getProperty("user.dir")
                + System.getProperty("file.separator") + "images"
                + System.getProperty("file.separator") + "ai_generate"
                + System.getProperty("file.separator") + System.currentTimeMillis() + "output_image.png";

        try {
            // 调用方法将Base64字符串转换为图片文件
            decodeBase64ToImage(base64String, outputFilePath);
            log.warn("图片已成功保存到: " + outputFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 发起post请求
     */
    private static String doPostJson(String url, Map<String, String> urlParams, String json) {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        CloseableHttpResponse closeableHttpResponse = null;
        String resultString = "";
        try {
            // 创建HttpPost对象并设置URI
            String asciiUrl = URI.create(url).toASCIIString();
            HttpPost httpPost = new HttpPost(asciiUrl);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");

            // 添加URL参数
            if (urlParams != null) {
                URIBuilder uriBuilder = new URIBuilder(httpPost.getUri());
                for (Map.Entry<String, String> entry : urlParams.entrySet()) {
                    uriBuilder.addParameter(entry.getKey(), entry.getValue());
                }
                httpPost.setUri(uriBuilder.build());
            }

            // 设置请求实体
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON.withCharset(StandardCharsets.UTF_8));
            httpPost.setEntity(entity);

            // 执行HTTP请求
            closeableHttpResponse = closeableHttpClient.execute(httpPost);
            resultString = EntityUtils.toString(closeableHttpResponse.getEntity(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (closeableHttpResponse != null) {
                    closeableHttpResponse.close();
                }
                if (closeableHttpClient != null) {
                    closeableHttpClient.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    // 鉴权方法
    private static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // date="Thu, 12 Oct 2023 03:05:28 GMT";
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" + "date: " + date + "\n" + "POST " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }

    /**
     * 将Base64字符串解码并保存为图片文件
     *
     * @param base64String   Base64编码的字符串
     * @param outputFilePath 输出文件路径
     * @throws Exception 如果出现任何错误
     */
    private static void decodeBase64ToImage(String base64String, String outputFilePath) throws Exception {
        // 解码Base64字符串
        byte[] imageBytes = Base64.getDecoder().decode(base64String);

        // 创建输出文件
        File outputFile = new File(outputFilePath);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        }

        // 写入文件
        try (OutputStream os = new FileOutputStream(outputFile)) {
            os.write(imageBytes);
            os.flush();
        }
    }

}
