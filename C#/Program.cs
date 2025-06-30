using Google.Cloud.AIPlatform.V1;
using Google.Apis.Auth.OAuth2; // 引入 GoogleCredential
using System;
using System.Threading.Tasks;

public class TextInputSample
{
    public async Task<string> TextInput(
        string projectId = "xxxxx",
        string location = "us-central1",
        string publisher = "google",
        string model = "gemini-2.0-flash-001")
    {
        // 👇 加载服务账号 JSON 文件
        var credential = GoogleCredential.FromFile("xxxxx.json")
                                         .CreateScoped("https://www.googleapis.com/auth/cloud-platform");

        // 👇 用服务账号创建 PredictionServiceClient
        var predictionServiceClient = new PredictionServiceClientBuilder
        {
            Endpoint = $"{location}-aiplatform.googleapis.com",
            Credential = credential
        }.Build();

        string prompt = @"What's a good name for a flower shop that specializes in selling bouquets of dried flowers?";

        var generateContentRequest = new GenerateContentRequest
        {
            Model = $"projects/{projectId}/locations/{location}/publishers/{publisher}/models/{model}",
            Contents =
            {
                new Content
                {
                    Role = "USER",
                    Parts =
                    {
                        new Part { Text = prompt }
                    }
                }
            }
        };

        GenerateContentResponse response = await predictionServiceClient.GenerateContentAsync(generateContentRequest);

        string responseText = response.Candidates[0].Content.Parts[0].Text;
        Console.WriteLine(responseText);

        return responseText;
    }
}

// ✅ 添加这部分作为程序入口
public class Program
{
    public static async Task Main(string[] args)
    {
        var sample = new TextInputSample();
        await sample.TextInput();
    }
}