import com.google.genai.Client;
import com.google.genai.types.GenerateVideosConfig;
import com.google.genai.types.GenerateVideosOperation;
import com.google.genai.types.Video;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

public class VeoGenerateVertexAI {

    public static void main(String[] args) {

        Client client = Client.builder()
                .project("your_project")
                .location("us-central1")
                .vertexAI(true)
                .build();

        //veoUploadGcs(client);
        veoDownloadLocal(client);
    }

    public static void veoUploadGcs(Client client){

        GenerateVideosConfig config = GenerateVideosConfig.builder()
                .aspectRatio("16:9")
                .numberOfVideos(1)
                .outputGcsUri("your_gcs") //例如gs://demo/veo
                .build();

        GenerateVideosOperation operation = client.models.generateVideos(
                "veo-2.0-generate-001",
                "a cat reading a book",
                null,
                config
        );

        // When the operation hasn't been finished, operation.done() is empty
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
                                Video video = videos.get(0).video().orElse(null);
                                // Do something with the generated video
                            }
                    );
                }
        );

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
                                    downloadVideo(video,"neon_cat_video1.mp4");
                                });
                            }
                    );
                }
        );
    }

    // 用于将 Video 对象包含的字节数据写入本地文件
    private static void downloadVideo(Video video, String fileName) {
        // 视频的二进制数据通常存储在一个名为 videoBytes 的字段中
        Optional<byte[]> videoBytes = video.videoBytes();

        if (videoBytes.isPresent()) {
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                // 将 byte 数组写入文件输出流
                fos.write(videoBytes.get());
                System.out.println("fileName:" + fileName);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            System.out.println("video is null");
        }
    }


}
