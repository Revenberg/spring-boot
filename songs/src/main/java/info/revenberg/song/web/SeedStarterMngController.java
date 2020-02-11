/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2016, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package info.revenberg.song.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import info.revenberg.song.business.entities.SeedStarter;
import info.revenberg.song.business.services.SeedStarterService;
import info.revenberg.domain.Bundle;
import info.revenberg.domain.Song;
import info.revenberg.domain.Vers;

@Controller
public class SeedStarterMngController {
    
    @Autowired
    private SeedStarterService seedStarterService;

    private long songid;

    public SeedStarterMngController() {
        super();
    }

    @RequestMapping(value = "/songs/{bundleid}", method = RequestMethod.GET)
    public String showSongsList(Model model, @PathVariable("bundleid") long bundleid) {
        model.addAttribute("songs", this.seedStarterService.findAllSongs(bundleid));        
        model.addAttribute("versesvalue", null);
        this.songid = -1;        
        return "seedstartermng :: resultsListSongs";
    }

    @RequestMapping(value = "/verses/{songid}", method = RequestMethod.GET)
    public String showVersesList(Model model, @PathVariable("songid") long songid) {
        System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
        this.songid = songid;
        List<Vers> verses = this.seedStarterService.findAllVerses(songid);
        model.addAttribute("allVerses", verses);
        
        return "seedstartermng :: resultsListVerses";
    }

    @ModelAttribute("allBundles")
    public List<Bundle> allBundles() {
        System.out.println("!!!!!!!!!!!!! allBundles !!!!!!!!!!!!!!!!!");   
        this.songid = -1;     
        return this.seedStarterService.findAllBundle();
    }

    @ModelAttribute("allSongs")
    public List<Song> allSongs() {
        System.out.println("!!!!!!!!!!!!! allSongs !!!!!!!!!!!!!!!!!");        
        return this.seedStarterService.findAllSongs(this.songid);
    }


    @ModelAttribute("allVerses")
    public List<Vers> allVerses(final SeedStarter seedStarter) {
        System.out.println("!!!!!!!!!!!!! allVerses !!!!!!!!!!!!!!!!!");
//        List<Vers> verses = this.seedStarterService.findAllVerses(this.songid);
  //      for (Vers vers : verses) {
    //        seedStarter.getRows().add(new Row(vers));
      //  }
        //return verses;
        return null;
    }
    
    @RequestMapping({ "/", "/seedstartermng" })
    public String showSeedstarters0(final SeedStarter seedStarter, ModelMap model) {
        System.out.println("00000000000000000000000000000000");
        model.addAttribute("versesvalue", null);
        return "seedstartermng";
    }

    @RequestMapping(value = "/seedstartermng", params = { "save" })
    public String saveSeedstarter(final SeedStarter seedStarter, final BindingResult bindingResult,
            final ModelMap model) {
                System.out.println("222222222222222222222222222222222");
        if (bindingResult.hasErrors()) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!!! 0 !!!!!!!!!!!!!!!!!!");
            return "seedstartermng";
        }
        System.out.println("!!!!!!!!!! 1 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(model);
        System.out.println("!!!!!!!!!!!!! 2 !!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println(seedStarter.toString());
        System.out.println("!!!!!!!!!!!!!!!! 3 !!!!!!!!!!!!!!!!!!!!!!!!");
        //this.seedStarterService.add(seedStarter);
        model.clear();
        return "redirect:/seedstartermng";
    }
/*
    @RequestMapping(value = "/seedstartermng", params = { "addRow" })
    public String addRow(final SeedStarter seedStarter, final BindingResult bindingResult) {
        //seedStarter.getRows().add(new Row());
        return "seedstartermng";
    }
    */
/*
    @RequestMapping(value = "/seedstartermng", params = { "removeRow" })
    public String removeRow(final SeedStarter seedStarter, final BindingResult bindingResult,
            final HttpServletRequest req) {
        final Integer rowId = Integer.valueOf(req.getParameter("removeRow"));
        seedStarter.getRows().remove(rowId.intValue());
        return "seedstartermng";
    }
*/
}
