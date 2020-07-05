package ru.erasko.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.erasko.model.Picture;
import ru.erasko.repo.PictureRepository;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class PictureController {
    private static final Logger logger = LoggerFactory.getLogger(PictureController.class);

    private final PictureRepository pictureRepository;

    public PictureController(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @GetMapping("/picture/{pictureId}")
    public void findByIdPicture(@PathVariable("pictureId") Long pictureId,
                                            HttpServletResponse response) throws IOException {
        logger.info("Picture {}", pictureId);
        Optional<Picture> picture = pictureRepository.findById(pictureId);
        if (picture.isPresent()) {
            response.setContentType(picture.get().getContentType());
            response.getOutputStream().write(picture.get().getPictureData().getData());
        }
    }

//    @GetMapping("/picture/{pictureName}")
//    public void findByNamePicture(@PathVariable("pictureName") String pictureName,
//                                HttpServletResponse response) throws IOException {
//        logger.info("Picture name {}", pictureName);
//        Optional<Picture> picture = pictureRepository.findByName(pictureName);
//        if (picture.isPresent()) {
//            response.setContentType(picture.get().getContentType());
//            response.getOutputStream().write(picture.get().getPictureData().getData());
//        }
//    }
}
