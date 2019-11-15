package com.seven.parent.table;

import com.seven.model.*;
import lombok.Data;

import java.util.List;

/**
 * @program: huo
 *
 * @description: 表格对象
 *
 * @author: zxb
 *
 * @create: 2019-11-14 10:34
 **/
@Data
public class OneTable {
    private List<One> oneList;//表1.1
    private List<Two> twoList;//表1.2
    private List<Three> threeList;//表1.3
    private List<Four> fourList;//表1.4
    private List<Fives> fivesList;//表1.5
    private List<Six> sixList;//表1.6

}
