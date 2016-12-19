package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.Settings;
import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.service.ReleaseService;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.ReleaseViewModel;
import com.andrewd.recordlabel.web.service.ReleaseViewModelTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;

@RestController
@RequestMapping("api/releases/")
public class ReleasesController {

    @Autowired
    private ReleaseService releaseSvc;

    @Autowired
    private ReleaseViewModelTransformer viewModelTransformer;

    public final static int DEFAULT_BATCH_NUMBER = 1;

    @RequestMapping(value = "post", method = RequestMethod.POST)
    public ResponseEntity post(@RequestBody com.andrewd.recordlabel.supermodel.ReleaseSlim model) {
        return save(model);
    }

    /** This method is a work-around for my Angular JS front-end... because it adds id to the path*/
    @RequestMapping(value = "post/{id}", method = RequestMethod.POST)
    public ResponseEntity post(@PathVariable int id, @RequestBody com.andrewd.recordlabel.supermodel.ReleaseSlim model) {
        return save(model);
    }
    private ResponseEntity save(com.andrewd.recordlabel.supermodel.ReleaseSlim model) {
        // TODO: add model validations
        try {
            releaseSvc.save(model);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "get/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReleaseViewModel> get(@PathVariable int id) {
        Release release = releaseSvc.getRelease(id);

        if (release == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        };
        ReleaseViewModel viewModel = viewModelTransformer.transform(release);
        return ResponseEntity.ok(viewModel);
    }

    @RequestMapping(value = "getForEdit/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReleaseSlim> getForEdit(@PathVariable int id) {
        ReleaseSlim superModel = releaseSvc.getReleaseSlim(id);

        if (superModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(superModel);
    }

    @RequestMapping(value = "getBatch", method = RequestMethod.GET)
    public BatchedResult<Release> getBatch(@RequestParam(value = "number") int number, @RequestParam(value = "size") int size) {
        if (number < 1) number = DEFAULT_BATCH_NUMBER;
        if (size < 1) size = Settings.getItemsPerPage();

        return releaseSvc.getReleases(number, size);
    }

    @RequestMapping(value = "getTemplate", method = RequestMethod.GET)
    public ReleaseSlim getTemplate() {
        //TODO test to make sure it returns with current date
        ReleaseSlim template = new ReleaseSlim();
        template.date = (short)Calendar.getInstance().get(Calendar.YEAR);

        return template;
    }
}