import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.HttpOptions;
import com.google.genai.types.Part;

public class GenerateContentWithText {

  public static void main(String[] args) throws Exception {
    String modelId = "gemini-2.5-flash";
    generateContent(modelId);
  }

  public static String generateContent(String modelId) throws Exception {
    String path = "xxxxx.json";

    InputStream inputStream = new FileInputStream(path);
    List<String> SCOPES = List.of("https://www.googleapis.com/auth/cloud-platform");

    GoogleCredentials credentials = ServiceAccountCredentials.fromStream(inputStream);
    credentials = (credentials).createScoped(SCOPES);

    try (Client client = Client.builder()
        .project("xxxxx")
        .location("us-central1")
        .httpOptions(HttpOptions.builder().apiVersion("v1").build())
        .vertexAI(true)
        .credentials(credentials)
        .build()) {

      GenerateContentResponse response = client.models.generateContent(modelId, Content.fromParts(
          Part.fromText("How does AI work?")),
          null);

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