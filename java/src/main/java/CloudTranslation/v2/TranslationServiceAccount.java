package CloudTranslation.v2;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


public class TranslationServiceAccount {
    public static void main(String[] args) throws IOException {

        String service_account_key_path = "xxx.json";

        InputStream inputStream = new FileInputStream(service_account_key_path);
        List<String> SCOPES = List.of("https://www.googleapis.com/auth/cloud-platform");

        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(inputStream);
        credentials = (credentials).createScoped(SCOPES);

        Translate translate = TranslateOptions.newBuilder()
                .setProjectId("Your_projectId")
                .setCredentials(credentials)
                .build()
                .getService();

        Translation translation = translate.translate("你好，我想吃饭了");
        System.out.printf("Translated Text:"+ translation.getTranslatedText());

    }
}
