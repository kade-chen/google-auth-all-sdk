const {GoogleGenAI} = require('@google/genai');

const GOOGLE_CLOUD_PROJECT = process.env.GOOGLE_CLOUD_PROJECT;
const GOOGLE_CLOUD_LOCATION = process.env.GOOGLE_CLOUD_LOCATION || 'global';

async function generateContent(
  // projectId = GOOGLE_CLOUD_PROJECT,
  // location = GOOGLE_CLOUD_LOCATION
) {
  const ai = new GoogleGenAI({
    vertexai: true,
    project: "xxxxx",
    location: "xxxxx",
    apiVersion: 'v1',
    googleAuthOptions: {
      scopes: ['https://www.googleapis.com/auth/cloud-platform'],
      //credentials&keyFilename任选其一
      keyFilename: 'xxxxx.json',
      // credentials: {
      //   type: 'xxxxxxxxxxx',
      //   project_id: 'xxxxxxxxxxx',
      //   client_email: 'xxxxxxxxxxx',
      //   private_key_id: 'xxxxxxxxxxx',
      //   private_key: 'xxxxxxxxxxx',
      //   client_id: 'xxxxxxxxxxx',
      //   auth_uri: 'xxxxxxxxxxx',
      //   token_uri: 'xxxxxxxxxxx',
      //   auth_provider_x509_cert_url: 'xxxxxxxxxxx',
      //   client_x509_cert_url: 'xxxxxxxxxxx',
      //   universe_domain: 'xxxxxxxxxxx'
      // }
    }
  });

  const response = await ai.models.generateContent({
    model: 'gemini-2.5-flash',
    contents: 'How does AI work?',
  });

  console.log(response.text);

  return response.text;
}
generateContent().catch(console.error);
//1.npm install @google/genai
