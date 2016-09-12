
package com.codetest.generated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "EUR",
    "USD"
})
public class Holidays {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("EUR")
    private List<EUR> eUR = new ArrayList<EUR>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("USD")
    private List<USD> uSD = new ArrayList<USD>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The eUR
     */
    @JsonProperty("EUR")
    public List<EUR> getEUR() {
        return eUR;
    }

    /**
     * 
     * (Required)
     * 
     * @param eUR
     *     The EUR
     */
    @JsonProperty("EUR")
    public void setEUR(List<EUR> eUR) {
        this.eUR = eUR;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The uSD
     */
    @JsonProperty("USD")
    public List<USD> getUSD() {
        return uSD;
    }

    /**
     * 
     * (Required)
     * 
     * @param uSD
     *     The USD
     */
    @JsonProperty("USD")
    public void setUSD(List<USD> uSD) {
        this.uSD = uSD;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
