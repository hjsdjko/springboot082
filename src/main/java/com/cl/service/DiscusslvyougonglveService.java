package com.cl.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.IService;
import com.cl.utils.PageUtils;
import com.cl.entity.DiscusslvyougonglveEntity;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.cl.entity.view.DiscusslvyougonglveView;


/**
 * 旅游攻略评论表
 *
 * @author 
 * @email 
 * @date 2024-03-25 13:31:58
 */
public interface DiscusslvyougonglveService extends IService<DiscusslvyougonglveEntity> {

    PageUtils queryPage(Map<String, Object> params);
    
   	List<DiscusslvyougonglveView> selectListView(Wrapper<DiscusslvyougonglveEntity> wrapper);
   	
   	DiscusslvyougonglveView selectView(@Param("ew") Wrapper<DiscusslvyougonglveEntity> wrapper);
   	
   	PageUtils queryPage(Map<String, Object> params,Wrapper<DiscusslvyougonglveEntity> wrapper);
   	

}

