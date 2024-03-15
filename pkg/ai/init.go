package ai

import (
	"log"

	"github.com/google/generative-ai-go/genai"
	"github.com/pandishpancheta/listing-service/pkg/config"
	"golang.org/x/net/context"
	"google.golang.org/api/option"
)

func InitModel(cfg *config.Config) *genai.GenerativeModel {

	ctx := context.Background()
	// Access your API key as an environment variable (see "Set up your API key" above)
	client, err := genai.NewClient(ctx, option.WithAPIKey(cfg.ApiKey))
	if err != nil {
		log.Fatal(err)
	}
	defer client.Close()

	model := client.GenerativeModel("gemini-1.0-pro")
	return model
}
