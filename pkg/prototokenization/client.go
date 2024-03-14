package prototokenization

import (
	"log"

	"github.com/pandishpancheta/listing-service/pkg/config"
	tokenization "github.com/pandishpancheta/listing-service/pkg/prototokenization/pb"
	"google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

type ServiceClient struct {
	Client tokenization.TokenizationServiceClient
}

func InitServiceClient(cfg *config.Config) (tokenization.TokenizationServiceClient, error) {
	creds := insecure.NewCredentials()

	c, err := grpc.Dial(cfg.TokenizationServiceAddress, grpc.WithTransportCredentials(creds))
	if err != nil {
		log.Fatalf("Failed to dial TokenizationService: %v", err)
		return nil, err
	}

	return tokenization.NewTokenizationServiceClient(c), nil
}
