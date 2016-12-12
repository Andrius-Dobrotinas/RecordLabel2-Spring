package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.data.model.Release;
import com.andrewd.recordlabel.data.repository.ReleaseRepository;
import com.andrewd.recordlabel.data.service.ReleaseService;
import org.junit.Test;
import org.mockito.*;

public class ReleaseRepositoryTests {

    @InjectMocks
    ReleaseService svc = new ReleaseService();

    @Mock
    ReleaseRepository repository;

    @Test
    public void test() {

        Release release = new Release();
        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(release);

    }
}
