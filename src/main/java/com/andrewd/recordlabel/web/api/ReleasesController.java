package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.WebConfig;
import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.service.ReleaseService;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.Calendar;
import java.util.function.Function;

@RestController
@RequestMapping("api/releases/")
public class ReleasesController {

    public final static int DEFAULT_BATCH_NUMBER = 1;

    @Value("${" + WebConfig.ITEMS_PER_PAGE_SETTINGS_KEY + "}")
    public int itemsPerPage;

    @Autowired
    private ReleaseService releaseSvc;

    @Autowired
    private Function<Release, ReleaseViewModel> viewModelBuilder;

    @Autowired
    private Function<BatchedResult<Release>, BatchedResult<ReleaseListItemViewModel>>
            listViewModelTransformer;


    @RequestMapping(value = "post", method = RequestMethod.POST)
    public ResponseEntity post(@RequestBody com.andrewd.recordlabel.supermodel.ReleaseSlim model) {
        return save(model);
    }

    /** This method is a work-around for my Angular JS front-end... because it adds id to the path*/
    @RequestMapping(value = "post/{id}", method = RequestMethod.POST)
    public ResponseEntity post(@PathVariable int id,
                               @RequestBody com.andrewd.recordlabel.supermodel.ReleaseSlim model) {
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
        ReleaseViewModel viewModel = viewModelBuilder.apply(release);
        return ResponseEntity.ok(viewModel);
    }

    @RequestMapping(value = "getForEdit/{id}", method = RequestMethod.GET)
    public ResponseEntity<ReleaseSlim> getForEdit(@PathVariable int id) {
        ReleaseSlim superModel = releaseSvc.getReleaseSlim(id);

        if (superModel == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); //TODO: change status code
        }
        return ResponseEntity.ok(superModel);
    }

    @RequestMapping(value = "getBatch", method = RequestMethod.GET)
    public BatchedResult<ReleaseListItemViewModel> getBatch(@RequestParam(value = "number") int number,
                                                            @RequestParam(value = "size") int size) {
        if (number < 1) number = DEFAULT_BATCH_NUMBER;
        if (size < 1) size = itemsPerPage;

        BatchedResult<Release> releases = releaseSvc.getReleases(number, size);

        return listViewModelTransformer.apply(releases);
    }

    @RequestMapping(value = "getTemplate", method = RequestMethod.GET)
    public ReleaseSlim getTemplate() {
        //TODO test to make sure it returns with current date
        ReleaseSlim template = new ReleaseSlim();
        template.date = (short)Calendar.getInstance().get(Calendar.YEAR);

        return template;
    }
}