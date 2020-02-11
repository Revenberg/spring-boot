package info.revenberg.song.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UploadController {

    @GetMapping("/Upload")
    public String mainWithParam(Model model) {

        return "Upload"; //view
    }

}
