package com.kingjakeu.lolesports.api.fun;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonPropertyOrder({"식품 유형", "상호", "대표자", "주소", "연락처","사업자 번호", "통신 판매업 번호"})
public class ProducerData {
    @JsonProperty("식품 유형")
    private String foodCategory;
    @JsonProperty("상호")
    private String name;
    @JsonProperty("대표자")
    private String president;
    @JsonProperty("주소")
    private String address;
    @JsonProperty("연락처")
    private String contact;
    @JsonProperty("사업자 번호")
    private String producerNum;
    @JsonProperty("통신 판매업 번호")
    private String teleProducerNum;

    public boolean isEmpty(){
        return (this.foodCategory == null
                || this.name == null
                || this.producerNum == null
                || this.teleProducerNum == null)
                || (this.foodCategory.isBlank()
                && this.name.isBlank()
                && this.producerNum.isBlank()
                && this.teleProducerNum.isBlank());
    }
//    public String toString(){
//        StringBuilder stringBuilder = new StringBuilder("{");
//        stringBuilder.append("\"식품 유형\" : ").append("\"").append(foodCategory).append("\",");
//        stringBuilder.append("\"상호\" : ").append("\"").append(name).append("\",");
//        stringBuilder.append("\"대표자\" : ").append("\"").append(president).append("\",");
//        stringBuilder.append("\"주소\" : ").append("\"").append(address).append("\",");
//        stringBuilder.append("\"연락처\" : ").append("\"").append(contact).append("\",");
//        stringBuilder.append("\"사업자 번호\" : ").append("\"").append(producerNum).append("\",");
//        stringBuilder.append("\"통신 판매업 번호\" : ").append("\"").append(teleProducerNum).append("\"");
//        stringBuilder.append("}");
//        return stringBuilder.toString();
//    }
}
