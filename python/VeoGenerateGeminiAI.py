import time
from google import genai
from google.genai.types import GenerateVideosConfig, Image

client = genai.Client(api_key="xxx")

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

    # Download the video.
    video = operation.response.generated_videos[0]
    client.files.donwload(file=video.video)
    video.video.save("veo3.1_with_reference_images.mp4")
    print("Generated video saved to veo3.1_with_reference_images.mp4")

