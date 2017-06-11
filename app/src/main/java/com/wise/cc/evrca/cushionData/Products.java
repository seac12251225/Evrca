package com.wise.cc.evrca.cushionData;
import java.util.List;

/**
 * Auto-generated: 2017-06-10 15:56:17
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Products {

    private String type;
    private String name;
    private String brand;
    private List<Part> part;
    public void setType(String type) {
         this.type = type;
     }
     public String getType() {
         return type;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setBrand(String brand) {
         this.brand = brand;
     }
     public String getBrand() {
         return brand;
     }

    public void setPart(List<Part> part) {
         this.part = part;
     }
     public List<Part> getPart() {
         return part;
     }

}