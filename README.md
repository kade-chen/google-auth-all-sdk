# Mainly used for Google Gen AI SDKs service account authentication

## SDK includes the following development languages:
- [Go](/go/main.go)
- [Python](/python/main.py)
- [Java](/java/src/main/java/GenerateContentWithText.java)
- [Node.js](/node/main.js)

## Setup
1. Create a Google Cloud Project
2. Enable the Vertex AI API
3. Create a Service Account
4. Grant the Service Account the `roles/genai.genaiUser` role
5. Create a Key for the Service Account
6. Set the `GOOGLE_APPLICATION_CREDENTIALS` environment variable to the path of the Key
7. Set the `GOOGLE_CLOUD_PROJECT` environment variable to the name of the Project
8. Set the `GOOGLE_CLOUD_LOCATION` environment variable to the location of the API (default is `global`)

## Usage1