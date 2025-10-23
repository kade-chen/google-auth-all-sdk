from google.api_core.client_options import ClientOptions
from google.cloud import translate_v2 as translate

# 待翻译文本
text_to_translate = "你好，我想吃饭了"
target_language = "en"

api_key = "xxx"

client_options = ClientOptions(api_key = api_key)

client = translate.Client(client_options = client_options)

# V2 翻译调用：
result = client.translate(
    text_to_translate,
    target_language=target_language
)

print(f"Translated Text: {result['translatedText']}")