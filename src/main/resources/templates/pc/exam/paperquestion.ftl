<!DOCTYPE html>
<html lang="zh">
<head>
    <#include "../commons/head.ftl"/>
    <style>

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

        .q-item-type {
            display: flex;
            justify-content: space-between;
            margin-bottom: 5px;
            color: #1a4883;
            font-size: 16px;
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
    <div class="card-header">${exam.title!}</div>
    <div class="card-body row">
        <div class="col-6">
            <div>考试剩余时间：<span class="countdownInfo">00:00:00</span></div>
        </div>
        <div class="col-6">
            <button type="button" class="btn btn-primary" onclick="onSub()">交卷</button>
        </div>
    </div>
    <div class="card-body">
        <div class="q-item-type">
            <div class="q-item-type-name">问题类型</div>
            <div class="q-item-type-num">
                <span class="q-item-type-num-sum-active">0</span>/
                <span class="q-item-type-num-sum">0</span>
            </div>
        </div>
        <div style="padding: 1px 0;background-color: #eeeeee"></div>
        <div class="q-item-title"></div>
        <div class="q-item-op-box">
        </div>
    </div>
    <div class="card-footer row">
        <button type="button" class="col-6 btn btn-sm btn-primary" id="prevBtn">上一题</button>
        <button type="button" class="col-6 btn btn-sm btn-primary" id="nextBtn">下一题</button>
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

    function getQuestion() {
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
                    $(".q-item-op-box").html(getQuestionOptionHtm(question));// 选项
                    $(".q-item-type-num-sum-active").text(pageNum);
                    setQItemTypeName(question.qtype);
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
        let questionOptionList = question.questionOptionList;
        let type = question.qtype;// 0-是非 1-单选 2-多选
        let inputType = '';
        if (type == 0 || type == 1) {
            inputType = 'radio';
        }
        if (type == 2) {
            inputType = 'checkbox';
        }
        let htm = '';
        for (let i = 0; i < questionOptionList.length; i++) {
            htm += optionHtm(questionOptionList[i].title, questionOptionList[i].id, inputType);
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
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/checkBlankNum",
            cache: false,// 不缓存
            data: {
                "userPaperId": '${userPaperId!}'
            },
            success: function (data) {
                layer.close(indexLoadSub);
                let infos = data.data;
                let msg = "";
                if (infos.length != 0) {
                    msg = '第' + infos.join("，") + "题未作答"
                }
                layer.confirm(msg + '，确定提交？', {
                    title: '提示',
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    onSubFUN();
                }, function () {
                    // 取消
                });
            }
        });
    }

    function onSubFUN() {
        let indexLoadSub = layer.load(1, {// 遮罩层
            shade: [0.5, '#fff']
        });
        $.ajax({
            url: "${ctx.contextPath}/pc/exam/examPageSubmit",
            cache: false,// 不缓存
            data: {
                "userPaperId": '${userPaperId!}'
            },
            success: function (data) {
                layer.close(indexLoadSub);
                if (data.result) {
                    window.location.href = "${ctx.contextPath}/pc/exam/examSubmitOk?userPaperId=${userPaperId!}";
                } else {
                    alert(data.msg);
                }
            }
        });
    }

    function subTime() {
        let time = setInterval(function () {
            countdownInfo('${endTime!}');
            if (compareDateStringWithNow('${endTime!}')) {
                clearInterval(time);
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

    /**
     * 结束时间和当前时间的时间差显示
     * @param endStr 考试结束时间
     */
    function countdownInfo(endStr) {
        let endDate = new Date(endStr);
        // 计算时间差，单位为毫秒
        let diffMs = Math.abs(endDate.getTime() - new Date().getTime());
        // 将毫秒转换为小时、分钟、秒
        let hours = Math.floor(diffMs / 3600000);
        let minutes = Math.floor((diffMs % 3600000) / 60000);
        let seconds = Math.floor((diffMs % 60000) / 1000);
        // 格式化输出为HH:mm:ss
        let formattedDiff = hours.toString().padStart(2, '0') + ":" + minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0');
        // console.log(formattedDiff); // 输出相差的时间，格式为HH:mm:ss
        $('.countdownInfo').text(formattedDiff);
    }

</script>
</body>
</html>
