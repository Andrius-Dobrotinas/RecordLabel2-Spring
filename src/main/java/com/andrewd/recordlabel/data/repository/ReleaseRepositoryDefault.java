package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.data.model.*;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Repository
public class ReleaseRepositoryDefault implements ReleaseRepository {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Release entity) {
        Artist artist = new Artist();
        artist.name = "Andrew D.";
        entity.artist = artist;
        em.persist(entity.artist);
        em.persist(entity);
        em.flush();
    }

    public Release getRelease(int id) {
        return em.find(Release.class, id);
    }

    public List<MediaType> getMediaTypeList() {

        // This is fake data. TODO: implement properly (hit the database)
        List<MediaType> result = new ArrayList<>();

        MediaType model = new MediaType();
        model.id = 1;
        model.text = "LP";

        MediaType model2 = new MediaType();
        model2.id = 2;
        model2.text = "FLAC";

        result.add(model);
        result.add(model2);

        return result;
    }

    public List<Metadata> getMetadataList() {

        // This is fake data. TODO: implement properly (hit the database)
        List<Metadata> result = new ArrayList<>();
        Metadata model1 = new Metadata();
        model1.id = 1;
        model1.text = "artpop";
        model1.type = MetadataType.Genre;

        Metadata model2 = new Metadata();
        model2.id = 1;
        model2.text = "heavy metal";
        model2.type = MetadataType.Genre;

        result.add(model1);
        result.add(model2);

        return result;
    }
}