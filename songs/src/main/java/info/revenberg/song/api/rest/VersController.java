package info.revenberg.song.api.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import info.revenberg.domain.Line;
import info.revenberg.domain.Vers;
import info.revenberg.domain.line.FindLinesInImage;
import info.revenberg.exception.DataFormatException;
import info.revenberg.service.LineService;
import info.revenberg.service.VersService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

import java.awt.Transparency;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import info.revenberg.domain.line.ImageDefinition;

@RestController
@RequestMapping(value = "/rest/v1/vers")
@Api(tags = { "vers" })
public class VersController extends AbstractRestHandler {

        @Autowired
        private VersService versService;

        @Autowired
        private LineService lineService;
        
        @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.CREATED)
        @ApiOperation(value = "Create a vers resource.", notes = "Returns the URL of the new resource in the Location header.")
        public @ResponseBody Vers createVers(@RequestBody Vers vers, HttpServletRequest request,
                        HttpServletResponse response) {
                Vers createdVers = this.versService.createVers(vers);
                response.setHeader("Location",
                                request.getRequestURL().append("/").append(createdVers.getId()).toString());
                return createdVers;
        }

        @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a paginated list of all verses.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
        public @ResponseBody Page<Vers> getAllVers(
                        @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                        @ApiParam(value = "The page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                        HttpServletRequest request, HttpServletResponse response) {
                System.out.println("Page<Vers> getAllVers");
                System.out.println(page);
                System.out.println(size);
                Page<Vers> x = this.versService.getAllVerses(page, size);
                System.out.println(x.getTotalElements());
                System.out.println(x.getContent().get(0));

                return this.versService.getAllVerses(page, size);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a single vers.", notes = "You have to provide a valid vers ID.")
        public @ResponseBody Vers getVers(
                        @ApiParam(value = "The ID of the vers.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
                Optional<Vers> vers = this.versService.getVers(id);
                checkResourceFound(vers);
                return vers.get();
        }

        @RequestMapping(value = "/{id}/next", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a next id", notes = "You have to provide a valid vers ID.")
        public @ResponseBody Vers getNextId(
                        @ApiParam(value = "The ID of the vers.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
                Optional<Vers> oVers = this.versService.getVers(this.versService.getNextId(id));                
                checkResourceFound(oVers);                
                if (oVers.isPresent()) {
                        Vers vers =oVers.get();
                        String mediaTempLocation = "/var/songs/temp";
                        FindLinesInImage images = new FindLinesInImage(vers.getLocation(), mediaTempLocation + "/vers", vers.getSong().getBundle().getName(), vers.getSong().getName(), vers.getSong().getId());

                
                        for (Map.Entry<Integer, ImageDefinition> entry : images.getImageDefinitions().entrySet()) {
                                ImageDefinition imageDefinition = entry.getValue();
                
                                Line line = new Line();
                                line.setText(imageDefinition.getFilename());
                                line.setRank(entry.getKey() + 1);
                                line.setLocation(imageDefinition.getFilename());
                                line.setVers(vers);
                                line.setLocation(imageDefinition.getFilename());
                                System.out.println("@@@@@@@@@@@@@@@@@@@@ a @@@@@@@@@@@@@@@@@@@@@");
                                System.out.println(imageDefinition.getFilename());
                                System.out.println(line);
                
                                this.lineService.createLine(line);
                
                                System.out.println(imageDefinition.getTitle());
                                System.out.println("@@@@@@@@@@@@@@@@@@@@ c @@@@@@@@@@@@@@@@@@@@@");
                        }
                        return vers;
                }
                return null;
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Update a vers resource.", notes = "You have to provide a valid vers ID in the URL and in the payload. The ID attribute can not be updated.")
        public void updateVers(
                        @ApiParam(value = "The ID of the existing vers resource.", required = true) @PathVariable("id") Long id,
                        @RequestBody Vers vers, HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.versService.getVers(id));
                if (id != vers.getId())
                        throw new DataFormatException("ID doesn't match!");
                this.versService.updateVers(vers);
        }

        // todo: @ApiImplicitParams, @ApiResponses
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Delete a vers resource.", notes = "You have to provide a valid vers ID in the URL. Once deleted the resource can not be recovered.")
        public void deleteVers(
                        @ApiParam(value = "The ID of the existing vers resource.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.versService.getVers(id));
                this.versService.deleteVers(id);
        }

        @RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get image")
        public @ResponseBody byte[] getImage(
                        @ApiParam(value = "The ID of the existing vers resource.", required = true) @PathVariable("id") int id)
                        throws SQLException, IOException {
                checkResourceFound(this.versService.getVers(id));
                Optional<Vers> vers = this.versService.getVers(id);

                String loc = vers.get().getLocation();
                log.info(loc);
                File file = new File(loc);
                BufferedImage bufferimage = ImageIO.read(file);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(bufferimage, "jpg", output);
                return output.toByteArray();
        }

        /**
         * Takes a BufferedImage and resizes it according to the provided targetSize
         *
         * @param src        the source BufferedImage
         * @param targetSize maximum height (if portrait) or width (if landscape)
         * @return a resized version of the provided BufferedImage
         */
        private static BufferedImage resize(BufferedImage src, int targetSize) {
                if (targetSize <= 0) {
                        return src; // this can't be resized
                }
                int targetWidth = targetSize;
                int targetHeight = targetSize;
                float ratio = ((float) src.getHeight() / (float) src.getWidth());
                if (ratio <= 1) { // square or landscape-oriented image
                        targetHeight = (int) Math.ceil((float) targetWidth * ratio);
                } else { // portrait image
                        targetWidth = Math.round((float) targetHeight / ratio);
                }
                BufferedImage bi = new BufferedImage(targetWidth, targetHeight,
                                src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB
                                                : BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = bi.createGraphics();
                g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); // produces
                                                                                                                     // a
                                                                                                                     // balanced
                                                                                                                     // resizing
                                                                                                                     // (fast
                                                                                                                     // and
                                                                                                                     // decent
                                                                                                                     // quality)
                g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
                g2d.dispose();
                return bi;
        }

        @RequestMapping(value = "/{id}/scalledimage", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get scalled image")
        public @ResponseBody byte[] getScalledImage(
                        @ApiParam(value = "The ID of the existing vers resource.", required = true) @PathVariable("id") int id,
                        @ApiParam(value = "The new  size", required = true) @RequestParam(value = "size", required = true) Integer size)
                        throws SQLException, IOException {
                checkResourceFound(this.versService.getVers(id));
                Optional<Vers> vers = this.versService.getVers(id);

                String loc = vers.get().getLocation();
                log.info(loc);
                File file = new File(loc);
                BufferedImage bufferimage = ImageIO.read(file);
                BufferedImage img = resize(bufferimage, size);

                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(img, "jpg", output);
                return output.toByteArray();
        }

}
