import com.google.genai.Client;
import com.google.genai.types.GenerateVideosConfig;
import com.google.genai.types.GenerateVideosOperation;

public class VeoGenerateGeminiAI {

    public static void main(String[] args) {

        Client client = Client.builder()
                .apiKey("xxx")
                .build();

        veoDownloadLocal(client);

    }

    public static void veoDownloadLocal(Client client){

        GenerateVideosConfig config = GenerateVideosConfig.builder()
                .aspectRatio("16:9")
                .numberOfVideos(1)
                .build();

        GenerateVideosOperation operation = client.models.generateVideos(
                "veo-2.0-generate-001",
                "a cat reading a book",
                null,
                config
        );

        while (!operation.done().isPresent()) {
            try {
                System.out.println("Waiting for operation to complete...");
                Thread.sleep(10000);
                // Sleep for 10 seconds and check the operation again
                operation = client.operations.getVideosOperation(operation, null);
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted while sleeping.");
                Thread.currentThread().interrupt();
            }
        }

        operation.response().ifPresent(
                response -> {
                    response.generatedVideos().ifPresent(
                            videos -> {
                                System.out.println("Generated " + videos.size() + " videos.");
                                videos.get(0).video().ifPresent(video -> {
                                    // 调用下载方法，将视频保存为本地文件
                                    client.files.download(video,"neon_cat_video.mp4",null);
                                });
                            }
                    );
                }
        );
    }

}
