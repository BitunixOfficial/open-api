package main

import (
	"log"
)

func main() {
	config, err := LoadConfig()
	if err != nil {
		log.Fatalf("Failed to load config: %v", err)
	}
	log.Printf("config: %+v", config)
}
