package services

import (
	"context"
	"database/sql"
	"log"

	"github.com/google/generative-ai-go/genai"

	"github.com/gofrs/uuid/v5"
	"github.com/pandishpancheta/listing-service/pkg/config"
	"github.com/pandishpancheta/listing-service/pkg/pb"
	"github.com/pandishpancheta/listing-service/pkg/prototokenization"
	tokenization "github.com/pandishpancheta/listing-service/pkg/prototokenization/pb"
	"google.golang.org/protobuf/types/known/emptypb"
)

type Services interface {
	ListingsService
}

type services struct {
	ListingsService
}

type ListingsService interface {
	CreateListing(ctx context.Context, req *pb.CreateListingRequest) (*pb.CreateListingResponse, error)
	GetListing(ctx context.Context, req *pb.GetListingRequest) (*pb.GetListingResponse, error)
	GetListings(ctx context.Context, req *emptypb.Empty) (*pb.GetListingsResponse, error)
	GetListingsByUser(ctx context.Context, req *pb.GetListingsByUserRequest) (*pb.GetListingsResponse, error)
	UpdateListing(ctx context.Context, req *pb.UpdateListingRequest) (*pb.UpdateListingResponse, error)
	UpdateListingStatus(ctx context.Context, req *pb.UpdateListingStatusRequest) (*pb.UpdateListingStatusResponse, error)
	DeleteListing(ctx context.Context, req *pb.DeleteListingRequest) (*emptypb.Empty, error)
}

type listingsService struct {
	db    *sql.DB
	cfg   *config.Config
	model *genai.GenerativeModel
}

func NewListingsService(db *sql.DB, cfg *config.Config, model *genai.GenerativeModel) Services {
	return &services{
		ListingsService: &listingsService{
			db:    db,
			cfg:   cfg,
			model: model,
		},
	}
}

func (s *listingsService) CreateListing(ctx context.Context, req *pb.CreateListingRequest) (*pb.CreateListingResponse, error) {
	// Generate UUID
	id, err := uuid.NewV4()
	if err != nil {
		return nil, err
	}

	c, err := prototokenization.InitServiceClient(s.cfg)
	if err != nil {
		return nil, err
	}

	// Tokenize chunk
	tokenizeRequest := tokenization.TokenizationRequest{
		TokenId: id.String(),
		Chunk:   req.Chunk,
	}

	tokenizeResponse, err := c.Tokenize(ctx, &tokenizeRequest)
	if err != nil {
		return nil, err
	}

	uri := tokenizeResponse.GetTokenURI()

	// Iterate over tags and insert into database if they don't exist
	for _, tag := range req.TagNames {
		tid, err := uuid.NewV4()
		if err != nil {
			return nil, err
		}

		_, err = s.db.ExecContext(ctx, "INSERT INTO tags (id, name) VALUES ($1, $2) ON CONFLICT DO NOTHING", tid, tag)
		if err != nil {
			return nil, err
		}

		_, err = s.db.ExecContext(ctx, "INSERT INTO listing_tags (listing_id, tag_id) VALUES ($1, $2)", id, tid)
		if err != nil {
			return nil, err
		}
	}

	// desc := ai.GenerateDescription(ctx, req.Chunk, *s.model)

	// Insert into database
	_, err = s.db.ExecContext(ctx, "INSERT INTO listings (id, name, description, uri, price, status, user_id) VALUES ($1, $2, $3, $4, $5, $6, $7)", id, req.Name, req.Description, uri, req.Price, "pending", req.UserId)
	if err != nil {
		return nil, err
	}

	return &pb.CreateListingResponse{Id: id.String(), Uri: uri}, nil
}

func (s *listingsService) GetListing(ctx context.Context, req *pb.GetListingRequest) (*pb.GetListingResponse, error) {
	var listing pb.GetListingResponse
	err := s.db.QueryRowContext(ctx, "SELECT l.id, l.name, l.description, l.price, l.uri, u.id, u.username FROM listings l JOIN users u ON l.user_id = u.id WHERE l.id = $1 AND l.status = $2", req.Id, "completed").Scan(&listing.Id, &listing.Name, &listing.Description, &listing.Price, &listing.Uri, &listing.UserId, &listing.Username)
	if err != nil {
		return nil, err
	}

	// Get tags
	rows, err := s.db.QueryContext(ctx, "SELECT t.name FROM tags t JOIN listing_tags lt ON t.id = lt.tag_id WHERE lt.listing_id = $1", req.Id)
	if err != nil {
		return nil, err
	}

	for rows.Next() {
		var tag string
		err = rows.Scan(&tag)
		if err != nil {
			return nil, err
		}
		listing.TagNames = append(listing.TagNames, tag)
	}

	return &listing, nil
}

func (s *listingsService) GetListings(ctx context.Context, req *emptypb.Empty) (*pb.GetListingsResponse, error) {
	rows, err := s.db.QueryContext(ctx, "SELECT l.id, l.name, l.description, l.price, l.uri, u.id, u.username FROM listings l JOIN users u ON l.user_id = u.id WHERE status = $1", "completed")
	if err != nil {
		if err == sql.ErrNoRows {
			return nil, nil
		}

		return nil, err
	}
	defer rows.Close()

	var listings pb.GetListingsResponse
	for rows.Next() {
		var listing pb.GetListingResponse
		err = rows.Scan(&listing.Id, &listing.Name, &listing.Description, &listing.Price, &listing.Uri, &listing.UserId, &listing.Username)
		if err != nil {
			log.Println(err)
			return nil, err
		}

		// Get tags
		tagRows, err := s.db.QueryContext(ctx, "SELECT t.name FROM tags t JOIN listing_tags lt ON t.id = lt.tag_id WHERE lt.listing_id = $1", listing.Id)
		if err != nil {
			return nil, err
		}

		for tagRows.Next() {
			var tag string
			err = tagRows.Scan(&tag)
			if err != nil {
				return nil, err
			}
			listing.TagNames = append(listing.TagNames, tag)
		}

		listings.Listings = append(listings.Listings, &listing)
	}

	return &listings, nil
}

func (s *listingsService) GetListingsByUser(ctx context.Context, req *pb.GetListingsByUserRequest) (*pb.GetListingsResponse, error) {
	rows, err := s.db.QueryContext(ctx, "SELECT l.id, l.name, l.description, l.price, l.uri, u.id, u.username FROM listings l JOIN users u ON l.user_id = u.id WHERE l.user_id = $1", req.UserId)
	if err != nil {
		if err == sql.ErrNoRows {
			return nil, nil
		}

		return nil, err
	}
	defer rows.Close()

	var listings pb.GetListingsResponse
	for rows.Next() {
		var listing pb.GetListingResponse
		err = rows.Scan(&listing.Id, &listing.Name, &listing.Description, &listing.Price, &listing.Uri, &listing.UserId, &listing.Username)
		if err != nil {
			log.Println(err)
			return nil, err
		}

		// Get tags
		tagRows, err := s.db.QueryContext(ctx, "SELECT t.name FROM tags t JOIN listing_tags lt ON t.id = lt.tag_id WHERE lt.listing_id = $1", listing.Id)
		if err != nil {
			return nil, err
		}

		for tagRows.Next() {
			var tag string
			err = tagRows.Scan(&tag)
			if err != nil {
				return nil, err
			}
			listing.TagNames = append(listing.TagNames, tag)
		}

		listings.Listings = append(listings.Listings, &listing)
	}

	return &listings, nil
}

func (s *listingsService) UpdateListing(ctx context.Context, req *pb.UpdateListingRequest) (*pb.UpdateListingResponse, error) {
	_, err := s.db.ExecContext(ctx, "UPDATE listings SET name = $1, description = $2, price = $3 WHERE id = $4 AND user_id = $5", req.Name, req.Description, req.Price, req.Id, req.UserId)
	if err != nil {
		return nil, err
	}

	return &pb.UpdateListingResponse{}, nil
}

func (s *listingsService) UpdateListingStatus(ctx context.Context, req *pb.UpdateListingStatusRequest) (*pb.UpdateListingStatusResponse, error) {
	_, err := s.db.ExecContext(ctx, "UPDATE listings SET status = $1 WHERE id = $2", req.Status, req.Id)
	if err != nil {
		return nil, err
	}

	return &pb.UpdateListingStatusResponse{}, nil
}

func (s *listingsService) DeleteListing(ctx context.Context, req *pb.DeleteListingRequest) (*emptypb.Empty, error) {
	_, err := s.db.ExecContext(ctx, "DELETE FROM listings WHERE id = $1", req.Id)
	if err != nil {
		return nil, err
	}

	return &emptypb.Empty{}, nil
}
