package com.github.raily01.shoprestservice.util;

import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;

@Getter
public class ByteCountingInputStream extends InputStream {

    private final InputStream parent;
    private long currentPos = 0;

    public ByteCountingInputStream(InputStream parent) {
        this.parent = parent;
    }

    @Override
    public int read() throws IOException {
        int read = parent.read();
        currentPos++;
        return read;
    }
}
