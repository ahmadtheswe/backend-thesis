package com.ahmadthesis.image.adapter.input.rest.common.parser;

import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.multipart.Part;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

public class FilePartParser {
    public static String parsePartToString(Part filePartMono) {
        return filePartMono.content().map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return new String(bytes, StandardCharsets.UTF_8);
                })
                .flatMap(Mono::just).blockFirst();
    }
}