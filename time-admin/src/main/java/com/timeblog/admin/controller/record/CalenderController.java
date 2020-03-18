package com.timeblog.admin.controller.record;

import com.timeblog.business.base.Result;
import com.timeblog.business.domain.TimeRecord;
import com.timeblog.business.domain.dto.QueryRecordDto;
import com.timeblog.framework.mapper.TimeRecordDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Dong.Chao
 * @Classname CalenderController
 * @Description 记录Controller
 * @Date 2020/3/17 15:54
 * @Version V1.0
 */

@Controller
@RequestMapping("/record")
public class CalenderController {

    @Resource
    private TimeRecordDao timeRecordDao;

    @RequestMapping("/toRecord")
    public String toRecord(){
        return "recording/calendar";
    }


    @RequestMapping("/queryRecordByTime")
    @ResponseBody
    public Result queryRecordByTime(@RequestBody QueryRecordDto queryRecordDto){
        //开始时间，结束时间
        List<TimeRecord> timeRecords = timeRecordDao.queryAllByDate(queryRecordDto.getStartDate(),queryRecordDto.getEndDate());
        return Result.success(timeRecords);
    }




    @RequestMapping("/insertRecordByTime")
    @ResponseBody
    public Result insertRecordByTime(@RequestBody TimeRecord timeRecord){

        timeRecord.setRecordEndTime(new Date());
        timeRecordDao.insert(timeRecord);
        //开始时间，结束时间
        return Result.success();
    }






}
