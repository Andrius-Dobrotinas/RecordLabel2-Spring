package com.andrewd.recordlabel.data.services;

import com.andrewd.recordlabel.supermodels.Image;
import java.util.List;

public interface ImagesService {

    List<Image> getImages(int ownerId);
}