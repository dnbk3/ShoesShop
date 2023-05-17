package vn.btl.hdvcoreservice.service.impl;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.exceptions.*;
import io.imagekit.sdk.models.FileCreateRequest;
import io.imagekit.sdk.models.results.Result;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vn.btl.hdvcoreservice.service.ImageService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ImageServiceImpl implements ImageService {


    @Override
    public String uploadImage(MultipartFile file){
        try {
            byte[] data = file.getBytes();
            FileCreateRequest request = new FileCreateRequest(data, file.getOriginalFilename());
            Result result = ImageKit.getInstance().upload(request);
            log.info("image url response {}", result.getUrl());
            return result.getUrl();
        }
        catch (ForbiddenException e) {
            throw new RuntimeException(e);
        } catch (TooManyRequestsException e) {
            throw new RuntimeException(e);
        } catch (InternalServerException e) {
            throw new RuntimeException(e);
        } catch (UnauthorizedException e) {
            throw new RuntimeException(e);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        } catch (UnknownException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String> uploadImages(List<MultipartFile> files){
        List<String> results = files.stream().map( file -> uploadImage(file)).collect(Collectors.toList());
        return results;
    }
}
