package site.lgong.list;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import site.lgong.entity.SortEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ListSortTest {

    public static void main(String[] args) {
        List<SortEntity> list = new ArrayList<>();
        list.add(new SortEntity("C", "P3", "S2", 1));
        list.add(new SortEntity("A", "P1", "S1", 2));
        list.add(new SortEntity("A", "P2", "S1", 3));
        list.add(new SortEntity("B", "P1", "S1", 4));
        list.add(new SortEntity("C", "P1", "S1", 5));
        list.add(new SortEntity("B", "P1", "S2", 6));
        list.add(new SortEntity("A", "P1", "S2", 7));
        list.add(new SortEntity("C", "P1", "S2", 8));
        list.add(new SortEntity("", "P1", "S2", 9));
        list.add(new SortEntity("A", "", "", 10));
        list.add(new SortEntity("", "", "S2", 11));
        list.add(new SortEntity("", "", "", 12));
        list.add(new SortEntity("A", "P4", "", 13));
        list.add(new SortEntity("A", "P4", "S5", 14));

        System.out.println(getMostMatchedObject(list, "A", "P4", "S5").getId());
    }

    public static SortEntity getMostMatchedObject(List<SortEntity> list, String channelCode, String productCode, String salePackageCode) {

        List<SortEntity> entityList = list.stream()
                .filter(sortEntity -> channelCode != null && (StringUtils.isBlank(sortEntity.getChannelCode()) || channelCode.equals(sortEntity.getChannelCode())))
                .filter(sortEntity -> productCode != null && (StringUtils.isBlank(sortEntity.getProductCode()) || productCode.equals(sortEntity.getProductCode())))
                .filter(sortEntity -> salePackageCode != null && (StringUtils.isBlank(sortEntity.getSalePackageCode()) || salePackageCode.equals(sortEntity.getSalePackageCode())))
                .collect(Collectors.toList());

        System.out.println(JSONObject.toJSONString(entityList));
        System.out.println("===========");
        Collections.sort(entityList);
        System.out.println(JSONObject.toJSONString(entityList));
        return entityList.stream().findFirst().orElse(null);
    }

}
