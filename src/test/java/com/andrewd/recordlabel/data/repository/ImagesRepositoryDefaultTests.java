package com.andrewd.recordlabel.data.repository;

import com.andrewd.recordlabel.data.entities.ContentBase;
import com.andrewd.recordlabel.data.entities.Image;
import com.andrewd.recordlabel.data.entities.Release;
import com.andrewd.recordlabel.data.entity.EntitySaver;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import javax.persistence.*;
import java.util.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ImagesRepositoryDefaultTests {

    @InjectMocks
    ImagesRepositoryDefault repository;

    @Mock
    EntityManager em;

    @Mock
    EntitySaver saver;

    private final int ownerId = 1;
    private final Release owner = new Release();

    private Image image;
    private List<Image> images;

    @Before
    public void init() {
        image = new Image();
        images = new ArrayList<>();
        images.add(image);

        Mockito.when(em.find(Matchers.eq(ContentBase.class), Matchers.eq(ownerId)))
                .thenReturn(owner);
    }

    @Test
    public void saveImages_mustRetrieveOwnerEntityUsingItsId() {
        repository.saveImages(ownerId, images);

        Mockito.verify(em, times(1))
                .find(Matchers.eq(ContentBase.class), Matchers.eq(ownerId));
    }

    @Test(expected = EntityNotFoundException.class)
    public void saveImages_ifOwnerIsNullMustThrowException() {
        Mockito.when(em.find(Matchers.eq(ContentBase.class), Matchers.eq(ownerId)))
                .thenReturn(null);

        repository.saveImages(ownerId, images);
    }

    @Test
    public void saveImages_mustSetOwnerForEachImage() {
        Image image2 = new Image();
        images.add(image2);

        repository.saveImages(ownerId, images);

        Assert.assertEquals("Each image's owner property must be equal to Owner",
                owner, image.owner);
        Assert.assertEquals("Each image's owner property must be equal to Owner",
                owner, image2.owner);
    }

    @Test
    public void saveImages_mustSaveImagesUsingEntitySaver() {
        repository.saveImages(ownerId, images);

        Mockito.verify(saver, times(1))
                .save(Matchers.eq(em), Matchers.eq(images));
    }

    @Test
    public void saveImages_mustFlushChangesToTheEntityManager() {
        repository.saveImages(ownerId, images);

        Mockito.verify(em, times(1)).flush();
    }

    @Test
    public void saveImages_mustReturnTheSameImages() {
        List<Image> result = repository.saveImages(ownerId, images);

        Assert.assertArrayEquals(images.toArray(), result.toArray());
    }
}