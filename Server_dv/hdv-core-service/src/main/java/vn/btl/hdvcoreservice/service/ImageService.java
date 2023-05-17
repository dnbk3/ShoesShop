package vn.btl.hdvcoreservice.service;

import io.imagekit.sdk.exceptions.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {

    String uploadImage(MultipartFile file) throws IOException, ForbiddenException, TooManyRequestsException, InternalServerException, UnauthorizedException, BadRequestException, UnknownException;

    List<String> uploadImages(List<MultipartFile> file) ;

}
