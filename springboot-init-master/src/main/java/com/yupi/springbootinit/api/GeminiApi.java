package com.yupi.springbootinit.api;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class GeminiApi {
    public static String GeminiAi(String massage) {
        String textContent = null;
        try {
            String API_KEY = "key";
            // 设置请求URL
            URL url = new URL("https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=" + API_KEY);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // 设置请求方法为POST
            connection.setRequestMethod("POST");

            // 启用输入输出
            connection.setDoOutput(true);
            connection.setDoInput(true);

            // 设置请求头
            connection.setRequestProperty("Content-Type", "application/json");

            // 构建请求体
            String requestBody = "{\n" +
                    "    \"contents\": [\n" +
                    "        {\n" +
                    "            \"parts\": [\n" +
                    "                {\n" +
                    "                    \"text\": \"你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
                    "分析需求：\n" +
                    "{数据分析的需求或者目标}\n" +
                    "原始数据：\n" +
                    "{csv格式的原始数据，用,作为分隔符}\n" +
                    "请根据这两部分内容，按照以下指定格式生成内容（此外不要输出任何多余的开头、结尾、注释）\n" +
                    "【【【【【\n" +
                    "{前端 Echarts V5 的 option 配置对象js代码，合理地将数据进行可视化，省略option,必须对每个参数用双引号包起来,不要生成任何多余的内容，比如注释}\n" +
                    "【【【【【\n" +
                    "{明确的数据分析结论、越详细越好，不要生成多余的注释}\n" +
                    massage +
                    "\"\n"+
                    "                }\n" +
                    "            ]\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}";

            // 获取输出流并写入请求体
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 获取响应码
            int responseCode = connection.getResponseCode();

            // 读取响应内容
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                System.out.println("Response Code: " + responseCode);
                System.out.println("Response Body: " + response.toString());
                // 解析响应内容
                String jsonData = response.toString();
                textContent = JSONParserExample(jsonData);
                System.out.println("Text Content: \n" + textContent);
            }

            // 关闭连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textContent;
    }
    private static String JSONParserExample(String jsonData){
        String textContent=null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);

            // 提取text内容
            textContent = jsonNode.at("/candidates/0/content/parts/0/text").asText();

            System.out.println("Text Content: \n" + textContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return textContent;
    }
}


