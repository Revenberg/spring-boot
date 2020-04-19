package info.revenberg.song.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import info.revenberg.dao.jpa.SongRepository;
import info.revenberg.domain.Bundle;
import info.revenberg.domain.Song;
import info.revenberg.service.BundleService;
import info.revenberg.service.SongService;
import io.swagger.annotations.ApiParam;
import java.util.Optional;

@Controller
@RequestMapping(value = "song")
public class SongWebController {
 
    @Autowired
    private SongRepository songRepository;
    
    @Autowired
    private SongService songService;

    @Autowired
    private BundleService bundleService;

    @GetMapping("")
    public String getSongs(Model model) {
        model.addAttribute("songs", songRepository.findAll());
        return "song-list";
    }
    
    @GetMapping("{bundleid}")
    public String getSongsOfBundle(
        @ApiParam(value = "The BundleId of the bundle.", required = true) @PathVariable("bundleid") Long bundleid,
        Model model) {
        Optional<Bundle> bundle = bundleService.getBundle(bundleid);
        if (bundle != null) {
                model.addAttribute("bundle", bundle.get());
        }
        model.addAttribute("songs", songRepository.findAllByBundleid(bundleid));
        return "song-list";
    }

    @GetMapping("/edit/{id}")
    public String showSignUpForm(
        @ApiParam(value = "The ID of the song.", required = true) @PathVariable("id") Long id,
        Model model            
        ) {
            Optional<Song> song = this.songService.getSong(id);
            model.addAttribute("song", song);
        return "song-edit";
    }
     
    @PostMapping("/add")
    public String addSong(@Valid Song song, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "song-add";
        }         
        
        songRepository.save(song);        
        model.addAttribute("songs", songRepository.findAll());
        return "index";
    }
 }