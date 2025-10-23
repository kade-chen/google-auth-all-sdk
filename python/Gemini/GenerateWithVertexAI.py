from google import genai
from google.genai.types import (
    GenerateContentConfig,
    HarmCategory,
    HarmBlockThreshold,
    SafetySetting,
    ThinkingConfig,
)
from google.oauth2 import service_account

####################################
#        Vertex AI Gemini API      #
####################################

# 注意：请确保此路径指向您实际的服务账户密钥文件
service_account_key_path="xxx.json"
# 身份验证范围
credentials = service_account.Credentials.from_service_account_file(
            service_account_key_path,
            scopes=['https://www.googleapis.com/auth/cloud-platform']
)

# 初始化客户端，指定 Vertex AI 项目、区域和凭证
client = genai.Client(
    vertexai=True,
    credentials=credentials,
    project="your_preject", # 替换为您的项目ID
    location = "us-central1", # 替换为您的区域
)

# --- 模型配置 (与 Java 逻辑一致) ---

# 1. 系统指令 (System Instruction) - 启用中文回答
# "你是一位人工智能专家讲解员。请用中文提供简洁、高层次的摘要，适合初学者理解，保持鼓励和清晰的语气。"
system_instruction = "你是一位人工智能专家讲解员。请用中文提供简洁、高层次的摘要，适合初学者理解，保持鼓励和清晰的语气。"

# 2. 安全设置 (Safety Settings)
safety_settings = [
    SafetySetting(
        category=HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT, # 危害类别：危险内容
        threshold=HarmBlockThreshold.BLOCK_ONLY_HIGH, # 仅阻止高阈值（即阻止较少）。
    ),
    SafetySetting(
        category=HarmCategory.HARM_CATEGORY_HATE_SPEECH, # 危害类别：仇恨言论
        threshold=HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE, # 阻止中等阈值及以上。
    ),
]

# 3. 思考配置 (Thinking Config)
thinking_config = ThinkingConfig(
    include_thoughts=False, # 不返回模型内部生成的思考过程文本
    thinking_budget=0,      # 禁用思考模式
)

# 4. 组合配置
config = GenerateContentConfig(
    system_instruction=system_instruction,
    safety_settings=safety_settings,
    thinking_config=thinking_config,
)

# --- 发起内容生成请求 ---
model_id = "gemini-2.5-flash"
prompt = "How does AI work?" # 虽然提示是英文，但系统指令会强制模型用中文回答

response = client.models.generate_content(
    model=model_id,
    contents=prompt,
    config=config, # 传入完整配置
)

print(response.text)
# Example response:
# Okay, let's break down how AI works. It's a broad field, so I'll focus on the ...
#
# Here's a simplified overview:
# ...

#  /usr/bin/python3 /Users/kade.chen/go-kade-project/github/google-auth-all-sdk/python/Gemini.GenerateWithVertexAI.py
#  Python 3.9.6