package pic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
public class UploadPostController {

    @RequestMapping(path = "/uploadPicture", method = RequestMethod.POST)
    public String upload(HttpSession session, MultipartFile file) throws IOException {

        File dir = new File("public/files");
        dir.mkdirs();

        File f = File.createTempFile("file", file.getOriginalFilename(), dir);
        FileOutputStream fos = new FileOutputStream(f);
        fos.write(file.getBytes());

        session.setAttribute("picture", file.getOriginalFilename());

        return "redirect:/displayResults";
    }

}
