package main

import (
	"context"
	"fmt"

	"cloud.google.com/go/auth"
	"cloud.google.com/go/auth/credentials"
	"github.com/kade-chen/library/tools/format"
	"github.com/kade-chen/library/tools/generics"
	"google.golang.org/genai"
)

func main() {
	var creds1 *auth.Credentials
	creds1, _ = credentials.DetectDefault(&credentials.DetectOptions{
		Scopes: []string{"https://www.googleapis.com/auth/cloud-platform"},
		// CredentialsJSON: data,
		CredentialsFile: "/Users/kade.chen/go-kade-project/github/mcenter/etc/kade-poc.json",
	})
	client, err := genai.NewClient(context.Background(), &genai.ClientConfig{
		//目前不支持，另外这个apikey，不能backend，projectid,location,credentials都填，否则会报错
		//https://console.cloud.google.com/apis/credentials?project=kade-poc (Vertex AI API/Generative Language API)
		//https://console.developers.google.com/apis/api/generativelanguage.googleapis.com/overview?project=499036589398
		// APIKey:   "AIzaSyAWtMUuGHBtUCSH829TcHiCO21m_8w08lU",
		Backend: genai.BackendVertexAI,
		Project: "kade-poc",
		Location:    "us-central1",
		Credentials: creds1,

		HTTPOptions: genai.HTTPOptions{APIVersion: "v1"},
	})
	if err != nil {
		panic(err.Error())
	}
	cc, err := client.Models.GenerateContent(context.Background(), "gemini-2.5-flash", []*genai.Content{
		{
			Parts: []*genai.Part{
				{
					Text: "k8s是什么",
				},
			},
			Role: "user",
		},
	}, &genai.GenerateContentConfig{
		Temperature:     generics.Generics[float32](0.9),
		TopK:            generics.Generics[float32](1),
		TopP:            generics.Generics[float32](1),
		MaxOutputTokens: 2048,
		CandidateCount:  1,
	})
	if err != nil {
		panic(err.Error())
	}
	fmt.Println("cc:--------", format.ToJSON(cc))
}
