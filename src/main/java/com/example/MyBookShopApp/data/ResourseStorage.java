package com.example.MyBookShopApp.data;

import com.example.MyBookShopApp.entity.BookFile;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
@Service
public class ResourseStorage {

   private final BookFileRepository bookFileRepository;
    @Autowired
    public ResourseStorage(BookFileRepository bookFileRepository) {
        this.bookFileRepository = bookFileRepository;
    }



    @Value("${upload.path}")
    String uploadPath;
    @Value("${download.path}")
    String downloadPath;
    public String saveNewBookImge(MultipartFile file, String slug) throws IOException {
        String resourceUrl=null;

        if (!file.isEmpty()){
            if (!new File(uploadPath).exists()){
                Files.createDirectories(Paths.get(uploadPath ));
                Logger.getLogger(this.getClass().getSimpleName()).info("created image folder in: " + uploadPath);
            }
            String fileName = slug + "." + FilenameUtils.getExtension(file.getOriginalFilename());

            Path path = Paths.get(uploadPath, fileName);
            resourceUrl = "/book-covers/" + fileName;
            file.transferTo(path);
            Logger.getLogger(this.getClass().getSimpleName()).info(fileName + " uploader OK!");
        }
        return resourceUrl;

    }

    public Path getBookFilePath(String hash){
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        return Paths.get(bookFile.getPath());
    }



    public MediaType getBookFileNAme(String hash){
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        String mimeType =
                URLConnection.guessContentTypeFromName(Paths.get(bookFile.getPath()).getFileName().toString());
        if (mimeType !=null){
            return MediaType.parseMediaType(mimeType);
        }else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
    @SneakyThrows
    public byte[] getBookFileByteArray(String hash){
        BookFile bookFile = bookFileRepository.findBookFileByHash(hash);
        Path path = Paths.get(downloadPath,bookFile.getPath());
        return Files.readAllBytes(path);
    }
}
