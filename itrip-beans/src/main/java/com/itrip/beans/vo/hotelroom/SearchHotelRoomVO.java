package com.itrip.beans.vo.hotelroom;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 前端输入-查询酒店房间搜索条件VO
 * Created by XX on 17-5-11.
 */
@Data
@ApiModel(value = "SearchHotelRoomVO",description = "查询酒店房间的VO")
public class SearchHotelRoomVO implements Serializable{

    @ApiModelProperty("[必填] 酒店ID")
    private Long hotelId;
    @ApiModelProperty("[非必填] 是否预订(0:未预定 1:预订)")
    private Integer isBook;
    @ApiModelProperty("[非必填] 是否有早餐(0:没有 1:有)")
    private Integer isHavingBreakfast;
    @ApiModelProperty("[非必填] 是否及时响应(0:不及时 1:及时)")
    private Integer isTimelyResponse;
    @ApiModelProperty("[非必填] 床型ID")
    private Long roomBedTypeId;
    @ApiModelProperty("[必填] 入职日期")
    private Date startDate;
    @ApiModelProperty("[必填] 退房日期")
    private Date endDate;
    @ApiModelProperty("[非必填] 是否可取消(0:不可以 1:可以)")
    private Integer isCancel;
    @ApiModelProperty("[非必填] 支付类型(1:在线付 2:到店付 3:不限)")
    private Integer payType;
}

