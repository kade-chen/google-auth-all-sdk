# Mainly used for Google Gen AI SDKs service account authentication

## SDK includes the following development languages:
- Go
- Python
- Java
- Node.js
- C#

## Prerequisites ➡️ Choose one of the following:

### 1.Service Account
1. Create a Google Cloud Project
2. [Enable the Vertex AI API](https://console.cloud.google.com/apis/library/aiplatform.googleapis.com) 
3. [Enable the Cloud Storage API](https://console.cloud.google.com/apis/library/storage-component.googleapis.com)
4. [Create a Service Account](https://console.cloud.google.com/iam-admin/serviceaccounts/create)
5. Grant the Service Account the `roles/aiplatform.admin` or `Vertex AI Administrator` role
6. Create a Key for the Service Account
7. Download the Key as a JSON file
8. Use the JSON file to authenticate the SDK

### 2.Security Configuration ➡️ Mixed use of API-KEY and Service Account
1. Create a Google Cloud Project
2. [Enable the Vertex AI API](https://console.cloud.google.com/apis/library/aiplatform.googleapis.com) 
3. [Enable the Cloud Storage API](https://console.cloud.google.com/apis/library/storage-component.googleapis.com)
4. [Create a Service Account](https://console.cloud.google.com/iam-admin/serviceaccounts/create)
5. Grant the Service Account the `roles/aiplatform.admin` or `Vertex AI Administrator` role
6. Go to the Credentials page and create a new API Key
7. To bind the API key to a service account, select the Authenticate API calls through a service account checkbox and then click Select a service account to select the service account you want to bind to the key.
8. [Add API key restrictions](https://cloud.google.com/docs/authentication/api-keys#ip)
9. Click Create Key

### Usage
- [Go](/go/main.go)
- [Python](/python/GenerateWithVertexAI.py)
- [Java](/java/src/main/java/GenerateWithVertexAI.java)
- [Node.js](/node/main.js)
- [C#](/C#/Program.cs)

## Machine Learning
- [Machine Learning](https://github.com/kade-chen/machine-learning)
