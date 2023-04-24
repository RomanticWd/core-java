package site.lgong.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SortEntity implements Comparable<SortEntity> {

    private String channelCode;

    private String productCode;

    private String salePackageCode;

    private Integer id;

    /**
     * 实现排序
     * channelCode 权重6 productCode 权重3 salePackageCode权重1
     * 这样保证所有参数都存在的时候优先级最高，只要channelCode存在，优先级比其他二者存在优先级高。
     *
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(SortEntity o) {
        int thisWeight = this.getWeight();
        int oWeight = o.getWeight();

        if (thisWeight != oWeight) {
            return Integer.compare(oWeight, thisWeight);
        }

        int cmp = this.channelCode.compareTo(o.channelCode);
        if (cmp != 0) {
            return cmp;
        }

        cmp = this.productCode.compareTo(o.productCode);
        if (cmp != 0) {
            return cmp;
        }

        return this.salePackageCode.compareTo(o.salePackageCode);
    }

    private int getWeight() {
        int total = 0;
        if (StringUtils.isNotBlank(channelCode)) {
            total += 6;
        }

        if (StringUtils.isNotBlank(productCode)) {
            total += 3;
        }

        if (StringUtils.isNotBlank(salePackageCode)) {
            total += 1;
        }
        return total;
    }
}
