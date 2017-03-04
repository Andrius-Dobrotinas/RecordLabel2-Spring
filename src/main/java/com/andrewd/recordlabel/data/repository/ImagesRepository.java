package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.*;
import java.util.List;

public interface ImagesRepository {
    List<Image> getImages(int ownerId);
}