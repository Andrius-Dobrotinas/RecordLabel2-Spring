package com.andrewd.recordlabel.io;

import java.io.IOException;

/**
 * Represents a function that performs an actual input/output
 * operation and therefore may throw an IOException
 */
public interface IOFunction<I, O> {
    O apply (I input) throws IOException;
}