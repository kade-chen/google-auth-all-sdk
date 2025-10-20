import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.genai.Client;
import com.google.genai.types.*;

public class GenerateWithVertexAI {

  public static void main(String[] args) throws Exception {
    String modelId = "gemini-2.5-flash";
    generateContent(modelId);
  }

  public static String generateContent(String modelId) throws Exception {
    String path = "xxx.json";

    InputStream inputStream = new FileInputStream(path);
    List<String> SCOPES = List.of("https://www.googleapis.com/auth/cloud-platform");

    GoogleCredentials credentials = ServiceAccountCredentials.fromStream(inputStream);
    credentials = (credentials).createScoped(SCOPES);

    try (Client client = Client.builder()
            .project("your_preject")
            .location("us-central1")
            .httpOptions(HttpOptions.builder().apiVersion("v1").build())
            .vertexAI(true)
            .credentials(credentials)
            .build()) {

      Content systemInstruction = Content.fromParts(Part
              .fromText("你是一位人工智能专家讲解员。请用中文提供简洁、高层次的摘要，适合初学者理解，保持鼓励和清晰的语气。"));

      List<SafetySetting> safetySettings = List.of(
              SafetySetting.builder()
                      .category(HarmCategory.Known.HARM_CATEGORY_DANGEROUS_CONTENT) //危害类别是危险内容。
                      .threshold(HarmBlockThreshold.Known.BLOCK_ONLY_HIGH) //仅阻止高阈值（即阻止较少）。
                      .build(),
              SafetySetting.builder()
                      .category(HarmCategory.Known.HARM_CATEGORY_HATE_SPEECH) //危害类别是仇恨言论
                      .threshold(HarmBlockThreshold.Known.BLOCK_MEDIUM_AND_ABOVE) //阻止中等阈值及以上。
                      .build()
      );

      ThinkingConfig thinkingConfig = ThinkingConfig.builder()
              .includeThoughts(false) //用于控制是否在 API 响应中返回模型内部生成的思考过程文本。
              .thinkingBudget(0) // 用于模型内部推理和规划的计算资源量,模型默认是开启思考模式，设置值为0是禁用思考模式。
              .build();

      GenerateContentConfig config = GenerateContentConfig.builder()
              .systemInstruction(systemInstruction) //设置系统指令
              .safetySettings(safetySettings) //配置安全过滤
              .thinkingConfig(thinkingConfig) //配置模型思考模式
              .build();

      GenerateContentResponse response = client.models.generateContent(
              modelId,
              Content.fromParts(Part.fromText("How does AI work?")),
              config);

      System.out.print(response.text());
      // Example response:
      // Okay, let's break down how AI works. It's a broad field, so I'll focus on the
      // ...
      //
      // Here's a simplified overview:
      // ...
      return response.text();
    }
  }
}

// step1 :mvn compile