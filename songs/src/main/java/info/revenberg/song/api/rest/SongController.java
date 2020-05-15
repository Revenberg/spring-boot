package info.revenberg.song.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import info.revenberg.domain.Bundle;
import info.revenberg.domain.Song;
import info.revenberg.exception.DataFormatException;
import info.revenberg.service.BundleService;
import info.revenberg.service.SongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/rest/v1/song")
@Api(tags = { "song" })
public class SongController extends AbstractRestHandler {

        @Autowired
        private SongService songService;

        @Autowired
        private BundleService bundleService;

        @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.CREATED)
        @ApiOperation(value = "Create a song resource.", notes = "Returns the URL of the new resource in the Location header.")
        public @ResponseBody Song createSong(@RequestBody Song song, HttpServletRequest request,
                        HttpServletResponse response) {
                Song createdSong = this.songService.createSong(song);
                response.setHeader("Location",
                                request.getRequestURL().append("/").append(createdSong.getId()).toString());
                return createdSong;
        }

        @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a paginated list of all songs.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
        public @ResponseBody Page<Song> getAllSong(
                        @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                        @ApiParam(value = "The page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                        HttpServletRequest request, HttpServletResponse response) {
                return this.songService.getAllSongs(page, size);
        }

        @RequestMapping(value = "/{id}/all", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get all song by in bundle")
        public @ResponseBody Page<Song> getAllSongesByName(
                        @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                        @ApiParam(value = "The page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                        @ApiParam(value = "The ID of the existing bundle resource.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {

                Page<Song> songs = this.songService.getAllSongsOfBundle(page, size, id);
                return songs;
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Update a song resource.", notes = "You have to provide a valid song ID in the URL and in the payload. The ID attribute can not be updated.")
        public void updateSong(
                        @ApiParam(value = "The ID of the existing song resource.", required = true) @PathVariable("id") Long id,
                        @RequestBody Song song, HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.songService.getSong(id));
                if (id != song.getId())
                        throw new DataFormatException("ID doesn't match!");
                this.songService.updateSong(song);
        }

        // todo: @ApiImplicitParams, @ApiResponses
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Delete a song resource.", notes = "You have to provide a valid song ID in the URL. Once deleted the resource can not be recovered.")
        public void deleteSong(
                        @ApiParam(value = "The ID of the existing song resource.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.songService.getSong(id));
                this.songService.deleteSong(id);
        }

        @RequestMapping(value = "/{id}/findByName", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a paginated list of all songs.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
        public @ResponseBody Song findSongsByName(@RequestParam(value = "name", required = true) String name,
                        @ApiParam(value = "The ID of the existing bundle resource.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) {
                Optional<Bundle> bundle = this.bundleService.getBundle(id);
                checkResourceFound(bundle);
                return this.songService.findSongByNameInBundle(name, bundle.get().getId());
        }

}
