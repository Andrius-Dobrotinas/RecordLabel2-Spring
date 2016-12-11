package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.repository.ReleaseRepository;
import com.andrewd.recordlabel.data.model.Release;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReleaseService {

    @Autowired
    private ReleaseRepository repositry;

    public void save(Release entity) {
        repositry.save(entity);
    }

    public Release getRelease(int id) {
        return repositry.getRelease(id);
    }
}