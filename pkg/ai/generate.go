package ai

import (
	"context"
	"encoding/json"
	"fmt"
	"log"

	"github.com/google/generative-ai-go/genai"
)

func GenerateDescription(ctx context.Context, bytes []byte, model genai.GenerativeModel) string {

	prompt := []genai.Part{
		genai.ImageData("png", bytes),
		genai.Text("Describe the image."),
	}
	resp, err := model.GenerateContent(ctx, prompt...)
	if err != nil {
		log.Fatal(err)
	}

	marshalResponse, _ := json.MarshalIndent(resp, "", "  ")
	fmt.Println(marshalResponse)

	return string(marshalResponse)
}
