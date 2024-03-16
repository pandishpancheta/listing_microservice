package config

import (
	"os"
	"strings"
)

type Config struct {
	TCP_PORT string

	DB_HOST string
	DB_PORT string
	DB_USER string
	DB_PASS string
	DB_NAME string

	TokenizationServiceAddress string

	ApiKey string

	AUTHORIZATION string
	COOKIE        string
}

func LoadConfig() *Config {
	return &Config{
		TCP_PORT:                   strings.TrimSuffix(os.Getenv("TCP_PORT"), "\n"),
		DB_HOST:                    strings.TrimSuffix(os.Getenv("DB_HOST"), "\n"),
		DB_PORT:                    strings.TrimSuffix(os.Getenv("DB_PORT"), "\n"),
		DB_USER:                    strings.TrimSuffix(os.Getenv("DB_USER"), "\n"),
		DB_PASS:                    strings.TrimSuffix(os.Getenv("DB_PASS"), "\n"),
		DB_NAME:                    strings.TrimSuffix(os.Getenv("DB_NAME"), "\n"),
		TokenizationServiceAddress: strings.TrimSuffix(os.Getenv("TOKENIZATION_SERVICE_ADDRESS"), "\n"),
		ApiKey:                     strings.TrimSuffix(os.Getenv("API_KEY"), "\n"),
		AUTHORIZATION:              strings.TrimSuffix(os.Getenv("AUTHORIZATION"), "\n"),
		COOKIE:                     strings.TrimSuffix(os.Getenv("COOKIE"), "\n"),
	}
}
