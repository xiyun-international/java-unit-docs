package com.middle.stage.test.optimization.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.middle.stage.test.optimization.commons.CallResult;
import com.middle.stage.test.optimization.commons.CommonsListUtil;
import com.middle.stage.test.optimization.dao.data.*;
import com.middle.stage.test.optimization.dao.dto.CanteenDTO;
import com.middle.stage.test.optimization.model.enums.CommonIsEnum;
import com.middle.stage.test.optimization.model.enums.ObjectTypeForLogEnum;
import com.middle.stage.test.optimization.model.form.DinnerTypeForm;
import com.middle.stage.test.optimization.model.view.DinnerTypeView;
import com.middle.stage.test.optimization.service.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 门店餐别
 *
 * @author zyq
 * @date 2020/2/9
 */
@Slf4j
@Service
public class ShopServiceImpl implements ShopService {

    private static final int DINNER_TYPE_OF_FULL_DAY = 1;

    private DinnerTypeService dinnerTypeService;

    private DinnerService dinnerService;

    private ShopCommonService shopCommonService;

    private CanteenDinnerService canteenDinnerService;

    private OperationLogService operationLogService;

    private final String INSERT_RECORD_TITLE = "*添加餐别【";
    private final String UPDATE_RECORD_TITLE = "*更新餐别【";
    private final String DELETE_RECORD_TITLE = "*删除餐别【";

    public ShopServiceImpl(DinnerTypeService dinnerTypeService, DinnerService dinnerService, ShopCommonService shopCommonService, CanteenDinnerService canteenDinnerService, OperationLogService operationLogService) {
        this.dinnerTypeService = dinnerTypeService;
        this.dinnerService = dinnerService;
        this.shopCommonService = shopCommonService;
        this.canteenDinnerService = canteenDinnerService;
        this.operationLogService = operationLogService;
    }

    public ShopServiceImpl() {
    }

    /**
     * 保存门店餐别关系
     *
     * @param loginUser
     * @param canteenDTO
     */
    public CallResult saveCanteenDinnerRelation(List<DinnerTypeForm> newDinnerList, UserDO loginUser, CanteenDTO canteenDTO) {

        log.info("ShopService.updateCanteen newDinnerList = [{}]", JSONObject.toJSON(newDinnerList));

        DinnerTypeDO dinnerType = dinnerTypeService.selectByPrimaryKey(DINNER_TYPE_OF_FULL_DAY);
        if (CollectionUtils.isEmpty(newDinnerList)) {
            DinnerTypeView dinnerTypeView = DinnerTypeView.intDefaultTimeToStr(dinnerType);
            DinnerTypeForm dinnerTypeForm = new DinnerTypeForm();
            BeanUtils.copyProperties(dinnerTypeView, dinnerTypeForm);
            newDinnerList.add(dinnerTypeForm);
        }
        //转换实体类及时间
        List<DinnerTypeDO> newDinnerTypeDOList = dinnerTypeFormListToDinnerTypeDOList(newDinnerList);
        List<DinnerTypeDO> oldDinnerList = dinnerTypeService.selectDinnerTypeByCanteenId(canteenDTO.getCanteenId());
        List<DinnerTypeDO> oldDinnerTypeDOList = Lists.newArrayList();
        if (CollectionUtils.isEmpty(oldDinnerList)) {
            //历史门店没有餐别，默认将旧数据设置为全天
            oldDinnerTypeDOList.add(dinnerType);
        } else {
            oldDinnerTypeDOList = oldDinnerList;
        }
        log.info("ShopService.updateCanteen oldDinnerList = [{}]", JSONObject.toJSON(oldDinnerList));
        //通用集合求差集、交集
        CommonsListUtil commonsListUtil = new CommonsListUtil(newDinnerTypeDOList, oldDinnerTypeDOList).invoke();
        List<DinnerTypeDO> tmpNewDinnerTypeDOList = commonsListUtil.getTmpNewObjectList();
        List<DinnerTypeDO> tmpOldDinnerTypeDOList = commonsListUtil.getTmpOldObjectList();
        List<DinnerTypeDO> tmpIntersectNewDinnerTypeDOList = commonsListUtil.getTmpIntersectNewObjectList();
        List<DinnerTypeDO> tmpIntersectOldDinnerTypeDOList = commonsListUtil.getTmpIntersectOldObjectList();
        //排序
        tmpIntersectNewDinnerTypeDOList.stream().sorted(Comparator.comparing(DinnerTypeDO::getDinnerTypeId));
        tmpIntersectOldDinnerTypeDOList.stream().sorted(Comparator.comparing(DinnerTypeDO::getDinnerTypeId));
        //本次操作信息记录
        StringBuffer updateRecord = new StringBuffer();
        List<DinnerTypeDO> dinnerTypeEntities = dinnerTypeService.selectAll();
        HashMap<Integer, DinnerTypeDO> dinnerTypeDOMap = dinnerTypeService.listToMap(dinnerTypeEntities);

        // 旧集合求出差集不为空，执行逻辑删除
        int deleteResult = getDeleteResult(loginUser, canteenDTO, tmpOldDinnerTypeDOList, updateRecord, dinnerTypeDOMap);
        //交集求出数据更新
        List<DinnerTypeDO> batchUpdateList = Lists.newArrayList();
        setBatchUpdateList(tmpIntersectNewDinnerTypeDOList, tmpIntersectOldDinnerTypeDOList, batchUpdateList);
        int updateResult = getUpdateResult(loginUser, canteenDTO, updateRecord, dinnerTypeDOMap, batchUpdateList);
        // 新集合求出差集不为空，执行添加
        int insertResult = getInsertResult(loginUser, canteenDTO, tmpNewDinnerTypeDOList, updateRecord, dinnerTypeDOMap);
        //添加本次变更操作记录
        if (updateRecord.length() > 0) {
            pushDataAndSaveLog(newDinnerList, loginUser, canteenDTO, newDinnerTypeDOList, oldDinnerList, updateRecord);
        }
        log.info("ShopService.updateCanteen insertResult = [{}] tmpNewDinnerTypeDOList = [{}]", insertResult, JSONObject.toJSON(tmpNewDinnerTypeDOList));
        log.info("ShopService.updateCanteen deleteResult = [{}] tmpOldDinnerTypeDOList = [{}]", deleteResult, JSONObject.toJSON(tmpOldDinnerTypeDOList));
        log.info("ShopService.updateCanteen updateResult = [{}] batchUpdateList = [{}]", updateResult, JSONObject.toJSON(batchUpdateList));
        return CallResult.success(CallResult.RETURN_STATUS_OK, "操作成功", deleteResult + updateResult + insertResult);
    }

    public void pushDataAndSaveLog(List<DinnerTypeForm> newDinnerList, UserDO loginUser, CanteenDTO canteenDTO, List<DinnerTypeDO> newDinnerTypeDOList, List<DinnerTypeDO> oldDinnerList, StringBuffer updateRecord) {
        //推送餐别数据
        shopCommonService.pushDinnerToIsv(IsvPushDO.OPERATE_TYPE_UPDATE,
                IsvPushDO.OBJECT_TYPE_DINNER, canteenDTO.getMerchantId(), canteenDTO.getCanteenId(), canteenDTO.getEquId());
        // operationLog service 保存日志
        operationLogService.saveLog(canteenDTO.getCanteenId().longValue(),
                ObjectTypeForLogEnum.DINNER,
                OperationLogDO.OPERATION_EDIT,
                updateRecord.toString(),
                JSONObject.toJSONString(oldDinnerList),
                JSONObject.toJSONString(newDinnerList),
                loginUser.getUserId(),
                loginUser.getMerchantId());

        List<DinnerDO> dinnerEntities = dinnerTypeDOListToDinnerTypeDOList(loginUser, canteenDTO, newDinnerTypeDOList);
        //只有全天时不添加进缓存
        if (!(newDinnerTypeDOList.size() == 1 && newDinnerTypeDOList.get(0).getDinnerTypeId() == DINNER_TYPE_OF_FULL_DAY)) {
            canteenDinnerService.putCanteenDinnerType2Cache(canteenDTO.getCanteenId(), dinnerEntities);
        }
    }

    public int getInsertResult(UserDO loginUser, CanteenDTO canteenDTO, List<DinnerTypeDO> tmpNewDinnerTypeDOList, StringBuffer updateRecord, HashMap<Integer, DinnerTypeDO> dinnerTypeDOMap) {
        int insertResult = 0;
        if (!tmpNewDinnerTypeDOList.isEmpty()) {
            List<DinnerDO> newDinnerDOList = dinnerTypeDOListToDinnerTypeDOList(loginUser, canteenDTO, tmpNewDinnerTypeDOList);
            insertResult = dinnerService.batchInsert(newDinnerDOList);
            newDinnerDOList.forEach(tuple -> {
                DinnerTypeDO DinnerTypeDO = dinnerTypeDOMap.get(tuple.getDinnerTypeId());
                if (DinnerTypeDO != null) {
                    getRecordMsg(updateRecord, tuple, DinnerTypeDO, INSERT_RECORD_TITLE);
                }
            });
        }
        return insertResult;
    }

    public int getUpdateResult(UserDO loginUser, CanteenDTO canteenDTO, StringBuffer updateRecord, HashMap<Integer, DinnerTypeDO> dinnerTypeDOMap, List<DinnerTypeDO> batchUpdateList) {
        int updateResult = 0;
        if (!batchUpdateList.isEmpty()) {
            List<DinnerDO> dinnerTypeDOList = dinnerTypeDOListToDinnerTypeDOList(loginUser, canteenDTO, batchUpdateList);
            updateResult = dinnerService.batchUpdateByCondition(dinnerTypeDOList);
            dinnerTypeDOList.forEach(tuple -> {
                DinnerTypeDO DinnerTypeDO = dinnerTypeDOMap.get(tuple.getDinnerTypeId());
                if (DinnerTypeDO != null) {
                    getRecordMsg(updateRecord, tuple, DinnerTypeDO, UPDATE_RECORD_TITLE);
                }
            });
        }
        return updateResult;
    }

    public void setBatchUpdateList(List<DinnerTypeDO> tmpIntersectNewDinnerTypeDOList, List<DinnerTypeDO> tmpIntersectOldDinnerTypeDOList, List<DinnerTypeDO> batchUpdateList) {
        if (!tmpIntersectNewDinnerTypeDOList.isEmpty() && !tmpIntersectOldDinnerTypeDOList.isEmpty()) {
            for (int i = 0; i < tmpIntersectNewDinnerTypeDOList.size(); i++) {
                DinnerTypeDO intersectNew = tmpIntersectNewDinnerTypeDOList.get(i);
                DinnerTypeDO intersectOld = tmpIntersectOldDinnerTypeDOList.get(i);
                //更新
                if (!intersectNew.getDefaultStartTime().equals(intersectOld.getDefaultStartTime())
                        || !intersectNew.getDefaultEndTime().equals(intersectOld.getDefaultEndTime())) {
                    batchUpdateList.add(intersectNew);
                }
            }
        }
    }

    public int getDeleteResult(UserDO loginUser, CanteenDTO canteenDTO, List<DinnerTypeDO> tmpOldDinnerTypeDOList, StringBuffer updateRecord, HashMap<Integer, DinnerTypeDO> dinnerTypeDOMap) {
        int deleteResult = 0;
        if (!tmpOldDinnerTypeDOList.isEmpty()) {
            List<DinnerDO> oldDinnerDOList = dinnerTypeDOListToDinnerTypeDOList(loginUser, canteenDTO, tmpOldDinnerTypeDOList);
            deleteResult = dinnerService.batchDeleteByCondition(oldDinnerDOList);
            oldDinnerDOList.forEach(tuple -> {
                DinnerTypeDO DinnerTypeDO = dinnerTypeDOMap.get(tuple.getDinnerTypeId());
                if (DinnerTypeDO != null) {
                    getRecordMsg(updateRecord, tuple, DinnerTypeDO, DELETE_RECORD_TITLE);
                }
            });
        }
        return deleteResult;
    }


    /**
     * 拼接本次编辑操作 变更信息
     *
     * @param msg
     * @param tuple
     * @param dinnerTypeDO
     * @param operateMsg
     */
    public void getRecordMsg(StringBuffer msg, DinnerDO tuple, DinnerTypeDO dinnerTypeDO, String operateMsg) {
        if (dinnerTypeDO != null) {
            dinnerTypeDO.setDefaultStartTime(tuple.getStartTime());
            dinnerTypeDO.setDefaultEndTime(tuple.getEndTime());
            DinnerTypeView dinnerTypeView = DinnerTypeView.intDefaultTimeToStr(dinnerTypeDO);
            msg.append(operateMsg)
                    .append(dinnerTypeDO.getDinnerTypeName())
                    .append("】，时段: ")
                    .append(dinnerTypeView.getStrDefaultStartTime())
                    .append("-")
                    .append(dinnerTypeView.getStrDefaultEndTime());
        }
    }

    /**
     * 将餐别类型实体类集合转换为 餐别门店关系集合
     *
     * @param loginUser               登录用户
     * @param canteenDTO              门店
     * @param newDinnerTypeEntityList 餐别类型实体类集合
     * @return List<DinnerEntity> 餐别门店关系集合
     */
    public List<DinnerDO> dinnerTypeDOListToDinnerTypeDOList(UserDO loginUser, CanteenDTO canteenDTO, List<DinnerTypeDO> newDinnerTypeEntityList) {
        return newDinnerTypeEntityList.stream().map(tuple -> getDinnerDO(loginUser, canteenDTO, tuple)).collect(Collectors.toList());
    }

    /**
     * 前端餐别参数集合转换为餐别类型实体类集合
     *
     * @param dinnerTypeFormList 餐别参数集合
     * @return
     */
    public List<DinnerTypeDO> dinnerTypeFormListToDinnerTypeDOList(List<DinnerTypeForm> dinnerTypeFormList) {
        return dinnerTypeFormList.stream().map(DinnerTypeForm::strDefaultTimeToInt).collect(Collectors.toList());
    }

    public DinnerDO getDinnerDO(UserDO loginUser, CanteenDTO canteenDTO, DinnerTypeDO dinnerTypeEntity) {
        DinnerDO dinnerEntity = new DinnerDO();
        dinnerEntity.setDinnerTypeId(dinnerTypeEntity.getDinnerTypeId());
        dinnerEntity.setCanteenId(canteenDTO.getCanteenId());
        dinnerEntity.setStartTime(dinnerTypeEntity.getDefaultStartTime());
        dinnerEntity.setEndTime(dinnerTypeEntity.getDefaultEndTime());
        dinnerEntity.setDeleteStatus(CommonIsEnum.TRUE.getVal());
        dinnerEntity.setCreateUserName(loginUser.getUserName());
        dinnerEntity.setCreateUserId(loginUser.getUserId());
        dinnerEntity.setCreateTime(LocalDateTime.now());
        dinnerEntity.setUpdateUserId(loginUser.getUserId());
        dinnerEntity.setUpdateUserName(loginUser.getUserName());
        dinnerEntity.setUpdateTime(LocalDateTime.now());
        return dinnerEntity;
    }

    public static HashMap<Integer, DinnerTypeDO> listToMap(List<DinnerTypeDO> list) {
        HashMap<Integer, DinnerTypeDO> dinnerTypeEntityMap = new HashMap<>();
        list.forEach(tuple -> {
            dinnerTypeEntityMap.put(tuple.getDinnerTypeId(), tuple);
        });
        return dinnerTypeEntityMap;
    }
}

