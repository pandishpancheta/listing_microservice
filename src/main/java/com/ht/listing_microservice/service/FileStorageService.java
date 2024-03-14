package com.ht.listing_microservice.service;

import com.google.protobuf.ByteString;
import com.ht.listing_microservice.repository.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileStorageService {

    private final FileRepository fileRepository;

    public FileStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public String saveFile(UUID tokenId, byte[] fileBytes) {
//        TokenizationServiceProto.TokenizationRequest tokenizationRequest = TokenizationServiceProto.TokenizationRequest.newBuilder()
//                .setTokenId(String.valueOf(tokenId))
//                .setChunk(ByteString.copyFrom(fileBytes))
//                .build();
//        TokenizationServiceProto.TokenizationResponse tokenizationResponse = TokenizationServic.getDescriptor().

//        return tokenizationResponse.getTokenURI();
        return "";
    }
}