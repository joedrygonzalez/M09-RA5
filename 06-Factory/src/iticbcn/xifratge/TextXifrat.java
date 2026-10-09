package iticbcn.xifratge;

import java.nio.charset.StandardCharsets;

public class TextXifrat {
    private byte[] bytes;

    public TextXifrat(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public String toString() {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    public byte[] getBytes() {
        return bytes;
    }
}
