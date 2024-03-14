package com.ht.listing_microservice.service;

import com.ht.listing_microservice.entity.FileEntity;
import com.ht.listing_microservice.repository.FileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Service
public class FileStorageService {

    private final FileRepository fileRepository;

    public FileStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Transactional
    public String saveFile(UUID tokenId, byte[] fileBytes) {
        TokenizationRequest tokenizationRequest = TokenizationRequestOrBuilder.newBuilder()
                .setTokenId(tokenId.toString())
                .setFileBytes(ByteString.copyFrom(fileBytes))
                .build();

        TokenizationResponse tokenizationResponse = tokenizationServiceBlockingStub.tokenize(tokenizationRequest);
        
        return tokenizationResponse.getTokenURI();
    }
}