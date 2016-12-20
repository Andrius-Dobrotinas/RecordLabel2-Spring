package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.data.model.*;
import org.junit.Assert;

/**
 * EntityModelTransformationVerifiers for EntityToModel and ModelToEntity Transformers
 */
public class EntityModelTransformationVerifiers {

    public static void verifyRelease(Release entity, com.andrewd.recordlabel.supermodel.Release model) {
        Assert.assertEquals("Release ids must match", entity.id, model.id);
        Assert.assertEquals("Release dates must match", entity.date, model.date);
        Assert.assertEquals("Release catalog numbers must match", entity.catalogueNumber, model.catalogueNumber);
        Assert.assertEquals("Release lengths must match", entity.length, model.length);
        Assert.assertEquals("Release print statuses must match", entity.printStatus, model.printStatus);
        Assert.assertEquals("Release titles must match", entity.title, model.title);
    }

    public static void verifyReleaseSlim(Release entity, com.andrewd.recordlabel.supermodel.ReleaseSlim model) {
        Assert.assertEquals("Release ids must match", entity.id, model.id);
        Assert.assertEquals("Release dates must match", entity.date, model.date);
        Assert.assertEquals("Release catalog numbers must match", entity.catalogueNumber, model.catalogueNumber);
        Assert.assertEquals("Release lengths must match", entity.length, model.length);
        Assert.assertEquals("Release print statuses must match", entity.printStatus, model.printStatus);
        Assert.assertEquals("Release titles must match", entity.title, model.title);
    }

    public static void verifyArtist(Artist entity, com.andrewd.recordlabel.supermodel.Artist superModel) {
        Assert.assertEquals("Artist ids must match", entity.id, superModel.id);
        Assert.assertEquals("Artist names must match", entity.name, superModel.name);
        Assert.assertEquals("Artist texts must match", entity.text, superModel.text);
    }

    public static void verifyReference(Reference entity, com.andrewd.recordlabel.supermodel.Reference superModel) {
        Assert.assertEquals("Reference ids must match", entity.id, superModel.id);
        Assert.assertEquals("Reference targets must match", entity.target, superModel.target);
        Assert.assertEquals("Reference types must match", entity.type, superModel.type);
        Assert.assertEquals("Reference orders must match", entity.order, superModel.order);
    }

    public static void verifyMetadata(Metadata entity, com.andrewd.recordlabel.supermodel.Metadata superModel) {
        Assert.assertEquals("Metadata ids must match", entity.id, superModel.id);
        Assert.assertEquals("Metadata texts must match", entity.text, superModel.text);
        Assert.assertEquals("Metadata types must match", entity.type, superModel.type);
    }

    public static void verifyTrack(Track entity, com.andrewd.recordlabel.supermodel.Track superModel) {
        Assert.assertEquals("Track ids must match", entity.id, superModel.id);
        Assert.assertEquals("Track references must match", entity.reference, superModel.reference);
        Assert.assertEquals("Track titles must match", entity.title, superModel.title);
    }

    public static void verifyMediaType(MediaType entity, com.andrewd.recordlabel.supermodel.MediaType superModel) {
        Assert.assertEquals("Media type ids must match", entity.id, superModel.id);
        Assert.assertEquals("Media type texts must match", entity.text, superModel.text);
    }
}