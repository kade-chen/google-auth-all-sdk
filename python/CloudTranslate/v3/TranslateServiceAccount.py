from google.cloud import translate_v3
from google.oauth2 import service_account
from google.cloud.translate_v3.types import TranslateTextResponse

service_account_key_path = "xxx.json"
PROJECT_ID = "YOUR_PROJECT"
scopes = ["https://www.googleapis.com/auth/cloud-platform"]

# 凭证加载
credentials = service_account.Credentials.from_service_account_file(
    service_account_key_path,
    scopes=scopes
)

def translate_text(
        text: str,
        target_language_code: str,
        source_language_code: str,
        project_id: str,
        credentials: service_account.Credentials
) -> TranslateTextResponse:
    """使用 Google Cloud Translation API v3 翻译文本。"""

    # 1. 初始化 Translation 客户端。
    client = translate_v3.TranslationServiceClient(credentials=credentials)
    parent = f"projects/{project_id}/locations/global"
    mime_type = "text/plain"

    # 2. 调用 API
    response = client.translate_text(
        contents=[text],
        parent=parent,
        mime_type=mime_type,
        source_language_code=source_language_code,
        target_language_code=target_language_code,
    )

    # 3. 显示并返回结果
    for translation in response.translations:
        print(f"原文本: {text}")
        print(f"翻译结果: {translation.translated_text}")

    return response

if __name__ == "__main__":
    text_to_translate = "你好，我想吃饭了"

    # 明确指定源语言为中文 (zh-CN)，目标语言为英文 (en)
    translate_text(
        text=text_to_translate,
        source_language_code="zh-CN",
        target_language_code="en",
        project_id=PROJECT_ID,
        credentials=credentials
    )