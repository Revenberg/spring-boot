package info.revenberg.song.api.rest;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import info.revenberg.domain.Line;
import info.revenberg.exception.DataFormatException;
import info.revenberg.service.LineService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import java.awt.Transparency;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/rest/v1/line")
@Api(tags = { "line" })
public class LineController extends AbstractRestHandler {

        @Autowired
        private LineService lineService;

        @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.CREATED)
        @ApiOperation(value = "Create a line resource.", notes = "Returns the URL of the new resource in the Location header.")
        public @ResponseBody Line createLine(@RequestBody Line line, HttpServletRequest request,
                        HttpServletResponse response) {
                Line createdLine = this.lineService.createLine(line);
                response.setHeader("Location",
                                request.getRequestURL().append("/").append(createdLine.getId()).toString());
                return createdLine;
        }

        @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a paginated list of all Lines.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
        public @ResponseBody Page<Line> getAllLine(
                        @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                        @ApiParam(value = "The page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                        HttpServletRequest request, HttpServletResponse response) {
                System.out.println("Page<Line> getAllLine");
                System.out.println(page);
                System.out.println(size);
                Page<Line> x = this.lineService.getAllLines(page, size);
                System.out.println(x.getTotalElements());
                System.out.println(x.getContent().get(0));

                return this.lineService.getAllLines(page, size);
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a single line.", notes = "You have to provide a valid line ID.")
        public @ResponseBody Line getLine(
                        @ApiParam(value = "The ID of the line.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
                Optional<Line> line = this.lineService.getLine(id);
                checkResourceFound(line);
                return line.get();
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Update a line resource.", notes = "You have to provide a valid line ID in the URL and in the payload. The ID attribute can not be updated.")
        public void updateLine(
                        @ApiParam(value = "The ID of the existing line resource.", required = true) @PathVariable("id") Long id,
                        @RequestBody Line line, HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.lineService.getLine(id));
                if (id != line.getId())
                        throw new DataFormatException("ID doesn't match!");
                this.lineService.updateLine(line);
        }

        // todo: @ApiImplicitParams, @ApiResponses
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Delete a line resource.", notes = "You have to provide a valid line ID in the URL. Once deleted the resource can not be recovered.")
        public void deleteLine(
                        @ApiParam(value = "The ID of the existing line resource.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.lineService.getLine(id));
                this.lineService.deleteLine(id);
        }

        @RequestMapping(value = "/{id}/image", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get image")
        public @ResponseBody byte[] getImage(
                        @ApiParam(value = "The ID of the existing line resource.", required = true) @PathVariable("id") int id)
                        throws SQLException, IOException {
                checkResourceFound(this.lineService.getLine(id));
                Optional<Line> line = this.lineService.getLine(id);

                String loc = line.get().getLocation();
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
        * @param src the source BufferedImage
        * @param targetSize maximum height (if portrait) or width (if landscape)
        * @return a resized version of the provided BufferedImage
        */
       private static BufferedImage resize(BufferedImage src, int targetSize) {
           if (targetSize <= 0) {
               return src; //this can't be resized
           }
           int targetWidth = targetSize;
           int targetHeight = targetSize;
           float ratio = ((float) src.getHeight() / (float) src.getWidth());
           if (ratio <= 1) { //square or landscape-oriented image
               targetHeight = (int) Math.ceil((float) targetWidth * ratio);
           } else { //portrait image
               targetWidth = Math.round((float) targetHeight / ratio);
           }
           BufferedImage bi = new BufferedImage(targetWidth, targetHeight, src.getTransparency() == Transparency.OPAQUE ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB);
           Graphics2D g2d = bi.createGraphics();
           g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR); //produces a balanced resizing (fast and decent quality)
           g2d.drawImage(src, 0, 0, targetWidth, targetHeight, null);
           g2d.dispose();
           return bi;
       }

        @RequestMapping(value = "/{id}/scalledimage", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get scalled image")
        public @ResponseBody byte[] getScalledImage(
                        @ApiParam(value = "The ID of the existing line resource.", required = true) @PathVariable("id") int id,
                        @ApiParam(value = "The new  size", required = true) @RequestParam(value = "size", required = true) Integer  size)
                        throws SQLException, IOException {
                checkResourceFound(this.lineService.getLine(id));
                Optional<Line> line = this.lineService.getLine(id);

                String loc = line.get().getLocation();
                log.info(loc);
                File file = new File(loc);
                BufferedImage bufferimage = ImageIO.read(file);
                BufferedImage img = resize(bufferimage, size);
                
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                ImageIO.write(img, "jpg", output);
                return output.toByteArray();
        }

}
