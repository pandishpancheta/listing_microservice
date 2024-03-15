package main

import (
	"net"

	"github.com/pandishpancheta/listing-service/pkg/ai"

	_ "github.com/joho/godotenv/autoload"
	"github.com/pandishpancheta/listing-service/pkg/config"
	"github.com/pandishpancheta/listing-service/pkg/db"
	"github.com/pandishpancheta/listing-service/pkg/pb"
	"github.com/pandishpancheta/listing-service/pkg/services"
	"google.golang.org/grpc"
)

func main() {

	cfg := config.LoadConfig()

	pgdb := db.Init(cfg)
	db.InitTables(pgdb)

	model := ai.InitModel(cfg)

	lis, err := net.Listen("tcp", "localhost:"+cfg.TCP_PORT)
	if err != nil {
		panic(err)
	}

	ls := services.NewListingsService(pgdb, cfg, model)

	grpcServer := grpc.NewServer()

	pb.RegisterListingsServiceServer(grpcServer, ls)

	if err := grpcServer.Serve(lis); err != nil {
		panic(err)
	}
}
