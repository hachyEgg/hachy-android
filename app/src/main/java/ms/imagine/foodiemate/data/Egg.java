package ms.imagine.foodiemate.data;

import org.jetbrains.annotations.NotNull;


public class Egg {
    private long timestamp;
    private int status;
    @NotNull
    private String remoteImgURL;

    public final long getTimestamp() {
        return this.timestamp;
    }

    public final void setTimestamp(long var1) {
        this.timestamp = var1;
    }

    public final int getStatus() {
        return this.status;
    }

    public final void setStatus(int var1) {
        this.status = var1;
    }

    @NotNull
    public final String getRemoteImgURL() {
        return this.remoteImgURL;
    }

    public final void setRemoteImgURL( String var1) {
        this.remoteImgURL = var1;
    }

    public Egg( String toString, int toInt, long toLong) {
        this.timestamp = 100L;
        this.remoteImgURL = toString;
        this.status = toInt;
        this.timestamp = toLong;
    }
}
