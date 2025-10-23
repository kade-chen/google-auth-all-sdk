from google.cloud import translate_v2 as translate
from google.oauth2 import service_account

service_account_key_path= "xxx.json"

scopes = ["https://www.googleapis.com/auth/cloud-platform"]

# 待翻译文本
text_to_translate = "你好，我想吃饭了"
target_language = "en"

credentials = service_account.Credentials.from_service_account_file(
    service_account_key_path,
    scopes=scopes
)

client = translate.Client(credentials=credentials)

# V2 翻译调用：
result = client.translate(
    text_to_translate,
    target_language=target_language
)

print(f"Translated Text: {result['translatedText']}")