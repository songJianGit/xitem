<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>
        .panel-heading {
            text-align: center;
        }

        .q-item-title {
            margin-top: 15px;
            text-indent: 2em;
            font-size: 16px;
            font-weight: 600;
            margin-bottom: 15px;
        }

        .q-item-op-box {
            display: flex;
            flex-direction: column;
            margin-top: 7px;
            margin-bottom: 7px;
            margin-left: 7px;
        }

        .q-item-op {
            /*display: flex;*/
            /*align-items: flex-start;*/
            margin-top: 10px;
            margin-bottom: 10px;
            background: white;
            box-shadow: 0 1px 1px 0 rgb(239 243 255), 0 3px 7px 0 rgba(0, 0, 0, 0.1);
            padding: 15px;
        }

        .q-item-op span {
            margin-left: 7px;
            font-size: 16px;
            color: #000;
            line-height: 24px;
        }

        .q-item-op input {
            /*明明对齐了，但是手机上就是差点距离，这边微调一下位置*/
            margin-top: 2px;
            width: 20px;
            height: 20px;
        }

        .footer-btn {
            text-align: center;
        }

        .q-item-type {
            display: flex;
            justify-content: space-between;
            margin-bottom: 5px;
            color: #1a4883;
            font-size: 16px;
        }

        .float-card {
            background: #eff3ff;
            width: 95%;
            margin: 0 auto;
            margin-top: 10px;
        }

        .q-item-type-num-sum-active {
            color: #2566b8;
            font-size: 18px;
        }

        .q-item-type-num-sum {
            font-size: 12px;
            color: #2566b8;
        }

    </style>
</head>
<body>
<div class="card">

    <div class="panel-heading"
         style="background-image: none;background-color: #2566b8;color: white;border-color: #ffffff;">${exam.name!}</div>

    <div class="panel-body float-card">
        <button type="button" class="btn btn-sm btn-primary" onclick="onSub()"
                style="float: right;background-color: #2566b8;border: #2566b8;">交卷
        </button>
        <div>${username!}</div>
        <div>${dataDay!}</div>
        <div style="margin-top: 13px">考试结束时间：${endTime!}</div>
    </div>

    <div class="panel-body float-card">
        <div class="q-item-type">
            <div class="q-item-type-name">多选</div>
            <div class="q-item-type-num"><span class="q-item-type-num-sum-active">0</span>/<span
                        class="q-item-type-num-sum">0</span></div>
        </div>

        <div style="padding: 1px 0;background-color: #eeeeee"></div>
        <div class="q-item-title"></div>
        <div class="q-item-op-box">
        </div>
    </div>
    <div class="panel-footer row float-card" style="overflow: hidden;">
        <div class="footer-btn col-xs-5 col-md-5 col-sm-5 btn btn-sm btn-primary"
             style="background-color: #2566b8;border: #2566b8;" id="prevBtn">上一题
        </div>
        <div class="footer-btn col-xs-5 col-md-5 col-sm-5 btn btn-sm btn-primary"
             style="background-color: #2566b8;border: #2566b8;float:right" id="nextBtn">下一题
        </div>
    </div>
</div>
<#include "../commons/js.ftl"/>
<script type="text/javascript">
    // TODO 页面调整===========================
    let pageNum = 1;// 当前页
    let userPaperQuestions = '${userPaperQuestionIds!}'.split(",");// 当前试卷题目集合
    let action_question_type = '';// 当前题目类型
    let action_user_paper_question_id = '';// 当前题id

    $("#prevBtn").click(function () {
        if (pageNum > 1) {
            pageNum--;
            getQuestion();
        } else {
            layer.msg("已是第一题");
        }
    });
    $("#nextBtn").click(function () {
        if (pageNum < userPaperQuestions.length) {
            pageNum++;
            getQuestion();
        } else {
            layer.msg("已是最后一题");
        }
    });

    // msgFlag 0-抑制消息提示 1-不限制
    function getQuestion(msgFlag) {
        if (isBlank(msgFlag)) {
            msgFlag = 1;
        }
        if (isBlank(action_user_paper_question_id)) {
            action_user_paper_question_id = userPaperQuestions[0];
        }
        let indexLoad = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/getQuestion",
            cache: false,// 不缓存
            data: {
                "userPaperId": '${userPaperId!}',
                "nextQid": userPaperQuestions[pageNum - 1]
            },
            success: function (data) {
                layer.close(indexLoad);
                if (data.result) {
                    let question = data.data;
                    action_question_type = question.qtype;
                    action_user_paper_question_id = data.data.id;
                    $(".q-item-title").html(question.title);
                    let questionOptionList = question.questionOptionList;
                    $(".q-item-op-box").html(getQuestionOptionHtm(questionOptionList));// 选项
                    $(".q-item-type-num-sum-active").text(pageNum);
                } else {
                    layer.msg(data.msg);
                }
            }
        });
    }

    function setQItemTypeName(type) {
        // 0-是非 1-单选 2-多选
        if (type == 0) {
            $(".q-item-type-name").text('【是非题】');
        }
        if (type == 1) {
            $(".q-item-type-name").text('【单选题】');
        }
        if (type == 2) {
            $(".q-item-type-name").text('【多选题】');
        }
    }

    function setAnswer(userPaperQuestion) {
        let answer = userPaperQuestion.answer;
        if (isNotBlank(answer)) {
            if (action_question_type == 0 || action_question_type == 1) {
                $(".q-item-op-i" + answer).prop("checked", true);
            }
            if (action_question_type == 2) {
                let answerS = answer.split(",");
                for (let i = 0; i < answerS.length; i++) {
                    $(".q-item-op-i" + answerS[i]).prop("checked", true);
                }
            }
        }
    }

    function getAnswer() {
        let inputName = '';
        let vals;
        if (action_question_type == 0 || action_question_type == 1) {
            inputName = 'radio-input';
            vals = $("input[name='" + inputName + "']:checked").val();
        }
        if (action_question_type == 2) {
            inputName = 'checkbox-input';
            vals = $("input[name='" + inputName + "']:checked").map(function () {
                return this.value;
            }).get();
        }
        if (isNotBlank(vals)) {
            if (action_question_type == 2) {
                return vals.join(",");
            } else {
                return vals;
            }
        } else {
            return "";
        }
    }

    function getQuestionOptionHtm(question) {
        let htm = '';
        let type = question.qtype;// 0-是非 1-单选 2-多选
        let inputType = '';
        if (type == 0 || type == 0) {
            inputType = 'radio';
        }
        if (type == 2) {
            inputType = 'checkbox';
        }
        for (let i = 0; i < question.length; i++) {
            htm += optionHtm(question[i].title, i, inputType);
        }
        return htm;
    }

    function optionHtm(lable, val, type) {
        let htm = '';
        if (isNotBlank(lable)) {
            htm += '<div class="q-item-op">';
            htm += '<input style="float: left;width: 8%" class="q-item-op-i' + val + '" name="' + type + '-input" value="' + val + '" type="' + type + '"/>';
            htm += '<div style="float: left;width: 90%;margin-left: 1%">' + lable + '</div>';
            htm += '</div>';
        }
        return htm;
    }

    $(function () {
        getQuestion();
        $(".q-item-type-num-sum").text(userPaperQuestions.length);
        subTime();
    });

    function onSub() {
        let indexLoadSub = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        getQuestion(0);
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/checkBlankNum",
            cache: false,// 不缓存
            async: false,// 同步
            data: {
                "userPaperId": '${userPaperId!}'
            },
            success: function (data) {
                layer.close(indexLoadSub);
                let infos = data.data;
                let msg = "";
                if (infos.length != 0) {
                    msg = '第' + infos.join("，") + "题未作答，"
                }
                layer.confirm(msg + '确定提交？', {
                    title: '提示',
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    onSubFUN();
                }, function () {
                    console.log("取消");
                });
            }
        });
    }

    function onSubFUN() {
        // 提交当前题目；
        // 题目的答案提交通过上一题下一题按钮触发，但是到达最后一页的时候，用户点不了下一题，只能点上一题，如果其没有点上一题，直接提交了
        // 就没办法触发答案的提交
        // 所以这边直接对当前题目再进行一次提交
        getQuestion(0);
        let indexLoadSub = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/examPageSubmit",
            cache: false,// 不缓存
            async: false,// 同步
            data: {
                "userPaperQuestionId": action_user_paper_question_id
            },
            success: function (data) {
                // console.log(data);
                layer.close(indexLoadSub);
                if (data.result) {
                    window.location.href = "${ctx.contextPath}/pc/exam/examSubmitOk?userPaperId=" + data.data;
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    function adminFlag() {
        layer.msg("预览模式，无需提交");
    }

    function subTime() {
        let t = setInterval(function () {
            console.log("xxx", compareDateStringWithNow('${endTime!}'))
            if (compareDateStringWithNow('${endTime!}')) {
                clearInterval(t);
                layer.msg("考试时间已到，系统自动提交");
                setTimeout(function () {
                    onSubFUN();
                }, 1000)
            }
        }, 1000);
    }

    function compareDateStringWithNow(dateString) {
        let inputDate = new Date(dateString);
        let currentDate = new Date();

        if (inputDate > currentDate) {
            // return '输入的时间大于当前时间';
            return false;
        } else if (inputDate < currentDate) {
            // return '输入的时间小于当前时间';
            return true;
        } else {
            // return '输入的时间等于当前时间';
            return true;
        }
    }

</script>
</body>
</html>
