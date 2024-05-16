package com.xxsword.xitem.admin.service.exam.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.exam.entity.Paper;
import com.xxsword.xitem.admin.domain.exam.entity.Question;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionOption;
import com.xxsword.xitem.admin.domain.system.entity.Dict;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.QuestionMapper;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.exam.QuestionOptionService;
import com.xxsword.xitem.admin.service.exam.QuestionService;
import com.xxsword.xitem.admin.service.system.DictService;
import com.xxsword.xitem.admin.utils.ExcelUtils;
import com.xxsword.xitem.admin.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Autowired
    private DictService dictService;
    @Autowired
    private QuestionOptionService questionOptionService;

    @Override
    public List<Question> setQuestionQclass(List<Question> list) {
        Map<String, Dict> mapDict = dictService.mapDictByType(Constant.DICT_TYPE_QCLASS);
        for (Question item : list) {
            item.setQclassname(mapDict.get(item.getQclass()).getName());
        }
        return list;
    }

    @Override
    @Transactional
    public RestResult excelQuestion(String path, UserInfo userInfo) {
        Workbook wb = ExcelUtils.readExcle(new File(path));
        Sheet sheet = wb.getSheetAt(0);// 读取第一页
        List<Question> listQ = new ArrayList<>();
        List<String> listError = new ArrayList<>();
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            String qclass = ExcelUtils.getString(row.getCell(0));// 问题分类(字典)
            Dict dict = dictService.getOne(new LambdaQueryWrapper<Dict>().eq(Dict::getType, Constant.DICT_TYPE_QCLASS).eq(Dict::getName, qclass));
            String qtype = ExcelUtils.getString(row.getCell(1));// 问题类型（0-是非 1-单选 2-多选）
            String qscore = ExcelUtils.getString(row.getCell(2));// 分值
            String qtitle = ExcelUtils.getString(row.getCell(3));// 问题描述
            String qoption = ExcelUtils.getString(row.getCell(4));// 问题选项
            String qanswer = ExcelUtils.getString(row.getCell(5));// 正确答案
            if (dict == null) {
                listError.add("第" + (rowNum + 1) + "行，题目分类异常");
            }
            if (StringUtils.isBlank(qclass)) {
                listError.add("第" + (rowNum + 1) + "行，题目分类不可为空");
            }
            if (StringUtils.isBlank(qtype)) {
                listError.add("第" + (rowNum + 1) + "行，题目类型不可为空");
            }
            if (StringUtils.isBlank(qtitle)) {
                listError.add("第" + (rowNum + 1) + "行，题目描述不可为空");
            }
            if (StringUtils.isBlank(qscore)) {
                listError.add("第" + (rowNum + 1) + "行，分值不可为空");
            } else {
                if (!Utils.isStringCanBeConvertedToNumber(qscore)) {
                    listError.add("第" + (rowNum + 1) + "行，分值异常");
                }
            }

            Integer qtypeInfo = getQtype(qtype);
            if (qtypeInfo == null) {
                listError.add("第" + (rowNum + 1) + "行，问题类型异常");
            }
            boolean have = haveQuestionTitle(qtitle);// 先去题库找
            if (have) {
                listError.add("第" + (rowNum + 1) + "行，该题目标题已存在");
            } else {
                List<QuestionOption> listQP = new ArrayList<>();// 题目选项
                Question question = new Question();// 题目
                question.setBaseInfo(userInfo);
                question.setTitle(qtitle);
                question.setQclass(dict.getId());
                question.setQtype(qtypeInfo);
                if (qtypeInfo == 0) {// 是非题
                    QuestionOption questionOptionA = new QuestionOption();
                    questionOptionA.setBaseInfo(userInfo);
                    questionOptionA.setTitle("正确");
                    questionOptionA.setOptionright(0);

                    QuestionOption questionOptionB = new QuestionOption();
                    questionOptionB.setBaseInfo(userInfo);
                    questionOptionB.setTitle("错误");
                    questionOptionB.setOptionright(0);

                    if (StringUtils.isNotBlank(qanswer)) {
                        if ("正确".equals(qanswer)) {
                            questionOptionA.setOptionright(1);
                        } else {
                            questionOptionB.setOptionright(1);
                        }
                    }

                    listQP.add(questionOptionA);
                    listQP.add(questionOptionB);
                }
                if (qtypeInfo == 1 || qtypeInfo == 2) {// 单选和多选
                    if (StringUtils.isNotBlank(qoption)) {
                        String[] qoptions = qoption.split("\\|");
                        for (String option : qoptions) {
                            option = Utils.getString(option);
                            QuestionOption questionOption = new QuestionOption();
                            questionOption.setBaseInfo(userInfo);
                            questionOption.setTitle(option);
                            questionOption.setOptionright(0);

                            String[] qanswers = qanswer.split("\\|");
                            for (String aw : qanswers) {
                                aw = Utils.getString(aw);
                                if (option.equals(aw)) {
                                    questionOption.setOptionright(1);
                                    break;
                                }
                            }
                            listQP.add(questionOption);
                        }
                    }
                    questionOptionService.saveBatch(listQP);
                }
                listQ.add(question);
            }
        }
        if (listError.size() != 0) {// 有异常，则不做保存逻辑，直接返回错误信息
            return RestResult.Fail(listError);
        }
        saveOrUpdateBatch(listQ);// 题目保存
        return RestResult.OK();
    }

    @Override
    @Transactional
    public void saveQuestionAndOption(UserInfo userInfo, Question question, String optionJson) {
        question.setBaseInfo(userInfo);
        saveOrUpdate(question);
        List<QuestionOption> questionOptionList = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(optionJson);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject option = jsonArray.getJSONObject(i);
            String optionId = option.getString("optionId");
            String optionTitle = option.getString("optionTitle");
            Integer optionRight = option.getInteger("optionRight");
            QuestionOption questionOption = new QuestionOption();
            if (StringUtils.isNotBlank(optionId)) {
                questionOption = questionOptionService.getById(optionId);
            }
            questionOption.setBaseInfo(userInfo);
            questionOption.setQid(question.getId());
            questionOption.setTitle(optionTitle);
            questionOption.setOptionright(optionRight);
            questionOptionList.add(questionOption);
        }
        questionOptionService.saveOrUpdateBatch(questionOptionList);
        Set<String> ids = questionOptionList.stream().map(QuestionOption::getId).collect(Collectors.toSet());
        clearOption(ids, question.getId());
    }

    /**
     * 清除被删除的选项
     */
    private void clearOption(Set<String> ids, String questionId) {
        List<QuestionOption> list = questionOptionService.questionOptionListByQid(questionId);
        Set<String> qpIds = list.stream().map(QuestionOption::getId).collect(Collectors.toSet());
        List<String> delIds = new ArrayList<>();
        for (String item : qpIds) {
            if (ids.contains(item)) {
                continue;
            }
            delIds.add(item);
        }
        List<QuestionOption> listUp = new ArrayList<>();
        for (String id : delIds) {
            QuestionOption qo = new QuestionOption();
            qo.setId(id);
            qo.setStatus(0);
            listUp.add(qo);
        }
        questionOptionService.updateBatchById(listUp);
    }

    private Integer getQtype(String qtype) {
        if ("是非题".equals(qtype)) {
            return 0;
        }
        if ("单选题".equals(qtype)) {
            return 1;
        }
        if ("多选题".equals(qtype)) {
            return 2;
        }
        return null;
    }

    /**
     * 该标题的题目，是否已经存在
     *
     * @param title
     * @return
     */
    private boolean haveQuestionTitle(String title) {
        if (StringUtils.isBlank(title)) {
            return true;
        }
        LambdaQueryWrapper<Question> q = Wrappers.lambdaQuery();
        q.eq(Question::getStatus, 1);
        q.eq(Question::getTitle, title);
        q.orderByDesc(Question::getCdate, Question::getId);
        long count = count(q);
        return count != 0;
    }

    @Override
    public void upLastInfo(UserInfo doUserInfo, String ids) {
        String[] idsS = ids.split(",");
        List<Question> listUp = new ArrayList<>();
        for (String id : idsS) {
            Question itemUp = new Question();
            itemUp.setId(id);
            itemUp.setBaseInfo(doUserInfo);
            listUp.add(itemUp);
        }
        updateBatchById(listUp);
    }

    @Override
    public void delQuestionByIds(String ids) {
        String[] idsS = ids.split(",");
        List<Question> listUp = new ArrayList<>();
        for (String id : idsS) {
            Question question = new Question();
            question.setId(id);
            question.setStatus(0);
            listUp.add(question);
        }
        updateBatchById(listUp);
    }
}
