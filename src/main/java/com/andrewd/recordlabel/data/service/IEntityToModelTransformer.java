package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.*;

import java.util.*;
import java.util.function.Function;

public interface IEntityToModelTransformer {
    com.andrewd.recordlabel.supermodel.Release getRelease(Release entity);
    com.andrewd.recordlabel.supermodel.Metadata getMetadata(Metadata entity);
    com.andrewd.recordlabel.supermodel.Reference getReference(Reference entity);
    com.andrewd.recordlabel.supermodel.Track getTrack(Track entity);
    <T, Tm> ArrayList<Tm> transformList(List<T> list, Function<T, Tm> function);
}