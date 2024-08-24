package com.cl.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.cl.utils.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.cl.annotation.IgnoreAuth;

import com.cl.entity.LvyougonglveEntity;
import com.cl.entity.view.LvyougonglveView;

import com.cl.service.LvyougonglveService;
import com.cl.service.TokenService;
import com.cl.utils.PageUtils;
import com.cl.utils.R;
import com.cl.utils.MPUtil;
import com.cl.utils.CommonUtil;
import java.io.IOException;
import com.cl.service.StoreupService;
import com.cl.entity.StoreupEntity;

/**
 * 旅游攻略
 * 后端接口
 * @author 
 * @email 
 * @date 2024-03-25 13:31:58
 */
@RestController
@RequestMapping("/lvyougonglve")
public class LvyougonglveController {
    @Autowired
    private LvyougonglveService lvyougonglveService;

    @Autowired
    private StoreupService storeupService;


    


    /**
     * 后端列表
     */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params,LvyougonglveEntity lvyougonglve,
		HttpServletRequest request){
        EntityWrapper<LvyougonglveEntity> ew = new EntityWrapper<LvyougonglveEntity>();

		PageUtils page = lvyougonglveService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, lvyougonglve), params), params));

        return R.ok().put("data", page);
    }
    
    /**
     * 前端列表
     */
	@IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params,LvyougonglveEntity lvyougonglve, 
		HttpServletRequest request){
        EntityWrapper<LvyougonglveEntity> ew = new EntityWrapper<LvyougonglveEntity>();

		PageUtils page = lvyougonglveService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, lvyougonglve), params), params));
        return R.ok().put("data", page);
    }

	/**
     * 列表
     */
    @RequestMapping("/lists")
    public R list( LvyougonglveEntity lvyougonglve){
       	EntityWrapper<LvyougonglveEntity> ew = new EntityWrapper<LvyougonglveEntity>();
      	ew.allEq(MPUtil.allEQMapPre( lvyougonglve, "lvyougonglve")); 
        return R.ok().put("data", lvyougonglveService.selectListView(ew));
    }

	 /**
     * 查询
     */
    @RequestMapping("/query")
    public R query(LvyougonglveEntity lvyougonglve){
        EntityWrapper< LvyougonglveEntity> ew = new EntityWrapper< LvyougonglveEntity>();
 		ew.allEq(MPUtil.allEQMapPre( lvyougonglve, "lvyougonglve")); 
		LvyougonglveView lvyougonglveView =  lvyougonglveService.selectView(ew);
		return R.ok("查询旅游攻略成功").put("data", lvyougonglveView);
    }
	
    /**
     * 后端详情
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
        LvyougonglveEntity lvyougonglve = lvyougonglveService.selectById(id);
		lvyougonglve.setClicktime(new Date());
		lvyougonglveService.updateById(lvyougonglve);
		lvyougonglve = lvyougonglveService.selectView(new EntityWrapper<LvyougonglveEntity>().eq("id", id));
        return R.ok().put("data", lvyougonglve);
    }

    /**
     * 前端详情
     */
	@IgnoreAuth
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id){
        LvyougonglveEntity lvyougonglve = lvyougonglveService.selectById(id);
		lvyougonglve.setClicktime(new Date());
		lvyougonglveService.updateById(lvyougonglve);
		lvyougonglve = lvyougonglveService.selectView(new EntityWrapper<LvyougonglveEntity>().eq("id", id));
        return R.ok().put("data", lvyougonglve);
    }
    


    /**
     * 赞或踩
     */
    @RequestMapping("/thumbsup/{id}")
    public R vote(@PathVariable("id") String id,String type){
        LvyougonglveEntity lvyougonglve = lvyougonglveService.selectById(id);
        if(type.equals("1")) {
        	lvyougonglve.setThumbsupnum(lvyougonglve.getThumbsupnum()+1);
        } else {
        	lvyougonglve.setCrazilynum(lvyougonglve.getCrazilynum()+1);
        }
        lvyougonglveService.updateById(lvyougonglve);
        return R.ok("投票成功");
    }

    /**
     * 后端保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody LvyougonglveEntity lvyougonglve, HttpServletRequest request){
    	lvyougonglve.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(lvyougonglve);
        lvyougonglveService.insert(lvyougonglve);
        return R.ok();
    }
    
    /**
     * 前端保存
     */
    @RequestMapping("/add")
    public R add(@RequestBody LvyougonglveEntity lvyougonglve, HttpServletRequest request){
    	lvyougonglve.setId(new Date().getTime()+new Double(Math.floor(Math.random()*1000)).longValue());
    	//ValidatorUtils.validateEntity(lvyougonglve);
        lvyougonglveService.insert(lvyougonglve);
        return R.ok();
    }



    /**
     * 修改
     */
    @RequestMapping("/update")
    @Transactional
    public R update(@RequestBody LvyougonglveEntity lvyougonglve, HttpServletRequest request){
        //ValidatorUtils.validateEntity(lvyougonglve);
        lvyougonglveService.updateById(lvyougonglve);//全部更新
        return R.ok();
    }



    

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
        lvyougonglveService.deleteBatchIds(Arrays.asList(ids));
        return R.ok();
    }
    
	
	/**
     * 前端智能排序
     */
	@IgnoreAuth
    @RequestMapping("/autoSort")
    public R autoSort(@RequestParam Map<String, Object> params,LvyougonglveEntity lvyougonglve, HttpServletRequest request,String pre){
        EntityWrapper<LvyougonglveEntity> ew = new EntityWrapper<LvyougonglveEntity>();
        Map<String, Object> newMap = new HashMap<String, Object>();
        Map<String, Object> param = new HashMap<String, Object>();
		Iterator<Map.Entry<String, Object>> it = param.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, Object> entry = it.next();
			String key = entry.getKey();
			String newKey = entry.getKey();
			if (pre.endsWith(".")) {
				newMap.put(pre + newKey, entry.getValue());
			} else if (StringUtils.isEmpty(pre)) {
				newMap.put(newKey, entry.getValue());
			} else {
				newMap.put(pre + "." + newKey, entry.getValue());
			}
		}
		params.put("sort", "clicktime");
        params.put("order", "desc");
		PageUtils page = lvyougonglveService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, lvyougonglve), params), params));
        return R.ok().put("data", page);
    }

    /**
     * 按收藏推荐
     */
    @RequestMapping("/autoSort2")
    public R autoSort2(@RequestParam Map<String, Object> params,LvyougonglveEntity lvyougonglve, HttpServletRequest request){
        String userId = request.getSession().getAttribute("userId").toString();
        String inteltypeColumn = "jingdianmingcheng";
        List<StoreupEntity> storeups = storeupService.selectList(new EntityWrapper<StoreupEntity>().eq("type", 1).eq("userid", userId).eq("tablename", "lvyougonglve").orderBy("addtime", false));
        List<String> inteltypes = new ArrayList<String>();
        Integer limit = params.get("limit")==null?10:Integer.parseInt(params.get("limit").toString());
        List<LvyougonglveEntity> lvyougonglveList = new ArrayList<LvyougonglveEntity>();
        //去重
        if(storeups!=null && storeups.size()>0) {
            for(StoreupEntity s : storeups) {
                lvyougonglveList.addAll(lvyougonglveService.selectList(new EntityWrapper<LvyougonglveEntity>().eq(inteltypeColumn, s.getInteltype())));
            }
        }
        EntityWrapper<LvyougonglveEntity> ew = new EntityWrapper<LvyougonglveEntity>();
        params.put("sort", "id");
        params.put("order", "desc");
        PageUtils page = lvyougonglveService.queryPage(params, MPUtil.sort(MPUtil.between(MPUtil.likeOrEq(ew, lvyougonglve), params), params));
        List<LvyougonglveEntity> pageList = (List<LvyougonglveEntity>)page.getList();
        if(lvyougonglveList.size()<limit) {
            int toAddNum = (limit-lvyougonglveList.size())<=pageList.size()?(limit-lvyougonglveList.size()):pageList.size();
            for(LvyougonglveEntity o1 : pageList) {
                boolean addFlag = true;
                for(LvyougonglveEntity o2 : lvyougonglveList) {
                    if(o1.getId().intValue()==o2.getId().intValue()) {
                        addFlag = false;
                        break;
                    }
                }
                if(addFlag) {
                    lvyougonglveList.add(o1);
                    if(--toAddNum==0) break;
                }
            }
        } else if(lvyougonglveList.size()>limit) {
            lvyougonglveList = lvyougonglveList.subList(0, limit);
        }
        page.setList(lvyougonglveList);
        return R.ok().put("data", page);
    }







}
