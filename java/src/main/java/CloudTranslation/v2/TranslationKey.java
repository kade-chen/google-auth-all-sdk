package CloudTranslation.v2;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

public class TranslationKey {
    public static void main(String[] args) {

        String textToTranslate = "你好，我想吃饭了";
        String targetLanguage = "en";

        String apiKey = "xxx";

        Translate translate = TranslateOptions.newBuilder()
                .setApiKey(apiKey)
                .build()
                .getService();

        Translation translation = translate.translate(
                textToTranslate,
                Translate.TranslateOption.targetLanguage(targetLanguage)
        );

        System.out.println("Translated Text: " + translation.getTranslatedText());
    }
}
