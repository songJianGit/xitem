package com.xxsword.xitem.admin.mapper.exam;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xxsword.xitem.admin.domain.exam.dto.QRSDto;
import com.xxsword.xitem.admin.domain.exam.dto.QuestionDto;
import com.xxsword.xitem.admin.domain.exam.entity.QRS;
import com.xxsword.xitem.admin.domain.exam.vo.QRSVO;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QRSMapper extends BaseMapper<QRS> {

    @Select("<script>" +
            "select " +
            "a.*,b.title,b.qtype " +
            "from t_ex_qrs a left join t_ex_question b on a.qid=b.id " +
            "where 1=1 " +
            "<if test='qrsDto.qrId!=null and qrsDto.qrId!=\"\"'>" +
            "and a.qr_id=#{qrsDto.qrId} " +
            "</if>" +
            "<if test='questionDto.title!=null and questionDto.title!=\"\"'>" +
            "and b.title like concat('%', #{questionDto.title}, '%') " +
            "</if>" +
            "<if test='questionDto.qclass!=null and questionDto.qclass!=\"\"'>" +
            "and b.qclass=#{questionDto.qclass} " +
            "</if>" +
            "<if test='questionDto.qtype!=null'>" +
            "and b.qtype=#{questionDto.qtype} " +
            "</if>" +
            "order by a.seq, a.id " +
            "</script>")
    List<QRSVO> listQRS(QRSDto qrsDto, QuestionDto questionDto);

}
