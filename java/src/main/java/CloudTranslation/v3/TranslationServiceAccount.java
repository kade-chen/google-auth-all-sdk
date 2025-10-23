package CloudTranslation.v3;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.v3.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class TranslationServiceAccount {

    private static final String service_account_key_path = "xxx.json";

    private static final List<String> SCOPES =
            List.of("https://www.googleapis.com/auth/cloud-platform");

    public static void main(String[] args) throws IOException {

        String projectId = "YOUR_PROJECT"; // 请替换为您的项目 ID
        String targetLanguage = "en";
        String text = "你好，我想吃饭了";

        translateText(projectId, targetLanguage, text);
    }

    public static void translateText(String projectId, String targetLanguage, String text)
            throws IOException {

        GoogleCredentials baseCredentials;
        try (InputStream inputStream = new FileInputStream(service_account_key_path)) {
            baseCredentials = ServiceAccountCredentials.fromStream(inputStream);
        }

         GoogleCredentials scopedCredentials = baseCredentials.createScoped(SCOPES);

        TranslationServiceSettings translationServiceSettings =
                TranslationServiceSettings.newBuilder()
                        .setCredentialsProvider(() -> scopedCredentials)
                        .build();

        try (TranslationServiceClient client = TranslationServiceClient.create(translationServiceSettings)) {

            LocationName parent = LocationName.of(projectId, "global");

            String sourceLanguageCode = "zh-CN";

            TranslateTextRequest request =
                    TranslateTextRequest.newBuilder()
                            .setParent(parent.toString())
                            .setMimeType("text/plain")
                            .setSourceLanguageCode(sourceLanguageCode) // 设置源语言
                            .setTargetLanguageCode(targetLanguage) // 设置目标语言
                            .addContents(text)
                            .build();

            TranslateTextResponse response = client.translateText(request);

            for (Translation translation : response.getTranslationsList()) {
                System.out.printf("原文本: %s\n", text);
                System.out.printf("Translated text: %s\n", translation.getTranslatedText());
            }
        }
    }


}
