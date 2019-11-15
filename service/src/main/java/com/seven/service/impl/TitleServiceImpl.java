package com.seven.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.seven.dao.*;
import com.seven.model.*;
import com.seven.parent.table.OneTable;
import com.seven.response.ResultTitleOne;
import com.seven.service.ITitleService;
import com.seven.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 * 标题表 服务实现类
 * </p>
 *
 * @author huo
 * @since 2019-11-14
 */
@Service
public class TitleServiceImpl extends ServiceImpl<TitleMapper, Title> implements ITitleService {

    @Autowired
    private TitleMapper titleMapper;
    @Autowired
    private OneMapper oneMapper;
    @Autowired
    private TwoMapper twoMapper;
    @Autowired
    private ThreeMapper threeMapper;
    @Autowired
    private FourMapper fourMapper;
    @Autowired
    private FivesMapper fivesMapper;
    @Autowired
    private SixMapper sixMapper;



    @Override
    public Object workOverview() {
        Title title = titleMapper.selectById(1);
        return transfer(title);
    }

    @Override
    public Object Construction(){
        Title title = titleMapper.selectById(2);
        return transfer(title);
    }

    @Override
    public Object WorkBasis(){
        Title title = titleMapper.selectById(3);
        return transfer(title);
    }
    @Override
    public Object Procedures(){
        Title title = titleMapper.selectById(4);
        return transfer(title);
    }

    @Override
    public Object WorkProcessanAndMainResearchContent() {
        Title title = titleMapper.selectById(5);
        return transfer(title);
    }

    @Override
    public Object WorkProcessan() {
        Title title = titleMapper.selectById(6);
        if (title!=null){
            ResultTitleOne resultTitleOne = (ResultTitleOne) transfer(title);
            OneTable oneTable = new OneTable();
            QueryWrapper<One> one = new QueryWrapper<>();
            one.select("time", "contnt", "Completion");
            List<One> maps = oneMapper.selectList(one);
            if (maps.size()>0){
                oneTable.setOneList(maps);
                resultTitleOne.setOneTable(oneTable);
                return resultTitleOne;
            }
        }
        return Result.failure(203,"数据不存在");
    }


    @Override
    public Object MainResearchContent() {
        Title title = titleMapper.selectById(7);
        return transfer(title);
    }

    @Override
    public Object SiteAirCoolingObservatory() {
        Title title = titleMapper.selectById(8);
        return transfer(title);
    }

    @Override
    public Object SiteObservationConstruction() {
        Title title = titleMapper.selectById(9);
        return transfer(title);
    }

    @Override
    public Object InstallationAndQualification() {
        Title title = titleMapper.selectById(10);
        if (title!=null) {
            ResultTitleOne resultTitleOne = (ResultTitleOne) transfer(title);
            OneTable oneTable = new OneTable();;
            QueryWrapper<Two> two = new QueryWrapper<>();
            two.select("name", "model", "height","factory");
            List<Two> maps = twoMapper.selectList(two);
            if (maps.size() > 0) {
                oneTable.setTwoList(maps);
                resultTitleOne.setOneTable(oneTable);
                return resultTitleOne;
            }
        }
        return Result.failure(203,"数据不存在");
    }

    @Override
    public Object Observations() {
        Title title = titleMapper.selectById(11);
        return transfer(title);
    }

    @Override
    public Object TetheredAirshipLowAltitudeDetection() {
        Title title = titleMapper.selectById(12);
        return transfer(title);
    }

    @Override
    public Object ObservationInstrument() {
        Title title = titleMapper.selectById(13);
        return transfer(title);
    }

    @Override
    public Object MainSpecifications() {
        Title title = titleMapper.selectById(14);
        return transfer(title);
    }

    @Override
    public Object Radiosonde() {
        Title title = titleMapper.selectById(15);
        return transfer(title);
    }

    @Override
    public Object InstrumentVerification() {
        Title title = titleMapper.selectById(16);
        return transfer(title);
    }

    @Override
    public Object WorkProcess() {
        Title title = titleMapper.selectById(17);
        return transfer(title);
    }

    @Override
    public Object ObservationMethodsAndDataQuality() {
        Title title = titleMapper.selectById(18);
        return transfer(title);
    }

    @Override
    public Object ObservationLocation() {
        Title title = titleMapper.selectById(19);
        return transfer(title);
    }

    @Override
    public Object Meteorological() {
        Title title = titleMapper.selectById(20);
        if (title!=null) {
            ResultTitleOne resultTitleOne = (ResultTitleOne) transfer(title);
            OneTable oneTable = new OneTable();
            QueryWrapper<Three> thre = new QueryWrapper<>();
            thre.select("meteorological_element", "reasonable_range");
            List<Three> threes = threeMapper.selectList(thre);
            if (threes.size() > 0) {
                oneTable.setThreeList(threes);
                resultTitleOne.setOneTable(oneTable);
            }
            QueryWrapper<Four> four = new QueryWrapper<>();
            four.select("meteorological_element", "con","reasonable_correlation");
            List<Four> fours = fourMapper.selectList(four);
            if (fours.size()>0){
                oneTable.setFourList(fours);
                resultTitleOne.setOneTable(oneTable);
            }
            QueryWrapper<Fives> five = new QueryWrapper<>();
            five.select("meteorological_element", "reasonable_correlation");
            List<Fives> fives = fivesMapper.selectList(five);
            if (fives.size() > 0) {
                oneTable.setFivesList(fives);
                resultTitleOne.setOneTable(oneTable);
            }
            QueryWrapper<Six> six = new QueryWrapper<>();
            six = six.select("meteorological_element", "reasonable_difference");
            List<Six> sixs = sixMapper.selectList(six);
            if (sixs.size()>0){
                oneTable.setSixList(sixs);
                resultTitleOne.setOneTable(oneTable);
            }
            return resultTitleOne;
        }

        return Result.failure(203,"数据不存在");
    }

    public Object transfer(Title title){
        boolean present = Optional.ofNullable(title).isPresent();
        if (present) {
            ResultTitleOne resultTitleOne = new ResultTitleOne();
            String titleContent = title.getTitleContent();
            String content = title.getContent();
            String image=title.getImageAddress();
            if (StringUtils.isNotEmpty(image)){
                String[] split = image.split(",");
                resultTitleOne.setImageAddress(Arrays.asList(split));
            }
            resultTitleOne.setTitle(titleContent);
            resultTitleOne.setContent(content);
            return resultTitleOne;
        }
        return Result.failure (203,"数据不存在");
    }

}
