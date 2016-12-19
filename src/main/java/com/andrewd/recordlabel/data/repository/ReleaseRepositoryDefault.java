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
    public Release save(Release entity) {
        // TODO: temporary, in development
        entity.references = null;
        entity.tracks = null;
        entity.metadata = null;

        if (entity.id == 0) {
            em.persist(entity);
        }
        else {
            em.merge(entity);
        }
        em.flush();

        return entity;
    }

    public Release getRelease(int id) {
        return em.find(Release.class, id);
    }

    // TODO: add the whole batching mechanism
    public List<Release> getAllReleases() {
        // TODO: replace with real thing
        List<Release> result = new ArrayList<>();
        for(int i = 0; i < 3; i++) {
            result.add(getReleaseStub(i));
        }
        return result;
    }

    public int getTotalReleaseCount() {
        return (int)((Long)em.createQuery("select count(i) from Release i")
                .getSingleResult()).longValue();
    }

    public List<MediaType> getMediaTypeList() {
        return em.createQuery("select i from MediaType i", MediaType.class)
                .getResultList();
    }

    public List<Metadata> getMetadataList() {
        return em.createQuery("select i from Metadata i", Metadata.class)
                .getResultList();
    }

    /* TODO: might want to make Artist properties lazily-loaded and then
    make a separate query that returns a barebones model from this same query
     */
    public List<Artist> getAllArtists() {
        return em.createQuery("select i from Artist i", Artist.class).getResultList();
    }


    private Release getReleaseStub(int id) {
        Release r = new Release();
        r.id = id;
        r.artist = getArtistStub();
        r.catalogueNumber = "CK32000";
        r.date = 1973;
        r.length = 43;
        r.media = getMediaStub();
        r.printStatus = PrintStatus.OutOfPrint;
        //r.text = "One of THE greatest records on earth!";
        r.title = "Raw Power";
        r.tracks = getTracksStub();

        return r;
    }

    private Artist getArtistStub() {
        Artist a = new Artist();
        a.name = "Iggy & The Stooges";
        a.text = "Greatest band on earth!";
        return a;
    }
    private MediaType getMediaStub() {
        MediaType m = new MediaType();
        m.id = 1;
        m.text = "LP";
        return m;
    }

    private java.util.ArrayList<Track> getTracksStub() {
        Track t1 = new Track();
        t1.id = 1;
        t1.title = "Search And Destroy";
        t1.reference = "https://www.youtube.com/v/BJIqnXTqg8I";

        Track t2 = new Track();
        t2.id = 2;
        t2.title = "Gimme Danger";
        t2.reference = "https://www.youtube.com/v/JFAcOnhcpGA";

        java.util.ArrayList<Track> tracks = new java.util.ArrayList<Track>();
        tracks.add(t1);
        tracks.add(t2);

        return tracks;
    }
}