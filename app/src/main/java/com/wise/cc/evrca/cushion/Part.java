package com.wise.cc.evrca.cushion;
import java.util.List;

/**
 * Auto-generated: 2017-06-10 15:56:17
 *
 * @author www.jsons.cn 
 * @website http://www.jsons.cn/json2java/ 
 */
public class Part {


    private String partname;
    private String name;
    private List<String> material;

    private List<String> craftwork;
    public void setPartname(String partname) {
         this.partname = partname;
     }
     public String getPartname() {
         return partname;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setMaterial(List<String> material) {
         this.material = material;
     }
     public List<String> getMaterial() {
         return material;
     }

    public void setCraftwork(List<String> craftwork) {
         this.craftwork = craftwork;
     }
     public List<String> getCraftwork() {
         return craftwork;
     }

}