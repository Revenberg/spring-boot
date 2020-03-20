package info.revenberg.song.api.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import info.revenberg.domain.Bundle;
import info.revenberg.exception.DataFormatException;
import info.revenberg.service.BundleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/rest/v1/bundle")
@Api(tags = { "bundle" })
public class BundleController extends AbstractRestHandler {

        @Autowired
        private BundleService bundleService;

        @RequestMapping(value = "", method = RequestMethod.POST, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.CREATED)
        @ApiOperation(value = "Create a bundle resource.", notes = "Returns the URL of the new resource in the Location header.")
        public @ResponseBody Bundle createBundle(@RequestBody Bundle bundle, HttpServletRequest request,
                        HttpServletResponse response) {
                Bundle createdBundle = this.bundleService.createBundle(bundle);
                response.setHeader("Location",
                                request.getRequestURL().append("/").append(createdBundle.getId()).toString());
                return createdBundle;
        }

        @RequestMapping(value = "", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a paginated list of all bundles.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
        public @ResponseBody Page<Bundle> getAllBundle(
                        @ApiParam(value = "The page number (zero-based)", required = true) @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                        @ApiParam(value = "The page size", required = true) @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                        HttpServletRequest request, HttpServletResponse response) {
                return this.bundleService.getAllBundles(page, size);
        }

        @RequestMapping(value = "findByName", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Find bundle by name.")
        public @ResponseBody List<Bundle> getAllBundlesByName(
                        @ApiParam(value = "The name of the bundle", required = true) @RequestParam(value = "name", required = true) String name,
                        HttpServletRequest request, HttpServletResponse response) {
                List<Bundle> bundle = this.bundleService.getAllBundlesByName(name);
                checkResourceFound(bundle);
                return bundle;
        }

        @RequestMapping(value = "findByMnemonic", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Find bundle by Mnemonic.")
        public @ResponseBody List<Bundle> getAllBundlesByMnemonic(
                        @ApiParam(value = "The mnemonic of the bundle", required = true) @RequestParam(value = "mnemonic", required = true) String mnemonic,
                        HttpServletRequest request, HttpServletResponse response) {
                // Page<Bundle> x = this.bundleService.findBundleByName(page, size);
                List<Bundle> bundle = this.bundleService.getAllBundlesByMnemonic(mnemonic);
                checkResourceFound(bundle);
                return bundle;
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = { "application/json" })
        @ResponseStatus(HttpStatus.OK)
        @ApiOperation(value = "Get a single bundle.", notes = "You have to provide a valid bundle ID.")
        public @ResponseBody Bundle getBundle(
                        @ApiParam(value = "The ID of the bundle.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) throws Exception {
                Optional<Bundle> bundle = this.bundleService.getBundle(id);
                checkResourceFound(bundle);
                return bundle.get();
        }

        @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
                        "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Update a bundle resource.", notes = "You have to provide a valid bundle ID in the URL and in the payload. The ID attribute can not be updated.")
        public void updateBundle(
                        @ApiParam(value = "The ID of the existing bundle resource.", required = true) @PathVariable("id") Long id,
                        @RequestBody Bundle bundle, HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.bundleService.getBundle(id));
                if (id != bundle.getId())
                        throw new DataFormatException("ID doesn't match!");
                this.bundleService.updateBundle(bundle);
        }

        // todo: @ApiImplicitParams, @ApiResponses
        @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = { "application/json" })
        @ResponseStatus(HttpStatus.NO_CONTENT)
        @ApiOperation(value = "Delete a bundle resource.", notes = "You have to provide a valid bundle ID in the URL. Once deleted the resource can not be recovered.")
        public void deleteBundle(
                        @ApiParam(value = "The ID of the existing bundle resource.", required = true) @PathVariable("id") Long id,
                        HttpServletRequest request, HttpServletResponse response) {
                checkResourceFound(this.bundleService.getBundle(id));
                this.bundleService.deleteBundle(id);
        }
}
