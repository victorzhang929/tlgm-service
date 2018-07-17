package com.cetiti.tlgm.service.common.model.performance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;

import com.cetiti.tlgm.service.common.CommonUtil;
import com.cetiti.tlgm.service.common.mapper.GridCommunityTownshipMapper;
import lombok.Data;

import static com.cetiti.tlgm.service.common.constant.GridCommunityTownshipConstant.COMMUNITY_ID_LENGTH;
import static com.cetiti.tlgm.service.common.constant.GridCommunityTownshipConstant.TOWNSHIP_ID_LENGTH;

/**
 * 三级网格模型
 * @author zhangwei
 * @email zhangwei@cetiti.com
 * @date 2018-06-27 15:38:40
 */
@Data
public class GridCommunityTownship implements Serializable {
    private BigDecimal gridId;
    private String gridName;
    private BigDecimal communityId;
    private String communityName;
    private BigDecimal townshipId;
    private String townshipName;

    public void setGridId(BigDecimal gridId) {
        this.gridId = gridId;
        if (null == this.communityId) {
            this.communityId = CommonUtil.getSubDecimal(COMMUNITY_ID_LENGTH, gridId);
        }
        if (null == this.townshipId) {
            this.townshipId = CommonUtil.getSubDecimal(TOWNSHIP_ID_LENGTH, gridId);
        }
    }

    public void setCommunityId(BigDecimal communityId) {
        this.communityId = communityId;
        if (null == this.townshipId) {
            this.townshipId = CommonUtil.getSubDecimal(TOWNSHIP_ID_LENGTH, communityId);
        }
    }

    public void fixAllName(GridCommunityTownshipMapper gridCommunityTownshipMapper) throws SQLException {
        this.setGridName(gridCommunityTownshipMapper.getNameById(this.gridId));
        this.setCommunityName(gridCommunityTownshipMapper.getNameById(this.communityId));
        this.setTownshipName(gridCommunityTownshipMapper.getNameById(this.townshipId));
    }
}
