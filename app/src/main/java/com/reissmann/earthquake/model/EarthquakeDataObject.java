
package com.reissmann.earthquake.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "metadata",
    "features",
    "bbox"
})
public class EarthquakeDataObject implements Parcelable {

    @JsonProperty("type")
    private String type;
    @JsonProperty("metadata")
    private Metadata metadata;
    @JsonProperty("features")
    private List<Feature> features = null;
    @JsonProperty("bbox")
    private List<Double> bbox = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("metadata")
    public Metadata getMetadata() {
        return metadata;
    }

    @JsonProperty("metadata")
    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @JsonProperty("features")
    public List<Feature> getFeatures() {
        return features;
    }

    @JsonProperty("features")
    public void setFeatures(List<Feature> features) {
        this.features = features;
    }

    @JsonProperty("bbox")
    public List<Double> getBbox() {
        return bbox;
    }

    @JsonProperty("bbox")
    public void setBbox(List<Double> bbox) {
        this.bbox = bbox;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeParcelable(this.metadata, flags);
        dest.writeTypedList(this.features);
        dest.writeList(this.bbox);
    }

    public EarthquakeDataObject() {
    }

    protected EarthquakeDataObject(Parcel in) {
        this.type = in.readString();
        this.metadata = in.readParcelable(Metadata.class.getClassLoader());
        this.features = in.createTypedArrayList(Feature.CREATOR);
        this.bbox = new ArrayList<Double>();
        in.readList(this.bbox, Double.class.getClassLoader());
        int additionalPropertiesSize = in.readInt();
    }

    public static final Parcelable.Creator<EarthquakeDataObject> CREATOR = new Parcelable.Creator<EarthquakeDataObject>() {
        @Override
        public EarthquakeDataObject createFromParcel(Parcel source) {
            return new EarthquakeDataObject(source);
        }

        @Override
        public EarthquakeDataObject[] newArray(int size) {
            return new EarthquakeDataObject[size];
        }
    };
}
