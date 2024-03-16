package ai

import (
	b "bytes"
	"context"
	"encoding/json"
	"fmt"
	"github.com/pandishpancheta/listing-service/pkg/config"
	"io/ioutil"
	"net/http"
)

func GenerateDescription(ctx context.Context, url string, config *config.Config) string {
	payload := map[string]interface{}{
		"model": "gpt-4-vision-preview",
		"messages": []map[string]interface{}{
			{
				"role": "user",
				"content": []map[string]interface{}{
					{
						"type": "text",
						"text": "What’s in this image?",
					},
					{
						"type": "image_url",
						"image_url": map[string]interface{}{
							"url": url,
						},
					},
				},
			},
		},
		"max_tokens": 300,
	}

	payloadBytes, err := json.Marshal(payload)
	if err != nil {
		fmt.Println("Error encoding payload:", err)
		return ""
	}

	req, err := http.NewRequest("POST", url, b.NewBuffer(payloadBytes))
	if err != nil {
		fmt.Println("Error creating request:", err)
		return ""
	}

	req.Header.Set("Authorization", "Bearer "+config.AUTHORIZATION)
	req.Header.Set("Content-Type", "application/json")
	req.Header.Set("Cookie", config.COOKIE)

	client := &http.Client{}
	resp, err := client.Do(req)
	if err != nil {
		fmt.Println("Error making request:", err)
		return ""
	}
	defer resp.Body.Close()

	body, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		fmt.Println("Error reading response body:", err)
		return ""
	}

	var result map[string]interface{}
	err = json.Unmarshal(body, &result)
	if err != nil {
		fmt.Println("Error decoding response:", err)
		return ""
	}

	choices := result["choices"].([]interface{})
	if len(choices) > 0 {
		if choice, ok := choices[0].(map[string]interface{}); ok {
			if text, exists := choice["text"]; exists {
				if textStr, ok := text.(string); ok {
					return textStr
				}
			}
		}
	}

	return ""
}
