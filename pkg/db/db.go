package db

import (
	"database/sql"
	"fmt"
	"strconv"

	_ "github.com/lib/pq"
	"github.com/pandishpancheta/listing-service/pkg/config"
)

func Init(cfg *config.Config) *sql.DB {
	port, err := strconv.Atoi(cfg.DB_PORT)
	if err != nil {
		panic(err)
	}

	psqlInfo := fmt.Sprintf("host=%s port=%d user=%s "+
		"password=%s dbname=%s sslmode=disable",
		cfg.DB_HOST, port, cfg.DB_USER, cfg.DB_PASS, cfg.DB_NAME)

	db, err := sql.Open("postgres", psqlInfo)
	if err != nil {
		panic(err)
	}

	err = db.Ping()
	if err != nil {
		panic(err)
	}

	fmt.Println("Successfully connected!")
	return db
}

func InitTables(db *sql.DB) {
	_, err := db.Exec(`CREATE TABLE IF NOT EXISTS listings (
		created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
		id UUID PRIMARY KEY,
		name TEXT,
		description TEXT,
		price INT,
		status TEXT
	)`)
	if err != nil {
		panic(err)
	}

	_, err = db.Exec(`CREATE TABLE IF NOT EXISTS tags (
		id UUID PRIMARY KEY,
		name TEXT UNIQUE NOT NULL
	)`)
	if err != nil {
		panic(err)
	}

	_, err = db.Exec(`CREATE TABLE IF NOT EXISTS listing_tags (
		listing_id UUID,
		tag_id UUID,
		PRIMARY KEY (listing_id, tag_id),
		FOREIGN KEY (listing_id) REFERENCES listings(id) ON DELETE CASCADE,
		FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE
	)`)
	if err != nil {
		panic(err)
	}
}
