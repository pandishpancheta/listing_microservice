package models

import (
	"time"

	"github.com/gofrs/uuid/v5"
)

// Status represents the status of a listing pending/completed/failed
type Status string

const (
	Pending   Status = "pending"
	Completed Status = "completed"
	Failed    Status = "failed"
)

type Tag struct {
	ID   uuid.UUID `json:"id"`
	Name string    `json:"name"`
}

type Listing struct {
	CreatedAt   time.Time `json:"created_at"`
	UpdatedAt   time.Time `json:"updated_at"`
	ID          uuid.UUID `json:"id"`
	Name        string    `json:"name"`
	Description string    `json:"description"`
	URI         string    `json:"uri"`
	Price       float64   `json:"price"`
	Status      Status    `json:"status"`
	Tags        []Tag     `json:"tags"`
}
