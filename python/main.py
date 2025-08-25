from google import genai
from google.oauth2 import service_account


service_account_key_path="xxxxx.json"
credentials = service_account.Credentials.from_service_account_file(
            service_account_key_path,
            scopes=['https://www.googleapis.com/auth/cloud-platform']
)

client = genai.Client(
    vertexai=True,
    credentials=credentials,
    project="xxxxx",
    location = "global",
)
response = client.models.generate_content(
    model="gemini-2.5-flash",
    contents="How does AI work?",
)
print(response.text)
# Example response:
# Okay, let's break down how AI works. It's a broad field, so I'll focus on the ...
#
# Here's a simplified overview:
# ...

#  /usr/bin/python3 /Users/kade.chen/go-kade-project/github/google-auth-all-sdk/python/main.py 
#  Python 3.9.6