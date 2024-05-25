package com.xxsword.xitem.admin.service.exam.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxsword.xitem.admin.constant.Constant;
import com.xxsword.xitem.admin.domain.exam.convert.QuestionConvert;
import com.xxsword.xitem.admin.domain.exam.convert.QuestionOptionConvert;
import com.xxsword.xitem.admin.domain.exam.entity.Question;
import com.xxsword.xitem.admin.domain.exam.entity.QuestionOption;
import com.xxsword.xitem.admin.domain.exam.entity.UserPaperQuestion;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionExcelVO;
import com.xxsword.xitem.admin.domain.exam.vo.QuestionVO;
import com.xxsword.xitem.admin.domain.system.entity.Dict;
import com.xxsword.xitem.admin.domain.system.entity.UserInfo;
import com.xxsword.xitem.admin.mapper.exam.QuestionMapper;
import com.xxsword.xitem.admin.model.RestResult;
import com.xxsword.xitem.admin.service.exam.QuestionOptionService;
import com.xxsword.xitem.admin.service.exam.QuestionService;
import com.xxsword.xitem.admin.service.system.DictService;
import com.xxsword.xitem.admin.utils.ExamUtil;
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
        List<String> listError = new ArrayList<>();
        Set<String> titleSet = new HashSet<>();
        List<QuestionExcelVO> questionExcelVOS = new ArrayList<>();
        for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }
            String qclass = ExcelUtils.getString(row.getCell(0));// 问题分类(字典)
            Dict dict = dictService.getOne(new LambdaQueryWrapper<Dict>().eq(Dict::getType, Constant.DICT_TYPE_QCLASS).eq(Dict::getName, qclass));
            String qtype = ExcelUtils.getString(row.getCell(1));// 问题类型（0-是非 1-单选 2-多选）
            String qtitle = ExcelUtils.getString(row.getCell(2));// 问题描述
            String qoption = ExcelUtils.getString(row.getCell(3));// 问题选项
            String qanswer = ExcelUtils.getString(row.getCell(4));// 正确答案
            if (dict == null) {
                listError.add("第" + (rowNum + 1) + "行，题目分类异常");
            }
            if (StringUtils.isBlank(qclass)) {
                listError.add("第" + (rowNum + 1) + "行，题目分类不可为空");
            }
            if (StringUtils.isBlank(qtype)) {
                listError.add("第" + (rowNum + 1) + "行，题目类型不可为空");
            }
            int qtypeInfo = getQtype(qtype);
            if (qtypeInfo == -1) {
                listError.add("第" + (rowNum + 1) + "行，题目类型异常");
            }
            if (StringUtils.isBlank(qtitle)) {
                listError.add("第" + (rowNum + 1) + "行，题目描述（题干）不可为空");
            }
            if (StringUtils.isBlank(qoption) && qtypeInfo != 0) {
                listError.add("第" + (rowNum + 1) + "行，题目选项不可为空");
            }
            if (StringUtils.isBlank(qanswer)) {
                listError.add("第" + (rowNum + 1) + "行，正确答案不可为空");
            }
            if (!checkAnswers(qoption, qanswer) && qtypeInfo != 0) {
                listError.add("第" + (rowNum + 1) + "行，正确答案不在选项中");
            }
            if (!checkRadioAnswers(qanswer) && qtypeInfo == 1) {
                listError.add("第" + (rowNum + 1) + "行，单选题的正确答案只能有一个");
            }
            if (!checkYNAnswers(qanswer) && qtypeInfo == 0) {
                listError.add("第" + (rowNum + 1) + "行，是非题的正确答案填写异常");
            }
            if (titleSet.contains(qtitle)) {
                listError.add("第" + (rowNum + 1) + "行，题目描述（题干）出现多次");
            } else {
                titleSet.add(qtitle);
            }
            boolean have = haveQuestionTitle(qtitle);
            if (have) {
                listError.add("第" + (rowNum + 1) + "行，该题目已存在于系统中");
            } else {
                if (listError.size() != 0) {
                    continue;
                }
                QuestionExcelVO questionExcelVO = new QuestionExcelVO();
                questionExcelVO.setTitle(qtitle);
                questionExcelVO.setQclass(dict.getId());
                questionExcelVO.setQtype(qtypeInfo);
                questionExcelVO.setQoption(qoption);
                questionExcelVO.setQanswer(qanswer);
                questionExcelVOS.add(questionExcelVO);
            }
        }
        if (listError.size() != 0) {
            return RestResult.Fail(listError);// 只要有异常，则不做保存逻辑
        }
        saveByQuestionVO(userInfo, questionExcelVOS);
        return RestResult.OK();
    }

    /**
     * 检查选项是否包含所有答案
     *
     * @param options
     * @param answers
     * @return
     */
    private boolean checkAnswers(String options, String answers) {
        String[] qoptions = options.split("\\|");
        String[] qanswers = answers.split("\\|");
        Set<String> setAll = new HashSet<>(Arrays.asList(qoptions));
        for (String item : qanswers) {
            if (!setAll.contains(item)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查单选的答案是否唯一
     *
     * @param answers
     * @return
     */
    private boolean checkRadioAnswers(String answers) {
        String[] qanswers = answers.split("\\|");
        return qanswers.length == 1;
    }

    /**
     * 检查是非题的答案是否正确
     *
     * @param answers
     * @return
     */
    private boolean checkYNAnswers(String answers) {
        if ("正确".equals(answers) || "错误".equals(answers)) {
            return true;
        }
        return false;
    }

    private void saveByQuestionVO(UserInfo userInfo, List<QuestionExcelVO> list) {
        for (QuestionExcelVO item : list) {
            int qtypeInfo = item.getQtype();
            List<QuestionOption> listQP = new ArrayList<>();// 题目选项
            Question question = new Question();// 题目
            question.setBaseInfo(userInfo);
            question.setTitle(item.getTitle().trim());
            question.setQclass(item.getQclass());
            question.setQtype(qtypeInfo);
            saveOrUpdate(question);// 题目保存
            if (qtypeInfo == 0) {// 是非题
                QuestionOption questionOptionA = new QuestionOption();
                questionOptionA.setBaseInfo(userInfo);
                questionOptionA.setTitle("正确");
                questionOptionA.setOptionright(0);
                questionOptionA.setQid(question.getId());

                QuestionOption questionOptionB = new QuestionOption();
                questionOptionB.setBaseInfo(userInfo);
                questionOptionB.setTitle("错误");
                questionOptionB.setOptionright(0);
                questionOptionB.setQid(question.getId());

                if (StringUtils.isNotBlank(item.getQanswer())) {
                    if ("正确".equals(item.getQanswer())) {
                        questionOptionA.setOptionright(1);
                    } else {
                        questionOptionB.setOptionright(1);
                    }
                }

                listQP.add(questionOptionA);
                listQP.add(questionOptionB);
            }
            if (qtypeInfo == 1 || qtypeInfo == 2) {// 单选和多选
                if (StringUtils.isNotBlank(item.getQoption())) {
                    String[] qoptions = item.getQoption().split("\\|");
                    for (String option : qoptions) {
                        option = Utils.getString(option);
                        QuestionOption questionOption = new QuestionOption();
                        questionOption.setBaseInfo(userInfo);
                        questionOption.setTitle(option.trim());
                        questionOption.setOptionright(0);
                        questionOption.setQid(question.getId());

                        String[] qanswers = item.getQanswer().split("\\|");
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
            }
            questionOptionService.saveBatch(listQP);
        }
    }

    @Override
    @Transactional
    public void saveQuestionAndOption(UserInfo userInfo, Question question, String optionJson) {
        question.setBaseInfo(userInfo);
        saveOrUpdate(question);
        List<QuestionOption> questionOptionList = new ArrayList<>();
        JSONArray jsonArray = JSONArray.parseArray(optionJson);
        int optionSize = jsonArray.size();
        if (optionSize > 12) {
            optionSize = 12;// 最多12个，因为12*19+11=240刚好在255以内，存储答案的字段能存下
        }
        for (int i = 0; i < optionSize; i++) {
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

    private int getQtype(String qtype) {
        if ("是非题".equals(qtype)) {
            return 0;
        }
        if ("单选题".equals(qtype)) {
            return 1;
        }
        if ("多选题".equals(qtype)) {
            return 2;
        }
        return -1;
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

    @Override
    public QuestionVO getQuestionVO(UserPaperQuestion userPaperQuestion, boolean setOption, boolean setRight, boolean setABC) {
        Question question = getById(userPaperQuestion.getQid());
        QuestionVO questionVO = QuestionConvert.INSTANCE.toQuestionVO(question);
        questionVO.setScore(userPaperQuestion.getQscore());
        questionVO.setUserpaperquestionid(userPaperQuestion.getId());
        questionVO.setAnswer(userPaperQuestion.getAnswer());
        if (setOption) {
            List<QuestionOption> questionOptionList = questionOptionService.questionOptionListByQid(questionVO.getId());
            if (setRight) {
                StringBuilder answer = new StringBuilder();
                for (int i = 0; i < questionOptionList.size(); i++) {
                    QuestionOption option = questionOptionList.get(i);
                    if (option.getOptionright().equals(1)) {
                        answer.append(ExamUtil.convertNumberToLetter(i));
                    }
                }
                questionVO.setAnswer(answer.toString());
            } else {
                for (QuestionOption option : questionOptionList) {
                    option.setOptionright(null);// 不显示答案
                }
            }
            if (setABC) {
                for (int i = 0; i < questionOptionList.size(); i++) {
                    QuestionOption option = questionOptionList.get(i);
                    option.setTitle(ExamUtil.convertNumberToLetter(i) + ".&nbsp;" + option.getTitle());
                }
            }
            questionVO.setQuestionOptionList(QuestionOptionConvert.INSTANCE.toQuestionOptionVO(questionOptionList));
        }
        return questionVO;
    }

    @Override
    public List<QuestionVO> getQuestionVO(List<UserPaperQuestion> userPaperQuestionList, boolean setOption, boolean setRight, boolean setABC) {
        List<QuestionVO> questionList = new ArrayList<>();
        for (UserPaperQuestion userPaperQuestion : userPaperQuestionList) {
            QuestionVO questionVO = this.getQuestionVO(userPaperQuestion, setOption, setRight, setABC);
            questionList.add(questionVO);
        }
        return questionList;
    }

}
