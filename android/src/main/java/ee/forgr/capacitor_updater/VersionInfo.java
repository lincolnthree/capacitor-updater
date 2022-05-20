package ee.forgr.capacitor_updater;

import com.getcapacitor.JSObject;

import java.util.Objects;

public class VersionInfo {
    public static final String VERSION_BUILTIN = "builtin";
    public static final String DOWNLOADED_BUILTIN = "1970-01-01T00:00:00.000Z";

    private final String downloaded;
    private final String name;
    private final String version;
    private final VersionStatus status;

    public VersionInfo(final String version, final VersionStatus status, final String downloaded, final String name) {
        this.downloaded = downloaded;
        this.name = name;
        this.version = version;
        this.status = status;
    }

    public Boolean isBuiltin() {
        return VERSION_BUILTIN.equals(this.getVersion());
    }

    public Boolean isErrorStatus() {
        return VersionStatus.ERROR == this.status;
    }

    public boolean isDownloaded() {
        return !this.isBuiltin() && this.downloaded != null && this.downloaded.trim().length() == DOWNLOADED_BUILTIN.length();
    }

    public String getDownloaded() {
        return this.isBuiltin() ? DOWNLOADED_BUILTIN : this.downloaded;
    }

    public String getName() {
        return this.isBuiltin() ? VERSION_BUILTIN : this.name;
    }

    public String getVersion() {
        return this.version == null ? VERSION_BUILTIN : this.version;
    }

    public VersionStatus getStatus() {
        return this.isBuiltin() ? VersionStatus.SUCCESS : this.status;
    }

    public JSObject fromJSONString(final String objStringified) {
        try {
            JSONObject json = new JSONObject(new JSONTokener(jsonString));
            this.downloaded = json.getString('downloaded');
            this.name = json.getString('name');
            this.version = json.getString('version');
            this.status = json.getString('status');
        } catch (JSONException err){
            Log.d("Error", err.toString());
        }
    }

    public JSObject toJSON() {
        final JSObject result = new JSObject();
        result.put("downloaded", this.getDownloaded());
        result.put("name", this.getName());
        result.put("version", this.getVersion());
        result.put("status", this.getStatus());
        return result;
    }
    


    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof VersionInfo)) return false;
        final VersionInfo that = (VersionInfo) o;
        return this.getVersion().equals(that.getVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.version);
    }

    @Override
    public String toString() {
        return "{" +
                "downloaded: '" + this.downloaded + "'" +
                ", name: '" + this.name + "'" +
                ", version: '" + this.version + "'" +
                ", status: '" + this.status +
                "'}";
    }
}