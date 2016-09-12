
package com.codetest.generated;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "to",
    "from",
    "rate",
    "valueDate",
    "currentDate",
    "tradeType",
    "tradeDate",
    "tradeStyle",
    "excerciseStartDate",
    "expiryDate",
    "premiumDate",
    "deliveryDate"
})
public class TradeInfo {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("to")
    @NotEmpty(message="Currency TO data is required")
    private String to;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("from")
    @NotEmpty(message="Currency FROM data is required")
    private String from;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("rate")
    @NotEmpty(message="Currency reate can not be empty")
    private String rate;
    
    @JsonProperty("valueDate")
    @DateTimeFormat(pattern="MM/dd/yyyy")
    @NotBlank(message = "ValueDate is required")
    private String valueDate;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("currentDate")
    private String currentDate;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("tradeType")
    @NotEmpty(message = "TradeType is required")
    private String tradeType;
    /**
     * 
     * (Required)
     * 
     */
    @DateTimeFormat(pattern="MM/dd/yyyy")
    @NotEmpty(message = "TradeDate is required")
    @JsonProperty("tradeDate")
    private String tradeDate;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("tradeStyle")
    private String tradeStyle;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("excerciseStartDate")
    @DateTimeFormat(pattern="MM/dd/yyyy")
    private String excerciseStartDate;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("expiryDate")
    @DateTimeFormat(pattern="MM/dd/yyyy")
    private String expiryDate;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("premiumDate")
    @DateTimeFormat(pattern="MM/dd/yyyy")
    private String premiumDate;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("deliveryDate")
    @DateTimeFormat(pattern="MM/dd/yyyy")
    private String deliveryDate;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The to
     */
    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    /**
     * 
     * (Required)
     * 
     * @param to
     *     The to
     */
    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The from
     */
    @JsonProperty("from")
    public String getFrom() {
        return from;
    }

    /**
     * 
     * (Required)
     * 
     * @param from
     *     The from
     */
    @JsonProperty("from")
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The rate
     */
    @JsonProperty("rate")
    public String getRate() {
        return rate;
    }

    /**
     * 
     * (Required)
     * 
     * @param rate
     *     The rate
     */
    @JsonProperty("rate")
    public void setRate(String rate) {
        this.rate = rate;
    }
    
    /**
     * 
     * @return
     *     The valueDate
     */
    @JsonProperty("valueDate")
    public String getValueDate() {
        return valueDate;
    }

    /**
     * 
     * @param valueDate
     *     The valueDate
     */
    @JsonProperty("valueDate")
    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }


    /**
     * 
     * (Required)
     * 
     * @return
     *     The currentDate
     */
    @JsonProperty("currentDate")
    public String getCurrentDate() {
        return currentDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param currentDate
     *     The currentDate
     */
    @JsonProperty("currentDate")
    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tradeType
     */
    @JsonProperty("tradeType")
    public String getTradeType() {
        return tradeType;
    }

    /**
     * 
     * (Required)
     * 
     * @param tradeType
     *     The tradeType
     */
    @JsonProperty("tradeType")
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tradeDate
     */
    @JsonProperty("tradeDate")
    public String getTradeDate() {
        return tradeDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param tradeDate
     *     The tradeDate
     */
    @JsonProperty("tradeDate")
    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tradeStyle
     */
    @JsonProperty("tradeStyle")
    public String getTradeStyle() {
        return tradeStyle;
    }

    /**
     * 
     * (Required)
     * 
     * @param tradeStyle
     *     The tradeStyle
     */
    @JsonProperty("tradeStyle")
    public void setTradeStyle(String tradeStyle) {
        this.tradeStyle = tradeStyle;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The excerciseStartDate
     */
    @JsonProperty("excerciseStartDate")
    public String getExcerciseStartDate() {
        return excerciseStartDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param excerciseStartDate
     *     The excerciseStartDate
     */
    @JsonProperty("excerciseStartDate")
    public void setExcerciseStartDate(String excerciseStartDate) {
        this.excerciseStartDate = excerciseStartDate;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The expiryDate
     */
    @JsonProperty("expiryDate")
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param expiryDate
     *     The expiryDate
     */
    @JsonProperty("expiryDate")
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The premiumDate
     */
    @JsonProperty("premiumDate")
    public String getPremiumDate() {
        return premiumDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param premiumDate
     *     The premiumDate
     */
    @JsonProperty("premiumDate")
    public void setPremiumDate(String premiumDate) {
        this.premiumDate = premiumDate;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The deliveryDate
     */
    @JsonProperty("deliveryDate")
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * 
     * (Required)
     * 
     * @param deliveryDate
     *     The deliveryDate
     */
    @JsonProperty("deliveryDate")
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Generated("org.jsonschema2pojo")
    public enum From {

        EUR("EUR"),
        USD("USD"),
        CHF("CHF");
        private final String value;
        private final static Map<String, TradeInfo.From> CONSTANTS = new HashMap<String, TradeInfo.From>();

        static {
            for (TradeInfo.From c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private From(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static TradeInfo.From fromValue(String value) {
            TradeInfo.From constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("org.jsonschema2pojo")
    public enum To {

        EUR("EUR"),
        USD("USD"),
        CHF("CHF");
        private final String value;
        private final static Map<String, TradeInfo.To> CONSTANTS = new HashMap<String, TradeInfo.To>();

        static {
            for (TradeInfo.To c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private To(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static TradeInfo.To fromValue(String value) {
            TradeInfo.To constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Generated("org.jsonschema2pojo")
    public enum TradeType {

        ALL("ALL"),
        SPOT("SPOT"),
        FORWARD("FORWARD"),
        OPTION("OPTION");
        private final String value;
        private final static Map<String, TradeInfo.TradeType> CONSTANTS = new HashMap<String, TradeInfo.TradeType>();

        static {
            for (TradeInfo.TradeType c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private TradeType(String value) {
            this.value = value;
        }

        @JsonValue
        @Override
        public String toString() {
            return this.value;
        }

        @JsonCreator
        public static TradeInfo.TradeType fromValue(String value) {
            TradeInfo.TradeType constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
