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
    public URI saveFile(byte[] fileBytes) {
        // Create a new FileEntity instance
        FileEntity fileEntity = new FileEntity();
        fileEntity.setData(fileBytes);

        // Save the file entity to the database
        FileEntity savedEntity = fileRepository.save(fileEntity);

        // Generate URI for the saved file
        String fileName = "file-" + savedEntity.getId(); // You can customize the file name as needed
        String fileUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/")
                .path(fileName)
                .toUriString();

        return URI.create(fileUri);
    }
}