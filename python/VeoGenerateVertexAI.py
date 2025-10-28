import time
from google import genai
from google.genai.types import GenerateVideosConfig, Image

client = genai.Client(vertexai=True, project="your_project",location="us-central1")


#VertexAI API将生成视频上传到GCS
def veo_upload_gcs():

    operation = client.models.generate_videos(
        model="veo-2.0-generate-001",
        prompt="a cat reading a book",
        config=GenerateVideosConfig(
            aspect_ratio="16:9",
            output_gcs_uri="your_gcs",#gs://demo/veo
            number_of_videos=1,
        ),
    )

    while not operation.done:
        time.sleep(15)
        operation = client.operations.get(operation)
        print(operation)

    if operation.response:
        print(operation.result.generated_videos[0].video.uri)


#VertexAI API把生成视频保存到本地
def veo_download_local():

    operation = client.models.generate_videos(
        model="veo-2.0-generate-001",
        prompt="a cat reading a book",
        config=GenerateVideosConfig(
            aspect_ratio="16:9",
            number_of_videos=1,
        ),
    )

    while not operation.done:
        time.sleep(15)
        operation = client.operations.get(operation)
        print(operation)

    if operation.response:
        generated_video = operation.response.generated_videos[0]

        # 获取 video_bytes 并保存为视频文件
        video_bytes = generated_video.video.video_bytes
        if video_bytes:
            with open("generated_video.mp4", "wb") as f:
                f.write(video_bytes)
            print("Video saved as generated_video.mp4")
        else:
            print("No video bytes available")

if __name__ == '__main__':

    veo_upload_gcs()
    veo_download_local()
