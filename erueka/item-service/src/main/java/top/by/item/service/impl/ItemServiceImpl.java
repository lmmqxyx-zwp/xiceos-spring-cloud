package top.by.item.service.impl;

import org.springframework.stereotype.Service;
import top.by.item.entity.Item;
import top.by.item.service.ItemService;

import java.util.HashMap;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService {

    private static final Map<Long, Item> ITEM_MAP = new HashMap<>();

    static {// 准备一些静态数据,模拟数据库
        ITEM_MAP.put(1L, new Item(1L, "商品1", "http://图片1", "商品描述1", 1000L));
        ITEM_MAP.put(2L, new Item(2L, "商品2", "http://图片2", "商品描述2", 2000L));
        ITEM_MAP.put(3L, new Item(3L, "商品3", "http://图片3", "商品描述3", 3000L));
        ITEM_MAP.put(4L, new Item(4L, "商品4", "http://图片4", "商品描述4", 4000L));
        ITEM_MAP.put(5L, new Item(5L, "商品5", "http://图片5", "商品描述5", 5000L));
        ITEM_MAP.put(6L, new Item(6L, "商品6", "http://图片6", "商品描述6", 6000L));
        ITEM_MAP.put(7L, new Item(7L, "商品7", "http://图片7", "商品描述7", 7000L));
        ITEM_MAP.put(8L, new Item(8L, "商品8", "http://图片8", "商品描述8", 8000L));
        ITEM_MAP.put(9L, new Item(9L, "商品9", "http://图片9", "商品描述9", 9000L));
    }

    /**
     * 模拟实现商品查询
     *
     * @param id
     * @return
     */
    public Item queryItemById(Long id) {
        return ITEM_MAP.get(id);
    }

}
